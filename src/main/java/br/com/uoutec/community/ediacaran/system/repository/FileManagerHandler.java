package br.com.uoutec.community.ediacaran.system.repository;

import java.io.File;
import java.io.IOException;

import br.com.uoutec.application.io.Path;
import br.com.uoutec.application.io.Vfs;

public interface FileManagerHandler {

	default String toFilePath(String value) {
		
		if(value == null) {
			return null;
		}
		
		return value
				.replaceAll("\\\\+"             , "\\" + File.separator)
				.replaceAll("/+"                , "\\" + File.separator)
				.replaceAll(File.separator + "+", "\\" + File.separator);
		
		/*
		try {
			value = value.replaceAll("\\\\+", File.separator).replaceAll("/+", File.separator);
			File f = new File(value);
			f.getCanonicalFile();
			return f.toString();
		}
		catch(IOException e) {
			return null;
		}
		*/
	}
	
	FileMetadata toMetadata(Path base, Path file);

	default FileMetadata toMetadata(Path base, String path) {
		return toMetadata(base, Vfs.getPath(path));
	}
	
	Path toFile(Path base, FileMetadata omd);

	Object read(Path file, FileMetadata metadata) throws IOException;
	
	void write(Path file, FileMetadata metadata, Object value) throws IOException;
	
	void delete(Path file, FileMetadata metadata) throws IOException;
	
}
