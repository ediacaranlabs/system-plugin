package br.com.uoutec.community.ediacaran.system.actions;

import java.util.HashMap;
import java.util.Map;

public class ActionExecutorRequestBuilder {

	private Map<String,String> data = new HashMap<>();
	
	public ActionExecutorRequestBuilder withParameter(String name, String value) {
		data.put(name, value);
		return this;
	}

	public static ActionExecutorRequestBuilder builder() {
		return new ActionExecutorRequestBuilder();
	}
	
	public ActionExecutorRequest build() {
		return new HashMapActionExecutorRequest(data);
	}
	
}
