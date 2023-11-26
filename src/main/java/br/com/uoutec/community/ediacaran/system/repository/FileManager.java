package br.com.uoutec.community.ediacaran.system.repository;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import br.com.uoutec.application.io.Path;

public class FileManager {

	private static final String PATH_FORMAT = "(\\/[a-z][a-z0-9]+([_-][a-z0-9]+)*)+";
	
	public static final String BASE_PATH = "/";
	
	private Pattern pathPattern = Pattern.compile(PATH_FORMAT);

	private FileManagerHandler fileManagerHandler;
	
	private Path basePath;
	
	public FileManager(Path basePath, FileManagerHandler fileManagerHandler) {
		this.basePath = basePath;
		this.fileManagerHandler = fileManagerHandler;
	}
	
	public FileMetadata toMetadata(Path base, String path) {
		return fileManagerHandler.toMetadata(base, path);
	}

	public FileMetadata toMetadata(Path base, Path file) {
		return fileManagerHandler.toMetadata(base, file);
	}
	
	public FileMetadata unique(){
		return unique(null, null);
	}
	
	public FileMetadata unique(Filter filter){
		return unique(null, true, filter);
	}

	public FileMetadata unique(String path, Filter filter){
		return unique(path, true, filter);
	}
	
	public FileMetadata unique(String path, boolean recursive, Filter filter){
		
		List<FileMetadata> list = list(path, recursive, filter);
		
		if(list.size() > 1) {
			throw new IllegalStateException("found: " + list.size());
		}
		
		return list.isEmpty()? null : list.get(0);
		
	}
	
	public List<FileMetadata> list(){
		return list(null);
	}
	
	public List<FileMetadata> list(Filter filter){
		return list(null, true, filter);
	}

	public List<FileMetadata> list(String path, Filter filter){
		return list(path, true, filter);
	}
	
	public List<FileMetadata> list(String path, boolean recursive, Filter filter){
		
		Path base;
		
		if(path != null && !BASE_PATH.equals(path)) {
			if(!pathPattern.matcher(path).matches()) {
				throw new IllegalStateException("invalid path: " + path);
			}
			
			path = fileManagerHandler.toFilePath(path);
			base = basePath.getPath(path);
		}
		else {
			base = basePath;
		}
		
		List<FileMetadata> list = new LinkedList<FileMetadata>();
		list(list, base, filter, recursive);
		return list;
	}
	
	private void list(List<FileMetadata> result, Path path, Filter filter, boolean recursive) {
		
		Path[] l = path.getFiles();
		
		if(l == null) {
			return;
		}
		
		for(Path f: l) {
			if(f.isDirectory() && recursive) {
				list(result, f, filter, recursive);
			}
			else
			if(f.isFile()) {
				FileMetadata omd = fileManagerHandler.toMetadata(this.basePath, f);
				if(omd != null && (filter == null || filter.accept(omd))) {
					result.add(omd);
				}
			}
		}
	}
	
	/* load */

	public FileValue get(FileMetadata fmd) throws IOException {
		
		Path file = fileManagerHandler.toFile(basePath, fmd);

		if(!file.normalizePath().getAbsolutePath().getFullName().startsWith(basePath.normalizePath().getAbsolutePath().getFullName())){
			throw new IOException("invalid path: " + fmd);
		}
		
		if(!file.exists() || !file.canRead()) {
			return null;
		}

		Object object = fileManagerHandler.read(file, fmd);
		
		return object == null? null : new FileValue(file, object);
		
	}
	
	/* persist */
	
	public Path persist(FileMetadata fmd, Object object) throws IOException {
		
		Path file = fileManagerHandler.toFile(basePath, fmd);
		
		if(!file.normalizePath().getAbsolutePath().getFullName().startsWith(basePath.normalizePath().getAbsolutePath().getFullName())){
			throw new IOException("invalid path: " + fmd);
		}
		
		file.getParent().mkdirs();
		fileManagerHandler.write(file, fmd, object);
		return file;
	}
	
	/* delete */
	
	public void delete(FileMetadata fmd) throws IOException {
		
		Path file = fileManagerHandler.toFile(basePath, fmd);
		
		if(!file.normalizePath().getAbsolutePath().getFullName().startsWith(basePath.normalizePath().getAbsolutePath().getFullName())){
			throw new IOException("invalid path: " + fmd);
		}
		
		fileManagerHandler.delete(file, fmd);
		
		delete(basePath, file.getParent());
	}
	
	private void delete(Path base, Path file) throws IOException {
		
		if(file.normalizePath().getFullName().equals(base.normalizePath().getFullName())) {
			return;
		}
		
		file.delete();
		
		Path parent = file.getParent();
		
		//if(parent.listFiles(e->e.isFile()).length == 0) {
		if(parent.getFiles(e->e.isFile()).length == 0) {
			delete(base, parent);
		}
		
	}
	
	@FunctionalInterface
	public static interface Filter {
		
		boolean accept(FileMetadata omd);
		
	}
	
	public static class FileValue {
		
		private Path file;
		
		private long lastModified;
		
		private Object object;

		public FileValue(Path file, Object object) {
			this.file = file;
			this.lastModified = object == null? -1 : file.lastModified();
			this.object = object;
		}
		
		public boolean isValid() {
			return lastModified == file.lastModified();
		}

		public Path getFile() {
			return file;
		}

		public long getLastModified() {
			return lastModified;
		}

		public Object getObject() {
			return object;
		}
		
	}

}
