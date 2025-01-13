package br.com.uoutec.community.ediacaran.system.actions;

import java.util.List;

public interface ActionsRepository {

	void setSecurityKey(String value);
	
	void register(String securityKey, ActionExecutorRequestEntry request);

	void remove(String securityKey, ActionExecutorRequestEntry request);
	
	List<ActionExecutorRequestEntry> getNext(String securityKey, int quantity);
	
}
