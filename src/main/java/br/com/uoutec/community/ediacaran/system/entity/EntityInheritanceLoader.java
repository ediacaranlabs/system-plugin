package br.com.uoutec.community.ediacaran.system.entity;

import java.util.Arrays;
import java.util.List;

import br.com.uoutec.application.scanner.DefaultScanner;
import br.com.uoutec.application.scanner.filter.AnnotationTypeFilter;

public class EntityInheritanceLoader {

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
