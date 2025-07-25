package br.com.uoutec.community.ediacaran.system.actions;

@FunctionalInterface
public interface ActionExecutor {

	void execute(ActionExecutorRequest request, ActionExecutorResponse response) throws Throwable;
	
}
