package br.com.uoutec.community.ediacaran.system.repository;

import java.util.Locale;

public interface ObjectListener {
	
	default void beforeLoad(String id, Locale locale) {};
	
	default void afterLoad(String id, Locale locale, Object obj) {};
	
	default void beforeRegister(String id, Locale locale, Object object) {};
	
	default void afterRegister(String id, Locale locale, Object object) {};

	default void beforeUnregister(String id, Locale locale) {};
	
	default void afterUnregister(String id, Locale locale) {};
		
}
