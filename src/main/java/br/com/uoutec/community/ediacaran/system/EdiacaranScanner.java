package br.com.uoutec.community.ediacaran.system;

import java.net.URL;

import org.brandao.brutos.annotation.scanner.DefaultScanner;
import org.brandao.brutos.scanner.vfs.Vfs;

import br.com.uoutec.application.ClassUtil;
import br.com.uoutec.community.ediacaran.plugins.EntityContextPlugin;
import br.com.uoutec.community.ediacaran.plugins.PluginData;
import br.com.uoutec.community.ediacaran.plugins.PublicType;

public class EdiacaranScanner extends DefaultScanner{
	
	private URL loc;
	
	public EdiacaranScanner() throws ClassNotFoundException {
		PluginData pd = EntityContextPlugin.getEntity(PluginData.class);
		loc = pd.getType().getProtectionDomain().getCodeSource().getLocation();
	}
	
	protected boolean accepts(String resource) {
		
		try {
			if(super.accepts(resource)) {
				Class<?> type = ClassUtil.get(Vfs.toClass(resource));
				URL typeLocation = type.getProtectionDomain().getCodeSource().getLocation();
				return typeLocation.equals(loc) || PublicType.class.isAssignableFrom(type);
			}
			
			return false;
		}
		catch(Throwable e) {
			throw new IllegalStateException(e);
		}
	}

}
