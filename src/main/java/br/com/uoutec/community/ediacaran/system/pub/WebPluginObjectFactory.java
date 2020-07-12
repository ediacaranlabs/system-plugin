package br.com.uoutec.community.ediacaran.system.pub;

import java.util.Properties;

import org.brandao.brutos.ObjectFactory;

import br.com.uoutec.community.ediacaran.plugins.EntityContextPlugin;

public class WebPluginObjectFactory implements ObjectFactory{

	@Override
	public Object getBean(String name) {
		return EntityContextPlugin.getEntity(name, Object.class);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object getBean(Class clazz) {
		return EntityContextPlugin.getEntity(clazz);
	}

	@Override
	public void configure(Properties properties) {
	}

	@Override
	public void destroy() {
	}

}
