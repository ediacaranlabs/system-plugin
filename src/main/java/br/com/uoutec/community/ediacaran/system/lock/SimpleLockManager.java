package br.com.uoutec.community.ediacaran.system.lock;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SimpleLockManager implements LockManager {

	private Object[] ctrlLocks;

	private ConcurrentMap<String, LockEntry> locks;
	
	public SimpleLockManager() {
		this.ctrlLocks = new Object[256];
		
		for(int i=0;i<this.ctrlLocks.length;i++) {
			this.ctrlLocks[i] = new Object();
		}
		
		this.locks = new ConcurrentHashMap<>();
	}
	
	@Override
	public void lock(String name) {
		Lock lock = createLock(name);
		lock.lock();
	}

	@Override
	public boolean tryLock(String name, long time, TimeUnit timeUnit) throws InterruptedException {
		
		Lock lock = createLock(name);
		try {
			return lock.tryLock(time, timeUnit);
		}
		catch(InterruptedException ex) {
			destroyLock(name);
			throw ex;
		}
		
	}

	@Override
	public void unlock(String name) {
		Lock lock = destroyLock(name);
		lock.unlock();
	}

	private Lock createLock(String name) {
		
		Object crtlock = ctrlLocks[name.hashCode() % ctrlLocks.length];
		LockEntry lock = null;
		
		synchronized (crtlock) {
			
			lock = locks.get(name);
			
			if(lock == null) {
				lock = new LockEntry(new ReentrantLock(), new AtomicInteger(0));
				locks.put(name, lock);
			}
			
			lock.count.incrementAndGet();
		}
		
		return lock.lock;
	}

	private Lock destroyLock(String name) {
		
		Object crtlock = ctrlLocks[name.hashCode() % ctrlLocks.length];
		LockEntry lock = null;
		
		synchronized (crtlock) {
			lock = locks.get(name);
			
			if(lock == null) {
				throw new RuntimeException("lock not found: " + name);
			}
			
			int count = lock.count.decrementAndGet();
			
			if(count == 0) {
				locks.remove(name);
			}
		}
		
		return lock.lock;
	}
	
	private static class LockEntry {
		
		public Lock lock;
		
		public AtomicInteger count;

		public LockEntry(Lock lock, AtomicInteger count) {
			super();
			this.lock = lock;
			this.count = count;
		}
		
	}
	
}
