package com.brightcove.commons.misc.date;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 *    Provides utilities for working with Dates and Times
 * </p>
 * 
 * @author <a href="https://github.com/three4clavin">three4clavin</a>
 *
 */
public class DateTimeUtils {
	/**
	 * <p>
	 *    Generates a timestamp safe for use with a filename
	 * </p>
	 * <p>
	 *    Passing in null will use the current date/time
	 * </p>
	 * 
	 * @param date Date/time to generate stamp for
	 * @return String with a timestamp suitable for use in a filename
	 */
	public static String genTimestamp(Date date){
		if(date == null){
			date = new Date();
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmssZ");
		return(sdf.format(date));
	}
	
	/**
	 * <p>
	 *    Generates a human readable timestamp - NOT safe for use with
	 *    a filename
	 * </p>
	 * <p>
	 *    Passing in null will use the current date/time
	 * </p>
	 * 
	 * @param date Date/time to generate stamp for
	 * @return String with a human readable timestamp
	 */
	public static String genReadableTimestamp(Date date){
		if(date == null){
			date = new Date();
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss Z");
		return(sdf.format(date));
	}
}
