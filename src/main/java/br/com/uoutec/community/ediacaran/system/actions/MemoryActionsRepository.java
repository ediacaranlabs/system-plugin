package br.com.uoutec.community.ediacaran.system.actions;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MemoryActionsRepository implements ActionsRepository {

	private LinkedList<ActionExecutorRequestEntry> list;
	
	private String securityKey;
	
	public MemoryActionsRepository() {
		this.list = new LinkedList<>();
		this.securityKey = null;
	}
	@Override
	public void setSecurityKey(String value) {
		if(securityKey != null) {
			throw new IllegalStateException();
		}
		this.securityKey = value;
	}

	@Override
	public synchronized void register(String securityKey, ActionExecutorRequestEntry request) {
		
		if(!this.securityKey.equals(securityKey)) {
			throw new IllegalStateException("securityKey");
		}
		
		int indexOf = list.indexOf(request);
		
		if(indexOf != -1) {
			list.remove(request);
		}
		
		list.addLast(request);
	}

	@Override
	public synchronized List<ActionExecutorRequestEntry> getNext(String securityKey, int quantity) {

		if(!this.securityKey.equals(securityKey)) {
			throw new IllegalStateException("securityKey");
		}
		
		List<ActionExecutorRequestEntry> result = new ArrayList<>();
		
		if(quantity > list.size()) {
			quantity = list.size();
		}
		
		while(quantity > 0) {
			ActionExecutorRequestEntry e = list.getFirst();
			result.add(e);
			list.addLast(list.removeFirst());
			quantity--;
		}
		
		return result;
	}
	
	@Override
	public void remove(String securityKey, ActionExecutorRequestEntry request) {
		
		if(!this.securityKey.equals(securityKey)) {
			throw new IllegalStateException("securityKey");
		}
		
		list.remove(request);
		
	}

}
