package br.com.uoutec.community.ediacaran.system.repository;

public class PathMetadata{
	
	public String driver;
	
	public String path;
	
	public String id;

	public PathMetadata(String driver, String path, String id) {
		this.driver = driver;
		this.path = path;
		this.id = id;
	}

	public String getFullId() {
		StringBuilder sb = new StringBuilder();
		
		sb
			.append(path == null || path.isEmpty()? "" : path    )
			.append(id == null   || id.isEmpty()  ? "" : "/" + id);
		
		return sb.length() == 0? null : sb.toString();
	}
	
	public String getGlobalId() {
		String fullId = getFullId();
		
		if(fullId == null) {
			return driver;
		}
		else {
			return driver + fullId;
		}
		
	}
	
	public String getDriver() {
		return driver;
	}

	public String getPath() {
		return path;
	}

	public String getId() {
		return id;
	}
	
}
