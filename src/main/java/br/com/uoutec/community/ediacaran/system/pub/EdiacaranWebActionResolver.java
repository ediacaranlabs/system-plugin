package br.com.uoutec.community.ediacaran.system.pub;

import java.net.MalformedURLException;

import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.MutableMvcRequest;
import org.brandao.brutos.web.RequestMethodType;
import org.brandao.brutos.web.WebActionResolver;
import org.brandao.brutos.web.mapping.RequestEntry;

public class EdiacaranWebActionResolver extends WebActionResolver{

	public EdiacaranWebActionResolver() {
	}
	
    public RequestEntry get(String value, RequestMethodType methodType, 
    		MutableMvcRequest request) throws MalformedURLException{
    	String prefix = request.getApplicationContext().getConfiguration().getProperty(BrutosConstants.VIEW_RESOLVER_PREFIX);
    	value = value.substring(prefix.length());
    	return super.get(value, methodType, request);
    }
	
}
