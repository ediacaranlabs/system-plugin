package br.com.uoutec.community.ediacaran.system.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.uoutec.application.ClassUtil;
import br.com.uoutec.application.scanner.DefaultScanner;
import br.com.uoutec.application.scanner.filter.AnnotationTypeFilter;
import br.com.uoutec.community.ediacaran.plugins.PublicBean;

@Singleton
public class EntityInheritanceManager implements PublicBean {

	public static final String PERMISSION_PREFIX = "entityInheritance.";
	
	private static final Logger logger = LoggerFactory.getLogger(EntityInheritanceManager.class);
	
	private ConcurrentMap<Class<?>, ConcurrentMap<String, Class<?>>> clazz;
	
	public EntityInheritanceManager(){
		this.clazz = new ConcurrentHashMap<Class<?>, ConcurrentMap<String,Class<?>>>();
	}
	
	public void loadEntities(List<Class<?>> clazzList) {
		
		SecurityManager sm = System.getSecurityManager();
		
		for (Class<?> clazz : clazzList) {
			EntityInheritance entityInheritance = 
					clazz.getAnnotation(EntityInheritance.class);
			
			String name   = entityInheritance.name();
			Class<?> base = entityInheritance.base();
			
			
			if(sm != null) {
				sm.checkPermission(new RuntimePermission(PERMISSION_PREFIX + base.getSimpleName() + ".register"));
			}
			
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

	public void removeEntities(List<Class<?>> clazzList) {
		
		SecurityManager sm = System.getSecurityManager();
		
		for (Class<?> clazz : clazzList) {
			EntityInheritance entityInheritance = 
					clazz.getAnnotation(EntityInheritance.class);
			
			String name   = entityInheritance.name();
			Class<?> base = entityInheritance.base();
			
			ConcurrentMap<String,Class<?>> map = this.clazz.get(base);
			
			if(map != null){
				
				if(sm != null) {
					sm.checkPermission(new RuntimePermission(PERMISSION_PREFIX + base.getSimpleName() + ".unregister"));
				}
				
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
	
	@SuppressWarnings("unchecked")
	public <T> T getInstance(Class<T> base, String name) 
			throws InstantiationException, IllegalAccessException{
		Class<?> type = this.getType(base, name);
		return (T)ClassUtil.getInstance(type == null? base : type);
	}
	
	public ConcurrentMap<String, Class<?>> getMap(Class<?> base){
		return this.clazz.get(base);
	}
	
	public static class EntityInheritanceUtilLoader {
		
		@SuppressWarnings("unchecked")
		public List<Class<?>> loadEntities(String ... basePackage) {
			
			AnnotationTypeFilter filter = new AnnotationTypeFilter();
			filter.setExpression(Arrays
					.asList(EntityInheritance.class.getName()));

			DefaultScanner s = new DefaultScanner();
			s.setBasePackage(basePackage);
			s.addIncludeFilter(filter);
			s.scan();
			return s.getClassList();
		}
		
	}
	
	@Target({ ElementType.METHOD, ElementType.TYPE })
	@Retention(RetentionPolicy.RUNTIME)
	public @interface EntityInheritance {
	
		public String name() default "";
		
		public Class<?> base();
		
	}
}
