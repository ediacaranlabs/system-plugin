package br.com.uoutec.community.ediacaran.system.actions;

public interface ActionExecutorRequest {

	String getNextAction();
	
	String getParameter(String name);
	
}
