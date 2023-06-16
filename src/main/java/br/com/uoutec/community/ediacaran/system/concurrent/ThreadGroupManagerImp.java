package br.com.uoutec.community.ediacaran.system.concurrent;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;

import javax.inject.Singleton;

import br.com.uoutec.community.ediacaran.plugins.PublicBean;

@Singleton
public class ThreadGroupManagerImp 
	implements ThreadGroupManager, PublicBean{

	private ConcurrentMap<String, ExecutorService> gropus;
	
	public ThreadGroupManagerImp(){
		this.gropus = new ConcurrentHashMap<String, ExecutorService>();
	}
	
	public void registryThreadGroup(String name, 
			ExecutorService executorservice) throws ThreadGroupManagerException{
		//TODO: system security
		if(this.gropus.containsKey(name)){
			throw new ThreadGroupManagerException("group has already been registered: " + name);
		}
		
		this.gropus.put(name, executorservice);
	}

	public ExecutorService getThreadGroup(String name) throws ThreadGroupManagerException{
		
		ExecutorService executorservice = this.gropus.get(name);
		
		if(executorservice == null){
			throw new ThreadGroupManagerException("group not found: " + name);
		}
		
		return executorservice;
	}
	
	public void removeThreadGroup(String name) {
		this.gropus.remove(name);
	}

}
