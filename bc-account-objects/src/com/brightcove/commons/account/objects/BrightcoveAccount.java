package com.brightcove.commons.account.objects;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *    A simple class to represent a Brightcove Studio account
 * </p>
 * 
 * @author <a href="https://github.com/three4clavin">three4clavin</a>
 * 
 */
public class BrightcoveAccount {
	private Long   publisherId;
	private String accountName;
	private String readToken;
	private String writeToken;
	private String ftpUsername;
	private String ftpPassword;
	
	List<BrightcoveUser> users;
	
	/**
	 * <p>
	 *    Constructor.
	 * </p>
	 * 
	 * <p>
	 *    Only the publisher/account id is required (all other fields will be
	 *    set to null/empty list), but it is strongly recommended to fill out
	 *    all fields that are known.
	 * </p>
	 * 
	 * @param publisherId Publisher Id for this account
	 */
	public BrightcoveAccount(Long publisherId){
		this.publisherId = publisherId;
		accountName = null;
		readToken   = null;
		writeToken  = null;
		ftpUsername = null;
		ftpPassword = null;
		
		users = new ArrayList<BrightcoveUser>();
	}
	
	public void setPublisherId(Long publisherId){
		this.publisherId = publisherId;
	}
	
	public Long getPublisherId(){
		return publisherId;
	}
	
	public void setAccountName(String accountName){
		this.accountName = accountName;
	}
	
	public String getAccountName(){
		return accountName;
	}
	
	public void setReadToken(String readToken){
		this.readToken = readToken;
	}
	
	public String getReadToken(){
		return readToken;
	}
	
	public void setWriteToken(String writeToken){
		this.writeToken = writeToken;
	}
	
	public String getWriteToken(){
		return writeToken;
	}
	
	public void setFtpUsername(String ftpUsername){
		this.ftpUsername = ftpUsername;
	}
	
	public String getFtpUsername(){
		return ftpUsername;
	}
	
	public void setFtpPassword(String ftpPassword){
		this.ftpPassword = ftpPassword;
	}
	
	public String getFtpPassword(){
		return ftpPassword;
	}
	
	public List<BrightcoveUser> getUsers(){
		return users;
	}
	
	public void addUser(BrightcoveUser user){
		users.add(user);
	}
	
	public void setUsers(List<BrightcoveUser> users){
		this.users = users;
	}
	
	public void clearUsers(){
		this.users = new ArrayList<BrightcoveUser>();
	}
}
