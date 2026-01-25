package br.com.uoutec.community.ediacaran.system.actions;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActionTask implements Runnable{

	private final Logger logger = LoggerFactory.getLogger(ActionTask.class);
	
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
		
		ActionExecutorEntry ex = actionFlow.get(request.getNexAction());
		
		if(ex == null) {
			
			if(logger.isWarnEnabled()) {
				logger.warn("action {} dont have next action", request.getId(), String.valueOf(request.getNexAction()));
			}
			
			actionsRepository.remove(id, request);
			return;
		}
		
		ActionExecutorResponseImp response = new ActionExecutorResponseImp();

		
		request.setStatus(ActionExecutorRequestStatus.ACTIVE);
		actionsRepository.register(id, request);
		
		try {
			ex.getExecutor().execute(request, response);
			
			if(logger.isTraceEnabled()) {
				logger.trace("action executed {}", request.getStatus());
			}
			
			if(!response.isFinished()) {
				
				
				if(!ex.getNextActions().isEmpty()) {
					
					request.setDateSchedule(LocalDateTime.now().plus(10, ChronoUnit.SECONDS));
					request.setStatus(ActionExecutorRequestStatus.ONHOLD);
					request.setRequest(new HashMapActionExecutorRequest(request.getId(), response.getParams()));
					
					String nextAction = response.getNextAction();
					
					if(nextAction != null) {
						if(!ex.getNextActions().contains(nextAction)) {
							throw new IllegalStateException("next action not found: " + nextAction);
						}
						
					}
					else {
						nextAction = ex.getNextActions().iterator().next();
					}
					
					request.setNexAction(nextAction);
				}
				
			}
			else {
				request.setStatus(ActionExecutorRequestStatus.FINALIZED);
			}

			request.setAttempts(request.getAttempts() + 1);
			
			if(request.getAttempts() >= ex.getAttemptsBeforeFailure()){
				request.setStatus(ActionExecutorRequestStatus.FINALIZED);
			}
			
			actionsRepository.register(id, request);
		}
		catch(Throwable e) {
			
			request.setDateSchedule(LocalDateTime.now().plus(10, ChronoUnit.SECONDS));
			request.setStatus(ActionExecutorRequestStatus.ONHOLD);
			
			if(ex.getExceptionAction().containsKey(e.getClass())) {
				
				String nextAction = ex.getExceptionAction().get(e.getClass());
				
				if(nextAction != null) {
					request.setNexAction(nextAction);
				}
				else {
					request.setAttempts(request.getAttempts() + 1);
				}
				
			}
			else
			if(request.getAttempts() < ex.getAttemptsBeforeFailure()){
				request.setDateSchedule(LocalDateTime.now().plus(10, ChronoUnit.SECONDS));
				request.setAttempts(request.getAttempts() + 1);
			}
			else {
				request.setStatus(ActionExecutorRequestStatus.FINALIZED);
			}

			actionsRepository.register(id, request);
			
		}
		
	}
	
}
