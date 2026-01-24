package br.com.uoutec.community.ediacaran.system.actions;

public interface ActionExecutorResponse {

	void setParameter(String name, String value);
	
	String getParameter(String name);
	
	String getNextAction();
	
	boolean isFinished();
	
	void setFinished(boolean value);
	
}
