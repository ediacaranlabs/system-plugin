package br.com.uoutec.community.ediacaran.system.entity;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.uoutec.application.ClassUtil;
import br.com.uoutec.application.security.ContextSystemSecurityCheck;
import br.com.uoutec.application.security.RuntimeSecurityPermission;
import br.com.uoutec.ediacaran.core.plugins.PublicBean;

@Singleton
public class EntityInheritanceManager implements PublicBean {

	public static final String PERMISSION_PREFIX = "entityInheritance.";
	
	private static final Logger logger = LoggerFactory.getLogger(EntityInheritanceManager.class);
	
	private ConcurrentMap<Class<?>, ConcurrentMap<String, Class<?>>> clazz;
	
	public EntityInheritanceManager(){
		this.clazz = new ConcurrentHashMap<Class<?>, ConcurrentMap<String,Class<?>>>();
	}
	
	public <T> void register(Class<? extends T> type, String name, Class<T> base) {

		ContextSystemSecurityCheck.checkPermission(
				new RuntimeSecurityPermission(PERMISSION_PREFIX + base.getSimpleName() + ".register")
		);
		
		ConcurrentMap<String,Class<?>> map = this.clazz.get(base);
		
		if(map == null){
			map = new ConcurrentHashMap<String, Class<?>>();
			this.clazz.put(base, map);
		}
		
		if(map.putIfAbsent(name, type) != null){
			if(logger.isWarnEnabled()) {
				logger.warn("entity has been added: {} {}", name, type);
			}
		}
		else 
		if(logger.isTraceEnabled()){
			logger.trace("added entitie: {} {}", name, type);
		}
		
	}
	
	/*
	public void loadEntities(List<Class<?>> clazzList) {
		
		for (Class<?> clazz : clazzList) {
			EntityInheritance entityInheritance = 
					clazz.getAnnotation(EntityInheritance.class);
			
			String name   = entityInheritance.name();
			Class<?> base = entityInheritance.base();
			
			
			ContextSystemSecurityCheck.checkPermission(new RuntimeSecurityPermission(PERMISSION_PREFIX + base.getSimpleName() + ".register"));
			
			ConcurrentMap<String,Class<?>> map = this.clazz.get(base);
			
			if(map == null){
				map = new ConcurrentHashMap<String, Class<?>>();
				this.clazz.put(base, map);
			}
			
			if(map.putIfAbsent(name, clazz) != null){
				if(logger.isWarnEnabled()) {
					logger.warn("entity has been added: {} {}", name, clazz);
				}
			}
			else 
			if(logger.isTraceEnabled()){
				logger.trace("added entitie: {} {}", name, clazz);
			}
			
		}
		
	}
    */
	
	public void remove(String name, Class<?> base) {
		
		ContextSystemSecurityCheck.checkPermission(
				new RuntimeSecurityPermission(PERMISSION_PREFIX + base.getSimpleName() + ".unregister")
		);
		
		ConcurrentMap<String,Class<?>> map = this.clazz.get(base);
		
		if(map != null){
			
			ContextSystemSecurityCheck.checkPermission(new RuntimeSecurityPermission(PERMISSION_PREFIX + base.getSimpleName() + ".unregister"));
			
			map.remove(name, clazz);
			
			logger.trace("removed entitie: {} {}", name, clazz);
			
			if(map.isEmpty()) {
				this.clazz.remove(base);
				if(logger.isTraceEnabled()) {
					logger.trace("removed: {} {}", name, clazz);
				}
			}
			
		}
		
	}
	
	/*
	public void removeEntities(List<Class<?>> clazzList) {
		
		for (Class<?> clazz : clazzList) {
			EntityInheritance entityInheritance = 
					clazz.getAnnotation(EntityInheritance.class);
			
			String name   = entityInheritance.name();
			Class<?> base = entityInheritance.base();
			
			ConcurrentMap<String,Class<?>> map = this.clazz.get(base);
			
			if(map != null){
				
				ContextSystemSecurityCheck.checkPermission(new RuntimeSecurityPermission(PERMISSION_PREFIX + base.getSimpleName() + ".unregister"));
				
				map.remove(name, clazz);
				
				logger.trace("removed entitie: {} {}", name, clazz);
				
				if(map.isEmpty()) {
					this.clazz.remove(base);
					if(logger.isTraceEnabled()) {
						logger.trace("removed: {} {}", name, clazz);
					}
				}
				
			}
			
		}		
		
	}
	*/
	
	public Class<?> getType(Class<?> base, String name) {
		
		ConcurrentMap<String,Class<?>> map = this.clazz.get(base);
		
		if(base == null){
			return null;
		}
		
		Class<?> c = map.get(name);
		
		if(c == null){
			return null;
		}
		
		return c;
	}
	
	public <T> T getInstance(Class<T> base, String name) 
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		return getInstance(base, name, new Class<?>[] {}, new Object[]{});	
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getInstance(Class<T> base, String name, Class<?>[] types, Object[] params) 
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		Class<?> type = this.getType(base, name);
		
		return (T)ClassUtil.getInstance(type == null? base : type, types, params);
	}
	
	public ConcurrentMap<String, Class<?>> getMap(Class<?> base){
		return this.clazz.get(base);
	}
	
}
