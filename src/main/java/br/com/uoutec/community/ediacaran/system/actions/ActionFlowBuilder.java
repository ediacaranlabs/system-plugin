package br.com.uoutec.community.ediacaran.system.actions;

import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class ActionFlowBuilder {

	private ActionExecutorEntry entry;
	
	private ActionFlowBuilder parent;
	
	public ActionFlowBuilder() {
		this.entry = new ActionExecutorEntry();
		this.entry.setId("ROOT");
		this.parent = null;
	}

	public ActionFlowBuilder addNextAction(String actionID, ActionExecutor executor) {
		ActionFlowBuilder next = new ActionFlowBuilder();
		next.parent = this;
		next.entry.setId(actionID);
		next.entry.setExecutor(executor);
		
		entry.getNextActions().add(actionID);
		
		return next;
	}
	
	public ActionFlowBuilder setExceptionAction(Class<? extends Throwable> type, String actionID) {
		entry.getExceptionAction().put(type, actionID);
		return this;
	}

	public ActionFlowBuilder setExceptionAction(Class<? extends Throwable> type, ActionExecutor executor) {
		String actionID = UUID.randomUUID().toString();
		ActionFlowBuilder builder = addNextAction(actionID, executor);
		entry.getExceptionAction().put(type, actionID);
		return builder;
	}
	
	public ActionFlowBuilder getParent() {
		return parent;
	}
	
	public ActionFlowBuilder setAttemptsBeforeFailure(int attemptsBeforeFailure) {
		entry.setAttemptsBeforeFailure(attemptsBeforeFailure);
		return this;
	}

	public ActionFlowBuilder setTimeBeforeTryAgain(long timeBeforeTryAgain,ChronoUnit unitTtimeBeforeTryAgain) {
		entry.setTimeBeforeTryAgain(timeBeforeTryAgain);
		entry.setUnitTtimeBeforeTryAgain(unitTtimeBeforeTryAgain);
		return this;
	}

}
