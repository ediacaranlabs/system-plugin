package br.com.uoutec.community.ediacaran.system.repository;

import java.util.Locale;

public class ObjectMetadata {

	
	private PathMetadata pathMetadata;
	
	private Locale locale;

	public ObjectMetadata(PathMetadata pathMetadata, Locale locale) {
		this.pathMetadata = pathMetadata;
		this.locale = locale;
	}

	public PathMetadata getPathMetadata() {
		return pathMetadata;
	}

	public Locale getLocale() {
		return locale;
	}
	
}
