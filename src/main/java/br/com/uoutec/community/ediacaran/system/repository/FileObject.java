package br.com.uoutec.community.ediacaran.system.repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class FileObject {

	private File file;

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
		return new FileInputStream(file);
	}
	
	public OutputStream getOutputStream() throws FileNotFoundException {
		return new FileOutputStream(file);
	}

	public boolean isTransient() {
		return isTransient;
	}

}
