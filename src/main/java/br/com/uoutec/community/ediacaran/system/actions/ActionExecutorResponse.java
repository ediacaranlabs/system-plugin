package br.com.uoutec.community.ediacaran.system.actions;

public interface ActionExecutorResponse {

	void setParameter(String name, String value);
	
	Object getParameter(String name);

	<T> T getParameter(String name, Class<T> type);
	
	String getNextAction();
	
	boolean isFinished();
	
	void setFinished(boolean value);
	
}
