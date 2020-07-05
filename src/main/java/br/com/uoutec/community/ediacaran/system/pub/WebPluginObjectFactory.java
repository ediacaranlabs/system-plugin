package br.com.uoutec.community.ediacaran.system.pub;

import java.util.Properties;

import org.brandao.brutos.ObjectFactory;

import br.com.uoutec.community.ediacaran.plugins.EntityContextPlugin;

public class WebPluginObjectFactory implements ObjectFactory{

	private EntityContextPlugin entityContext;
	
	@Override
	public Object getBean(String name) {
		return entityContext.getEntity(name, Object.class);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object getBean(Class clazz) {
		return entityContext.getEntity(clazz);
	}

	@Override
	public void configure(Properties properties) {
		this.entityContext = WebPluginContextListener.getCurrentEntityContextPlugin();
	}

	@Override
	public void destroy() {
		this.entityContext = null;
	}

}
