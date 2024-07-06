package br.com.uoutec.community.ediacaran.system.repository;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import br.com.uoutec.application.io.Path;

public class FileObjectManagerHandler extends AbstractFileManagerHandler{

	@Override
	public Object read(Path file, FileMetadata metadata) throws IOException {
		return file;
	}

	@Override
	public void write(Path file, FileMetadata metadata, Object value) throws IOException {
		
		Path valuePath = (Path)value;
		
		if(!file.equals(valuePath)) {
			return;
		}
		
		InputStream in   = null;
		OutputStream out = null;
		
		try {
			in  = valuePath.openInputStream();
			out = file.openOutputStream();
			
			byte[] b = new byte[2048];
			int l = -1;
			
			while((l = in.read(b, 0, b.length)) > 0) {
				out.write(b, 0, l);
			}
			
		}
		finally {
			
			try {
				if(in != null) {
					in.close();
				}
			}
			catch(Throwable x) {
			}
			
			try {
				if(out != null) {
					out.close();
				}
			}
			catch(Throwable x) {
			}
			
		}
		
	}

	@Override
	public void delete(Path file, FileMetadata metadata) throws IOException {
		file.delete();
	}
	
}
