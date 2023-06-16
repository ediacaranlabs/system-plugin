package br.com.uoutec.community.ediacaran.system.concurrent;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ListThreadPoolExecutor extends ThreadPoolExecutor{

	public ListThreadPoolExecutor(
			int size, int workQueueSize) {
		super(size, size, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(workQueueSize));
		
		this.setRejectedExecutionHandler(
				new ThreadPoolExecutor.CallerRunsPolicy());
	}
	
}
