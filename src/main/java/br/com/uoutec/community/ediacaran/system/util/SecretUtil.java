package br.com.uoutec.community.ediacaran.system.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class SecretUtil {

	private static final String SECRET = IDGenerator.getUniqueOrderID('S', 0);
	
	//private static final Decoder base64Decoder = Base64.getDecoder();

	//private static final Encoder base64Encoder = Base64.getEncoder();
	
	public static String toPassword(String value, String id){
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hashbytes = digest.digest((value + ":" + id).getBytes(StandardCharsets.UTF_8));
			return toString(hashbytes);
		}
		catch(Throwable ex) {
			throw new IllegalStateException(ex);
		}
	}

	public static String encode(String value, String secret){
		try {
			SecretKeySpec key = 
				generateSecretKey(secret.toCharArray(), secret.getBytes(), secret.length() % 42, 128, "AES");
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
		
		byte[] cipherText = cipher.doFinal(text.getBytes(encode));
		
		return toString(cipherText);
	}
	
	public static String decode(String value, String secret){
		try {
			SecretKeySpec key = 
				generateSecretKey(secret.toCharArray(), secret.getBytes(), secret.length() % 42, 128, "AES");
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
        byte[] encrypted = toBytes(value);
        
        byte[] original  = cipher.doFinal(encrypted);
        return new String(original, encode);
	}
	
	public static SecretKeySpec generateSecretKey(char[] password, byte[] salt, 
			int iterationCount, int keyLength, String algorithm) throws NoSuchAlgorithmException, InvalidKeySpecException {
		
        PBEKeySpec keySpec = new PBEKeySpec(password, salt, iterationCount, keyLength);
        Arrays.fill(password, Character.MIN_VALUE);  
        try {
        	SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        	SecretKey tempKey = keyFactory.generateSecret(keySpec);
        	//System.out.println(base64Encoder.encodeToString(tempKey.getEncoded()));
            return new SecretKeySpec(tempKey.getEncoded(), algorithm);
        }
        finally{
        	keySpec.clearPassword();
        }
        
    }
	
	private static String toString(byte[] value){
		StringBuilder builder = new StringBuilder();
		
		for(byte b: value){
			builder.append(Character.forDigit((b >> 4) & 0xF, 16));
			builder.append(Character.forDigit((b & 0xF), 16));
		}

		return builder.toString();
	}
	
	private static byte[] toBytes(String value){
		byte[] data = new byte[value.length() / 2];
		
		for(int i=0;i<data.length;i++){
			int start = i*2;
			int end   = start + 2;
			String part = value.substring(start, end);
			data[i] = (byte)Integer.parseInt(part, 16);
		}
		return data;
	}
	
	public static boolean isEquals(String a, String b) {
		return a == null || b == null? false : a.equals(b); 
	}

	public static String toProtectedID(String id) {
		return SecretUtil.encode(String.valueOf(id), SECRET);
	}

	public static String toID(String protectedID) {
		try {
			return SecretUtil.decode(protectedID, SECRET);
		}
		catch(Throwable ex){
			return null;
		}
	}
	
}
