package br.com.uoutec.community.ediacaran.system.lock;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.inject.Singleton;

import br.com.uoutec.community.ediacaran.plugins.PublicBean;

@Singleton
public class NamedLock implements PublicBean {

	private Object[] locks;
	
	private ConcurrentMap<String, LockCount> count;
	
	public NamedLock() {
		count = new ConcurrentHashMap<String, LockCount>();
		locks = new Object[100];
		
		for(int i=0;i<locks.length;i++) {
			locks[i] = new Object();
		}
	}
	
	public boolean lock(String name) {
		
		int index = Math.abs(name.hashCode() % locks.length);
		Object lock = locks[index];
		
		LockCount c = null;
		
		synchronized(lock) {
			
			c = count.get(name);
			
			if(c == null) {
				c = new LockCount(new ReentrantLock(), 1);
				count.put(name, c);
			}
			else {
				c.count++;
			}
			
		}
		
		c.lock.lock();
		
		return true;
	}

	public boolean tryLock(String name, long time, TimeUnit timeUnit) throws InterruptedException {
		
		int index = Math.abs(name.hashCode() % locks.length);
		Object lock = locks[index];
		LockCount c = null;
		
		synchronized(lock) {
			
			c = count.get(name);
			
			if(c == null) {
				c = new LockCount(new ReentrantLock(), 1);
				count.put(name, c);
			}
			else {
				c.count++;
			}
			
		}
		
		c.lock.tryLock(time, timeUnit);
		
		return true;
	}
	
	public void unlock(String name, boolean active) {
		
		int index = Math.abs(name.hashCode() % locks.length);
		Object lock = locks[index];
		LockCount c = null;
		
		synchronized(lock) {
			
			c = count.get(name);
			
			if(c == null) {
				throw new IllegalStateException("lock"); 
			}
			
			c.count--;
			
			if(c.count == 0) {
				count.remove(name);
			}
		}
		
		if(active) {
			c.lock.unlock();
		}
		
	}

	public static class LockCount{
		
		public Lock lock;
		
		public int count;

		public LockCount(Lock lock, int count) {
			this.lock = lock;
			this.count = count;
		}
		
		
	}
	
}
