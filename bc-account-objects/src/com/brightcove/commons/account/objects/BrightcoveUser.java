package com.brightcove.commons.account.objects;

/**
 * <p>
 *    A simple class to represent a Brightcove Studio user
 * </p>
 * 
 * @author <a href="https://github.com/three4clavin">three4clavin</a>
 *
 */
public class BrightcoveUser {
	private String name;
	private String email;
	
	/**
	 * <p>
	 *    Constructor
	 * </p>
	 * 
	 * @param name Full name of user (must be email address safe (e.g. no @ symbols)
	 * @param email Email address of user (will not be checked, but must be valid)
	 */
	public BrightcoveUser(String name, String email){
		this.name  = name;
		this.email = email;
	}
	
	/**
	 * <p>
	 *    Sets the full name of the user
	 * </p>
	 * 
	 * @param name Full name of user (must be email address safe (e.g. no @ symbols)
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * <p>
	 *    Gets the full name of the user
	 * </p>
	 * 
	 * @return Full name of user (must be email address safe (e.g. no @ symbols)
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * <p>
	 *    Sets the email address of the user
	 * </p>
	 * 
	 * @param email Email address of user (will not be checked, but must be valid)
	 */
	public void setEmail(String email){
		this.email = email;
	}
	
	/**
	 * <p>
	 *    Gets the email address of the user
	 * </p>
	 * 
	 * @return Email address of user (will not be checked, but must be valid)
	 */
	public String getEmail(){
		return email;
	}
}
