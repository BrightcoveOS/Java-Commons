package com.brightcove.commons.ftp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.net.ftp.FTPClient;

/**
 * <p>
 *    Utility class to perform download of files from an FTP server.  Not
 *    intended to be called directly, but critical for the FTPDownloader class.
 * </p>
 * 
 * @author <a href="https://github.com/three4clavin">three4clavin</a>
 *
 */
public class FTPDownloaderThread extends FTPThread {
	List<DownloadMapping> downloadMappings;
	
	public FTPDownloaderThread(String serverName, String username, String password, Boolean skipTransfer, Boolean removeSource, Boolean passiveTransfer, List<DownloadMapping> downloadMappings, Boolean debug) {
		log = Logger.getLogger(this.getClass().getCanonicalName());
		
		this.downloadMappings = downloadMappings;
		
		this.serverName          = serverName;
		this.serverPort          = 21;
		this.username            = username;
		this.password            = password;
		this.skipTransfer        = skipTransfer;
		this.removeSource        = removeSource;
		this.passiveTransfer     = passiveTransfer;
		this.debug               = debug;
		
		exception = null;
		
		ftpc = new FTPClient();
	}
	
	public FTPDownloaderThread(String serverName, Integer serverPort, String username, String password, Boolean skipTransfer, Boolean removeSource, Boolean passiveTransfer, List<DownloadMapping> downloadMappings, Boolean debug) {
		log = Logger.getLogger(this.getClass().getCanonicalName());
		
		this.downloadMappings = downloadMappings;
		
		this.serverName          = serverName;
		this.serverPort          = serverPort;
		this.username            = username;
		this.password            = password;
		this.skipTransfer        = skipTransfer;
		this.removeSource        = removeSource;
		this.passiveTransfer     = passiveTransfer;
		this.debug               = debug;
		
		exception = null;
		
		ftpc = new FTPClient();
	}
	
	public void run() {
		ftpc = new FTPClient();
		
		if(! connect()){
			// Exception trying to connect
			return;
		}
		
		try{
			log.info("Downloading from server \"" + serverName + "\".");
			for(DownloadMapping downloadMapping : downloadMappings){
				String remoteFile = downloadMapping.getSource();
				File   localFile  = downloadMapping.getDestination();
				
				log.info("\tDownloading file \"" + remoteFile + "\" to \"" + localFile.getAbsolutePath() + "\".");
				
				FileOutputStream fos = new FileOutputStream(localFile);
				ftpc.retrieveFile(remoteFile, fos);
				printFTPCommandInfo("retrieve file (" + remoteFile + ")");
				fos.flush();
				fos.close();
				
				if(removeSource){
					log.info("\tRemoving source file \"" + remoteFile + "\" per request.");
					if(! ftpc.deleteFile(remoteFile)){
						printFTPCommandInfo("delete file (" + remoteFile + ")");
						throw new IOException("Couldn't delete file \"" + remoteFile + "\".");
					}
					else{
						printFTPCommandInfo("delete file (" + remoteFile + ")");
					}
				}
			}
			
			printFTPCommandInfo("end of file transfers");
		}
		catch(Exception e){
			exception = e;
		}
		
		disconnect();
	}
	
	/**
	 * <p>
	 *    Clears out the queue of files to download
	 * </p>
	 */
	public void clearDownloadMappings(){
		downloadMappings = new ArrayList<DownloadMapping>();
	}
	
	/**
	 * <p>
	 *    Adds a file to download
	 * </p>
	 * 
	 * @param mapping File to download
	 */
	public void addDownloadMapping(DownloadMapping mapping){
		downloadMappings.add(mapping);
	}
	
	/**
	 * <p>
	 *    Adds a file to download
	 * </p>
	 * 
	 * @param source Path to download
	 * @param destination Where on local file system to download to
	 */
	public void addDownloadMapping(String source, File destination){
		DownloadMapping mapping = new DownloadMapping(source, destination);
		addDownloadMapping(mapping);
	}
	
	/**
	 * <p>
	 *    Returns a list of all files to be downloaded
	 * </p>
	 * 
	 * @return List of files to download
	 */
	public List<DownloadMapping> getDownloadMappings(){
		return downloadMappings;
	}
}
