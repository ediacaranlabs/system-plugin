package com.ediacaran.system.pub;

import org.brandao.brutos.web.MediaType;
import org.brandao.brutos.web.WebConfigurableRenderViewImp;

public class TemplateRenderView extends WebConfigurableRenderViewImp{

	public TemplateRenderView(){
		super();
		this.removeRenderView(MediaType.TEXT_HTML);
		this.registryRenderView(MediaType.TEXT_HTML, new TemplateRenderViewType());
	}
}
