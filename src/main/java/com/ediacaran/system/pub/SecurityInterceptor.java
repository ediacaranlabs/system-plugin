package com.ediacaran.system.pub;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.brandao.brutos.ApplicationContext;
import org.brandao.brutos.ResourceAction;
import org.brandao.brutos.annotation.Intercepts;
import org.brandao.brutos.annotation.InterceptsStack;
import org.brandao.brutos.interceptor.AbstractInterceptor;
import org.brandao.brutos.interceptor.InterceptedException;
import org.brandao.brutos.interceptor.InterceptorHandler;
import org.brandao.brutos.interceptor.InterceptorStack;
import org.brandao.brutos.mapping.Action;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.web.WebMvcRequest;
import org.brandao.brutos.web.WebMvcResponse;
import org.brandao.brutos.web.WebScopeType;

import com.ediacaran.system.Constants;
import com.ediacaran.system.entity.SystemUser;
import com.ediacaran.system.pub.annotation.GuaranteedAccessTo;

@InterceptsStack(name="securityStack", isdefault=false)
@Intercepts(isDefault=false)
@Singleton
public class SecurityInterceptor extends AbstractInterceptor{

	public void intercepted(InterceptorStack stack, InterceptorHandler handler)
			throws InterceptedException {
		
		ApplicationContext context    = handler.getContext();
		Scope sessionScope            = context.getScopes().get(WebScopeType.SESSION);
		SystemUser authenticated      = (SystemUser) sessionScope.get(Constants.SYSTEM_USER_BEAN_NAME);
		if(authenticated == null || authenticated.getPrivilege() == null){
			WebMvcRequest request = (WebMvcRequest) handler.getRequest();
			HttpServletRequest httpRequest = (HttpServletRequest) request.getServletRequest();
			WebMvcResponse response = (WebMvcResponse) handler.getResponse();
			HttpServletResponse httpResponse = (HttpServletResponse) response.getServletResponse();
			
			httpResponse.addHeader("Location", httpRequest.getContextPath() + "/");
			httpResponse.setStatus(302);
			handler.getRequest().getRequestInstrument().setHasViewProcessed(true);
			return;
		}
		else{
			ResourceAction resourceAction         = handler.getRequest().getResourceAction();
			Controller controller                 = resourceAction.getController();
			Action action                         = resourceAction.getMethodForm();
			GuaranteedAccessTo guaranteedAccessTo = null;
			
			if(action != null && action.getMethod() != null){
				guaranteedAccessTo = 
					action.getMethod().getAnnotation(GuaranteedAccessTo.class);
				
				if(guaranteedAccessTo == null){
					guaranteedAccessTo = 
							controller.getClassType().getAnnotation(GuaranteedAccessTo.class);
				}
			}
			else{
				guaranteedAccessTo = 
						controller.getClassType().getAnnotation(GuaranteedAccessTo.class);
			}
			
			
			if(guaranteedAccessTo == null || authenticated.getPrivilege().isAssignableFrom(guaranteedAccessTo.value())){
				stack.next(handler);
			}
			else{
				throw 
					new	SecurityException(
						"user: " + authenticated.getId() + 
						", resource: " + handler.getRequest().getRequestId());
			}
		}
		
	}

	public boolean accept(InterceptorHandler handler) {
		return true;
	}
	
}
