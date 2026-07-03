package br.com.uoutec.community.ediacaran.system.concurrent;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import br.com.uoutec.application.security.SecurityThread;

@Singleton
public class PluginThreadPoolExecutor extends ThreadPoolExecutor{

	public PluginThreadPoolExecutor() {
		super(Runtime.getRuntime().availableProcessors()*8, Runtime.getRuntime().availableProcessors()*8, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(Runtime.getRuntime().availableProcessors()*16), (e)->new SecurityThread(e));
		
		this.setRejectedExecutionHandler(
				new ThreadPoolExecutor.CallerRunsPolicy());
	}
	
}
