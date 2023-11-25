package br.com.uoutec.community.ediacaran.system.repository;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.uoutec.application.io.Path;
import br.com.uoutec.application.io.Vfs;

public abstract class AbstractFileManagerHandler implements FileManagerHandler {

	private static final String PATH_FORMAT = "(\\/[a-z][a-z0-9]+(_[a-z0-9]+)*)+";
	
	private static final String ID_FORMAT = "[a-z][a-z0-9]+(_[a-z0-9]+)*";

	private static final String TYPE_FORMAT = "[0-9a-z]{4,10}";
	
	private static final String LOCALE_FORMAT = "(default)|([a-z]{2,2}_[A-Z]{2,2})";

	private static final String FILENAME_FORMAT = "(" + ID_FORMAT + ")_(" + TYPE_FORMAT + ")_(" + LOCALE_FORMAT + ").obj";
	
	private Pattern idPattern = Pattern.compile(ID_FORMAT);
	
	private Pattern pathPattern = Pattern.compile(PATH_FORMAT);
	
	private Pattern typePattern = Pattern.compile(TYPE_FORMAT);
	
	private Pattern fileNamePattern = Pattern.compile(FILENAME_FORMAT);
	
	@Override
	public FileMetadata toMetadata(Path base, Path file) {
		
		String fileName = file.getName();
		
		Matcher m = fileNamePattern.matcher(fileName);
		
		if(!m.matches()) {
			return null;
		}
		
		m.reset();
		
		if(!m.find()) {
			throw new IllegalStateException("id");
		}
		
		String baseName = base.normalizePath().getAbsolutePath().getFullName();
		String pathName = file.getParent().normalizePath().getAbsolutePath().getFullName();
		
		if(!pathName.startsWith(baseName)) {
			throw new IllegalStateException("path");
		}
		
		pathName = pathName.substring(baseName.length());
		pathName = pathName.replace(File.separator, "/");
		
		String id = m.group(1);
		String type = m.group(3);
		
		String localeName = m.group(4);
		String[] localeParts = localeName.split("_");
		Locale locale = localeParts.length == 1? null : new Locale(localeParts[0], localeParts[1]);
		
		Map<String,Object> md = new HashMap<String,Object>();
		md.put("locale", locale);
		return new FileMetadata(pathName, id, type, md);
	}

	@Override
	public FileMetadata toMetadata(Path base, String path) {
		return toMetadata(base, Vfs.getPath(path));
	}

	@Override
	public Path toFile(Path base, FileMetadata omd) {
		
		if(!idPattern.matcher(omd.getName()).matches()) {
			throw new IllegalStateException("invalid id: " + omd.getName());
		}

		if(!pathPattern.matcher(omd.getPath()).matches()) {
			throw new IllegalStateException("invalid path: " + omd.getPath());
		}
		
		if(!typePattern.matcher(omd.getType()).matches()) {
			throw new IllegalStateException("invalid type: " + omd.getType());
		}
		
		String path = toFilePath(omd.getPath() + "/" + omd.getName());
		Locale locale = (Locale) omd.getExtMetadata("locale");
		
		StringBuilder builder = new StringBuilder(path).append("_").append(omd.getType()).append("_");
		
		if(locale == null) {
			builder.append("default");
		}
		else {
			builder.append(locale.getLanguage()).append("_").append(locale.getCountry());
		}
		
		builder.append(".obj");
		
		return base.getPath(builder.toString());
	}

}
