package br.com.uoutec.community.ediacaran.system;

import java.util.HashSet;
import java.util.Set;

import org.brandao.brutos.ComponentRegistry;
import org.brandao.brutos.annotation.web.AnnotationDefinitionReader;
import org.brandao.brutos.web.AbstractWebApplicationContext;
import org.brandao.brutos.xml.ScannerEntity;

import br.com.uoutec.community.ediacaran.plugins.EntityContextPlugin;
import br.com.uoutec.community.ediacaran.plugins.PluginType;
import br.com.uoutec.community.ediacaran.system.pub.type.DateFactory;

public class EdiacaranWebApplicationContext extends AbstractWebApplicationContext{
	
	@Override
	protected void loadDefinitions(ComponentRegistry registry) {
		
		//add default types
		registry.registerType(new DateFactory());
		
		AnnotationDefinitionReader definitionReader = 
				new AnnotationDefinitionReader(this, registry);

		PluginType pd = EntityContextPlugin.getEntity(PluginType.class);
		
		Set<String> packageNames = new HashSet<String>();
		packageNames.add(pd.getPackage().getName());

		for(Package p: pd.getPackages()) {
			packageNames.add(p.getName());
		}
		
		for(Package p: pd.getParentPackages()) {
			packageNames.add(p.getName());
		}
		
		ScannerEntity se = new ScannerEntity();
		se.setBasePackage(packageNames.toArray(new String[0]));
		se.setScannerClassName(EdiacaranScanner.class.getName());
		se.setUseDefaultfilter(true);
		definitionReader.setScannerEntity(se);
		definitionReader.loadDefinitions();
		

	}

}
