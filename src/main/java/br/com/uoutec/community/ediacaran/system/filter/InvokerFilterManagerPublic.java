package br.com.uoutec.community.ediacaran.system.filter;

import javax.inject.Singleton;

import br.com.uoutec.community.ediacaran.plugins.PublicBean;
import br.com.uoutec.filter.invoker.InvokerFilterManager;

@Singleton
public class InvokerFilterManagerPublic 
	extends InvokerFilterManager
	implements PublicBean {

}
