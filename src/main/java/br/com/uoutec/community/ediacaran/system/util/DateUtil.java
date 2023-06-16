package br.com.uoutec.community.ediacaran.system.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

public class DateUtil {

	public static int getDays(Date dataLow, Date dataHigh){

		if( dataLow == null || dataHigh == null )
			throw new NullPointerException();

		Calendar low = Calendar.getInstance();
		Calendar high = Calendar.getInstance();

		low.setTime(dataLow);
		high.setTime(dataHigh);

		int yearLow = low.get(Calendar.YEAR);
		int yearHigh = high.get(Calendar.YEAR);

		if( yearHigh < yearLow )
			throw new IllegalArgumentException();

		if( yearLow == yearHigh ){
			return high.get(Calendar.DAY_OF_YEAR) - low.get(Calendar.DAY_OF_YEAR); 
		}
		else{
			int days = 0;
			Calendar c = GregorianCalendar.getInstance();
			c.set(Calendar.DAY_OF_MONTH, 31);
			c.set(Calendar.MONTH, 11);
			for( int year=yearLow;year<yearHigh; year++ ){
				c.set(Calendar.YEAR, year);
				days += c.get(Calendar.DAY_OF_YEAR);
			}
			days -= low.get(Calendar.DAY_OF_YEAR);
			days += high.get(Calendar.DAY_OF_YEAR);
			return days;
		}
	}
	
	public static long getHours(Date dataLow, Date dataHigh){

		int fullDays = 0;
		
		if( dataLow == null || dataHigh == null )
			throw new NullPointerException();

		Calendar low = Calendar.getInstance();
		Calendar high = Calendar.getInstance();

		low.setTime(dataLow);
		high.setTime(dataHigh);

		int hourLow  = low.get(Calendar.HOUR_OF_DAY);
		int minLow   = low.get(Calendar.MINUTE);
		int secLow   = low.get(Calendar.SECOND);
		int yearLow  = low.get(Calendar.YEAR);
		
		int hourHigh = high.get(Calendar.HOUR_OF_DAY);
		int minHigh  = high.get(Calendar.MINUTE);
		int secHigh  = high.get(Calendar.SECOND);
		int yearHigh = high.get(Calendar.YEAR);

		if( yearHigh < yearLow )
			throw new IllegalArgumentException();

		if( yearLow == yearHigh ){
			fullDays = high.get(Calendar.DAY_OF_YEAR) - low.get(Calendar.DAY_OF_YEAR); 
		}
		else{
			int days = 0;
			Calendar c = GregorianCalendar.getInstance();
			c.set(Calendar.DAY_OF_MONTH, 31);
			c.set(Calendar.MONTH, 11);
			for( int year=yearLow;year<yearHigh; year++ ){
				c.set(Calendar.YEAR, year);
				days += c.get(Calendar.DAY_OF_YEAR);
			}
			days -= low.get(Calendar.DAY_OF_YEAR);
			days += high.get(Calendar.DAY_OF_YEAR);
			fullDays = days;
		}
		
		long timeLow = 
				TimeUnit.HOURS.toSeconds(hourLow) + 
				TimeUnit.MINUTES.toSeconds(minLow) +
				secLow;
		
		long timeHight = 
				TimeUnit.HOURS.toSeconds(hourHigh) + 
				TimeUnit.MINUTES.toSeconds(minHigh) +
				secHigh;
		
		long totalSeconds = TimeUnit.DAYS.toSeconds(fullDays);
		totalSeconds -= timeLow;
		totalSeconds += timeHight;
		return TimeUnit.SECONDS.toHours(totalSeconds);
	}	
}
