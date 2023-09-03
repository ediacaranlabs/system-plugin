package br.com.uoutec.community.ediacaran.system.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.brandao.brutos.io.DefaultResourceLoader;
import org.brandao.brutos.io.Resource;
import org.brandao.brutos.io.ResourceLoader;

import br.com.uoutec.application.Configuration;

public class TemplateUtil {

	private static final String propertyRegex    = "\\{\\{([^\\}]+)\\}\\}";
	
	private static final Pattern propertyPattern = Pattern.compile(propertyRegex);
	
	private ResourceLoader loader;

	public TemplateUtil() {
		this.loader = new DefaultResourceLoader();
	}
	
	public String parser(String path, Configuration config, String encoding) throws IOException {
		String template   = this.getContent(path, encoding);
		return config == null || config.isEmpty()? template : parser(template, config);
	}
	
	public String parser(String template, Configuration config) throws IOException {
		
		StringBuffer text = new StringBuffer();
		
		Matcher matcher = propertyPattern.matcher(template);

		while(matcher.find()) {
			String var = matcher.group(1);
			String value = config.getProperty(var);
			matcher.appendReplacement(text, value == null? "" : value);
		}
		
		matcher.appendTail(text);
		
		return text.toString();
	}
	
	private String getContent(String path, String encoding) throws IOException {
		
		try(InputStream in = getRaw(path)){
			
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int len = 0;
			
			while((len = in.read(buf, 0, buf.length)) > -1){
				out.write(buf, 0, len);
			}
			String tmp = new String(out.toByteArray(), encoding);
			return tmp;
			
		}
		
	}
	
	public InputStream getRaw(String path) throws IOException {
		
		if(path == null || path.isEmpty()) {
			return null;
		}
		
		if(!path.startsWith(ResourceLoader.CLASSPATH_URL_PREFIX)) {
			if(!path.startsWith(ResourceLoader.FILE_URL_PREFIX)) {
				path = ResourceLoader.FILE_URL_PREFIX.concat(new File(path).getAbsolutePath());
			}
		}
		
		Resource resource = this.loader.getResource(path);
		
		if(resource == null || !resource.exists()) {
			return null;
		}
		
		return resource.getInputStream();
	}

	public Resource getContent(String path) throws IOException {
		
		if(path == null || path.isEmpty()) {
			return null;
		}
		
		if(!path.startsWith(ResourceLoader.CLASSPATH_URL_PREFIX)) {
			if(!path.startsWith(ResourceLoader.FILE_URL_PREFIX)) {
				path = ResourceLoader.FILE_URL_PREFIX.concat(new File(path).getAbsolutePath());
			}
		}
		return this.loader.getResource(path);
	}
	
}
