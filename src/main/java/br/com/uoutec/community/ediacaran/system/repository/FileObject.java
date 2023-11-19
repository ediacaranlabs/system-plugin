package br.com.uoutec.community.ediacaran.system.repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

import br.com.uoutec.community.ediacaran.io.FileSystem;

public class FileObject {

	private File file;

	private FileSystem fileSystem = new FileSystem();
	
	private boolean isTransient;
	
	public FileObject(File file) {
		this(file, true);
	}
	
	public FileObject(File file, boolean isTransient) {
		this.file = file;
		this.isTransient = isTransient;
		
		if(file == null || !file.exists()) {
			throw new IllegalStateException("file");
		}
		
	}
	
	public InputStream getInputStream() throws FileNotFoundException {
		//return new FileInputStream(file);
		return fileSystem.getInputStream(file);
	}
	
	public OutputStream getOutputStream() throws FileNotFoundException {
		//return new FileOutputStream(file);
		return fileSystem.getOutputStream(file);
	}

	public boolean isTransient() {
		return isTransient;
	}

}
