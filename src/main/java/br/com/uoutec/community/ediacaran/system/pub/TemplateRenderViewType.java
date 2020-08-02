package br.com.uoutec.community.ediacaran.system.pub;

import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.web.WebDispatcherType;
import org.brandao.brutos.web.WebMvcRequest;
import org.brandao.brutos.web.WebMvcResponse;
import org.brandao.brutos.web.http.view.JSPRenderView;

import br.com.uoutec.community.ediacaran.plugins.EntityContextPlugin;
import br.com.uoutec.community.ediacaran.plugins.PluginsProperties;

public class TemplateRenderViewType extends JSPRenderView{

	private PluginsProperties pluginsProperties;
	
	public TemplateRenderViewType() {
		this.pluginsProperties = EntityContextPlugin.getEntity(PluginsProperties.class);
	}
	
	protected void show(int responseStatus, String reason,
			WebMvcRequest webRequest,
			WebMvcResponse webResponse,
			String view, DispatcherType dispatcherType){
		
        view = pluginsProperties.getValue(view);
        
		if(WebDispatcherType.REDIRECT.equals(dispatcherType)){
			super.show(responseStatus, reason, webRequest, webResponse, view, dispatcherType);
		}
		else{
			super.show(responseStatus, reason, webRequest, webResponse, view, dispatcherType);
		}
	}
	
}
