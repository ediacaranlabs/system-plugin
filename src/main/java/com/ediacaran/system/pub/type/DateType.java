package com.ediacaran.system.pub.type;

import java.text.DateFormat;
import java.util.Date;

import org.brandao.brutos.type.DefaultDateType;
import org.brandao.brutos.type.UnknownTypeException;

import br.com.uoutec.i18n.MessageLocale;

public class DateType 
	extends DefaultDateType{

	public Object convert(Object value) {
		if (value instanceof Date){
			return value;
		}
		else
		if (value instanceof String){
			return ((String) value).isEmpty() ? null : toValue((String) value);
		}
		else
		if (value == null){
			return null;
		}
		else{
			throw new UnknownTypeException(value.getClass().getName());
		}
	}
	
	private Object toValue(String value) {
		try {
			DateFormat sdf = DateFormat.getDateInstance(DateFormat.MEDIUM, MessageLocale.getLocale());
			return sdf.parse(value);
		} catch (Throwable e) {
			return null;
		}
	}
	
}
