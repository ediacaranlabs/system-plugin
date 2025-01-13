package br.com.uoutec.community.ediacaran.system.actions;

public class ActionFlowBuilder {

	private ActionExecutorEntry entry;
	
	public ActionFlowBuilder() {
		this.entry = new ActionExecutorEntry();
		this.entry.setId("ROOT");
	}
	
}
