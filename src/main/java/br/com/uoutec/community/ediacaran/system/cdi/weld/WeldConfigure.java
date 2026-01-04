package br.com.uoutec.community.ediacaran.system.cdi.weld;

import java.lang.annotation.Annotation;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;

import br.com.uoutec.community.ediacaran.system.lock.LockManager;
import br.com.uoutec.community.ediacaran.system.lock.LockManagerBeanFactory;
import br.com.uoutec.ediacaran.core.cdi.BeanProxy;

public class WeldConfigure implements Extension {

	public void register(@Observes AfterBeanDiscovery bbd, BeanManager bm) {
		
		LockManagerBeanFactory lockManagerBeanFactory = new LockManagerBeanFactory(bm);
		bbd.addBean(new BeanProxy<LockManager>(lockManagerBeanFactory, LockManager.class) {
			
			public Class<? extends Annotation> getScope() {
				return RequestScoped.class;
			}
			
		});
		
	}
	
}