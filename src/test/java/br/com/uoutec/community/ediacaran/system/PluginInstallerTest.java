package br.com.uoutec.community.ediacaran.system;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import br.com.uoutec.community.ediacaran.system.cdi.ActiveRequestContext;
import br.com.uoutec.community.ediacaran.system.concurrent.ThreadGroupManager;
import br.com.uoutec.community.ediacaran.system.concurrent.ThreadGroupManagerException;
import br.com.uoutec.ediacaran.core.EdiacaranBootstrap;
import br.com.uoutec.ediacaran.core.plugins.EntityContextPlugin;
import br.com.uoutec.ediacaran.junit.PluginContext;
import br.com.uoutec.ediacaran.junit.junit5.EdiacaranExt;
import br.com.uoutec.ediacaran.weld.tomcat.TomcatServerBootstrapBuilder;

@ExtendWith(EdiacaranExt.class)
@PluginContext("system")
public class PluginInstallerTest {

	public EdiacaranBootstrap getEdiacaranBootstrap() {
		return TomcatServerBootstrapBuilder.builder()
		.build();
	}
	
	@Test
	@ActiveRequestContext
	public void installTest() throws ThreadGroupManagerException, ClassNotFoundException{
		//org.apache.jasper.servlet.JspServlet
		//org.apache.catalina.core.DefaultInstanceManager
		ThreadGroupManager threadGroupManager = EntityContextPlugin.getEntity(ThreadGroupManager.class);
		assertNotNull(threadGroupManager.getThreadGroup("default"));
	}
	
}
