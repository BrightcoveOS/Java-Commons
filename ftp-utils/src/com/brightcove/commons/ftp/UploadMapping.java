package com.brightcove.commons.ftp;

import java.io.File;

/**
 * <p>
 *    Provides a mapping between a source file on disk and a destination path
 *    on an FTP server
 * </p>
 * 
 * @author <a href="https://github.com/three4clavin">three4clavin</a>
 *
 */
public class UploadMapping {
	private File   source;
	private String dest;
	
	/**
	 * <p>
	 *    Default constructor
	 * </p>
	 * 
	 * @param source Source file on disk to upload
	 * @param dest Destination path on FTP server
	 */
	public UploadMapping(File source, String dest){
		this.source = source;
		this.dest   = dest;
	}
	
	/**
	 * <p>
	 *    Sets the source file on disk to upload
	 * </p>
	 * 
	 * @param source Source file on disk to upload
	 */
	public void setSource(File source){
		this.source = source;
	}
	
	/**
	 * <p>
	 *    Returns the source file on disk to upload
	 * </p>
	 * 
	 * @return Source file on disk to upload
	 */
	public File getSource(){
		return source;
	}
	
	/**
	 * <p>
	 *    Sets the destination path on the FTP server
	 * </p>
	 * 
	 * @param path Destination path on the FTP server
	 */
	public void setDestination(String path){
		this.dest = path;
	}
	
	/**
	 * <p>
	 *    Returns the destination path on the FTP server
	 * </p>
	 * 
	 * @return Destination path on the FTP server
	 */
	public String getDestination(){
		return dest;
	}
}
