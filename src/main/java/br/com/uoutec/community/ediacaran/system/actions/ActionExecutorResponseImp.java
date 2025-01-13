package br.com.uoutec.community.ediacaran.system.actions;

import java.util.HashMap;
import java.util.Map;

public class ActionExecutorResponseImp implements ActionExecutorResponse{

	private Map<String,String> params;
	
	private String nextAction;
	
	public ActionExecutorResponseImp() {
		this.params = new HashMap<>();
	}
	
	@Override
	public void setParameter(String name, String value) {
		params.put(name, value);
	}

	@Override
	public String getParameter(String name) {
		return params.get(name);
	}

	@Override
	public void setNexAction(String value) {
		this.nextAction = value;
	}

	Map<String, String> getParams() {
		return params;
	}

	String getNextAction() {
		return nextAction;
	}

}
