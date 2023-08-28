package br.com.uoutec.community.ediacaran.system.cdi.weld;

import java.io.Serializable;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.jboss.weld.context.RequestContext;
import org.jboss.weld.context.unbound.Unbound;

import br.com.uoutec.community.ediacaran.EdiacaranEventObject;
import br.com.uoutec.community.ediacaran.EdiacaranListenerManager;
import br.com.uoutec.community.ediacaran.system.cdi.RequestContextOperation;

@Interceptor
@RequestContextOperation
@Priority(Interceptor.Priority.PLATFORM_BEFORE + 100)
public class RequestContextInterceptor implements Serializable{

	private static final long serialVersionUID = -3059444465469022566L;
	
	@Inject
    @Unbound
    private RequestContext m_requestContext;

	@Inject
	private EdiacaranListenerManager listeners;
	
    public RequestContextInterceptor(){
    }
    
    @AroundInvoke
    public Object activateRequestContext(final InvocationContext p_invocationContext) throws Exception {
        try {
            m_requestContext.activate();
            
			listeners.fireEvent(
					new EdiacaranEventObject(this, "active_request_context",m_requestContext));
        	
            return p_invocationContext.proceed();
        }
        finally {
        	try {
    			listeners.fireEvent(
    					new EdiacaranEventObject(this, "deactivate_request_context", m_requestContext));
        	}
        	finally {
                m_requestContext.invalidate();
                m_requestContext.deactivate();
        	}
        }
    }
}