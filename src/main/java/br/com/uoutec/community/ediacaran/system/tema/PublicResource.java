package br.com.uoutec.community.ediacaran.system.tema;

public class PublicResource {

	private String resource;
	
	private String path;

	public PublicResource(String resource, String path) {
		this.resource = resource;
		this.path = path;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	
}
