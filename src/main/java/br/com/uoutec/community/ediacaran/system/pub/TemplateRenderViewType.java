package br.com.uoutec.community.ediacaran.system.pub;

import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.web.WebDispatcherType;
import org.brandao.brutos.web.WebMvcRequest;
import org.brandao.brutos.web.WebMvcResponse;
import org.brandao.brutos.web.http.view.JSPRenderView;

import br.com.uoutec.application.Configuration;
import br.com.uoutec.application.se.ApplicationBootstrapProvider;
import br.com.uoutec.community.ediacaran.ServerBootstrap;

public class TemplateRenderViewType extends JSPRenderView{

	private Configuration config;
	
	public TemplateRenderViewType() {
		ServerBootstrap sb = 
				(ServerBootstrap) ApplicationBootstrapProvider.getBootstrap();
		this.config = sb.getConfiguration();

	}
	protected void show(int responseStatus, String reason,
			WebMvcRequest webRequest,
			WebMvcResponse webResponse,
			String view, DispatcherType dispatcherType){
		
        view = config.getValue(view);
        
		if(WebDispatcherType.REDIRECT.equals(dispatcherType)){
			super.show(responseStatus, reason, webRequest, webResponse, view, dispatcherType);
		}
		else{
			super.show(responseStatus, reason, webRequest, webResponse, view, dispatcherType);
		}
	}
	
}
