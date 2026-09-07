package br.com.uoutec.community.ediacaran.system.schedule;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import javax.enterprise.context.control.ActivateRequestContext;
import javax.enterprise.inject.Alternative;

@Alternative
public class ScheduleRegistryMock implements ScheduleRegistry {

	private ConcurrentMap<String, ScheduleRegister<?>> map;
	
	public ScheduleRegistryMock() {
		this.map = new ConcurrentHashMap<>();
	}
	
	@Override
	@ActivateRequestContext
	public void schedule(String code, Runnable command, long delay, TimeUnit unit) {
		ScheduleRegister<?> e = new ScheduleRegister<>(command, delay, unit, null);
		map.put(code, e);
	}

	@Override
	@ActivateRequestContext
	public <V> void schedule(String code, Callable<V> callable, long delay, TimeUnit unit) {
		ScheduleRegister<?> e = new ScheduleRegister<>(callable, delay, unit, null);
		map.put(code, e);
	}

	@Override
	@ActivateRequestContext
	public void scheduleAtFixedRate(String code, Runnable command, long initialDelay, long period, TimeUnit unit) {
		ScheduleRegister<?> e = new ScheduleRegister<>(command, initialDelay, period, unit, null);
		map.put(code, e);
	}

	@Override
	@ActivateRequestContext
	public void scheduleWithFixedDelay(String code, Runnable command, long initialDelay, long delay, TimeUnit unit) {

		ScheduleRegister<?> e = new ScheduleRegister<>(command, initialDelay, delay, unit, null);
		map.put(code, e);
		
	}

	public Object execute(String code) throws Exception {
		
		ScheduleRegister<?> v = map.get(code);
		
		if(v != null) {
			if(v.getCallable() != null) {
				return v.getCallable().call();
			}
			else
			if(v.getRunnable() != null) {
				v.getRunnable().run();
			}
		}
		
		return null;
	}

	@Override
	public ScheduledStatus<?> getScheduledStatus(String code) {
		ScheduleRegister<?> v = map.get(code);
		return v == null? null : v.getFuture();
	}

	@Override
	public boolean cancelScheduled(String code) {
		ScheduleRegister<?> v = map.get(code);
		
		if(v != null) {
			map.remove(code, v);
			return true;
		}
		
		return false;
	}
	
	private static class ScheduleRegister<V> {
		
		private Runnable runnable;
		
		private Callable<V> callable;
		
		private long initialDelay;
		
		private long period;
		
		private TimeUnit unit;
		
		private ScheduledStatus<V> future;

		public ScheduleRegister(Runnable runnable, long initialDelay, long period, TimeUnit unit,
				ScheduledStatus<V> future) {
			this(runnable, null, initialDelay, period, unit, future);
		}

		@SuppressWarnings("unused")
		public ScheduleRegister(Callable<V> callable, long initialDelay, long period, TimeUnit unit,
				ScheduledStatus<V> future) {
			this(null, callable, initialDelay, period, unit, future);
		}

		public ScheduleRegister(Runnable runnable, long period, TimeUnit unit,
				ScheduledStatus<V> future) {
			this(runnable, null, -1, period, unit, future);
		}

		public ScheduleRegister(Callable<V> callable, long period, TimeUnit unit,
				ScheduledStatus<V> future) {
			this(null, callable, -1, period, unit, future);
		}
		
		public ScheduleRegister(Runnable runnable, Callable<V> callable, long initialDelay, long period, TimeUnit unit,
				ScheduledStatus<V> future) {
			this.runnable = runnable;
			this.callable = callable;
			this.initialDelay = initialDelay;
			this.period = period;
			this.unit = unit;
			this.future = future;
		}

		@SuppressWarnings("unused")
		public Runnable getRunnable() {
			return runnable;
		}

		@SuppressWarnings("unused")
		public Callable<V> getCallable() {
			return callable;
		}

		@SuppressWarnings("unused")
		public long getInitialDelay() {
			return initialDelay;
		}

		@SuppressWarnings("unused")
		public long getPeriod() {
			return period;
		}

		@SuppressWarnings("unused")
		public TimeUnit getUnit() {
			return unit;
		}

		public ScheduledStatus<V> getFuture() {
			return future;
		}
		
		
	}
	
}
