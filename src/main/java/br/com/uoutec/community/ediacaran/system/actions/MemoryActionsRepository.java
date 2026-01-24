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
	public synchronized boolean registerIfNotExist(String securityKey, ActionExecutorRequestEntry request) {
		
		int i;
		while((i = list.indexOf(request)) != -1){
			
			if(i != -1) {
				ActionExecutorRequestEntry actual = list.get(i);
				if(actual.getStatus() != ActionExecutorRequestStatus.FINALIZED) {
					return false;
				}
				
				list.remove(actual);
			}
			
		}
		
		
		register(securityKey, request);
		
		return true;
	}
	
	@Override
	public synchronized void register(String securityKey, ActionExecutorRequestEntry request) {
		
		if(!this.securityKey.equals(securityKey)) {
			throw new IllegalStateException("securityKey");
		}

		int i;
		while((i = list.indexOf(request)) != -1){
			
			if(i != -1) {
				ActionExecutorRequestEntry actual = list.get(i);
				list.remove(actual);
			}
			
		}

		if(request.getStatus() != ActionExecutorRequestStatus.FINALIZED) {
			list.addLast(request);
		}
		
	}

	@Override
	public synchronized List<ActionExecutorRequestEntry> getNext(String securityKey, int quantity) {

		if(!this.securityKey.equals(securityKey)) {
			throw new IllegalStateException("securityKey");
		}
		
		List<ActionExecutorRequestEntry> result = new ArrayList<>();
		
		if(list.isEmpty()) {
			return result;
		}
		
		if(quantity > list.size()) {
			quantity = list.size();
		}
		
		ActionExecutorRequestEntry first = list.getFirst();
		
		while(quantity > 0) {
			
			ActionExecutorRequestEntry e = list.removeFirst();
			
			if(e.getStatus() != ActionExecutorRequestStatus.FINALIZED) {
				result.add(e);
				quantity--;
			}
			
			list.addLast(e);
			
			if(list.getFirst().equals(first)) {
				break;
			}
			
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
