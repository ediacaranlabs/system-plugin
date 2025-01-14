package br.com.uoutec.community.ediacaran.system.actions;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import br.com.uoutec.application.security.SecurityThread;

@Singleton
public class ActionRegistryImp implements ActionRegistry{

	private ActionsRepository actionsRepository;
	
	private final String id;
	
	private Map<String, ActionExecutorEntry> actionFlow;
	
	private volatile ExecutionTask executionTask;
	
	private ExecutorService executorService;
	
	public ActionRegistryImp() {
		this.id = UUID.randomUUID().toString();
		this.actionFlow = new HashMap<>();
		this.executionTask = null;
		this.executorService = null;
	}
	
	@Override
	public synchronized void setActionsRepository(ActionsRepository actionsRepository) {
		
		this.actionsRepository = actionsRepository;
		this.actionsRepository.setSecurityKey(id);
		
		if(executionTask != null) {
			executionTask.setActionsRepository(actionsRepository);
		}
		
	}
	
	public synchronized void setExecutorService(ExecutorService value) {
		
		this.executorService = value;
		
		if(executionTask != null) {
			executionTask.setExecutorService(value);
		}
		
	}
	
	@Override
	public void registerAction(String actionID, int attemptsBeforeFailure, long timeBeforeTryAgain,
			ChronoUnit unitTtimeBeforeTryAgain, ActionExecutor executor) {
		
		ActionExecutorEntry actionExecutorEntry = new ActionExecutorEntry();
		actionExecutorEntry.setAttemptsBeforeFailure(attemptsBeforeFailure);
		actionExecutorEntry.setDefaultNextAction(null);
		actionExecutorEntry.setExecutor(executor);
		actionExecutorEntry.setId(actionID);
		actionExecutorEntry.setTimeBeforeTryAgain(timeBeforeTryAgain);
		actionExecutorEntry.setUnitTtimeBeforeTryAgain(unitTtimeBeforeTryAgain);
		
		actionFlow.put(actionID, actionExecutorEntry);
	}

	@Override
	public void removeAction(String actionID) {
		actionFlow.remove(actionID);
	}
	
	@Override
	public void addNextAction(String actionID, String nextAction) {
		
		ActionExecutorEntry actionExecutorEntry = actionFlow.get(actionID);
		
		if(actionExecutorEntry == null) {
			throw new NullPointerException(actionID);
		}
		
		List<String> nextActions = actionExecutorEntry.getNextActions();
		nextActions.remove(nextAction);
		nextActions.add(nextAction);
	}

	@Override
	public void removeNextAction(String actionID, String nextAction) {
		
		ActionExecutorEntry actionExecutorEntry = actionFlow.get(actionID);
		
		if(actionExecutorEntry == null) {
			return;
		}
		
		List<String> nextActions = actionExecutorEntry.getNextActions();
		nextActions.remove(nextAction);
		
	}

	@Override
	public void addExceptionAction(String actionID, Class<? extends Throwable> exceptionType, String nextAction) {
		
		ActionExecutorEntry actionExecutorEntry = actionFlow.get(actionID);
		
		if(actionExecutorEntry == null) {
			throw new NullPointerException(actionID);
		}
		
		Map<Class<?>, String> nextActions = actionExecutorEntry.getExceptionAction();
		nextActions.put(exceptionType, nextAction);

	}

	@Override
	public void removeExceptionAction(String actionID, Class<? extends Throwable> exceptionType) {
		
		ActionExecutorEntry actionExecutorEntry = actionFlow.get(actionID);
		
		if(actionExecutorEntry == null) {
			return;
		}
		
		Map<Class<?>, String> nextActions = actionExecutorEntry.getExceptionAction();
		nextActions.remove(exceptionType);
		
	}

	@Override
	public void executeAction(String actionID, ActionExecutorRequest request) {
		
		if(executionTask == null) {
			configureService();
		}
		
		ActionExecutorRequestEntry entry = 
				new ActionExecutorRequestEntry(
						UUID.randomUUID().toString(), 
						request, 
						actionID, 
						LocalDateTime.now(), 
						0
				);
		
		actionsRepository.register(id, entry);
	}

