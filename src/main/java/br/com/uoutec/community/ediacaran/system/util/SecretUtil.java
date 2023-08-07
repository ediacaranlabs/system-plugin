package br.com.uoutec.community.ediacaran.system.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class SecretUtil {

	private static final Decoder base64Decoder = Base64.getDecoder();

	private static final Encoder base64Encoder = Base64.getEncoder();
	
	public static String toPassword(String value, String id){
		try {
			SecretKeySpec key = 
				generateSecretKey(value.toCharArray(), id.getBytes(), id.length()*value.length() % 1024, 128);
			return encode(value, "UTF-8", "AES", key);
		}
		catch(Throwable ex) {
			throw new IllegalStateException(ex);
		}
	}

	public static String encode(String value, String secret){
		try {
			SecretKeySpec key = 
				generateSecretKey(secret.toCharArray(), secret.getBytes(), secret.length() % 42, 128);
			return encode(value, "UTF-8", "AES", key);
		}
		catch(Throwable ex) {
			throw new IllegalStateException(ex);
		}
	}

	public static String encode(String text, String encode, 
			String algorithm, Key key) throws NoSuchAlgorithmException, 
			NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {
		
		Cipher cipher = Cipher.getInstance(algorithm);
		cipher.init(Cipher.ENCRYPT_MODE, key);
		
		byte[] input = text.getBytes(encode);
		
		cipher.update(input);
		byte[] cipherText = cipher.doFinal();
		
		return base64Encoder.encodeToString(cipherText);
	}
	
	public static String decode(String value, String secret){
		try {
			SecretKeySpec key = 
				generateSecretKey(secret.toCharArray(), secret.getBytes(), secret.length() % 42, 128);
			return decode(value, "UTF-8", "AES", key);
		}
		catch(Throwable ex) {
			throw new IllegalStateException(ex);
		}
	}
	
	
	public static String decode(String value, String encode, 
			String algorithm, Key key) throws NoSuchAlgorithmException, 
		NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException{
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] encrypted = base64Decoder.decode(value);
        
        byte[] original  = cipher.doFinal(encrypted);
        return new String(original, encode);
	}
	
	public static SecretKeySpec generateSecretKey(char[] password, byte[] salt, 
			int iterationCount, int keyLength) throws NoSuchAlgorithmException, InvalidKeySpecException {
		
        PBEKeySpec keySpec = new PBEKeySpec(password, salt, iterationCount, keyLength);
        Arrays.fill(password, Character.MIN_VALUE);  
        try {
        	SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        	SecretKey tempKey = keyFactory.generateSecret(keySpec);
        	//System.out.println(base64Encoder.encodeToString(tempKey.getEncoded()));
            return new SecretKeySpec(tempKey.getEncoded(), "AES");
        }
        finally{
        	keySpec.clearPassword();
        }
        
    }
	
	public static boolean isEquals(String a, String b) {
		return a == null || b == null? false : a.equals(b); 
	}
	
}
