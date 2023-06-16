package br.com.uoutec.community.ediacaran.system.lock;

import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.brandao.concurrent.NamedLock;

public class NamedLockThreadContext {

	static ThreadLocal<ThreadGroupLock> threadGroupLock =
			new ThreadLocal<ThreadGroupLock>();
	
	public NamedLockThreadContext(){
	}
	
	public void activate(){
		ThreadGroupLock current = threadGroupLock.get();
		if(current == null){
			threadGroupLock.set(new ThreadGroupLock());
		}
		else{
			current.count++;
		}
	}
	
	public void deactivate(){
		
		ThreadGroupLock current = threadGroupLock.get();
		
		if(current == null || --current.count > 0){
			return;
		}
		
		try{
			if(current != null){
				this.releaseLocks(current.group);
			}
		}
		finally{
			threadGroupLock.remove();
		}
		
	}
	
	private void releaseLocks(ConcurrentMap<String, GroupLock> current){
		for(Entry<String, GroupLock> e: current.entrySet()){
			NamedLock namedLock = Lock.locks.get(e.getKey());
			e.getValue().releaseLocks(namedLock);
		}
		
	}
	
	public static class ThreadGroupLock{
		
		public ConcurrentMap<String, GroupLock> group;
		
		public int count;
		
		public ThreadGroupLock(){
			this.group = new ConcurrentHashMap<String, GroupLock>();
			this.count = 1;
		}
	}
	
}
