package br.com.uoutec.community.ediacaran.system;

import java.net.URL;

import org.brandao.brutos.annotation.scanner.DefaultScanner;
import org.brandao.brutos.scanner.vfs.Vfs;

import br.com.uoutec.application.ClassUtil;

public class EdiacaranScanner extends DefaultScanner{
	
	private URL loc;
	
	public EdiacaranScanner() throws ClassNotFoundException {
		ClassLoader cl = EdiacaranWebApplicationContext.class.getClassLoader();
		loc = EdiacaranWebApplicationContext.location;
	}
	
	protected boolean accepts(String resource) {
		
		try {
			if(super.accepts(resource)) {
				Class<?> type = ClassUtil.get(Vfs.toClass(resource));
				URL typeLocation = type.getProtectionDomain().getCodeSource().getLocation();
				return typeLocation.equals(loc);
			}
			
			return false;
		}
		catch(Throwable e) {
			throw new IllegalStateException(e);
		}
	}

}
