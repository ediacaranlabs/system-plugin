package br.com.uoutec.community.ediacaran.system.schedule;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.enterprise.context.control.ActivateRequestContext;
import javax.inject.Inject;
import javax.inject.Singleton;

import br.com.uoutec.community.ediacaran.system.lock.LockManager;

@Singleton
public class ScheduleRegistryImp implements ScheduleRegistry {

	private static final String LOCK =  ScheduleRegistry.class.getSimpleName() + ":";
	
	private final ScheduledExecutorService scheduler;
	
	private ConcurrentMap<String, ScheduleRegister<?>> map;
	
	@Inject
	private LockManager lockManager;

	public ScheduleRegistryImp() {
		this.scheduler = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors()*8);
		this.map = new ConcurrentHashMap<>();
	}
	
	@Override
	@ActivateRequestContext
	public void schedule(String code, Runnable command, long delay, TimeUnit unit) {
		
		lockManager.lock(LOCK + code.toLowerCase());
		try {
			ScheduleRegister<?> e = map.get(code);
			
			if(e == null || e.getFuture().isCancelled() || e.getFuture().isDone()) {
				ScheduledFuture<?> future = scheduler.schedule(command, delay, unit);
				e = new ScheduleRegister<>(command, delay, unit, new ScheduledStatusImp<>(future));
				map.put(code, e);
			}
			else{
				
				e.getFuture().cancel(true);
				
				if(!e.getFuture().isCancelled() && !e.getFuture().isDone()) {
					throw new IllegalStateException(code);
				}
				
				ScheduledFuture<?> future = scheduler.schedule(command, delay, unit);
				e = new ScheduleRegister<>(command, delay, unit, new ScheduledStatusImp<>(future));
				map.put(code, e);
				
			}
		}
		finally {
			lockManager.unlock(LOCK + code.toLowerCase());
		}
		
	}

	@Override
	@ActivateRequestContext
	public <V> void schedule(String code, Callable<V> callable, long delay, TimeUnit unit) {

		lockManager.lock(LOCK + code.toLowerCase());
		try {
			ScheduleRegister<?> e = map.get(code);
			
			if(e == null || e.getFuture().isCancelled() || e.getFuture().isDone()) {
				ScheduledFuture<V> future = scheduler.schedule(callable, delay, unit);
				e = new ScheduleRegister<>(callable, delay, unit, new ScheduledStatusImp<>(future));
				map.put(code, e);
			}
			else{
				
				e.getFuture().cancel(true);
				
				if(!e.getFuture().isCancelled() && !e.getFuture().isDone()) {
					throw new IllegalStateException(code);
				}
				
				ScheduledFuture<V> future = scheduler.schedule(callable, delay, unit);
				e = new ScheduleRegister<>(callable, delay, unit, new ScheduledStatusImp<>(future));
				map.put(code, e);
				
			}
		}
		finally {
			lockManager.unlock(LOCK + code.toLowerCase());
		}
		
	}

	@Override
	@ActivateRequestContext
	public void scheduleAtFixedRate(String code, Runnable command, long initialDelay, long period, TimeUnit unit) {

		lockManager.lock(LOCK + code.toLowerCase());
		try {
			ScheduleRegister<?> e = map.get(code);
			
			if(e == null || e.getFuture().isCancelled() || e.getFuture().isDone()) {
				ScheduledFuture<?> future = scheduler.scheduleAtFixedRate(command, initialDelay, period, unit);
				e = new ScheduleRegister<>(command, initialDelay, period, unit, new ScheduledStatusImp<>(future));
				map.put(code, e);
			}
			else{
				
				e.getFuture().cancel(true);
				
				if(!e.getFuture().isCancelled() && !e.getFuture().isDone()) {
					throw new IllegalStateException(code);
				}
				
				ScheduledFuture<?> future = scheduler.scheduleAtFixedRate(command, initialDelay, period, unit);
				e = new ScheduleRegister<>(command, initialDelay, period, unit, new ScheduledStatusImp<>(future));
				map.put(code, e);
				
			}
		}
		finally {
			lockManager.unlock(LOCK + code.toLowerCase());
		}
		
	}

	@Override
	@ActivateRequestContext
	public void scheduleWithFixedDelay(String code, Runnable command, long initialDelay, long delay, TimeUnit unit) {

		lockManager.lock(LOCK + code.toLowerCase());
		try {
			ScheduleRegister<?> e = map.get(code);
			
			if(e == null || e.getFuture().isCancelled() || e.getFuture().isDone()) {
				ScheduledFuture<?> future = scheduler.scheduleWithFixedDelay(command, initialDelay, delay, unit);
				e = new ScheduleRegister<>(command, initialDelay, delay, unit, new ScheduledStatusImp<>(future));
				map.put(code, e);
			}
			else{
				
				e.getFuture().cancel(true);
				
				if(!e.getFuture().isCancelled() && !e.getFuture().isDone()) {
					throw new IllegalStateException(code);
				}
				
				ScheduledFuture<?> future = scheduler.scheduleWithFixedDelay(command, initialDelay, delay, unit);
				e = new ScheduleRegister<>(command, initialDelay, delay, unit, new ScheduledStatusImp<>(future));
				map.put(code, e);
				
			}
		}
		finally {
			lockManager.unlock(LOCK + code.toLowerCase());
		}
		
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
			boolean result = v.getFuture().cancel(true);
			map.remove(code, v);
			return result;
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
