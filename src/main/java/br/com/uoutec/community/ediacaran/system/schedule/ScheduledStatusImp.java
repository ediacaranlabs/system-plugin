package br.com.uoutec.community.ediacaran.system.schedule;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ScheduledStatusImp<V> implements ScheduledStatus<V>{

	private final ScheduledFuture<V> future;
	
	public ScheduledStatusImp(ScheduledFuture<V> future) {
		this.future = future;
	}

	@Override
	public long getDelay(TimeUnit unit) {
		return future.getDelay(unit);
	}

	@Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		return future.cancel(mayInterruptIfRunning);
	}

	@Override
	public boolean isCancelled() {
		return future.isCancelled();
	}

	@Override
	public boolean isDone() {
		return future.isDone();
	}

	@Override
	public V get() throws InterruptedException, ExecutionException {
		return future.get();
	}

	@Override
	public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
		return future.get(timeout, unit);
	}

}