	protected void finalize() throws Throwable {
		try {
			if(executionTask != null) {
				executionTask.setAlive(false);
			}
		}
		finally {
			super.finalize();
		}
	}

	private synchronized void configureService() {
		
		if(executionTask != null) {
			return;
		}

		if(actionsRepository == null) {
			actionsRepository = new MemoryActionsRepository();
			actionsRepository.setSecurityKey(id);
		}
		
		if(executorService == null) {
			ThreadPoolExecutor threadPoolExecutor = 
					new ThreadPoolExecutor(1, Runtime.getRuntime().availableProcessors()*4, 0L, 
							TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(1));
			
			threadPoolExecutor.setThreadFactory((r)->{
				Thread thread = new SecurityThread(r);
				thread.setName("Executor-(" + id.toLowerCase() + ")-thread");
				return thread;
			});
			
			executorService = threadPoolExecutor;
		}
		
		executionTask = new ExecutionTask();
		executionTask.setAlive(true);
		executionTask.setActionFlow(actionFlow);
		executionTask.setActionsRepository(actionsRepository);
		executionTask.setId(id);
		executionTask.setExecutorService(executorService);
		
		SecurityThread actionRegistryThread = new SecurityThread(executionTask);
		actionRegistryThread.setName("Action registry Thread");
		actionRegistryThread.start();
	}
	
	private static class ExecutionTask implements Runnable{

		private Map<String, ActionExecutorEntry> actionFlow;
		
		private ActionsRepository actionsRepository;
		
		private String id;
		
		private boolean alive;

		private ExecutorService executorService;
		
		public void setActionFlow(Map<String, ActionExecutorEntry> actionFlow) {
			this.actionFlow = actionFlow;
		}

		public void setActionsRepository(ActionsRepository actionsRepository) {
			this.actionsRepository = actionsRepository;
		}

		public void setId(String id) {
			this.id = id;
		}

		public void setAlive(boolean alive) {
			this.alive = alive;
		}

		public void setExecutorService(ExecutorService executorService) {
			this.executorService = executorService;
		}

		@Override
		public void run() {
			while(alive) {
				execute();
				sleep();
			}
		}
		
		public void execute() {
			try {
				for(ActionExecutorRequestEntry request: getNextActionsToProcess()) {
					tryActiveAction(request);
				}
			}
			catch(Throwable ex) {
				ex.printStackTrace();
			}
		}
		
		private void sleep() {
			try {
				Thread.sleep(10000);
			}
			catch(Throwable ex) {
				alive = false;
			}
		}
		
		private void tryActiveAction(ActionExecutorRequestEntry request) {
			
			try {
				if(!isActiveRequest(request)) {
					throw new IllegalStateException("not active");
				}
				
				scheduleAgain(request);
				executeAction(request);
			}
			catch(IllegalStateException ex) {
				if(!"not active".equals(ex.getMessage())){
					throw ex;
				}
			}
			
		}
		
		private void executeAction(ActionExecutorRequestEntry request) {
			Runnable task = new ActionTask(request, actionFlow, actionsRepository, id);
			executorService.execute(task);
		}
		
		private void scheduleAgain(ActionExecutorRequestEntry request) {
			ActionExecutorRequestEntry newE = 
					new ActionExecutorRequestEntry(
							request.getId(), 
							request.getRequest(), 
							request.getStatus(), 
							LocalDateTime.now().plus(10, ChronoUnit.SECONDS),
							request.getAttempts()
					);
			
			actionsRepository.register(id, newE);
		}
		
		private List<ActionExecutorRequestEntry> getNextActionsToProcess(){
			return actionsRepository.getNext(id, 10);
		}
		private boolean isActiveRequest(ActionExecutorRequestEntry request) {
			return request.getDateSchedule().isAfter(LocalDateTime.now());
		}
	}
	
}
