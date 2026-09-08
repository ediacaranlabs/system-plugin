package br.com.uoutec.community.ediacaran.system.actions;

import java.time.LocalDateTime;
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
	
	protected void registerActionFlow(String actionID, ActionExecutorEntry actionExecutorEntry) {
		super.registerActionFlow(actionID, actionExecutorEntry);
		actionFlow.put(actionID, actionExecutorEntry);
	}

	protected void removeActionFlow(String actionID) {
		super.removeActionFlow(actionID);
		actionFlow.remove(actionID);
	}

	protected ActionExecutorEntry getActionFlow(String actionID) {
		return actionFlow.get(actionID);
	}
	
	public void executeAll() throws Throwable {

		while(true) {
			
			List<ActionExecutorRequestEntry> itens = actionsRepository.getNext(99);
			
			if(itens.isEmpty()) {
				break;
			}
			
			for(ActionExecutorRequestEntry request: itens) {
				ActionTask task = new ActionTask(request, actionFlow, actionsRepository, actionsRepository.securityKey);
				task.run();
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
			return actionsRepository.registerIfNotExist(this.securityKey, request);
		}

		public void register(ActionExecutorRequestEntry request) {
			actionsRepository.register(securityKey, request);
		}
		
		@Override
		public void register(String securityKey, ActionExecutorRequestEntry request) {
			actionsRepository.register(this.securityKey, request);
		}

		public void remove(ActionExecutorRequestEntry request) {
			actionsRepository.remove(securityKey, request);
		}
		
		@Override
		public void remove(String securityKey, ActionExecutorRequestEntry request) {
			actionsRepository.remove(this.securityKey, request);
		}

		@Override
		public List<ActionExecutorRequestEntry> getNext(String securityKey, int quantity) {
			return actionsRepository.getNext(this.securityKey, quantity);
		}

		public List<ActionExecutorRequestEntry> getNext(int quantity) {
			return actionsRepository.getNext(securityKey, quantity);
		}
		
	}
	
}
