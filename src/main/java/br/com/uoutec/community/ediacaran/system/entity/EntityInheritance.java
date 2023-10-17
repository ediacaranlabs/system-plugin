package br.com.uoutec.community.ediacaran.system.entity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface EntityInheritance {

	public String name() default "";
	
	public Class<?> base();
	
}
