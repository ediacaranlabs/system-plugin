package com.ediacaran.system.pub.entity;

import org.brandao.brutos.annotation.Constructor;
import org.brandao.brutos.annotation.Transient;

import com.ediacaran.system.entity.SystemUser;

import br.com.uoutec.i18n.ValidationException;
import br.com.uoutec.i18n.ValidatorBean;
import br.com.uoutec.pub.entity.IdValidation;

public class SystemUserByUserPassPubEntity 
	extends SystemUserByUserPubEntity{

	private static final long serialVersionUID = -2207173093030355831L;

	@Constructor
	public SystemUserByUserPassPubEntity(){
	}
	
	public SystemUserByUserPassPubEntity(SystemUser e){
		super(e);
	}
	
	protected void validate(boolean reload, boolean override, 
			Class<?> ... groups) throws ValidationException{

		super.validate(reload, override, groups);
		
		if(reload && override){
			ValidatorBean.validate(this, IdValidation.class, PasswordValidatorGroup.class);
		}
		else
		if(override){
			ValidatorBean.validate(this, PasswordValidatorGroup.class);
		}
		else{
			ValidatorBean.validate(this, IdValidation.class);
		}
		
	}
	
	@Override
	protected void copyTo(SystemUser o, boolean reload, boolean override,
			boolean validate) throws Throwable {
		
		/*
		if(!o.getEmail().equals(this.getEmail())){
			String error = MessageBundleUtils
					.getMessageResourceString(
							Constants.SYSTEM_RESOURCE_BUNDLE,
							SystemMessagesConstants.system.error.pattern_message, 
							new Object[]{"email"},
							LocaleProvider.getLocale());
			throw new ValidationException(error);
		}
		*/
		if(super.getPassword() != null){
			o.setPassword(super.getPassword());
		}
		
	}
	
	@Transient
	public void setFirstName(String firstName) {
		super.setFirstName(firstName);
	}

	@Transient
	public void setLastName(String lastName) {
		super.setLastName(lastName);
	}

	@Transient
	public void setOrganization(String organization) {
		super.setOrganization(organization);
	}

	@Transient
	public void setCountry(CountryPubEntity country) {
		super.setCountry(country);
	}

	@Transient
	public void setCountry(String country) {
		super.setCountry(country);
	}
	
	@Transient
	public void setAddress(String address) {
		super.setAddress(address);
	}

	@Transient
	public void setComplement(String complement) {
		super.setComplement(complement);
	}

	@Transient
	public void setCity(String city) {
		super.setCity(city);
	}

	@Transient
	public void setRegion(String region) {
		super.setRegion(region);
	}

	@Transient
	public void setZip(String zip) {
		super.setZip(zip);
	}

	@Transient
	public void setPhone(String phone) {
		super.setPhone(phone);
	}
	
}
