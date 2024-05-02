package br.com.uoutec.community.ediacaran.system.entity;

import java.util.Arrays;
import java.util.List;

import br.com.uoutec.application.scanner.filter.AnnotationTypeFilter;
import br.com.uoutec.ediacaran.core.EdiacaranScanner;

public class EntityInheritanceLoader {

	public List<Class<?>> loadEntities(String ... basePackage) {
		
		AnnotationTypeFilter filter = new AnnotationTypeFilter();
		filter.setExpression(Arrays.asList(EntityInheritance.class.getName()));

		EdiacaranScanner scanner = new EdiacaranScanner();
		scanner.setBasePackage(basePackage);
		scanner.addIncludeFilter(filter);
		scanner.scan();
		return scanner.getClassList();
	}
	
}
