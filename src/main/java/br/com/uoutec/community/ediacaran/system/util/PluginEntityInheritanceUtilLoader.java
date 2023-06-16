package br.com.uoutec.community.ediacaran.system.util;

import java.util.List;

import br.com.uoutec.community.ediacaran.system.util.EntityInheritanceManager.EntityInheritanceUtilLoader;

@Deprecated
public class PluginEntityInheritanceUtilLoader extends EntityInheritanceUtilLoader{

	private ClassLoader classLoader;
	
	public PluginEntityInheritanceUtilLoader() {
		this(Thread.currentThread().getContextClassLoader());
	}
	
	public PluginEntityInheritanceUtilLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}
	
	public List<Class<?>> loadEntities(String ... basePackage) {
		ClassLoader oldClassLoader = Thread.currentThread().getContextClassLoader();
		try {
			Thread.currentThread().setContextClassLoader(classLoader);
			return super.loadEntities(basePackage);
		}
		finally {
			Thread.currentThread().setContextClassLoader(oldClassLoader);
		}
	}
	
}
