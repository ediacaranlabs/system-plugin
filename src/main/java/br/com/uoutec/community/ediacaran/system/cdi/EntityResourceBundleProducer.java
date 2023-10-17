package br.com.uoutec.community.ediacaran.system.cdi;

import javax.inject.Singleton;

import br.com.uoutec.entityresourcebundle.EntityResourceBundle;

public class EntityResourceBundleProducer {

	private EntityResourceBundle value;
	
	public EntityResourceBundleProducer(){
		this.value = new EntityResourceBundlePublic();
	}
	
	@Singleton
	public EntityResourceBundle create() {
		return value;
	}
}
