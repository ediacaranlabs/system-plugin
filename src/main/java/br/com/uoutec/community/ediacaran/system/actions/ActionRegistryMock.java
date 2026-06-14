package br.com.uoutec.community.ediacaran.system.actions;

import java.time.temporal.ChronoUnit;
import java.util.concurrent.ExecutorService;

import javax.enterprise.inject.Alternative;

@Alternative
public class ActionRegistryMock implements ActionRegistry {

	public ActionRegistryMock() {
	}
	
	@Override
	public synchronized void setActionsRepository(ActionsRepository actionsRepository) {
	}
	
	public synchronized void setExecutorService(ExecutorService value) {
	}
	
	@Override
	public void registerAction(String actionID, int attemptsBeforeFailure, long timeBeforeTryAgain,
			ChronoUnit unitTtimeBeforeTryAgain, ActionExecutor executor) {
	}

	@Override
	public void removeAction(String actionID) {
	}
	
	@Override
	public void executeAfter(String actionID, String nextAction) {
	}

	@Override
	public void destroyExecuteAfter(String actionID) {
	}

	@Override
	public void addExceptionAction(String actionID, Class<? extends Throwable> exceptionType, String nextAction) {
	}

	@Override
	public void removeExceptionAction(String actionID, Class<? extends Throwable> exceptionType) {
	}

	@Override
	public void executeAction(String actionID, ActionExecutorRequest request) {
	}
	
}
