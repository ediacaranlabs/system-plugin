package br.com.uoutec.community.ediacaran.system.util;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class StringUtil {

	public static Set<String> toSet(String value, String separator){
		
		Enumeration<String> e = new EnumarationString(value, separator);
		Set<String> s = new HashSet<String>();
		
		while(e.hasMoreElements()) {
			s.add(e.nextElement());
		}
		
		return s;
	}

	public static List<String> toList(String value, String separator){
		
		Enumeration<String> e = new EnumarationString(value, separator);
		List<String> s = new ArrayList<String>();
		
		while(e.hasMoreElements()) {
			s.add(e.nextElement());
		}
		
		return s;
	}
	
	public static Enumeration<String> toEnumeration(String value, String separator){
		return new EnumarationString(value, separator);
	}
	
	public static String toString(Enumeration<?> e, String separator) {
		StringBuilder b = new StringBuilder();
		while(e.hasMoreElements()) {
			
			if(b.length() != 0) {
				b.append(separator);
			}
			
			b.append(String.valueOf(e.nextElement()));
		}
		
		return b.toString();
	}

	public static String normalize(String name) {
		return 
			Normalizer.normalize(name, Form.NFD)
			.replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
			.replaceAll("[^a-zA-Z0-9\\s]", " ")
			.toLowerCase()
			.replaceAll("\\s+", "-");
	}

	public static String normalize(String name, String separator) {
		return 
			Normalizer.normalize(name, Form.NFD)
			.replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
			.replaceAll("[^a-zA-Z0-9\\s]", " ")
			.toLowerCase()
			.replaceAll("\\s+", separator);
	}

	public static String toSearch(String value) {
		return toSearch(value, " ");
	}

	public static String toSearch(String value, String separator) {
		
		if(value == null) {
			return null;
		}
		
		value = normalize(value, " ");
		String[] split = value.split("\\s+");
		Set<String> set = Arrays.asList(split).stream().collect(Collectors.toSet());
		List<String> list = set.stream().collect(Collectors.toList());
		Collections.sort(list, (a,b)->a.compareTo(b));
		return String.join(separator, list);
	}
	
	public static String toString(Throwable ex) {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		Writer writer = new OutputStreamWriter(bout, Charset.forName("UTF-8"));
		PrintWriter pw = new PrintWriter(writer, true);
		ex.printStackTrace(pw);
		return new String(bout.toByteArray());
	}

	public static String toString(Collection<?> l, String separator) {
		StringBuilder b = new StringBuilder();
		for(Object e: l ) {
			
			if(b.length() != 0) {
				b.append(separator);
			}
			
			b.append(String.valueOf(e));
		}
		
		return b.toString();
	}
	
	public static String toString(Object[] l, String separator) {
		
		StringBuilder b = new StringBuilder();
		for(Object e: l ) {
			
			if(b.length() != 0) {
				b.append(separator);
			}
			
			b.append(String.valueOf(e));
		}
		
		return b.toString();
	}
	
	private static class EnumarationString implements Enumeration<String>{

		private String value;
		
		private String separator;
		
		private int nextSeparator;
		
		private int currentSeparator;

		public EnumarationString(String value, String separator){
			this.currentSeparator = 0;
			this.value            = value;
			this.separator        = separator;
			this.nextSeparator    = value.indexOf(separator);
			
			if(nextSeparator == -1){
				this.nextSeparator = value.length();
			}
		}
		
		public boolean hasMoreElements() {
			return this.currentSeparator < value.length();
		}

		public String nextElement() {
			String v          = value.substring(currentSeparator, nextSeparator);
			this.currentSeparator = this.nextSeparator + 1;
			this.nextSeparator    = value.indexOf(separator, this.currentSeparator);
			
			if(this.nextSeparator == -1){
				this.nextSeparator = value.length();
			}
			return v.trim();
		}
		
	}
	
}
