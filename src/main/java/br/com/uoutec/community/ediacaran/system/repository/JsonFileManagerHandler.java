package br.com.uoutec.community.ediacaran.system.repository;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.uoutec.application.io.Path;
import br.com.uoutec.community.ediacaran.system.util.DataUtil.ClassTypeAdapter;

public class JsonFileManagerHandler extends AbstractFileManagerHandler{

	private static final Gson gson;
	
	static{
		gson = new GsonBuilder()
        .excludeFieldsWithModifiers(Modifier.TRANSIENT | Modifier.STATIC)
        .registerTypeAdapter(Class.class, new ClassTypeAdapter())
        .create();		
	}

	@Override
	public Object read(Path file, FileMetadata metadata) throws IOException {

		try(Reader reader = getReader(file)){
			return  gson.fromJson(reader, Object.class);
		}
		
	}

	private Reader getReader(Path file) throws FileNotFoundException {
		//return new InputStreamReader(new java.io.FileInputStream(file), StandardCharsets.UTF_8);
		return new InputStreamReader(file.openInputStream(), StandardCharsets.UTF_8);
	}
	
	@Override
	public void write(Path file, FileMetadata metadata, Object value) throws FileNotFoundException, IOException {
		
		try(Writer stream = getWriter(file)){
			gson.toJson(value, Object.class, stream);
			stream.flush();
		}
		
	}

	public Writer getWriter(Path file) throws FileNotFoundException {
		//return new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
		return new OutputStreamWriter(file.openOutputStream(), StandardCharsets.UTF_8);
	}

	@Override
	public void delete(Path file, FileMetadata metadata) throws IOException {
		file.delete();
	}
	
}
