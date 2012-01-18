package com.brightcove.commons.ftp;

/**
 * <p>
 *    Utility class to store entries on an FTP server, to be used in
 *    generating a manifest of all files on a server.
 * </p>
 * 
 * @author <a href="https://github.com/three4clavin">three4clavin</a>
 *
 */
public class FTPManifestEntry {
	private String  path;
	private String  name;
	private Boolean completed;
	
	private FTPManifestEntryType type;
	
	public FTPManifestEntry(String path, String name, FTPManifestEntryType type, Boolean completed){
		this.path      = path;
		this.name      = name;
		this.type      = type;
		this.completed = completed;
	}
	
	public String getPath(){
		return path;
	}
	public void setPath(String path){
		this.path = path;
	}
	
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	
	public Boolean getCompleted(){
		return completed;
	}
	public void setCompleted(Boolean completed){
		this.completed = completed;
	}
	
	public FTPManifestEntryType getType(){
		return type;
	}
	public void setType(FTPManifestEntryType type){
		this.type = type;
	}
}
