package br.com.uoutec.community.ediacaran.system.actions;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.UUID;

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
		
		ActionExecutorEntry ex = actionFlow.get(request.getStatus());
		
		if(ex == null) {
			
			if(logger.isWarnEnabled()) {
				logger.warn("action {} not found", request.getStatus());
			}
			
			actionsRepository.remove(id, request);
			return;
		}
		
		ActionExecutorResponseImp response = new ActionExecutorResponseImp();

		try {
			ex.getExecutor().execute(request, response);
			
			if(logger.isTraceEnabled()) {
				logger.trace("action executed {}", request.getStatus());
			}
			
			actionsRepository.remove(id, request);
			
			if(!response.isFinished()) {
				ex.getNextActions().stream().forEach((e)->{
					ActionExecutorRequestEntry newE = 
							new ActionExecutorRequestEntry(
									UUID.randomUUID().toString(), 
									new HashMapActionExecutorRequest(response.getParams()), 
									e, 
									LocalDateTime.now().plus(10, ChronoUnit.SECONDS),
									0
							);
					actionsRepository.register(id, newE);
				});
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
