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
public class UploadMapping implements FTPMapping<File, String> {
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
	
	/* (non-Javadoc)
	 * @see com.brightcove.commons.ftp.FTPMapping#setSource(java.lang.Object)
	 */
	public void setSource(File source){
		this.source = source;
	}
	
	/* (non-Javadoc)
	 * @see com.brightcove.commons.ftp.FTPMapping#getSource()
	 */
	public File getSource(){
		return source;
	}
	
	/* (non-Javadoc)
	 * @see com.brightcove.commons.ftp.FTPMapping#setDestination(java.lang.Object)
	 */
	public void setDestination(String path){
		this.dest = path;
	}
	
	/* (non-Javadoc)
	 * @see com.brightcove.commons.ftp.FTPMapping#getDestination()
	 */
	public String getDestination(){
		return dest;
	}
}
