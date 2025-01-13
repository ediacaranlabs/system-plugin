package br.com.uoutec.community.ediacaran.system.actions;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Singleton;

@Singleton
public class ActionRegistryImp implements ActionRegistry{

	private ActionsRepository actionsRepository;
	
	private final String id;
	
	private Map<String, ActionExecutorEntry> actionFlow;
	
	private ExecutionTask executionTask;
	
	public ActionRegistryImp() {
		this.id = UUID.randomUUID().toString();
		this.actionFlow = new HashMap<>();
		this.executionTask = new ExecutionTask(actionFlow, actionsRepository, id);
		new Thread(executionTask).start();
	}
	
	@Override
	public void setActionsRepository(ActionsRepository actionsRepository) {
		this.actionsRepository = actionsRepository;
		this.actionsRepository.setSecurityKey(id);
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
	public void addNextAction(String actionID, String nextAction) {
		
		ActionExecutorEntry actionExecutorEntry = actionFlow.get(actionID);
		
		if(actionFlow == null) {
			throw new NullPointerException(actionID);
		}
		
		List<String> nextActions = actionExecutorEntry.getNextActions();
		nextActions.remove(nextAction);
		nextActions.add(nextAction);
	}

	@Override
	public void removeNextAction(String actionID, String nextAction) {
		
		ActionExecutorEntry actionExecutorEntry = actionFlow.get(actionID);
		
		if(actionFlow == null) {
			return;
		}
		
		List<String> nextActions = actionExecutorEntry.getNextActions();
		nextActions.remove(nextAction);
		
	}

	@Override
	public void addExceptionAction(String actionID, Class<? extends Throwable> exceptionType, String nextAction) {
		
		ActionExecutorEntry actionExecutorEntry = actionFlow.get(actionID);
		
		if(actionFlow == null) {
			throw new NullPointerException(actionID);
		}
		
		Map<Class<?>, String> nextActions = actionExecutorEntry.getExceptionAction();
		nextActions.put(exceptionType, nextAction);

	}

	@Override
	public void removeExceptionAction(String actionID, Class<? extends Throwable> exceptionType) {
		
		ActionExecutorEntry actionExecutorEntry = actionFlow.get(actionID);
		
		if(actionFlow == null) {
			return;
		}
		
		Map<Class<?>, String> nextActions = actionExecutorEntry.getExceptionAction();
		nextActions.remove(exceptionType);
		
	}

	@Override
	public void executeAction(String actionID, ActionExecutorRequest request) {
		ActionExecutorRequestEntry entry = 
				new ActionExecutorRequestEntry(UUID.randomUUID().toString(), request, actionID, LocalDateTime.now(), 0);
		actionsRepository.register(id, entry);
	}

	protected void finalize() throws Throwable {
		executionTask.alive = false;
		super.finalize();
	}
	
	private static class ExecutionTask implements Runnable{

		private Map<String, ActionExecutorEntry> actionFlow;
		
		private ActionsRepository actionsRepository;
		
		private String id;
		
		private boolean alive = true;

		public ExecutionTask(Map<String, ActionExecutorEntry> actionFlow, 
				ActionsRepository actionsRepository, String id) {
			this.actionFlow = actionFlow;
			this.actionsRepository = actionsRepository;
			this.id = id;
		}

		@Override
		public void run() {
			while(alive) {
				
				execute();
				
				try {
					Thread.sleep(10000);
				}
				catch(Throwable ex) {
					alive = false;
				}
				
			}
			
		}
		
		public void execute() {

			List<ActionExecutorRequestEntry> list = actionsRepository.getNext(id, 1);
			
			for(ActionExecutorRequestEntry e: list) {
				Thread th = new Thread(new ActionTask(e, actionFlow, actionsRepository, id));
				th.start();
			}
			
		}
		
	}
	
}
