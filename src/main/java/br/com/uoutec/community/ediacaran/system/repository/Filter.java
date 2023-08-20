package br.com.uoutec.community.ediacaran.system.repository;

import java.util.Locale;

@FunctionalInterface
public interface Filter {

	boolean accept(String path, String name,Locale locale, boolean recursive, ObjectMetadata omd);
	
}
