package br.com.uoutec.community.ediacaran.system.actions;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ActionExecutorRequestBuilder {

	private Map<String,String> data = new HashMap<>();

	private String id;
	
	public ActionExecutorRequestBuilder withId(String id) {
		this.id = id;
		return this;
	}
	
	public ActionExecutorRequestBuilder withParameter(String name, String value) {
		data.put(name, value);
		return this;
	}

	public static ActionExecutorRequestBuilder builder() {
		return new ActionExecutorRequestBuilder();
	}
	
	public ActionExecutorRequest build() {
		return new HashMapActionExecutorRequest(id == null? UUID.randomUUID().toString() : id, data);
	}
	
}
