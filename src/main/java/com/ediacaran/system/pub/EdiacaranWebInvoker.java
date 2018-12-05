package com.ediacaran.system.pub;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.brandao.brutos.MutableMvcRequest;
import org.brandao.brutos.MutableMvcResponse;
import org.brandao.brutos.RequestInstrument;
import org.brandao.brutos.StackRequestElement;
import org.brandao.brutos.web.MutableWebMvcRequest;
import org.brandao.brutos.web.WebInvoker;

import com.ediacaran.system.Constants;
import com.ediacaran.system.entity.Language;
import com.ediacaran.system.entity.SystemUser;
import com.ediacaran.system.registry.LanguageRegistry;

import br.com.uoutec.application.EntityContext;
import br.com.uoutec.i18n.MessageLocale;
import br.com.uoutec.i18n.ThreadMessageLocale;

public class EdiacaranWebInvoker extends WebInvoker{

	private Language defaultLanguage;
	
	private Map<String, Locale> localeMap = new HashMap<String, Locale>();
	
	public EdiacaranWebInvoker(){
		this.defaultLanguage = new Language();
		this.defaultLanguage.setId(1);
		this.defaultLanguage.setIso6391("pt");
		this.defaultLanguage.setIso6392t("por");
		this.defaultLanguage.setIsoName("Portuguese");
		this.defaultLanguage.setName("Português");
		
		localeMap = new HashMap<String, Locale>();
		localeMap.put("pt", new Locale("pt", "BR"));
		localeMap.put("en", new Locale("en", "US"));
	}
	
	protected void invokeApplication(MutableMvcRequest request, MutableMvcResponse response,
			StackRequestElement element, RequestInstrument requestInstrument) throws Throwable{
	
		MutableWebMvcRequest webRequest = (MutableWebMvcRequest)request;
		SystemUser user   = UserSession.getCurrentUser();
		Locale locale     = this.getCurrentLocale(user, webRequest);
		Language language = this.getCurrentLanguage(user, locale);
		
		request.setProperty(Constants.LOCALE_VAR,          locale);
		request.setProperty(Constants.LANGUAGE_VAR,        language);
		request.setProperty(Constants.USER_HOST,           webRequest.getServletRequest().getRemoteAddr());
		
		try{
			ThreadMessageLocale.setThreadLocale(locale);
			super.invokeApplication(request, response, element, requestInstrument);
		}
		finally{
			ThreadMessageLocale.destroyThreadLocale();
		}
	}
	
	protected void renderView(
			RequestInstrument requestInstrument, MutableMvcRequest request, 
			MutableMvcResponse response, StackRequestElement element) 
					throws IllegalAccessException, IllegalArgumentException, 
					InvocationTargetException{
		
		try{
			Locale locale = (Locale) request.getProperty(Constants.LOCALE_VAR);
			
			ThreadMessageLocale.setThreadLocale(locale);
			super.renderView(requestInstrument, request, response, element);
		}
		finally{
			ThreadMessageLocale.destroyThreadLocale();
		}
		
	}
	
	private Locale getCurrentLocale(SystemUser user, MutableWebMvcRequest request){
		try{
			if(user != null && user.getLanguage() != null){
				Locale locale = this.localeMap.get(user.getLanguage().getIso6391());
				
				if(locale != null && MessageLocale.isSupported(locale)){
					return locale;
				}
				
			}
			
			String langVar = (String)request.getParameter("lang");
			
			if(langVar != null && langVar.matches("[a-z]{2,2}")){
				
				Locale locale = this.localeMap.get(langVar);
				
				if(locale != null && MessageLocale.isSupported(locale)){
					return locale;
				}
				
			}
			
			Locale locale = request.getServletRequest().getLocale();
			
			return MessageLocale.isSupported(locale)? locale : MessageLocale.getDefaultLocale();
		}
		catch(Throwable e){
			return MessageLocale.getDefaultLocale();
		}
	}
	
	private Language getCurrentLanguage(SystemUser user, Locale locale){
		try{
			if(user != null){
				return user.getLanguage() == null? this.defaultLanguage : user.getLanguage();
			}
			else{
				if(locale != null){
					LanguageRegistry languageRegistry = 
							EntityContext.getEntity(LanguageRegistry.class);
					return languageRegistry.getLanguageByIso6391(locale.getLanguage());
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
