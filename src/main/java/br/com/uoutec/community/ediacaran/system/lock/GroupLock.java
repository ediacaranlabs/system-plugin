package br.com.uoutec.community.ediacaran.system.lock;

import java.io.Serializable;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.brandao.concurrent.NamedLock;

public class GroupLock {

	private ConcurrentMap<String, Serializable> resourceMap;
	
	public GroupLock(){
		this.resourceMap = new ConcurrentHashMap<String, Serializable>();
	}
	
	public Serializable lock(NamedLock namedLock, String resource){
		
		Serializable lockID = this.resourceMap.get(resource);
		
		if(lockID == null){
			lockID = namedLock.lock(resource);
			this.resourceMap.put(resource, lockID);
		}
		else{
			return lockID;
		}
		return lockID;
	}
	
	public void unlock(NamedLock namedLock, String resource){
		Serializable lockID = this.resourceMap.get(resource);
		
		if( lockID == null){
			throw new IllegalStateException("bloqueio não encontrado: " + lockID);
		}
		
		namedLock.unlock(lockID, resource);
		this.resourceMap.remove(resource);
	}

	public void releaseLocks(NamedLock namedLock){
		
		for(Entry<String, Serializable> resource: this.resourceMap.entrySet()){
			this.release(resource.getKey(), resource.getValue(), namedLock);
		}
		
	}
	
	private void release(String resource, Serializable id, NamedLock namedLock){
		try{
			namedLock.unlock(id, resource);
		}
		catch(Throwable e){
			
		}
	}
	
}
