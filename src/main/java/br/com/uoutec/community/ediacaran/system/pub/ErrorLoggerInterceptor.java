package br.com.uoutec.community.ediacaran.system.pub;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Method;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.brandao.brutos.MvcRequest;
import org.brandao.brutos.ResourceAction;
import org.brandao.brutos.annotation.Intercepts;
import org.brandao.brutos.interceptor.AbstractInterceptor;
import org.brandao.brutos.interceptor.InterceptedException;
import org.brandao.brutos.interceptor.InterceptorHandler;
import org.brandao.brutos.interceptor.InterceptorStack;
import org.brandao.brutos.mapping.ParameterAction;
import org.brandao.brutos.web.bean.JsonBeanEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
@Intercepts
public class ErrorLoggerInterceptor extends AbstractInterceptor{

	private static final Logger logger = LoggerFactory.getLogger(ErrorLoggerInterceptor.class); 

	@Inject
	public ErrorLoggerInterceptor(){
	}
	
	public void intercepted(InterceptorStack stack, InterceptorHandler handler)
			throws InterceptedException {
		
		Throwable ex = null;
		try{
			stack.next(handler);
			MvcRequest request = handler.getRequest();
			ResourceAction resourceAction = request.getResourceAction();
			Method method = resourceAction == null? null : resourceAction.getMethod();
			
			if((method != null && method.isAnnotationPresent(LogAction.class)) ||
				request.getThrowable() != null){
				
				ex = request.getThrowable();
				Object[] params = request.getParameters();
				List<ParameterAction> actionParams = resourceAction.getMethodForm().getParameters();
				
				StringBuilder paramBuilder = new StringBuilder();
				int index = 0;
				paramBuilder.append("[ ");
				
				for(ParameterAction pa: actionParams){
					
					if(index > 0){
						paramBuilder.append(", ");
					}
					
					ByteArrayOutputStream bout = new ByteArrayOutputStream();
					JsonBeanEncoder encoder = new JsonBeanEncoder(bout, "UTF-8");
					encoder.encode(pa, params[index++]);
					paramBuilder.append(new String(bout.toByteArray()));
				}
				
				paramBuilder.append(" ]");
				

				if(request.getThrowable() == null){
					logger.info(
						"Foi executada a ação " + 
								(resourceAction.getMethodForm() == null? 
										"null" : 
										resourceAction.getMethodForm().getName()) +
						" do controlador " + resourceAction.getController().getClassType().getSimpleName() + 
						" parametros: " + paramBuilder.toString()
					);
				}
				else
				if(request.getThrowable() != null){
					logger.error(
						"Falha ao executar a ação " + 
								(resourceAction.getMethodForm() == null? 
										"null" : 
										resourceAction.getMethodForm().getName()) +
						" do controlador " +resourceAction.getController().getClassType().getSimpleName() + 
						" parametros: " + paramBuilder.toString(),
						ex);
				}
			}
		}
		catch(Throwable e){
			
			logger.error("falha no mapeamento da requisição:", e);
			
			if(ex != null){
				logger.error("falha: ", ex);
			}
			
		}
		
	}

	public boolean accept(InterceptorHandler handler) {
		return true;
	}
	
}
