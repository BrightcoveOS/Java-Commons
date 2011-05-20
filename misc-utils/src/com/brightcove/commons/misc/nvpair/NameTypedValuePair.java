package com.brightcove.commons.misc.nvpair;

/**
 * <p>
 *    Implements a generic name-value pair where the value is typed.
 * </p>
 * 
 * @param <T> Type of value stored
 * @author <a href="https://github.com/three4clavin">three4clavin</a>
 */
class NameTypedValuePair<T> {
	String name;
	T      value;
	String typeName;
	
	/**
	 * <p>
	 *    Constructor
	 * </p>
	 * 
	 * @param name Name for NV Pair
	 * @param value Value for NV Pair
	 */
	public NameTypedValuePair(String name, T value){
		this.name     = name;
		this.value    = value;
		this.typeName = value.getClass().getName();
	}
	
	/**
	 * @return Gets the name for this NV Pair
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * @param name Sets the name for this NV Pair
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * @return Gets the value for this NV Pair
	 */
	public T getValue(){
		return value;
	}
	
	/**
	 * @param value Sets the value for this NV Pair
	 */
	public void setValue(T value){
		this.value    = value;
		this.typeName = value.getClass().getName();
	}
	
	/**
	 * @return The class name for the type of the value for this NV Pair
	 */
	public String getValueTypeName(){
		return typeName;
	}
}
