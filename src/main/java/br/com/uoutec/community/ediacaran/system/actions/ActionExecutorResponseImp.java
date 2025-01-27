package br.com.uoutec.community.ediacaran.system.actions;

import java.util.HashMap;
import java.util.Map;

public class ActionExecutorResponseImp implements ActionExecutorResponse{

	private Map<String,String> params;
	
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

	public Map<String, String> getParams() {
		return params;
	}

}
