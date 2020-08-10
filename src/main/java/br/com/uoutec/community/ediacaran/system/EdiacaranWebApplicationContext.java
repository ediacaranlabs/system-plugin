package br.com.uoutec.community.ediacaran.system;

import java.net.URL;

import org.brandao.brutos.ComponentRegistry;
import org.brandao.brutos.annotation.web.AnnotationDefinitionReader;
import org.brandao.brutos.web.AbstractWebApplicationContext;
import org.brandao.brutos.xml.ScannerEntity;

import br.com.uoutec.community.ediacaran.plugins.Plugin;

public class EdiacaranWebApplicationContext extends AbstractWebApplicationContext{

	private Plugin plugin;
	
	static URL location;
	
	public EdiacaranWebApplicationContext(Plugin plugin) {
		this.plugin = plugin;
	}
	
	@Override
	protected void loadDefinitions(ComponentRegistry registry) {
		AnnotationDefinitionReader definitionReader = 
				new AnnotationDefinitionReader(this, registry);

		Class<?> pt = plugin.getClass();
		
		try {
			EdiacaranWebApplicationContext.location = pt.getProtectionDomain().getCodeSource().getLocation();
			
			ScannerEntity se = new ScannerEntity();
			se.setBasePackage(new String[] {pt.getPackage().getName()});
			se.setScannerClassName(EdiacaranScanner.class.getName());
			se.setUseDefaultfilter(true);
			definitionReader.setScannerEntity(se);
			
			definitionReader.loadDefinitions();
		}
		finally {
			EdiacaranWebApplicationContext.location = null;
		}
	}

}
