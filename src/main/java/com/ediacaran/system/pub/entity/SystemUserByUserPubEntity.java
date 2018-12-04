package com.ediacaran.system.pub.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.brandao.brutos.annotation.Constructor;
import org.brandao.brutos.annotation.Transient;

import com.ediacaran.system.entity.Country;
import com.ediacaran.system.entity.SystemUser;
import com.ediacaran.system.registry.CountryRegistry;

import br.com.uoutec.application.EntityContext;
import br.com.uoutec.application.validation.CommonValidation;
import br.com.uoutec.entityresourcebundle.EntityResourceBundle;
import br.com.uoutec.i18n.MessageBundleUtils;
import br.com.uoutec.i18n.MessageLocale;
import br.com.uoutec.i18n.MessagesConstants;
import br.com.uoutec.i18n.ValidationException;
import br.com.uoutec.pub.entity.InvalidRequestException;

public class SystemUserByUserPubEntity extends SystemUserPubEntity{

	private static final long serialVersionUID = -6424904630035297618L;

	@NotNull
	@Pattern(regexp=CommonValidation.COUNTRY_CODE)
	private String country;

	@Constructor
	public SystemUserByUserPubEntity(){
	}
	
	public SystemUserByUserPubEntity(SystemUser e){
		super(e);
	}
	
	@Override
	protected void copyTo(SystemUser o, boolean reload, boolean override,
			boolean validate) throws Throwable {
		
		if(super.getPassword() != null){
			o.setPassword(super.getPassword());
		}
		
		o.setFirstName(super.getFirstName());
		o.setLastName(super.getLastName());
		o.setAddress(super.getAddress());
		o.setCity(super.getCity());
		o.setComplement(super.getComplement());
		
		CountryRegistry registry = EntityContext.getEntity(CountryRegistry.class);
		Country c = registry.getCountryByIsoAlpha3(this.country);
		
		if(!"XXX".equals(this.country) && c == null){
			String error = MessageBundleUtils
					.getMessageResourceString(
							MessagesConstants.RESOURCE_BUNDLE,
							MessagesConstants.system.error.not_empty_message, 
							new Object[]{"country"});
			throw new InvalidRequestException(error);
		}
		
		c = c == null? null : EntityResourceBundle.getEntity(c, Country.class, null);
		
		if(o.getCountry() == null){
			o.setCountry(c);
		}
		else
		if(c != null && c.getId() != o.getCountry().getId()){
			String error = MessageBundleUtils
					.getMessageResourceString(
							MessagesConstants.RESOURCE_BUNDLE,
							MessagesConstants.system.error.not_empty_message, 
							new Object[]{"country"});
			throw new InvalidRequestException(error);
		}
		
		o.setOrganization(super.getOrganization());
		o.setPhone(super.getPhone());
		o.setRegion(super.getRegion());
		o.setZip(super.getZip());
	}
	
	protected void validate(boolean reload, boolean override, 
			Class<?> ... groups) throws ValidationException{
		
		if(super.getSystemUser().getId() != super.getId()){
			String error = MessageBundleUtils
					.getMessageResourceString(
							MessagesConstants.RESOURCE_BUNDLE,
							MessagesConstants.system.error.pattern_message, 
							new Object[]{"id"},
							MessageLocale.getLocale());
			throw new ValidationException(error);
		}
	}
	
	@Transient
	public void setEmail(String email) {
		super.setEmail(email);
	}
	
	@Transient
	public void setCountry(CountryPubEntity country){
		super.setCountry(country);
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getCountryCode(){
		return this.country;
	}
	
}
