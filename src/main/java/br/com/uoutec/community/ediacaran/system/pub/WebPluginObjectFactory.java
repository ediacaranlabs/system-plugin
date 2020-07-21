package br.com.uoutec.community.ediacaran.system.pub;

import java.util.Properties;

import org.brandao.brutos.ObjectFactory;

import br.com.uoutec.community.ediacaran.plugins.EntityContextPlugin;
import br.com.uoutec.community.ediacaran.plugins.EntityContextPluginProvider;

public class WebPluginObjectFactory implements ObjectFactory{

	private EntityContextPluginProvider entityContextPluginProvider;
	
	@Override
	public Object getBean(String name) {
		return entityContextPluginProvider.getEntity(name, Object.class);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object getBean(Class clazz) {
		return entityContextPluginProvider.getEntity(clazz);
	}

	@Override
	public void configure(Properties properties) {
		entityContextPluginProvider = EntityContextPlugin.getEntity(EntityContextPluginProvider.class);
	}

	@Override
	public void destroy() {
		entityContextPluginProvider = null;
	}

}
