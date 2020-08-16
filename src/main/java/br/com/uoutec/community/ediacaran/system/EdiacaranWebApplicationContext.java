package br.com.uoutec.community.ediacaran.system;

import org.brandao.brutos.ComponentRegistry;
import org.brandao.brutos.annotation.web.AnnotationDefinitionReader;
import org.brandao.brutos.web.AbstractWebApplicationContext;
import org.brandao.brutos.xml.ScannerEntity;

import br.com.uoutec.community.ediacaran.plugins.EntityContextPlugin;
import br.com.uoutec.community.ediacaran.plugins.PluginData;

public class EdiacaranWebApplicationContext extends AbstractWebApplicationContext{
	
	@Override
	protected void loadDefinitions(ComponentRegistry registry) {
		AnnotationDefinitionReader definitionReader = 
				new AnnotationDefinitionReader(this, registry);

		PluginData pd = EntityContextPlugin.getEntity(PluginData.class);
		
		ScannerEntity se = new ScannerEntity();
		se.setBasePackage(new String[] {pd.getPackage().getName()});
		se.setScannerClassName(EdiacaranScanner.class.getName());
		se.setUseDefaultfilter(true);
		definitionReader.setScannerEntity(se);
		definitionReader.loadDefinitions();
	}

}
