package br.com.uoutec.community.ediacaran.system.error;

import javax.inject.Inject;
import javax.inject.Singleton;

import br.com.uoutec.community.ediacaran.plugins.PublicBean;
import br.com.uoutec.community.ediacaran.system.error.ErrorMapping.ErrorResolver;

@Singleton
public class ErrorMappingProvider implements ErrorResolver, PublicBean{

	private ErrorMapping errorMapping;
	
	public ErrorMappingProvider() {
	}
	
	@Inject
	private ErrorMappingProvider(ErrorMapping errorMapping){
		this.errorMapping = errorMapping;
	}
	
	@Override
	public String getError(Class<?> type, String action, String error, Throwable t) {
		ErrorResolver resolver = this.errorMapping.getErrorResolver(type, action, error, t);
		return 
			resolver == null? 
				this.errorMapping.getDefaultError() : 
				resolver.getError(type, action, error, t);
	}

}
