package br.com.uoutec.community.ediacaran.system.cdi.weld;

import java.io.Serializable;

import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import br.com.uoutec.application.ApplicationThread;
import br.com.uoutec.application.ApplicationThreadException;
import br.com.uoutec.community.ediacaran.system.cdi.Parallel;

@Interceptor
@Parallel
@Priority(Interceptor.Priority.PLATFORM_BEFORE + 50)
public class ParallelThreadContextInterceptor implements Serializable {

	private static final long serialVersionUID = 8794969441739688658L;

	public ParallelThreadContextInterceptor(){
	}
	
    @AroundInvoke
    public Object activateRequestContext(final InvocationContext p_invocationContext) throws Throwable {
    	
    	Object[] result = new Object[1];
    	
    	ApplicationThread appThread = new ApplicationThread(()->{
    		try {
    			result[0] =  p_invocationContext.proceed();
    		}
    		catch(Throwable ex) {
    			throw new ApplicationThreadException(ex);
    		}
    	});
    	
    	appThread.start(true);
    	
    	if(appThread.getException() != null){
    		throw appThread.getException();
    	}
    	
    	return result[0];
    }
    
}