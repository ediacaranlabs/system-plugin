package br.com.uoutec.community.ediacaran.system.entity;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import br.com.uoutec.application.scanner.DefaultScanner;
import br.com.uoutec.application.scanner.filter.AnnotationTypeFilter;
import br.com.uoutec.application.security.SecurityClassLoader;
import br.com.uoutec.application.security.SecurityThread;

public class EntityInheritanceLoader {

	@SuppressWarnings("unchecked")
	public List<Class<?>> loadEntities(String ... basePackage) {
		
		AnnotationTypeFilter filter = new AnnotationTypeFilter();
		filter.setExpression(Arrays
				.asList(EntityInheritance.class.getName()));

		DefaultScanner s = new DefaultScanner() {
			
			public SecurityClassLoader getClassLoader() throws IOException {
				return (SecurityClassLoader)((SecurityThread)SecurityThread.currentThread()).getSecurityContextClassLoader();
			}
			
		};
		
		s.setBasePackage(basePackage);
		
		s.addIncludeFilter(filter);
		s.scan();
		return s.getClassList();
	}
	
}
