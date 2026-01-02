package br.com.uoutec.community.ediacaran.system.lock;

import java.util.concurrent.TimeUnit;

public interface LockManager {

	void lock(String name);

	void tryLock(String name, long time, TimeUnit timeUnit);

	void unlock(String name);
	
}
