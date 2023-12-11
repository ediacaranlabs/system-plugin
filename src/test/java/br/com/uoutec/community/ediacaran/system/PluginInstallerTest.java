package br.com.uoutec.community.ediacaran.system;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import br.com.uoutec.application.SystemProperties;
import br.com.uoutec.application.security.SecurityThread;
import br.com.uoutec.application.security.SystemSecurityClassLoader;
import br.com.uoutec.community.ediacaran.system.concurrent.ThreadGroupManager;
import br.com.uoutec.community.ediacaran.system.concurrent.ThreadGroupManagerException;
import br.com.uoutec.ediacaran.core.plugins.EntityContextPlugin;
import br.com.uoutec.ediacaran.junit.PluginContext;
import br.com.uoutec.ediacaran.junit.junit5.EdiacaranExt;

@ExtendWith(EdiacaranExt.class)
@PluginContext("system")
public class PluginInstallerTest {

	@Test
	public void installTest() throws ThreadGroupManagerException, ClassNotFoundException{
		SecurityThread st = new SecurityThread(()->{});
		Thread t = new Thread();
		t.getName();
		Method[] m = getClass().getMethods();
		Method mm = m[0];
		mm.getName();
		ThreadGroupManager threadGroupManager = EntityContextPlugin.getEntity(ThreadGroupManager.class);
		assertNotNull(threadGroupManager.getThreadGroup("default"));
	}

	@Test
	public void reflectionAccessOtherClassLoader() throws Throwable {
		try {
			Object c = getClass().getClassLoader();
			Object o = SystemSecurityClassLoader.getDefaultSystemSecurityClassloader();
			
			//Object current = Thread.currentThread().getContextClassLoader();
			Object classLoader = SystemProperties.class.getClassLoader();
			//Object classLoader = SystemSecurityClassLoader.getDefaultSystemSecurityClassloader();
			Class<?> classLoaderClass = classLoader.getClass();
			Method load = classLoaderClass.getMethod("loadClass", String.class);
			Class<?> classFile = (Class<?>) load.invoke(classLoader, "java.io.File");
			Constructor<?> fileConstructor = classFile.getConstructor(String.class);
			Object file = fileConstructor.newInstance("c:/");

			Class<?> classFileInputStream = (Class<?>) load.invoke(classLoader, "java.io.FileInputStream");
			Constructor<?> fileInputStreamConstructor = classFileInputStream.getConstructor(classFile);
			Object fileInputStream = fileInputStreamConstructor.newInstance(file);
			
			fail("expected NoClassDefFoundError");
		}
		catch(InvocationTargetException ex) {
			
			Throwable e = ex.getTargetException();
			
			if(!(e instanceof ClassNotFoundException)) {
				throw e;
			}
			
		}
	}
	
}
