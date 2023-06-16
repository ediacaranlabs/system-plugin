package br.com.uoutec.community.ediacaran.system.cdi.weld;

import java.awt.EventQueue;
import java.io.Serializable;

import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import br.com.uoutec.community.ediacaran.system.cdi.Parallel;

@Interceptor
@Parallel
@Priority(Interceptor.Priority.PLATFORM_BEFORE + 50)
public class ParallelThreadContextInterceptor implements Serializable {

	private static final long serialVersionUID = 8794969441739688658L;

	public ParallelThreadContextInterceptor(){
	}
	
    @AroundInvoke
    public Object activateRequestContext(final InvocationContext p_invocationContext) throws Exception {
    	
    	final InvokerResult invokerResult = new InvokerResult();
    	
    	EventQueue.invokeAndWait(new Runnable(){

			@Override
			public void run() {
				try{
					invokerResult.result = p_invocationContext.proceed();
				}
				catch(Exception e){
					invokerResult.throwable = e;
				}
			}
    		
    	});
    	
    	if(invokerResult.throwable != null){
    		throw invokerResult.throwable;
    	}
    	
    	return invokerResult.result;
    }
    
    private static class InvokerResult{
    	
    	public Object result;
    	
    	public Exception throwable;
    	
    	public InvokerResult(){
    		this.result = null;
    		this.throwable = null;
    	}
    	
    }
}