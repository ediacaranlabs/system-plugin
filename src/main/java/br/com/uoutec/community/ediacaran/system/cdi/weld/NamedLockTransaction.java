package br.com.uoutec.community.ediacaran.system.cdi.weld;

import java.io.Serializable;

import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import br.com.uoutec.community.ediacaran.system.cdi.NamedLock;
import br.com.uoutec.community.ediacaran.system.lock.NamedLockThreadContext;

@Interceptor
@NamedLock
@Priority(Interceptor.Priority.PLATFORM_BEFORE + 10)
public class NamedLockTransaction implements Serializable{ 
	
	private static final long serialVersionUID = -7441660929163166217L;

	public NamedLockTransaction(){
	}
	
    @AroundInvoke
    public Object activateRequestContext(final InvocationContext p_invocationContext) throws Exception {

    	NamedLockThreadContext namedLockThreadContext = new NamedLockThreadContext();
    	
        try {
        	namedLockThreadContext.activate();
            return p_invocationContext.proceed();
        }
        finally{
        	namedLockThreadContext.deactivate();
        }
    }

}
