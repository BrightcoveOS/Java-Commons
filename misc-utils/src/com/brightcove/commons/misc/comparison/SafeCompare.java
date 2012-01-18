package com.brightcove.commons.misc.comparison;

import java.util.Comparator;
import java.util.List;

/**
 * <p>
 *    Provides mechanisms to compare two objects, guarding against the chance
 *    that one or both might be null
 * </p>
 * 
 * @author <a href="https://github.com/three4clavin">three4clavin</a>
 *
 */
public class SafeCompare {
	/**
	 * <p>
	 *    Compares two objects to each other, guarding against the
	 *    chance that one or both might be null.
	 * </p>
	 * 
	 * @param a First object to compare
	 * @param b Second object to compare
	 * @return "both null", "neither null", or result of a.equals(b)
	 */
	public static <T> SafeComparisonResult equals(T a, T b){
		SafeComparisonResult nullCheck = nullCompare(a,b);
		if(SafeComparisonResult.BOTH_NULL.equals(nullCheck) ||
		   SafeComparisonResult.NOT_EQUAL.equals(nullCheck)){
			return nullCheck;
		}
		
		// Neither are null, so compare
		if(a.equals(b)){
			return SafeComparisonResult.EQUAL;
		}
		return SafeComparisonResult.NOT_EQUAL;
	}
	
	/**
	 * <p>
	 *    Compares two String objects to each other, guarding against the
	 *    chance that one or both might be null, and ignoring case.
	 * </p>
	 * 
	 * @param a First object to compare
	 * @param b Second object to compare
	 * @return "both null", "neither null", or result of a.equalsIgnoreCase(b)
	 */
	public static SafeComparisonResult equalsIgnoreCase(String a, String b){
		SafeComparisonResult nullCheck = nullCompare(a,b);
		if(SafeComparisonResult.BOTH_NULL.equals(nullCheck) ||
		   SafeComparisonResult.NOT_EQUAL.equals(nullCheck)){
			return nullCheck;
		}
		
		// Neither are null, so compare
		if(a.equalsIgnoreCase(b)){
			return SafeComparisonResult.EQUAL;
		}
		return SafeComparisonResult.NOT_EQUAL;
	}
	
	/**
	 * <p>
	 *    Compares two generic objects to each other, guarding against the
	 *    chance that one or both might be null.
	 * </p>
	 * 
	 * @param a First object to compare
	 * @param b Second object to compare
	 * @param comparator Object to determine object equality in of objects in the two lists
	 * @return "both null", "neither null", or result of comparator.compare(a.get(key), b.get(key)) == 0
	 */
	public static <T> SafeComparisonResult equals(T a, T b, Comparator<T> comparator){
		SafeComparisonResult nullCheck = nullCompare(a,b);
		if(SafeComparisonResult.BOTH_NULL.equals(nullCheck) ||
		   SafeComparisonResult.NOT_EQUAL.equals(nullCheck)){
			return nullCheck;
		}
		
		// Neither are null, so compare
		if(comparator.compare(a, b) == 0){
			return SafeComparisonResult.EQUAL;
		}
		return SafeComparisonResult.NOT_EQUAL;
	}
	
	/**
	 * <p>
	 *    Compares two Lists of generic type to each other, guarding against
	 *    the chance that one or both might be null.
	 * </p>
	 * 
	 * @param <T> Type of objects in each list
	 * @param a First object to compare
	 * @param b Second object to compare
	 * @return "both null", "neither null", or result of "do a and b contain all of the same elements such that a.get(key).equals(b.get(key))?" (order does not matter)
	 */
	public static <T> SafeComparisonResult equals(List<T> a, List<T> b){
		SafeComparisonResult nullCheck = nullCompare(a,b);
		if(SafeComparisonResult.BOTH_NULL.equals(nullCheck) ||
		   SafeComparisonResult.NOT_EQUAL.equals(nullCheck)){
			return nullCheck;
		}
		
		// Neither are null, so compare
		if(a.size() != b.size()){
			return SafeComparisonResult.NOT_EQUAL;
		}
		
		for(T aElem : a){
			Boolean bFound = false;
			for(T bElem : b){
				if(bElem == null){
					if(aElem == null){
						bFound = true;
					}
				}
				else{
					if(bElem.equals(aElem)){
						bFound = true;
					}
				}
			}
			if(! bFound){
				return SafeComparisonResult.NOT_EQUAL;
			}
		}
		
		for(T bElem : b){
			Boolean aFound = false;
			for(T aElem : a){
				if(aElem == null){
					if(bElem == null){
						aFound = true;
					}
				}
				else{
					if(aElem.equals(bElem)){
						aFound = true;
					}
				}
			}
			if(! aFound){
				return SafeComparisonResult.NOT_EQUAL;
			}
		}
		
		// Every element in a is in b, and every element in b is in a
		return SafeComparisonResult.EQUAL;
	}
	
	/**
	 * <p>
	 *    Compares two Lists of generic type to each other, guarding against
	 *    the chance that one or both might be null.
	 * </p>
	 * 
	 * @param <T> Type of objects in each list
	 * @param a First object to compare
	 * @param b Second object to compare
	 * @param comparator Object to determine object equality in of objects in the two lists
	 * @return "both null", "neither null", or result of "do a and b contain all of the same elements such that comparator.compare(a.get(key), b.get(key)) == 0?" (order does not matter)
	 */
	public static <T> SafeComparisonResult equals(List<T> a, List<T> b, Comparator<T> comparator){
		SafeComparisonResult nullCheck = nullCompare(a,b);
		if(SafeComparisonResult.BOTH_NULL.equals(nullCheck) ||
		   SafeComparisonResult.NOT_EQUAL.equals(nullCheck)){
			return nullCheck;
		}
		
		// Neither are null, so compare
		if(a.size() != b.size()){
			return SafeComparisonResult.NOT_EQUAL;
		}
		
		for(T aElem : a){
			Boolean bFound = false;
			for(T bElem : b){
				if(bElem == null){
					if(aElem == null){
						bFound = true;
					}
				}
				else{
					if(comparator.compare(aElem, bElem) == 0){
						bFound = true;
					}
				}
			}
			if(! bFound){
				return SafeComparisonResult.NOT_EQUAL;
			}
		}
		
		for(T bElem : b){
			Boolean aFound = false;
			for(T aElem : a){
				if(aElem == null){
					if(bElem == null){
						aFound = true;
					}
				}
				else{
					if(comparator.compare(bElem, aElem) == 0){
						aFound = true;
					}
				}
			}
			if(! aFound){
				return SafeComparisonResult.NOT_EQUAL;
			}
		}
		
		// Every element in a is in b, and every element in b is in a
		return SafeComparisonResult.EQUAL;
	}
	
	/**
	 * <p>
	 *    Compares two objects to see if one or both are null or not
	 * </p>
	 * 
	 * @param <T> Type of object to compare
	 * @param a First object to compare
	 * @param b Second object to compare
	 * @return "both null", "neither null" or "not equal"
	 */
	public static <T> SafeComparisonResult nullCompare(T a, T b){
		if(a == null){
			if(b == null){
				return SafeComparisonResult.BOTH_NULL;
			}
			// a null, b not
			return SafeComparisonResult.NOT_EQUAL;
		}
		if(b == null){
			// b null, a not
			return SafeComparisonResult.NOT_EQUAL;
		}
		
		// Neither a or b are null
		return SafeComparisonResult.NEITHER_NULL;
	}
}
