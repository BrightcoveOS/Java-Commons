package com.brightcove.commons.misc.strings;

/**
 * <p>
 *    Provides utilities for working with strings
 * </p>
 * 
 * @author <a href="https://github.com/three4clavin">three4clavin</a>
 *
 */
public class StringUtils {
	
	/**
	 * <p>
	 *    Truncates a string to a specified length, adding elipses if requested.
	 * </p>
	 * 
	 * @param string String to truncate
	 * @param length Length to truncate to
	 * @param elipses String to use as elipses (usually "...")
	 * 
	 * @return Truncated string
	 */
	public static String truncate(String string, Integer length, String elipses){
		if(isNullOrEmpty(string)){
			return string;
		}
		
		if(string.length() < length){
			return string;
		}
		
		if(elipses == null){
			elipses = "";
		}
		
		Integer lastIndex = length - elipses.length();
		if(lastIndex < 0){
			return elipses;
		}
		
		String newString = string.substring(0, lastIndex) + elipses;
		return newString;
	}
	
	/**
	 * <p>
	 *    Works identically to String.replaceAll(), but guards against the string
	 *    being null (in which case null is returned).
	 * </p>
	 * 
	 * @param string String to replace characters in
	 * @param regex Regular expression to replace
	 * @param replacement Characters to replace the regular expression with
	 * 
	 * @return String with replaced characters
	 */
	public static String replaceAll(String string, String regex, String replacement){
		if(string == null){
			return null;
		}
		
		return string.replaceAll(regex, replacement);
	}
	
	/**
	 * <p>
	 *    Returns true if the string is null or empty ("").
	 * </p>
	 * 
	 * @param string String to check
	 * @return True if string is null or empty, False otherwise
	 */
	public static Boolean isNullOrEmpty(String string){
		if(string == null){
			return true;
		}
		
		if("".equals(string)){
			return true;
		}
		
		return false;
	}
}
