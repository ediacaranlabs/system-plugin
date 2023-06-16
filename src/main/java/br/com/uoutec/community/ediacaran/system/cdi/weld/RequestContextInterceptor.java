package br.com.uoutec.community.ediacaran.system.cdi.weld;

import java.io.Serializable;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.jboss.weld.context.RequestContext;
import org.jboss.weld.context.unbound.Unbound;

import br.com.uoutec.community.ediacaran.system.cdi.RequestContextOperation;

@Interceptor
@RequestContextOperation
@Priority(Interceptor.Priority.PLATFORM_BEFORE + 100)
public class RequestContextInterceptor implements Serializable{

	private static final long serialVersionUID = -3059444465469022566L;
	
	@Inject
    @Unbound
    private RequestContext m_requestContext;

    public RequestContextInterceptor(){
    }
    
    @AroundInvoke
    public Object activateRequestContext(final InvocationContext p_invocationContext) throws Exception {
        try {
            m_requestContext.activate();
            return p_invocationContext.proceed();
        } finally {
            m_requestContext.invalidate();
            m_requestContext.deactivate();
        }
    }
}