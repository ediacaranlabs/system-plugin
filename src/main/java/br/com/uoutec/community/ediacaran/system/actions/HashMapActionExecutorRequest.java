package br.com.uoutec.community.ediacaran.system.actions;

import java.util.Collections;
import java.util.Map;

public class HashMapActionExecutorRequest implements ActionExecutorRequest {

	private final Map<String,String> data;
	
	private final String id;
	
	public HashMapActionExecutorRequest(String id, Map<String,String> data) {
		this.data = Collections.unmodifiableMap(data);
		this.id = id;
	}
	
	@Override
	public String getParameter(String name) {
		return data.get(name);
	}

	@Override
	public String getId() {
		return id;
	}
	
}
