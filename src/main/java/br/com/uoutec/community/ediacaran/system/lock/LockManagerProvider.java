package br.com.uoutec.community.ediacaran.system.lock;

import java.util.List;

import br.com.uoutec.ediacaran.core.plugins.PublicBean;

public interface LockManagerProvider extends PublicBean {

	void registerLockManager(String name, LockManager manager) throws LockManagerProviderException;

	void removeLockManager(String name);

	List<String> getLockManagerNames();
	
	LockManager getLockManager();
	
}
