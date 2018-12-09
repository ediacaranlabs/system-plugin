package br.com.uoutec.community.ediacaran.system.pub;

import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.web.WebDispatcherType;
import org.brandao.brutos.web.WebMvcRequest;
import org.brandao.brutos.web.WebMvcResponse;
import org.brandao.brutos.web.http.view.JSPRenderView;

public class TemplateRenderViewType extends JSPRenderView{

	protected void show(int responseStatus, String reason,
			WebMvcRequest webRequest,
			WebMvcResponse webResponse,
			String view, DispatcherType dispatcherType){
		
		if(WebDispatcherType.REDIRECT.equals(dispatcherType)){
			super.show(responseStatus, reason, webRequest, webResponse, view, dispatcherType);
		}
		else{
	        view = view.replace("{template}", "default_template");
			super.show(responseStatus, reason, webRequest, webResponse, view, dispatcherType);
		}
	}
	
}
