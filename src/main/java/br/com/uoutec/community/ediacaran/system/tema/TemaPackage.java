package br.com.uoutec.community.ediacaran.system.tema;

import java.util.concurrent.ConcurrentMap;

public class TemaPackage {

	private String name;
	
	private String path;
	
	private ConcurrentMap<String, Component> tagTemplates;

	public TemaPackage(String name, String path, ConcurrentMap<String, Component> tagTemplates) {
		this.name = name;
		this.path = path;
		this.tagTemplates = tagTemplates;
	}

	public String getName() {
		return name;
	}

	public String getPath() {
		return path;
	}

	public ConcurrentMap<String, Component> getTagTemplates() {
		return tagTemplates;
	}
	
}
