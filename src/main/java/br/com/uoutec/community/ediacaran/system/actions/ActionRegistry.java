package br.com.uoutec.community.ediacaran.system.actions;

import java.time.temporal.ChronoUnit;

public interface ActionRegistry {

	void registerAction(String actionID, int attemptsBeforeFailure, long timeBeforeTryAgain, ChronoUnit unitTtimeBeforeTryAgain, ActionExecutor executor);
	
	void setNextAction(String actionID, String ... nextAction);

	void removeNextAction(String ... actionID);

	void setExceptionAction(String actionID, Class<? extends Throwable> exceptionType, String nextAction);
	
	void removeExceptionAction(String actionID, Class<? extends Throwable> exceptionType);
	
	void executeAction(String actionID, ActionExecutorRequest request);
}
