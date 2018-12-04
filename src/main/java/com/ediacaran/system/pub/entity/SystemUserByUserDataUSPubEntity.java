package com.ediacaran.system.pub.entity;

import org.brandao.brutos.annotation.Constructor;

import com.ediacaran.system.entity.SystemUser;
import com.ediacaran.system.util.AbstractEntityInheritanceUtil.EntityInheritance;

@EntityInheritance(base=SystemUserByUserDataPubEntity.class, name="USA")
public class SystemUserByUserDataUSPubEntity 
	extends SystemUserByUserDataPubEntity{

	private static final long serialVersionUID = -2915428559787116170L;

	@Constructor
	public SystemUserByUserDataUSPubEntity(){
	}
	
	public SystemUserByUserDataUSPubEntity(SystemUser e){
		super(e);
	}
	
}
