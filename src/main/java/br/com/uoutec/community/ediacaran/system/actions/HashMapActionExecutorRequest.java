package br.com.uoutec.community.ediacaran.system.actions;

import java.util.Collections;
import java.util.Map;

public class HashMapActionExecutorRequest implements ActionExecutorRequest{

	private final Map<String,String> data;
	
	private final String nextAction;
	
	public HashMapActionExecutorRequest(Map<String,String> data, String nextAction) {
		this.data = Collections.unmodifiableMap(data);
		this.nextAction = nextAction;
	}
	
	@Override
	public String getParameter(String name) {
		return data.get(name);
	}

	@Override
	public String getNextAction() {
		return nextAction;
	}

}
