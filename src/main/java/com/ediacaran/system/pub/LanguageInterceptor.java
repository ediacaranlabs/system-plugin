package com.ediacaran.system.pub;

import java.util.Locale;

import javax.inject.Inject;

import org.brandao.brutos.ApplicationContext;
import org.brandao.brutos.Scopes;
import org.brandao.brutos.interceptor.AbstractInterceptor;
import org.brandao.brutos.interceptor.InterceptedException;
import org.brandao.brutos.interceptor.InterceptorHandler;
import org.brandao.brutos.interceptor.InterceptorStack;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.web.WebScopeType;

import com.ediacaran.system.Constants;
import com.ediacaran.system.entity.Country;
import com.ediacaran.system.entity.Language;
import com.ediacaran.system.entity.Region;
import com.ediacaran.system.entity.SystemUser;
import com.ediacaran.system.registry.CountryRegistry;
import com.ediacaran.system.registry.LanguageRegistry;

import br.com.uoutec.application.EntityContext;

//@Singleton
//@Intercepts
public class LanguageInterceptor extends AbstractInterceptor{

	//private static final Logger logger = LoggerFactory.getLogger(LanguageInterceptor.class); 

	private Country defaultCountry;

	private Language defaultLanguage;
	
	@Inject
	public LanguageInterceptor(){
		this.defaultLanguage = new Language();
		this.defaultLanguage.setId(1);
		this.defaultLanguage.setIso6391("pt");
		this.defaultLanguage.setIso6392t("por");
		this.defaultLanguage.setIsoName("Portuguese");
		this.defaultLanguage.setName("Português");
		
		Region region = new Region();
		region.setId("1");
		
		this.defaultCountry = new Country();
		this.defaultCountry.setId(1);
		this.defaultCountry.setIsoAlpha2("BR");
		this.defaultCountry.setIsoAlpha3("BRA");
		this.defaultCountry.setTld(".com.br");
		this.defaultCountry.setIso4217("BRL");
		this.defaultCountry.setLanguage(this.defaultLanguage);
		this.defaultCountry.setName("Brasil");
		this.defaultCountry.setRegion(region);
		this.defaultCountry.setUfi(0);
		this.defaultCountry.setUni(1);
	}
	
	public void intercepted(InterceptorStack stack, InterceptorHandler handler)
			throws InterceptedException {
		
		ApplicationContext context = handler.getContext();
		Scopes scopes              = context.getScopes();
		Scope paramScope           = scopes.get(WebScopeType.PARAM);
		Scope requestScope         = scopes.get(WebScopeType.REQUEST);
		SystemUser user            = UserSession.getCurrentUser();
		String langVar             = (String)paramScope.get("lang");
		
		Country country            = this.getCurrentCountry(user, langVar);
		Language language          = this.getCurrentLanguage(user, langVar);
		
		Locale locale              = new Locale(language.getIso6391(), country.getIsoAlpha2());
		
		requestScope.put(Constants.LOCALE_VAR,   locale);
		requestScope.put(Constants.LANGUAGE_VAR, language);
		requestScope.put(Constants.COUNTRY_VAR,  country);
		
		stack.next(handler);
		
	}

	public boolean accept(InterceptorHandler handler) {
		return true;
	}
	
	private Country getCurrentCountry(SystemUser user, String langVar){
		try{
			if(user != null){
				return user.getCountry() == null? this.defaultCountry : user.getCountry();
			}
			else{
				if(langVar != null){
					CountryRegistry countryRegistry = 
							EntityContext.getEntity(CountryRegistry.class);
					return countryRegistry.getCountryByIsoAlpha2(langVar.split("\\-")[1]);
				}
			}
			
			return this.defaultCountry;
		}
		catch(Throwable e){
			e.printStackTrace();
			return this.defaultCountry;
		}
	}

	private Language getCurrentLanguage(SystemUser user, String langVar){
		try{
			if(user != null){
				return user.getLanguage() == null? this.defaultLanguage : user.getLanguage();
			}
			else{
				if(langVar != null){
					LanguageRegistry languageRegistry = 
							EntityContext.getEntity(LanguageRegistry.class);
					return languageRegistry.getLanguageByIso6391(langVar.split("\\-")[0]);
				}
			}
			
			return this.defaultLanguage;
		}
		catch(Throwable e){
			e.printStackTrace();
			return this.defaultLanguage;
		}
	}	
}
