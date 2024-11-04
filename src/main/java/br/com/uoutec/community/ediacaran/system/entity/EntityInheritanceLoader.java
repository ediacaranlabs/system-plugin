package br.com.uoutec.community.ediacaran.system.entity;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import br.com.uoutec.application.scanner.filter.AnnotationTypeFilter;
import br.com.uoutec.ediacaran.core.EdiacaranScanner;

public class EntityInheritanceLoader {

	@Inject
	private EntityInheritanceManager entityInheritanceManager;
	
	@SuppressWarnings("unchecked")
	public void loadEntities(String ... basePackage) {
		
		List<Class<?>> clazzList = getClassList(basePackage);
		
		for (Class<?> clazz : clazzList) {
			EntityInheritance entityInheritance = 
					clazz.getAnnotation(EntityInheritance.class);
			
			String name   = entityInheritance.name();
			Class<?> base = entityInheritance.base();
			
			entityInheritanceManager.register((Class<? extends Object>)clazz, name, (Class<Object>)base);
			
		}
		
	}

	public void removeEntities(String ... basePackage) {
		
		List<Class<?>> clazzList = getClassList(basePackage);
		
		for (Class<?> clazz : clazzList) {
			EntityInheritance entityInheritance = 
					clazz.getAnnotation(EntityInheritance.class);
			
			String name   = entityInheritance.name();
			Class<?> base = entityInheritance.base();
			
			entityInheritanceManager.remove(name, base);
			
		}
		
	}
	
	public List<Class<?>> getClassList(String ... basePackage) {
		
		AnnotationTypeFilter filter = new AnnotationTypeFilter();
		filter.setExpression(Arrays.asList(EntityInheritance.class.getName()));

		EdiacaranScanner scanner = new EdiacaranScanner();
		scanner.setBasePackage(basePackage);
		scanner.addIncludeFilter(filter);
		scanner.scan();
		return scanner.getClassList();
	}
	
}
