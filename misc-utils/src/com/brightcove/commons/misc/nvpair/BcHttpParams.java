package com.brightcove.commons.misc.nvpair;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

/**
 * <p>
 *    Makes org.apache.http.params.HttpParams a little more usable
 * </p>
 * 
 * @author <a href="https://github.com/three4clavin">three4clavin</a>
 *
 */
public class BcHttpParams {
	private List<NameTypedValuePair<Boolean>> booleanParams;
	private List<NameTypedValuePair<Integer>> integerParams;
	private List<NameTypedValuePair<Long>>    longParams;
	private List<NameTypedValuePair<Double>>  doubleParams;
	private List<NameTypedValuePair<String>>  stringParams;
	
	/**
	 * <p>
	 *    Basic constructor
	 * </p>
	 */
	public BcHttpParams(){
		booleanParams = new ArrayList<NameTypedValuePair<Boolean>>();
		integerParams = new ArrayList<NameTypedValuePair<Integer>>();
		longParams    = new ArrayList<NameTypedValuePair<Long>>();
		doubleParams  = new ArrayList<NameTypedValuePair<Double>>();
		stringParams  = new ArrayList<NameTypedValuePair<String>>();
	}
	
	/**
	 * <p>
	 *    Adds a boolean parameter
	 * </p>
	 * 
	 * @param name Name of parameter
	 * @param value Value of parameter
	 */
	public void addBooleanParam(String name, Boolean value){
		booleanParams.add(new NameTypedValuePair<Boolean>(name, value));
	}
	
	/**
	 * <p>
	 *    Adds an integer parameter
	 * </p>
	 * 
	 * @param name Name of parameter
	 * @param value Value of parameter
	 */
	public void addIntegerParam(String name, Integer value){
		integerParams.add(new NameTypedValuePair<Integer>(name, value));
	}
	
	/**
	 * <p>
	 *    Adds a long parameter
	 * </p>
	 * 
	 * @param name Name of parameter
	 * @param value Value of parameter
	 */
	public void addLongParam(String name, Long value){
		longParams.add(new NameTypedValuePair<Long>(name, value));
	}
	
	/**
	 * <p>
	 *    Adds a double parameter
	 * </p>
	 * 
	 * @param name Name of parameter
	 * @param value Value of parameter
	 */
	public void addDoubleParam(String name, Double value){
		doubleParams.add(new NameTypedValuePair<Double>(name, value));
	}
	
	/**
	 * <p>
	 *    Adds a string parameter
	 * </p>
	 * 
	 * @param name Name of parameter
	 * @param value Value of parameter
	 */
	public void addStringParam(String name, String value){
		stringParams.add(new NameTypedValuePair<String>(name, value));
	}
	
	/**
	 * <p>
	 *    Generates Apache HttpParams suitable for use with method.setParams()
	 * </p>
	 * 
	 * @return HttpParams object
	 */
	public HttpParams generateHttpParams(){
		HttpParams params = new BasicHttpParams();
		
		for(NameTypedValuePair<Boolean> ntvp : booleanParams){
			params.setBooleanParameter(ntvp.getName(), ntvp.getValue());
		}
		
		for(NameTypedValuePair<Integer> ntvp : integerParams){
			params.setIntParameter(ntvp.getName(), ntvp.getValue());
		}
		
		for(NameTypedValuePair<Long> ntvp : longParams){
			params.setLongParameter(ntvp.getName(), ntvp.getValue());
		}
		
		for(NameTypedValuePair<Double> ntvp : doubleParams){
			params.setDoubleParameter(ntvp.getName(), ntvp.getValue());
		}
		
		for(NameTypedValuePair<String> ntvp : stringParams){
			params.setParameter(ntvp.getName(), ntvp.getValue());
		}
		
		return params;
	}
}
