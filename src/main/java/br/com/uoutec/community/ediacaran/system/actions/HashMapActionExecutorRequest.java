package br.com.uoutec.community.ediacaran.system.actions;

import java.util.Collections;
import java.util.Map;

public class HashMapActionExecutorRequest implements ActionExecutorRequest{

	private final Map<String,String> data;
	
	public HashMapActionExecutorRequest(Map<String,String> data) {
		this.data = Collections.unmodifiableMap(data);
	}
	
	@Override
	public String getParameter(String name) {
		return data.get(name);
	}
	
}
