package br.com.uoutec.community.ediacaran.system.actions;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.inject.Alternative;

@Alternative
public class ActionRegistryMock extends ActionRegistryImp {

	private ActionsRepositoryProxy actionsRepository;
	
	private Map<String, ActionExecutorEntry> actionFlow = new HashMap<>();
	
	public synchronized void setActionsRepository(ActionsRepository actionsRepository) {
		this.actionsRepository = new ActionsRepositoryProxy(actionsRepository);
		super.setActionsRepository(this.actionsRepository);
	}
	
	@Override
	public void executeAction(String actionID, ActionExecutorRequest request) {
		
		if(this.actionsRepository == null) {
			this.setActionsRepository(new MemoryActionsRepository());
		}
		
		ActionExecutorRequestEntry entry = 
				new ActionExecutorRequestEntry(
						request.getId(), 
						request, 
						ActionExecutorRequestStatus.ONHOLD,
						actionID, 
						LocalDateTime.now(), 
						0
				);
		
		actionsRepository.registerIfNotExist(entry);

	}

	@Override
	public void registerAction(String actionID, int attemptsBeforeFailure, long timeBeforeTryAgain,
			ChronoUnit unitTtimeBeforeTryAgain, ActionExecutor executor) {
		super.registerAction(actionID, attemptsBeforeFailure, timeBeforeTryAgain, unitTtimeBeforeTryAgain, executor);
		
		actionID = actionID.toLowerCase();
		
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
		super.removeAction(actionID);
		actionID = actionID.toLowerCase();
		actionFlow.remove(actionID);
	}
	
	public void executeAll() throws Throwable {

		while(true) {
			
			List<ActionExecutorRequestEntry> itens = actionsRepository.getNext(99);
			
			if(itens.isEmpty()) {
				break;
			}
			
			for(ActionExecutorRequestEntry request: itens) {
				
				ActionExecutorEntry ex = actionFlow.get(request.getNexAction());
				
				if(ex == null) {
					throw new IllegalStateException("action: " + request.getNexAction());
				}
				
				ActionExecutorResponseImp response = new ActionExecutorResponseImp();

				ex.getExecutor().execute(request, response);
				
				if(response.isFinished() || (ex.getNextActions() == null || ex.getNextActions().isEmpty())) {
					request.setStatus(ActionExecutorRequestStatus.FINALIZED);
					actionsRepository.register(request);
				}
				else {
					
					String nextAction = response.getNextAction() == null? ex.getNextActions() : response.getNextAction();
					
					if(nextAction != null) {
						
						if(!ex.getNextActions().contains(nextAction)) {
							throw new IllegalStateException("next action not found: " + nextAction);
						}
	
						request.setDateSchedule(LocalDateTime.now());
						request.setStatus(ActionExecutorRequestStatus.ONHOLD);
						request.setRequest(new HashMapActionExecutorRequest(request.getId(), response.getParams()));
						request.setNexAction(nextAction);
						request.setAttempts(0);
						actionsRepository.register(request);
						
					}
					
				}
				
			}
			
		}
		

	}
	
	public class ActionsRepositoryProxy implements ActionsRepository {

		private ActionsRepository actionsRepository;
		
		private String securityKey;
		
		public ActionsRepositoryProxy(ActionsRepository actionsRepository) {
			this.actionsRepository = actionsRepository;
		}

		@Override
		public void setSecurityKey(String value) {
			securityKey = value;
			actionsRepository.setSecurityKey(value);
		}

		public boolean registerIfNotExist(ActionExecutorRequestEntry request) {
			return actionsRepository.registerIfNotExist(securityKey, request);
		}
		
		@Override
		public boolean registerIfNotExist(String securityKey, ActionExecutorRequestEntry request) {
			return actionsRepository.registerIfNotExist(securityKey, request);
		}

		public void register(ActionExecutorRequestEntry request) {
			actionsRepository.register(securityKey, request);
		}
		
		@Override
		public void register(String securityKey, ActionExecutorRequestEntry request) {
			actionsRepository.register(securityKey, request);
		}

		public void remove(ActionExecutorRequestEntry request) {
			actionsRepository.remove(securityKey, request);
		}
		
		@Override
		public void remove(String securityKey, ActionExecutorRequestEntry request) {
			actionsRepository.remove(securityKey, request);
		}

		@Override
		public List<ActionExecutorRequestEntry> getNext(String securityKey, int quantity) {
			return actionsRepository.getNext(securityKey, quantity);
		}

		public List<ActionExecutorRequestEntry> getNext(int quantity) {
			return actionsRepository.getNext(securityKey, quantity);
		}
		
	}
	
}
