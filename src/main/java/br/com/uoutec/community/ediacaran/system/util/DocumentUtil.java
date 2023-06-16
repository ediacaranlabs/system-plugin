package br.com.uoutec.community.ediacaran.system.util;

public class DocumentUtil {

	public static final String CPF_CNPJ_PATTERN = "([0-9]{11,11})|([0-9]{14,14})";
	
	public static boolean isValidCPF_CNPJ(String document){
		return isValidDocument(document);
	}
	
	private static boolean isValidDocument(String doc){
		
		if(doc == null || (doc.length() != 11 && doc.length() != 14)){
			return false;
		}
		
		char[] chars = doc.toCharArray();
		boolean r    = false;
		char tmpChar = chars[0];
		int dv1;
		int dv2;
		
		for(int i=0;i<chars.length;i++){
			if(tmpChar != chars[i]){
				r = true;
				break;
			}
		}
		
		if(!r){
			return false;
		}
		
		if(doc.length() == 11){
			dv1 = calcDVCPF_CNPJ(doc, 0, 9, 10);
			dv2 = calcDVCPF_CNPJ(doc, 0, 10, 11);
		}
		else
		if(doc.length() == 14){
			dv1 = calcDVCPF_CNPJ(doc, 0, 12, 5);
			dv2 = calcDVCPF_CNPJ(doc, 0, 13, 6);
		}
		else{
			return false;
		}
		
		r = 
			(chars[doc.length() - 2] - '0') == dv1 && 
			(chars[doc.length() - 1] - '0') == dv2;
		return r;
		
	}

	private static int calcDVCPF_CNPJ(String doc, int start, int len, int p){
		
		if(doc == null || doc.isEmpty()){
			return -1;
		}
		
		char[] chars = doc.toCharArray();
		int length   = start + len;
		int soma     = 0;
		int dv;
		
		for(int i=start;i<length;i++){
			soma += (chars[i] - '0')*p;
			p--;
			
			if(p < 2){
				p = 9;
			}
			
		}
		
		dv = soma % 11;
		dv = dv <2? 0 : 11 - dv;
		return dv;
	}
	
}
