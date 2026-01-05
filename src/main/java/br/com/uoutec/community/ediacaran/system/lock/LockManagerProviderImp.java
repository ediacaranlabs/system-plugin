package br.com.uoutec.community.ediacaran.system.lock;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Singleton;

import br.com.uoutec.application.security.ContextSystemSecurityCheck;
import br.com.uoutec.application.security.RuntimeSecurityPermission;
import br.com.uoutec.ediacaran.core.plugins.PluginConfiguration;
import br.com.uoutec.ediacaran.core.plugins.PluginType;

@Singleton
public class LockManagerProviderImp implements LockManagerProvider {

	private ConcurrentMap<String, LockManager> map;
	
	private PluginConfiguration pluginConfiguration;

	private LockManager defaultLockManager;
	
	public LockManagerProviderImp() {
	}
	
	@Inject
	public LockManagerProviderImp(PluginType pluginType) {
		this.pluginConfiguration = pluginType.getConfiguration();
		this.defaultLockManager = new SimpleLockManager();
		this.map = new ConcurrentHashMap<>();
	}
	
	@Override
	public void registerLockManager(String name, LockManager manager) throws LockManagerProviderException {
	
		ContextSystemSecurityCheck.checkPermission(new RuntimeSecurityPermission("system.register.lock_manager.register"));
		
		if(map.putIfAbsent(name, manager) != null) {
			throw new LockManagerProviderException();
		}
		
	}

	@Override
	public void removeLockManager(String name) {

		ContextSystemSecurityCheck.checkPermission(new RuntimeSecurityPermission("system.register.lock_manager.remove"));
		
		map.remove(name);
	}

	@Override
	public List<String> getLockManagerNames() {
		return map.keySet().stream().collect(Collectors.toList());
	}

	@Override
	public LockManager getLockManager() {
		String name = pluginConfiguration.getString("lock_manager");
		
		LockManager lockManager = map.get(name);
		
		return lockManager == null? defaultLockManager : lockManager;
	}

}
