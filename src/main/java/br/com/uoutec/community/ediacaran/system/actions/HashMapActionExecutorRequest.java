package br.com.uoutec.community.ediacaran.system.actions;

import java.util.Map;

public class HashMapActionExecutorRequest implements ActionExecutorRequest{

	private Map<String,String> data;
	
	public HashMapActionExecutorRequest(Map<String,String> data) {
		this.data = data;
	}
	
	@Override
	public String getParameter(String name) {
		return data.get(name);
	}

}
