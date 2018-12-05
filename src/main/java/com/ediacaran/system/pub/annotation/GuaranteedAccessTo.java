package com.ediacaran.system.pub.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.ediacaran.system.security.Privilege;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface GuaranteedAccessTo {

	Class<? extends Privilege> value() default Privilege.class;
	
}
