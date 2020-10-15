package br.com.uoutec.community.ediacaran.system.tema;

public class AttributeParserImp implements AttributeParser{

	@Override
	public String toName(String value, Object component) {
		return value;
	}

	@Override
	public Object toValue(Object value, Object component) {
		return value == null? null : String.valueOf(value);
	}

}
