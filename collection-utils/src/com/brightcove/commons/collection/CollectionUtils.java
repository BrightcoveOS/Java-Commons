package com.brightcove.commons.collection;

import java.util.ArrayList;
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
	 *    Given an object static array and a delimiter, creates a
	 *    delimited string of the objects in the array (using the
	 *    toString() method of each object).
	 * </p>
	 * 
	 * <p>
	 *    Null objects will be printed out as an empty string
	 * </p>
	 * 
	 * @param array Static array of objects
	 * @param delimiter Delimiter to separate the objects in the output String
	 * @return Delimited String of the objects in the array
	 */
	public static <T> String JoinToString(T[] array, String delimiter){
		return JoinToString(array, delimiter, false, null);
	}
	
	/**
	 * <p>
	 *    Given an object static array and a delimiter, creates a
	 *    delimited string of the objects in the array (using the
	 *    toString() method of each object).
	 * </p>
	 * 
	 * <p>
	 *    Null objects will be printed out as an empty string
	 * </p>
	 * 
	 * @param array Static array of objects
	 * @param delimiter Delimiter to separate the objects in the output String
	 * @param escapeDelimiter If true, delimiters in the original strings will be escaped with escapeSequence
	 * @param escapeSequence String to escape delimiters with
	 * @return Delimited String of the objects in the array
	 */
	public static <T> String JoinToString(T[] array, String delimiter, Boolean escapeDelimiter, String escapeSequence){
		List<T> list = new ArrayList<T>();
		for(T item : array){
			list.add(item);
		}
		return JoinToString(list, delimiter, escapeDelimiter, escapeSequence);
	}
	
	/**
	 * <p>
	 *    Given a iterable object (list, set, etc) and a delimiter, creates a
	 *    delimited string of the objects in the List (using the
	 *    toString() method of each object).
	 * </p>
	 * 
	 * <p>
	 *    Null objects will be printed out as an empty string
	 * </p>
	 * 
	 * @param list Iterable object (Set, List, etc) of objects
	 * @param delimiter Delimiter to separate the objects in the output String
	 * @return Delimited String of the objects in the List
	 */
	public static String JoinToString(Iterable<?> list, String delimiter){
		return JoinToString(list, delimiter, false, null);
	}
	
	/**
	 * <p>
	 *    Given a iterable object (list, set, etc) and a delimiter, creates a
	 *    delimited string of the objects in the List (using the
	 *    toString() method of each object).
	 * </p>
	 * 
	 * <p>
	 *    Null objects will be printed out as an empty string
	 * </p>
	 * 
	 * @param list Iterable object (Set, List, etc) of objects
	 * @param delimiter Delimiter to separate the objects in the output String
	 * @param escapeDelimiter If true, delimiters in the original strings will be escaped with escapeSequence
	 * @param escapeSequence String to escape delimiters with
	 * @return Delimited String of the objects in the List
	 */
	public static String JoinToString(Iterable<?> list, String delimiter, Boolean escapeDelimiter, String escapeSequence){
		String ret = "";
		
		if(list == null){
			return ret;
		}
		if(delimiter == null){
			delimiter = "";
		}
		
		for(Object item : list){
			if(! "".equals(ret)){
				ret += delimiter;
			}
			
			if(item == null){
				ret += "";
			}
			else{
				String orig = item.toString();
				
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
					ret += item.toString();
				}
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
