package br.com.uoutec.community.ediacaran.system.actions;

import java.util.HashMap;
import java.util.Map;

public class ActionExecutorEntry {

	private String id;
	
	private String defaultNextAction;

	private Map<String,String> nextActions;
	
	private ActionExecutor executor;
	
	private Map<Class<?>,String> exceptionAction;

	public ActionExecutorEntry() {
		this.nextActions = new HashMap<>();
		this.exceptionAction = new HashMap<>();
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

	public Map<String, String> getNextActions() {
		return nextActions;
	}

	public void setNextActions(Map<String, String> nextActions) {
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
