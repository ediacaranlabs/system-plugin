package br.com.uoutec.community.ediacaran.system.lock;

import java.util.concurrent.TimeUnit;

public interface LockManager {

	void lock(String name);

	boolean tryLock(String name, long time, TimeUnit timeUnit) throws InterruptedException;

	void unlock(String name);
	
}
