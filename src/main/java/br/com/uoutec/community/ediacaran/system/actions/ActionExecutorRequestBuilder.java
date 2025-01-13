package br.com.uoutec.community.ediacaran.system.actions;

import java.util.HashMap;
import java.util.Map;

public class ActionExecutorRequestBuilder {

	Map<String,String> data = new HashMap<>();
	
	public ActionExecutorRequestBuilder addParameter(String name, String value) {
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
