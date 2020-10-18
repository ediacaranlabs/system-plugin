package br.com.uoutec.community.ediacaran.system.tema;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TagTemplate {

    private String original;
    
    private Pattern pattern;

    private Pattern prePattern;
    
    private String stringPattern;
    
    private List<TagTemplateVar> vars;

    public TagTemplate(String value){
        this.parse(value);
        this.original   = value;
        this.pattern    = 
    		Pattern.compile(this.createRegex(), Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
        this.prePattern =
			Pattern.compile(this.createPreRegex(), Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
        this.stringPattern = 
    		this.createPattern();
    }

    /* -- private methods -- */
    
    private void parse(String content) {
    	
        List<String> frags  = new ArrayList<String>();
        List<String> ids    = new ArrayList<String>();
        List<String> regexs = new ArrayList<String>();
        int startFrag       = 0;
        char[] chars        = content.toCharArray();
        int openKeysCount   = 0;
        int closeKeysCount  = 0;
        int firstOpenKeys   = -1;
        int lastCloseKeys   = -1;

        for(int i=0;i<chars.length;i++){
        	
        	if(chars[i] == '{'){
        		
        		if(firstOpenKeys == -1){
        			frags.add(decode(content.substring(startFrag, i)));
        			firstOpenKeys = i;
        		}
        		
        		openKeysCount++;
        	}
        	
        	if(chars[i] == '}'){
        		lastCloseKeys = i;
        		closeKeysCount++;
        	}
        	
        	if(openKeysCount > 0 && openKeysCount == closeKeysCount){
        		
        		
        		String var = content.substring(firstOpenKeys + 1, lastCloseKeys);

        		int separatorIndex = var.indexOf(":");
        		
        		String id = 
    				var.indexOf(":") == -1?
    					var :
    					var.substring(0, separatorIndex);
    					
		        if(id == null || id.isEmpty())
		            throw new IllegalStateException("invalid parameter id " + var);
    					
    			String regex = 
					separatorIndex != -1?
						var.substring(separatorIndex + 1, var.length()) :
							null;
						
				regex = regex == null || regex.isEmpty()? "\\w{1,}" : regex;
				
				ids.add(id);
				regexs.add(regex);
				
				startFrag      = i + 1;
				firstOpenKeys  = -1;
				lastCloseKeys  = -1;
		        openKeysCount  = 0;
		        closeKeysCount = 0;
        	}
        }
        
        if(openKeysCount > 0 && openKeysCount != closeKeysCount){
            throw new IllegalStateException("expected: }");
        }
        
        if(startFrag >= 0 && startFrag <= content.length()){
        	if(startFrag == content.length())
                frags.add(null);
        	else
        		frags.add(decode(content.substring(startFrag, content.length())));
        }
        

        vars = new ArrayList<TagTemplateVar>();

        for( int i=0;i<ids.size();i++ ){
        	
            StringBuilder regexPrefix = new StringBuilder("^");
            StringBuilder regexSuffix = new StringBuilder("");
            
            for(int k=0;k<=i;k++){
            	String value = frags.get(k);
                regexPrefix.append( value == null || value.isEmpty()? "" : Pattern.quote(value) );
                
                if(i>0 && k<i){
                   regexPrefix.append( regexs.get(k) );
                }
            }
        	
            for(int k=i;k<ids.size();k++){
            	
                if(k>i){
                    regexSuffix.append( regexs.get(k) );
                }
                
                String value = frags.get(k+1);
                regexSuffix.append(value == null || value.isEmpty()? "" : Pattern.quote(value) );
            }
            
            regexSuffix.append("$");
            
            vars.add(
                    new TagTemplateVar(
                        i,
                        ids.get(i),
                        Pattern.compile(regexs.get(i), Pattern.DOTALL | Pattern.CASE_INSENSITIVE),
                        frags.get(i),
                        frags.get(i+1),
                        Pattern.compile(regexPrefix.toString(), Pattern.DOTALL | Pattern.CASE_INSENSITIVE),
                        Pattern.compile(regexSuffix.toString(), Pattern.DOTALL | Pattern.CASE_INSENSITIVE)) );
        }
        
    }

    private String decode(String value) {
    	String pattern = "&#(\\d{1,3});";
    	Pattern r = Pattern.compile(pattern);
    	Matcher m = r.matcher(value);
    	while(m.find()) {
    		String charCodeSTR = m.group(1);
    		int charInt = Integer.parseInt(charCodeSTR);
    		String charValue = Character.toString((char)(charInt & 0xFF));
    		value = m.replaceAll(charValue);
    	}
    	return value;
    }
    
    private String createPreRegex(){
        StringBuilder result = new StringBuilder();
        
        if(vars.isEmpty())
            return this.original;
        
        
        for( int i=0;i<vars.size();i++ ){
        	TagTemplateVar p = (TagTemplateVar)vars.get(i);

            if(i == 0){
            	
                if(p.getStart() != null && p.getStart().length() > 0){
                    result
                    	.append( (p.getStart() == null? "" : Pattern.quote(p.getStart())) );
                }
                
                result
                	.append("(")
                	.append(".*")
                	.append(")");
                
                if(p.getEnd() != null && p.getEnd().length() > 0){
                    result
                    	.append( (p.getEnd() == null? "" : Pattern.quote(p.getEnd())) );
                }
            }
            else{
            	
                result
                	.append("(")
                	.append(".*")
                	.append(")");
                
                if(p.getEnd() != null && p.getEnd().length() > 0){
                    result
                    	.append( (p.getEnd() == null? "" : Pattern.quote(p.getEnd())) );
                }
            }
            
        }
        
        return result.toString();
        
    }
    
    private String createPattern(){
        StringBuilder value = new StringBuilder();
        
        if(vars.isEmpty())
            return this.original;
        
        for(int i=0;i<vars.size();i++ ){
            TagTemplateVar p = vars.get(i);
            
            if(i == 0 && !p.isEmptyStart()){
                value.append(p.getStart());
            }

            value.append("(").append(p.getId()).append(")");
            
            if(!p.isEmptyEnd()){
                value.append(p.getEnd());
            }
            
        }
        
        return value.toString();
        
    }
    
    private String createRegex(){
        StringBuilder value = new StringBuilder();
        
        if(vars.isEmpty())
            return this.original;
        
        for(int i=0;i<vars.size();i++ ){
            TagTemplateVar p = vars.get(i);
            
            if(i == 0 && !p.isEmptyStart()){
            	value.append(Pattern.quote(p.getStart()));
            }
            
            value.append(p.getRegex());
            
            if(!p.isEmptyEnd()){
                value.append(Pattern.quote(p.getEnd()));
            }
            
        }
        
        return value.toString();
        
    }
    
    /* -- public methods -- */
    
    public String getPattern(){
    	return this.stringPattern;
    }
    
    public String toString(Object ... params){
        String value = null;
        
        if(vars.isEmpty())
            return this.original;
        
        for(int i=0;i<vars.size();i++ ){
            TagTemplateVar p = vars.get(i);
            
            if(i == 0 && p.getStart() != null){
                value = p.getStart();
            }

            value += String.valueOf(params[p.getIndex()]);
            
            if(p.getEnd() != null){
                value += p.getEnd();
            }
            
        }
        
        return value;
    }
    
    public void toWriter(Writer writter, Object ... params) throws IOException{
        
        if(vars.isEmpty()) {
        	writter.write(this.original);
            return;
        }
        
        for(int i=0;i<vars.size();i++ ){
            TagTemplateVar p = vars.get(i);
            
            if(i == 0 && p.getStart() != null){
                writter.write(p.getStart());
            }

            Object v = params[p.getIndex()];
            
            if(v instanceof VarParser) {
            	((VarParser)v).parse(writter);
            }
            else
            if(v instanceof TagTemplate) {
            	((TagTemplate)v).toWriter(writter, params);
            }
            else
            	writter.write(String.valueOf(v));
            
            if(p.getEnd() != null){
            	writter.write(p.getEnd());
            }
            
        }
        
    }

    public void toWriter(Writer writter, Map<String, Object> params) throws IOException{
        
        if(vars.isEmpty()) {
        	writter.write(this.original);
            return;
        }
        
        for(int i=0;i<vars.size();i++ ){
            TagTemplateVar p = vars.get(i);
            
            if(i == 0 && p.getStart() != null){
                writter.write(p.getStart());
            }

            Object v = params.get(p.getId());
            
            if(v instanceof VarParser) {
            	((VarParser)v).parse(writter);
            }
            else
            if(v instanceof TagTemplate) {
            	((TagTemplate)v).toWriter(writter, params);
            }
            else
            if(v != null) {
            	writter.write(String.valueOf(v));
            }
            
            if(p.getEnd() != null){
            	writter.write(p.getEnd());
            }
            
        }
        
    }
    
    public Map<String,List<String>> getParameters( String value ){
        Map<String,List<String>> params = new HashMap<String,List<String>>();

        for( int i=0;i<vars.size();i++ ){
        	TagTemplateVar p = vars.get(i);
            String tmp     = value;
            tmp            = p.getRegexPrefix().matcher(tmp).replaceAll("");
            tmp            = p.getRegexSuffix().matcher(tmp).replaceAll("");
            
            List<String> values = params.get(p.getId());
            
            if(values == null){
                values = new ArrayList<String>();
                params.put(p.getId(), values);
            }
            
            values.add(tmp);
        }

        return params;
    }
    
    public boolean matches(String value){
    	if(this.vars.isEmpty()){
    		return this.original.equalsIgnoreCase(value);
    	}
    	else{
    		return 
				this.prePattern.matcher(value).matches() && 
				this.pattern.matcher(value).matches();
    	}
    }

	public List<TagTemplateVar> getVars() {
		return vars;
	}
    
	public static interface VarParser{
		
		void parse(Writer writter) throws IOException;
		
		String parse() throws IOException;
	}
	
}
