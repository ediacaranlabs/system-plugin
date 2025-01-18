package br.com.uoutec.community.ediacaran.system.actions;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;

public class ActionTask implements Runnable{

	private ActionExecutorRequestEntry request;

	private Map<String, ActionExecutorEntry> actionFlow;
	
	private ActionsRepository actionsRepository;
	
	private String id;
	
	public ActionTask(ActionExecutorRequestEntry request, Map<String, ActionExecutorEntry> actionFlow,
			ActionsRepository actionsRepository, String id) {
		this.request = request;
		this.actionFlow = actionFlow;
		this.actionsRepository = actionsRepository;
		this.id = id;
	}

	@Override
	public void run() {
		
		ActionExecutorEntry ex = actionFlow.get(request.getStatus());
		
		if(ex == null) {
			actionsRepository.register(id, request);
			return;
		}
		
		ActionExecutorResponseImp response = new ActionExecutorResponseImp();

		try {
			ex.getExecutor().execute(request, response);
			
			String nextAction = response.getNextAction();
			
			if(nextAction == null && !ex.getNextActions().isEmpty()) {
				nextAction = ex.getNextActions().get(0);
			}
			
			if(nextAction == null) {
				actionsRepository.remove(id, request);
			}
			else {
				ActionExecutorRequestEntry newE = 
						new ActionExecutorRequestEntry(
								request.getId(), 
								new HashMapActionExecutorRequest(response.getParams()), 
								nextAction, 
								LocalDateTime.now().plus(10, ChronoUnit.SECONDS),
								0
						);
				actionsRepository.register(id, newE);
			}
			
		}
		catch(Throwable e) {
			
			if(ex.getExceptionAction().containsKey(e.getClass())) {
				String nextAction = ex.getExceptionAction().get(e.getClass());
				
				if(nextAction == null) {
					ActionExecutorRequestEntry newE = 
							new ActionExecutorRequestEntry(
									request.getId(), 
									request.getRequest(), 
									nextAction, 
									LocalDateTime.now().plus(10, ChronoUnit.SECONDS),
									request.getAttempts() + 1
							);
					actionsRepository.register(id, newE);
				}
				else {
					actionsRepository.remove(id, request);
				}
			}
			else
			if(request.getAttempts() < ex.getAttemptsBeforeFailure()){
				ActionExecutorRequestEntry newE = 
						new ActionExecutorRequestEntry(
								request.getId(), 
								request.getRequest(), 
								request.getStatus(), 
								LocalDateTime.now().plus(10, ChronoUnit.SECONDS),
								request.getAttempts() + 1
						);
				actionsRepository.register(id, newE);
			}
			else {
				actionsRepository.remove(id, request);
			}
			
		}
	}
	
}
