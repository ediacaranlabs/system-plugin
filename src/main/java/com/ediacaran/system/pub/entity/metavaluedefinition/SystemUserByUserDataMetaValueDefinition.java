package com.ediacaran.system.pub.entity.metavaluedefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.brandao.brutos.annotation.configuration.MetaValueDefinition;
import org.brandao.brutos.annotation.configuration.MetaValuesDefinition;

import com.ediacaran.system.SystemUserEntityTypes;

import br.com.uoutec.application.EntityContext;

public class SystemUserByUserDataMetaValueDefinition 
	implements MetaValuesDefinition{

	public SystemUserByUserDataMetaValueDefinition() {
	}

	public List<MetaValueDefinition> getMetaValues() {

		SystemUserEntityTypes systemUserEntityTypes =
				EntityContext.getEntity(SystemUserEntityTypes.class);
		
		List<MetaValueDefinition> values = new ArrayList<MetaValueDefinition>();
		Map<String, Class<?>> userCartEntityTypes = 
				systemUserEntityTypes.getSystemUserByUserDataPubEntityTypes(); 
		for (String name: userCartEntityTypes.keySet()) {
			values.add(
				new MetaValueDefinition(
					name.isEmpty()? null : name, 
					userCartEntityTypes.get(name)));
		}
		
		return values;
	}

}
