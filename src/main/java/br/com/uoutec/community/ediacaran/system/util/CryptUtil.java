package br.com.uoutec.community.ediacaran.system.util;

import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;

@Deprecated
@SuppressWarnings("restriction")
public class CryptUtil {

	//private static final Random random = new Random();
	
	private static byte[] key;
	
	private static final BASE64Decoder base64Decoder = new BASE64Decoder();
	
	public static void setKey(String akey) {
		try{
			key = base64Decoder.decodeBuffer(akey);
		}
		catch (Throwable e) {
			throw new IllegalStateException(e);
		}
	}
	
	public static String getNewKey(){
		try{
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			SecureRandom random = new SecureRandom(); 
			kgen.init(random);			
	        SecretKey skey = kgen.generateKey();
	        byte[] raw = skey.getEncoded();
	        return encode(raw);
		}
		catch(Throwable e){
			return null;
		}
	}
	
	public static String encrypt(String value){
		try {
	        SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
	        Cipher cipher = Cipher.getInstance("AES");
	        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
	        byte[] encrypted = cipher.doFinal(value.getBytes());
	        encrypted = obscuringData(encrypted);
	        
	        return encode(encrypted);			
		}
		catch(Throwable e){
			return null;
		}
	}
	
	public static String decrypt(String value){
		try {
	        SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
	        Cipher cipher = Cipher.getInstance("AES");
	        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(skeySpec.getEncoded(), "AES"));
	        byte[] encrypted = decode(value);
	        encrypted = clearData(encrypted);
	        
	        byte[] original  = cipher.doFinal(encrypted);
	        return new String(original);
		}
		catch(Throwable e){
			return null;
		}
	}
	
	public static boolean isEquals(String o1, String o2){
		try{
			byte[] o1Data = decode(o1);
			byte[] o2Data = decode(o2);
			
			o1Data = clearData(o1Data);
			o2Data = clearData(o2Data);
			
			return Arrays.equals(o1Data, o2Data);
		}
		catch(Throwable e){
			return false;
		}
	}
	
	private static byte[] obscuringData(byte[] value){
        //byte enc = (byte)random.nextInt(255);
		int encIndex = value.length/2;
		byte enc     = value.length == 0? 0 : value[encIndex];
        value        = Arrays.copyOf(value, value.length + 1);
        value[value.length - 1] = enc;
        
        for(int i=0;i<value.length - 1; i++){
        	value[i] = i == encIndex? value[i] : (byte)(value[i] ^ enc);
        }
		
        return value;
	}

	private static byte[] clearData(byte[] value){
		//byte enc = value[value.length - 1];
        value        = Arrays.copyOf(value, value.length - 1);
		int encIndex = value.length/2;
		byte enc     = value.length == 0? 0 : value[encIndex];
        
        for(int i=0;i<value.length; i++){
        	value[i] = i == encIndex? value[i] : (byte)(value[i] ^ enc);
        }
		
        return value;
	}

	private static String encode(byte[] value){
		StringBuilder builder = new StringBuilder();
		
		for(byte b: value){
			builder.append(Character.forDigit((b >> 4) & 0xF, 16));
			builder.append(Character.forDigit((b & 0xF), 16));
		}

		return builder.toString();
	}
	
	private static byte[] decode(String value){
		byte[] data = new byte[value.length() / 2];
		
		for(int i=0;i<data.length;i++){
			int start = i*2;
			int end   = start + 2;
			String part = value.substring(start, end);
			data[i] = (byte)Integer.parseInt(part, 16);
		}
		return data;
	}
	
}
