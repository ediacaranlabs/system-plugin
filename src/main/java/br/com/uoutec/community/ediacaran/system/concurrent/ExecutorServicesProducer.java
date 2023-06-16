package br.com.uoutec.community.ediacaran.system.concurrent;

import java.util.concurrent.ExecutorService;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import br.com.uoutec.community.ediacaran.plugins.PublicBean;

@Singleton
public class ExecutorServicesProducer implements PublicBean{

	public static final String DEFAULT_THREAD_GROUP = "default";
	
	@Inject
	private ThreadGroupManager threadGroupManager;
	
	@Produces
	@Named(DEFAULT_THREAD_GROUP)
	public ExecutorService createDefaultThreadGroup() throws ThreadGroupManagerException{
		return this.threadGroupManager.getThreadGroup(DEFAULT_THREAD_GROUP);
	}
	
}
