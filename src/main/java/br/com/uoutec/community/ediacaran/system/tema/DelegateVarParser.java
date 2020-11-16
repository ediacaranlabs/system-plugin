package br.com.uoutec.community.ediacaran.system.tema;

import java.io.IOException;
import java.io.Writer;

import br.com.uoutec.community.ediacaran.system.tema.TagTemplate.VarParser;

public class DelegateVarParser implements VarParser{

	private VarParser varParser;
	
	public VarParser getVarParser() {
		return varParser;
	}

	public void setVarParser(VarParser varParser) {
		this.varParser = varParser;
	}

	@Override
	public void parse(Writer writter) throws IOException {
		if(varParser != null) {
			varParser.parse(writter);
		}
	}

	@Override
	public String parse() throws IOException {
		return varParser.parse();
	}

}
