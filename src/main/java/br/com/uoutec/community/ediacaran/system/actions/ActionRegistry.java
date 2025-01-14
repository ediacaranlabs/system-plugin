package br.com.uoutec.community.ediacaran.system.actions;

import java.time.temporal.ChronoUnit;
import java.util.concurrent.ExecutorService;

import br.com.uoutec.ediacaran.core.plugins.PublicBean;

public interface ActionRegistry extends PublicBean{

	void registerAction(String actionID, int attemptsBeforeFailure, long timeBeforeTryAgain, ChronoUnit unitTtimeBeforeTryAgain, ActionExecutor executor);

	void removeAction(String actionID);
	
	void setActionsRepository(ActionsRepository actionsRepository);
	
	void setExecutorService(ExecutorService value);
	
	void addNextAction(String actionID, String nextAction);

	void removeNextAction(String actionID, String nextAction);

	void addExceptionAction(String actionID, Class<? extends Throwable> exceptionType, String nextAction);
	
	void removeExceptionAction(String actionID, Class<? extends Throwable> exceptionType);
	
	void executeAction(String actionID, ActionExecutorRequest request);
}
