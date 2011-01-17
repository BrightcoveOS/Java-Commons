package com.brightcove.commons.collection;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 *    This is a fairly simple set of static methods to perform operations on
 *    Collection objects (Lists, Arrays, Vectors, Sets, etc).
 * </p>
 * 
 * @author <a href="https://github.com/three4clavin">three4clavin</a>
 *
 */
public class CollectionUtils {
	/**
	 * <p>
	 *    Given a List and a delimiter, creates a delimited string of the
	 *    objects in the List (using the toString() method of each object).
	 * </p>
	 * 
	 * @param list List of objects
	 * @param delimiter Delimiter to separate the objects in the output String
	 * @return Delimited String of the objects in the List
	 */
	public static String JoinToString(List<?> list, String delimiter){
		return JoinToString(list, delimiter, false, null);
	}
	
	/**
	 * <p>
	 *    Given a List and a delimiter, creates a delimited string of the
	 *    objects in the List (using the toString() method of each object).
	 * </p>
	 * 
	 * @param list List of objects
	 * @param delimiter Delimiter to separate the objects in the output String
	 * @param escapeDelimiter If true, delimiters in the original strings will be escaped with escapeSequence
	 * @param escapeSequence String to escape delimiters with
	 * @return Delimited String of the objects in the List
	 */
	public static String JoinToString(List<?> list, String delimiter, Boolean escapeDelimiter, String escapeSequence){
		String ret = "";
		
		if(list == null){
			return ret;
		}
		if(delimiter == null){
			delimiter = "";
		}
		
		for(int listIdx=0;listIdx<list.size();listIdx++){
			Object o = list.get(listIdx);
			
			if(listIdx != 0){
				ret += delimiter;
			}
			
			String orig = o.toString();
			if(escapeDelimiter){
				StringBuffer sb = new StringBuffer();
				
				int windowStart  = 0;
				int windowLength = delimiter.length();
				int windowEnd    = windowStart + windowLength;
				
				while(windowEnd <= orig.length()){
					String window = orig.substring(windowStart, windowEnd);
					if(delimiter.equals(window)){
						sb.append(escapeSequence);
						sb.append(window);
						
						windowStart = windowEnd;
						windowEnd   = windowStart + windowLength;
					}
					else{
						String charAt = orig.substring(windowStart, windowStart+1);
						sb.append(charAt);
						
						windowStart = windowStart + 1;
						windowEnd   = windowStart + windowLength;
					}
				}
				if(windowStart < orig.length()){
					sb.append(orig.substring(windowStart, orig.length()));
				}
				
				ret += sb.toString();
			}
			else{
				ret += o.toString();
			}
		}
		
		return ret;
	}
	
	/**
	 * <p>
	 *    Given a Set and a delimiter, creates a delimited string of the
	 *    objects in the Set (using the toString() method of each object).
	 * </p>
	 * 
	 * @param set Set of objects
	 * @param delimiter Delimiter to separate the objects in the output String
	 * @return Delimited String of the objects in the Set
	 */
	public static String JoinToString(Set<?> set, String delimiter){
		return JoinToString(set, delimiter, false, null);
	}
	
	/**
	 * <p>
	 *    Given a Set and a delimiter, creates a delimited string of the
	 *    objects in the Set (using the toString() method of each object).
	 * </p>
	 * 
	 * @param set Set of objects
	 * @param delimiter Delimiter to separate the objects in the output String
	 * @param escapeDelimiter If true, delimiters in the original strings will be escaped with escapeSequence
	 * @param escapeSequence String to escape delimiters with
	 * @return Delimited String of the objects in the Set
	 */
	public static String JoinToString(Set<?> set, String delimiter, Boolean escapeDelimiter, String escapeSequence){
		String ret = "";
		
		if(set == null){
			return ret;
		}
		if(delimiter == null){
			delimiter = "";
		}
		
		Boolean start = true;
		for(Object o : set){
			if(start){
				start = false;
			}
			else{
				ret += delimiter;
			}
			
			String orig = o.toString();
			if(escapeDelimiter){
				StringBuffer sb = new StringBuffer();
				
				int windowStart  = 0;
				int windowLength = delimiter.length();
				int windowEnd    = windowStart + windowLength;
				
				while(windowEnd <= orig.length()){
					String window = orig.substring(windowStart, windowEnd);
					if(delimiter.equals(window)){
						sb.append(escapeSequence);
						sb.append(window);
						
						windowStart = windowEnd;
						windowEnd   = windowStart + windowLength;
					}
					else{
						String charAt = orig.substring(windowStart, windowStart+1);
						sb.append(charAt);
						
						windowStart = windowStart + 1;
						windowEnd   = windowStart + windowLength;
					}
				}
				if(windowStart < orig.length()){
					sb.append(orig.substring(windowStart, orig.length()));
				}
				
				ret += sb.toString();
			}
			else{
				ret += o.toString();
			}
		}
		
		return ret;
	}
	
	/**
	 * <p>
	 *    Creates an empty Set of Long types.
	 * </p>
	 * 
	 * @return Set of Long types
	 */
	public static Set<Long> CreateEmptyLongSet(){
		return ((Set<Long>)(new HashSet<Long>()));
	}
	
	/**
	 * <p>
	 *    Creates an empty Set of String types.
	 * </p>
	 * 
	 * @return Set of String types
	 */
	public static Set<String> CreateEmptyStringSet(){
		return ((Set<String>)(new HashSet<String>()));
	}
	
	/**
	 * <p>
	 *    Creates an empty Set of Integer types.
	 * </p>
	 * 
	 * @return Set of Integer types
	 */
	public static Set<Integer> CreateEmptyIntegerSet(){
		return ((Set<Integer>)(new HashSet<Integer>()));
	}
}
