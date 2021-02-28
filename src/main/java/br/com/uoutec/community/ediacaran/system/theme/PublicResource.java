package br.com.uoutec.community.ediacaran.system.theme;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((resource == null) ? 0 : resource.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PublicResource other = (PublicResource) obj;
		if (resource == null) {
			if (other.resource != null)
				return false;
		} else if (!resource.equals(other.resource))
			return false;
		return true;
	}

	
}
