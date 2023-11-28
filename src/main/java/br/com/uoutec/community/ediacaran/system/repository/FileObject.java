package br.com.uoutec.community.ediacaran.system.repository;

import java.io.InputStream;
import java.io.OutputStream;

import br.com.uoutec.application.io.Path;

public class FileObject {

	private Path file;

	private boolean isTransient;
	
	public FileObject(Path file) {
		this(file, true);
	}
	
	public FileObject(Path file, boolean isTransient) {
		this.file = file;
		this.isTransient = isTransient;
		
		if(file == null || !file.exists()) {
			throw new IllegalStateException("file");
		}
		
	}
	
	public InputStream getInputStream() {
		//return new FileInputStream(file);
		return file.openInputStream();
	}
	
	public OutputStream getOutputStream() {
		//return new FileOutputStream(file);
		return file.openOutputStream();
	}

	public boolean isTransient() {
		return isTransient;
	}

}
