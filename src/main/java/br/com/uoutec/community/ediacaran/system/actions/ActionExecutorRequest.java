package br.com.uoutec.community.ediacaran.system.actions;

public interface ActionExecutorRequest {

	String getId();
	
	Object getParameter(String name);

	<T> T getParameter(String name, Class<T> type);
	
}
