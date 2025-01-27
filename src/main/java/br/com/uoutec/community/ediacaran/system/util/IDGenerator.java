package br.com.uoutec.community.ediacaran.system.util;

import br.com.uoutec.application.SystemProperties;

public class IDGenerator {

	private static String CHARP = "QWERTY5IOPLKJHGF3SAZXCVBNM";
	
	private static int[] counts = new int[CHARP.length()];

	private static Object[] locks = new Object[CHARP.length()];
	
	private static final long startTime = SystemProperties.currentTimeMillis();
	
	static{
		
		for(int i=0;i<counts.length;i++){
			counts[i] = 0;
		}
		
		for(int i=0;i<locks.length;i++){
			locks[i] = new Object();
		}
		
	}
	
	public static String getUniqueOrderID(char prefix, int group){
		
		int index   = (int)(Thread.currentThread().getId() % locks.length);
		Object lock = locks[index];
		
		int currentIndex;
		
		synchronized (lock) {
			currentIndex = counts[index]++ % 1000;
		}
		
		return getID(prefix, group, index, currentIndex, startTime, SystemProperties.currentTimeMillis());
	}

	private static String getID(char prefix, int group, 
			int subGroup, int index, long startTime, long time){
		
		StringBuilder idBuilder = new StringBuilder();
		idBuilder
			.append("U")
			.append(Long.toString(Math.abs(startTime), Character.MAX_RADIX))
			.append(Long.toString(Math.abs(group), Character.MAX_RADIX))
			.append(prefix)
			.append(CHARP.charAt(subGroup))
			.append(Long.toString(Math.abs(time), Character.MAX_RADIX))
			.append(Long.toString(Math.abs(index), Character.MAX_RADIX));
		
		return idBuilder.toString().toUpperCase();
	}

	/*
	public static void main(String[] s){
		String result = IDN.toASCII("παράδειγμα.δοκιμή");
		result = IDN.toASCII("παράδειγμα.δοκιμή.ods.net.br");
		result = IDN.toASCII("ods.net.br");
	}
	*/
	
	/*
	public static void main(String[] s){
		String id = getID('P', Integer.MAX_VALUE, locks.length - 1, 1000, Long.MAX_VALUE, Long.MAX_VALUE);
		id = getUniqueOrderID('P', Integer.MAX_VALUE);
	}
	 */	
	
}
