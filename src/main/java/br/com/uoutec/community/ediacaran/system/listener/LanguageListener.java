package br.com.uoutec.community.ediacaran.system.listener;

import java.io.IOException;
import java.security.AccessControlException;

import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.uoutec.community.ediacaran.system.i18n.Plugini18nManager;
import br.com.uoutec.ediacaran.core.EdiacaranEventListener;
import br.com.uoutec.ediacaran.core.EdiacaranEventObject;
import br.com.uoutec.ediacaran.core.plugins.PluginInitializer;
import br.com.uoutec.ediacaran.core.plugins.PluginNode;

@Singleton
public class LanguageListener implements EdiacaranEventListener{

	private static final Logger logger = LoggerFactory.getLogger(LanguageListener.class);

	@Override
	public void onEvent(EdiacaranEventObject event) {
	
		if(event.getSource() instanceof PluginInitializer) {
			
			if("installing".equals(event.getType())){
				startPlugin((PluginNode)event.getData());
			}
			else
			if("destroying".equals(event.getType())){
				stopPlugin((PluginNode)event.getData());
			}
			
		}
		
	}

	private void startPlugin(PluginNode node) {
		try {
			loadLanguages();
		}
		catch(AccessControlException ex) {
			logger.warn("don't have permission to load language", ex);
		}
		catch(Throwable ex) {
			throw new RuntimeException(ex);
		}
	}
	
	private void stopPlugin(PluginNode node) {
		Plugini18nManager pim = new Plugini18nManager();
		pim.unregisterLanguages();
	}
	
	protected void loadLanguages() throws IOException {
		Plugini18nManager pim = new Plugini18nManager();
		pim.registerLanguages();
	}
	
}
