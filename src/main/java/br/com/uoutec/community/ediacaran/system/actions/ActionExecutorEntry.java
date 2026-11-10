package br.com.uoutec.community.ediacaran.system.actions;

import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public class ActionExecutorEntry {

	private String id;
	
	private String defaultNextAction;

	private volatile String nextAction;

	private volatile String previousAction;
	
	private ActionExecutor executor;
	
	private Map<Class<?>,String> exceptionAction;

	private int attemptsBeforeFailure;
	
	private long timeBeforeTryAgain;
	
	private ChronoUnit unitTtimeBeforeTryAgain;
	
	public ActionExecutorEntry() {
		this.nextAction = null;
		this.previousAction = null;
		this.exceptionAction = new HashMap<>();
	}
	
	public int getAttemptsBeforeFailure() {
		return attemptsBeforeFailure;
	}

	public void setAttemptsBeforeFailure(int attemptsBeforeFailure) {
		this.attemptsBeforeFailure = attemptsBeforeFailure;
	}

	public long getTimeBeforeTryAgain() {
		return timeBeforeTryAgain;
	}

	public void setTimeBeforeTryAgain(long timeBeforeTryAgain) {
		this.timeBeforeTryAgain = timeBeforeTryAgain;
	}

	public ChronoUnit getUnitTtimeBeforeTryAgain() {
		return unitTtimeBeforeTryAgain;
	}

	public void setUnitTtimeBeforeTryAgain(ChronoUnit unitTtimeBeforeTryAgain) {
		this.unitTtimeBeforeTryAgain = unitTtimeBeforeTryAgain;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDefaultNextAction() {
		return defaultNextAction;
	}

	public void setDefaultNextAction(String defaultNextAction) {
		this.defaultNextAction = defaultNextAction;
	}


	public String getNextAction() {
		return nextAction;
	}

	public void setNextAction(String nextAction) {
		this.nextAction = nextAction;
	}

	public String getPreviousAction() {
		return previousAction;
	}

	public void setPreviousAction(String previousAction) {
		this.previousAction = previousAction;
	}

	public ActionExecutor getExecutor() {
		return executor;
	}

	public void setExecutor(ActionExecutor executor) {
		this.executor = executor;
	}

	public Map<Class<?>, String> getExceptionAction() {
		return exceptionAction;
	}

	public void setExceptionAction(Map<Class<?>, String> exceptionAction) {
		this.exceptionAction = exceptionAction;
	}
	
}
