package br.com.uoutec.community.ediacaran.system.tema;

import java.io.IOException;
import java.io.Writer;

import br.com.uoutec.community.ediacaran.system.tema.TagTemplate.VarParser;

public class AbstractVarParser implements VarParser{

	@Override
	public void parse(Writer writter) throws IOException {
	}

	@Override
	public String parse() throws IOException {
		return null;
	}

	
}