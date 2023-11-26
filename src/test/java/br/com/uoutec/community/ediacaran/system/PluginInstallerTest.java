package br.com.uoutec.community.ediacaran.system;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import br.com.uoutec.community.ediacaran.junit.PluginContext;
import br.com.uoutec.community.ediacaran.junit.junit5.EdiacaranExt;
import br.com.uoutec.community.ediacaran.plugins.EntityContextPlugin;
import br.com.uoutec.community.ediacaran.system.concurrent.ThreadGroupManager;
import br.com.uoutec.community.ediacaran.system.concurrent.ThreadGroupManagerException;

@ExtendWith(EdiacaranExt.class)
@PluginContext("system")
public class PluginInstallerTest {

	@Test
	public void installTest() throws ThreadGroupManagerException{
		ThreadGroupManager threadGroupManager = EntityContextPlugin.getEntity(ThreadGroupManager.class);
		assertNotNull(threadGroupManager.getThreadGroup("default"));
	}
	
}
