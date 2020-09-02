package br.com.uoutec.community.ediacaran.system.pub;

import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.RenderViewException;
import org.brandao.brutos.web.WebDispatcherType;
import org.brandao.brutos.web.WebMvcRequest;
import org.brandao.brutos.web.WebMvcResponse;
import org.brandao.brutos.web.http.view.JSPRenderView;

import br.com.uoutec.community.ediacaran.VarParser;
import br.com.uoutec.community.ediacaran.plugins.EntityContextPlugin;

public class TemplateRenderViewType extends JSPRenderView{

	private VarParser varParser;
	
	public TemplateRenderViewType() {
		this.varParser = EntityContextPlugin.getEntity(VarParser.class);
	}
	
	protected void show(int responseStatus, String reason,
			WebMvcRequest webRequest,
			WebMvcResponse webResponse,
			String view, DispatcherType dispatcherType){
		
        view = varParser.getValue(view);
        if( dispatcherType == WebDispatcherType.REDIRECT ){
        	try {
        		webResponse.sendRedirect(view);
        	}
        	catch(Throwable e){
    			throw new RenderViewException(e);
    		}
        }
        else {
        	super.show(responseStatus, reason, webRequest, webResponse, view, dispatcherType);
        }
	}
	
}
