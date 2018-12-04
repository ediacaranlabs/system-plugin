package com.ediacaran.system;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.ediacaran.system.entity.SystemUser;
import com.ediacaran.system.pub.entity.SystemUserByUserDataPubEntity;
import com.ediacaran.system.util.EntityInheritanceUtil;

@Singleton
public class SystemUserEntityTypes {

	@Inject
	private EntityInheritanceUtil entityInheritanceUtil;

	public String getSystemUserEntityView(SystemUser systemUser){
		if(systemUser.getCountry() != null && 
				this.entityInheritanceUtil.getType(SystemUser.class, systemUser.getCountry().getIsoAlpha3()) != null){
			return 
				"user-data" +
				"-" + systemUser.getCountry().getIsoAlpha2().toLowerCase() +
				".jsp";
		}
		else{
			return 
				"user-data.jsp";
		}
	}
	
	public Map<String, Class<?>> getSystemUserByUserDataPubEntityTypes() {
		return this.entityInheritanceUtil.getMap(SystemUserByUserDataPubEntity.class);
	}
	
}
