package br.com.uoutec.community.ediacaran.system.actions;

import java.util.Collections;
import java.util.Map;

public class HashMapActionExecutorRequest implements ActionExecutorRequest {

	private final Map<String,Object> data;
	
	private final String id;
	
	public HashMapActionExecutorRequest(String id, Map<String,Object> data) {
		this.data = Collections.unmodifiableMap(data);
		this.id = id;
	}
	
	@Override
	public Object getParameter(String name) {
		return data.get(name);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getParameter(String name, Class<T> type) {
		return (T)data.get(name);
	}
	
	@Override
	public String getId() {
		return id;
	}
	
}
