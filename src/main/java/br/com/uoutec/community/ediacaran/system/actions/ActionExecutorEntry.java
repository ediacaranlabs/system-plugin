package br.com.uoutec.community.ediacaran.system.actions;

import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ActionExecutorEntry {

	private String id;
	
	private String defaultNextAction;

	private Set<String> nextActions;
	
	private ActionExecutor executor;
	
	private Map<Class<?>,String> exceptionAction;

	private int attemptsBeforeFailure;
	
	private long timeBeforeTryAgain;
	
	private ChronoUnit unitTtimeBeforeTryAgain;
	
	public ActionExecutorEntry() {
		this.nextActions = new HashSet<>();
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

	public Set<String> getNextActions() {
		return nextActions;
	}

	public void setNextActions(Set<String> nextActions) {
		this.nextActions = nextActions;
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
