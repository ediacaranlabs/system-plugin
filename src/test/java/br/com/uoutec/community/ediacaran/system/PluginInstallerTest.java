package br.com.uoutec.community.ediacaran.system;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import br.com.uoutec.community.ediacaran.system.cdi.ActiveRequestContext;
import br.com.uoutec.community.ediacaran.system.concurrent.ThreadGroupManagerException;
import br.com.uoutec.ediacaran.core.EdiacaranBootstrap;
import br.com.uoutec.ediacaran.core.plugins.EntityContextPlugin;
import br.com.uoutec.ediacaran.core.plugins.PluginType;
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
		PluginType pluginType = EntityContextPlugin.getEntity(PluginType.class);
		assertEquals("security", pluginType.getConfiguration().getMetadata().getCode());
	}
	
}
