package br.com.uoutec.community.ediacaran.system.repository;

import java.util.Map;

public class FileMetadata {
	
	private String path;
	
	private String name;
	
	private String type;

	private Map<String,Object> extMetadata;

	public FileMetadata(FileMetadata value) {
		this(value.path, value.name, value.type, value.extMetadata);
	}
	
	public FileMetadata(String path, String name, String type, Map<String, Object> extMetadata) {
		this.path = path;
		this.name = name;
		this.type = type;
		this.extMetadata = extMetadata;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public Object getExtMetadata(String name) {
		return extMetadata.get(name);
	}
	
}
