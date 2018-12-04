package com.ediacaran.system.pub.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.brandao.brutos.annotation.Constructor;

import com.ediacaran.system.entity.SystemUser;
import com.ediacaran.system.entity.SystemUserBR;
import com.ediacaran.system.util.AbstractEntityInheritanceUtil.EntityInheritance;
import com.ediacaran.system.util.DocumentUtil;

import br.com.uoutec.i18n.MessageBundleUtils;
import br.com.uoutec.i18n.MessagesConstants;
import br.com.uoutec.i18n.ValidationException;
import br.com.uoutec.pub.entity.InvalidRequestException;

@EntityInheritance(base=SystemUserByUserDataPubEntity.class, name="BRA")
public class SystemUserByUserDataBRPubEntity extends SystemUserByUserDataPubEntity{

	private static final long serialVersionUID = 1465096757319557192L;
	
	@NotNull
	@Pattern(regexp=DocumentUtil.CPF_CNPJ_PATTERN)
	private String document;
	
	@Constructor
	public SystemUserByUserDataBRPubEntity(){
	}
	
	public SystemUserByUserDataBRPubEntity(SystemUser e){
		super(e);
	}
	
	protected void validate(boolean reload, boolean override, 
			Class<?> ... groups) throws ValidationException{
		super.validate(reload, override, groups);
		
		if(!"00000000000".equals(this.document) && !DocumentUtil.isValidCPF_CNPJ(this.document)){
			String error = MessageBundleUtils
					.getMessageResourceString(
							MessagesConstants.RESOURCE_BUNDLE,
							MessagesConstants.system.error.not_match_message, 
							new Object[]{"document"});
			throw new ValidationException(error);
		}
	}
	
	@Override
	protected void copyTo(SystemUser o, boolean reload, boolean override,
			boolean validate) throws Throwable {

		super.copyTo(o, reload, override, validate);
		
		SystemUserBR e = (SystemUserBR)o;
		
		if(e.getDocument() == null){
			if("00000000000".equals(this.document)){
				String error = MessageBundleUtils
						.getMessageResourceString(
								MessagesConstants.RESOURCE_BUNDLE,
								MessagesConstants.system.error.not_match_message, 
								new Object[]{"document"});
				throw new InvalidRequestException(error);
			}
			else{
				e.setDocument(this.document);
			}
		}
		else
		if(!e.getDocument().equals(this.document) && !"00000000000".equals(this.document)){
			String error = MessageBundleUtils
					.getMessageResourceString(
							MessagesConstants.RESOURCE_BUNDLE,
							MessagesConstants.system.error.not_match_message, 
							new Object[]{"document"});
			throw new InvalidRequestException(error);
		}		
	}

}
