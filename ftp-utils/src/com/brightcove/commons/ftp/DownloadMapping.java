package com.brightcove.commons.ftp;

import java.io.File;

/**
 * <p>
 *    Provides a mapping between a destination file on disk and a source path
 *    on an FTP server
 * </p>
 * 
 * @author <a href="https://github.com/three4clavin">three4clavin</a>
 *
 */
public class DownloadMapping  implements FTPMapping<String, File> {
	private File   dest;
	private String source;
	
	/**
	 * <p>
	 *    Default constructor
	 * </p>
	 * 
	 * @param source Source path on FTP server
	 * @param dest Destination file on disk
	 */
	public DownloadMapping(String source, File dest){
		this.source = source;
		this.dest   = dest;
	}
	
	/* (non-Javadoc)
	 * @see com.brightcove.commons.ftp.FTPMapping#setSource(java.lang.Object)
	 */
	public void setSource(String source){
		this.source = source;
	}
	
	/* (non-Javadoc)
	 * @see com.brightcove.commons.ftp.FTPMapping#getSource()
	 */
	public String getSource(){
		return source;
	}
	
	/* (non-Javadoc)
	 * @see com.brightcove.commons.ftp.FTPMapping#setDestination(java.lang.Object)
	 */
	public void setDestination(File dest){
		this.dest = dest;
	}
	
	/* (non-Javadoc)
	 * @see com.brightcove.commons.ftp.FTPMapping#getDestination()
	 */
	public File getDestination(){
		return dest;
	}
}
