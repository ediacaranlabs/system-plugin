package com.ediacaran.system.pub.entity;

import org.brandao.brutos.annotation.Constructor;
import org.brandao.brutos.annotation.Transient;

import com.ediacaran.system.entity.Country;
import com.ediacaran.system.entity.SystemUser;
import com.ediacaran.system.registry.CountryRegistry;
import com.ediacaran.system.util.AbstractEntityInheritanceUtil.EntityInheritance;

import br.com.uoutec.application.EntityContext;
import br.com.uoutec.entityresourcebundle.EntityResourceBundle;
import br.com.uoutec.i18n.MessageBundleUtils;
import br.com.uoutec.i18n.MessagesConstants;
import br.com.uoutec.i18n.ValidationException;
import br.com.uoutec.i18n.ValidatorBean;
import br.com.uoutec.pub.entity.IdValidation;
import br.com.uoutec.pub.entity.InvalidRequestException;

@EntityInheritance(base=SystemUserByUserDataPubEntity.class, name="XXX")
public class SystemUserByUserDataPubEntity extends SystemUserByUserPubEntity{

	private static final long serialVersionUID = -6424904630035297618L;

	@Constructor
	public SystemUserByUserDataPubEntity(){
	}
	
	public SystemUserByUserDataPubEntity(SystemUser e){
		super(e);
	}
	
	@Override
	protected void copyTo(SystemUser o, boolean reload, boolean override,
			boolean validate) throws Throwable {
		
		CountryRegistry registry = EntityContext.getEntity(CountryRegistry.class);
		Country c = registry.getCountryByIsoAlpha3(this.getCountryCode());
		
		if(!"XXX".equals(this.getCountryCode()) && c == null){
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
		
		o.setFirstName(super.getFirstName());
		o.setLastName(super.getLastName());
		o.setAddress(super.getAddress());
		o.setCity(super.getCity());
		o.setComplement(super.getComplement());
		o.setOrganization(super.getOrganization());
		o.setPhone(super.getPhone());
		o.setRegion(super.getRegion());
		o.setZip(super.getZip());
	}

	protected void validate(boolean reload, boolean override, 
			Class<?> ... groups) throws ValidationException{

		super.validate(reload, override, groups);
		
		if(reload && override){
			ValidatorBean.validate(this, IdValidation.class, DataValidatorGroup.class);
		}
		else
		if(override){
			ValidatorBean.validate(this, DataValidatorGroup.class);
		}
		else{
			ValidatorBean.validate(this, IdValidation.class);
		}
		
	}
	
	@Transient
	public void setPassword(String password) {
		super.setPassword(password);
	}

	@Transient
	public void setPasswordConf(String passwordConf) {
		super.setPasswordConf(passwordConf);
	}

}
