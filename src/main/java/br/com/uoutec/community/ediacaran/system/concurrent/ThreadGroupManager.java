package br.com.uoutec.community.ediacaran.system.concurrent;

import java.util.concurrent.ExecutorService;

public interface ThreadGroupManager {

	void registryThreadGroup(String name, 
			ExecutorService executorservice) throws ThreadGroupManagerException;

	ExecutorService getThreadGroup(String name) throws ThreadGroupManagerException;
	
	void removeThreadGroup(String name);
	
}
