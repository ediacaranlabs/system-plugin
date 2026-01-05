package br.com.uoutec.community.ediacaran.system.lock;

import javax.enterprise.inject.spi.BeanManager;

import br.com.uoutec.ediacaran.core.BeanFactory;
import br.com.uoutec.ediacaran.core.cdi.EntityContextPluginProviderImp;
import br.com.uoutec.ediacaran.core.plugins.EntityContextPluginProvider;

public class LockManagerBeanFactory implements BeanFactory{

	private EntityContextPluginProvider entityContextPluginProvider;
	
	private volatile LockManagerProvider lockManagerProvider;
	
	public LockManagerBeanFactory(BeanManager beanManager){
		this.entityContextPluginProvider = new EntityContextPluginProviderImp(beanManager);
	}
	
	@Override
	public Object getInstance() {
		
		if(lockManagerProvider == null) {
			synchronized (this) {
				this.lockManagerProvider = entityContextPluginProvider.getEntity(LockManagerProvider.class);
			}
		}
		
		return lockManagerProvider.getLockManager();
	}

	@Override
	public boolean acceptPluginContext(String value) {
		return true;
	}

}
