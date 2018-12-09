package br.com.uoutec.community.ediacaran.system.pub.type;

import java.util.Date;

import org.brandao.brutos.annotation.TypeDef;
import org.brandao.brutos.type.DefaultTypeFactory;

@TypeDef
public class DateFactory extends DefaultTypeFactory{

	public DateFactory() {
		super(DateType.class, Date.class);
	}

}
