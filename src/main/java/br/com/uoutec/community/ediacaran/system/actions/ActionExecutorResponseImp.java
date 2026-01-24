package br.com.uoutec.community.ediacaran.system.actions;

import java.util.HashMap;
import java.util.Map;

public class ActionExecutorResponseImp implements ActionExecutorResponse{

	private Map<String,String> params;
	
	private String nextAction;
	
	private boolean finished;
	
	public ActionExecutorResponseImp() {
		this.params = new HashMap<>();
		this.finished = false;
	}
	
	@Override
	public void setParameter(String name, String value) {
		params.put(name, value);
	}

	@Override
	public String getParameter(String name) {
		return params.get(name);
	}

	public Map<String, String> getParams() {
		return params;
	}

	public String getNextAction() {
		return nextAction;
	}

	public void setNextAction(String nextAction) {
		this.nextAction = nextAction;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

}
