package br.com.uoutec.community.ediacaran.system.pub;

import java.util.Locale;

import javax.inject.Singleton;

import org.brandao.brutos.ApplicationContext;
import org.brandao.brutos.Scopes;
import org.brandao.brutos.annotation.Intercepts;
import org.brandao.brutos.annotation.InterceptsStack;
import org.brandao.brutos.interceptor.AbstractInterceptor;
import org.brandao.brutos.interceptor.InterceptedException;
import org.brandao.brutos.interceptor.InterceptorHandler;
import org.brandao.brutos.interceptor.InterceptorStack;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.web.WebScopeType;

import br.com.uoutec.community.ediacaran.core.system.Constants;
import br.com.uoutec.community.ediacaran.core.system.entity.Language;
import br.com.uoutec.community.ediacaran.core.system.registry.LanguageRegistry;
import br.com.uoutec.community.ediacaran.plugins.EntityContextPlugin;
import br.com.uoutec.community.ediacaran.plugins.PublicType;
import br.com.uoutec.community.ediacaran.web.EdiacaranWebInvoker;

//@Singleton
//@Intercepts(isDefault=false)
//@InterceptsStack(name="securityStack", isdefault=true)
@Deprecated
public class LanguageInterceptor 
	extends AbstractInterceptor 
	implements PublicType{

	//private static final Logger logger = LoggerFactory.getLogger(LanguageInterceptor.class); 
	
	private Language defaultLanguage;
	
	public LanguageInterceptor() {
		this.defaultLanguage = new Language();
		this.defaultLanguage.setId(1);
		this.defaultLanguage.setIso6391("pt");
		this.defaultLanguage.setIso6392t("por");
		this.defaultLanguage.setIsoName("Portuguese");
		this.defaultLanguage.setName("Português");
	}
	
	public void intercepted(InterceptorStack stack, InterceptorHandler handler)
			throws InterceptedException {
		
		ApplicationContext context = handler.getContext();
		Scopes scopes              = context.getScopes();
		Scope requestScope         = scopes.get(WebScopeType.REQUEST);
		Locale locale              = (Locale) requestScope.get(EdiacaranWebInvoker.LOCALE_VAR);
		Language language          = this.getCurrentLanguage(locale);
		
		requestScope.put(Constants.LANGUAGE_VAR, language);
		
		stack.next(handler);
		
	}

	public boolean accept(InterceptorHandler handler) {
		return true;
	}
	
	private Language getCurrentLanguage(Locale locale){
		try{
			if(locale != null){
				LanguageRegistry languageRegistry = 
						EntityContextPlugin.getEntity(LanguageRegistry.class);
				return languageRegistry.getLanguageByIso6392t(locale.getISO3Language());
			}
			return this.defaultLanguage;
		}
		catch(Throwable e){
			e.printStackTrace();
			return this.defaultLanguage;
		}
	}	
}
