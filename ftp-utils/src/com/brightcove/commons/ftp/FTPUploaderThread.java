package com.brightcove.commons.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.net.ftp.FTPClient;

/**
 * <p>
 *    Utility class to perform upload of files to an FTP server.  Not intended
 *    to be called directly, but critical for the FTPUploader class.
 * </p>
 * 
 * @author <a href="https://github.com/three4clavin">three4clavin</a>
 *
 */
public class FTPUploaderThread extends FTPThread {
	List<UploadMapping> uploadMappings;
	
	public FTPUploaderThread(String serverName, Integer serverPort, String username, String password, Boolean skipTransfer, Boolean removeSource, Boolean passiveTransfer, List<UploadMapping> uploadMappings, Boolean debug) {
		log = Logger.getLogger(this.getClass().getCanonicalName());
		
		this.uploadMappings = uploadMappings;
		
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
			log.info("Uploading to server \"" + serverName + "\".");
			for(UploadMapping uploadMapping : uploadMappings){
				File   localFile  = uploadMapping.getSource();
				String remoteFile = uploadMapping.getDestination();
				
				log.info("\tUploading file \"" + localFile.getAbsolutePath() + "\".");
				
				FileInputStream fis = new FileInputStream(localFile);
				ftpc.storeFile(remoteFile, fis);
				printFTPCommandInfo("store file (" + remoteFile + ")");
				fis.close();
				
				if(removeSource){
					log.info("\tRemoving source file \"" + localFile.getAbsolutePath() + "\" per request.");
					if(! localFile.delete()){
						throw new IOException("Couldn't delete file \"" + localFile.getAbsolutePath() + "\".");
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
	 *    Clears out the queue of files to upload
	 * </p>
	 */
	public void clearUploadMappings(){
		uploadMappings = new ArrayList<UploadMapping>();
	}
	
	/**
	 * <p>
	 *    Adds a file to upload
	 * </p>
	 * 
	 * @param mapping File to upload
	 */
	public void addUploadMapping(UploadMapping mapping){
		uploadMappings.add(mapping);
	}
	
	/**
	 * <p>
	 *    Adds a file to upload
	 * </p>
	 * 
	 * @param source File to upload
	 * @param destination Where on server to upload file to
	 */
	public void addUploadMapping(File source, String destination){
		UploadMapping mapping = new UploadMapping(source, destination);
		addUploadMapping(mapping);
	}
	
	/**
	 * <p>
	 *    Returns a list of all files to be uploaded
	 * </p>
	 * 
	 * @return List of files to upload
	 */
	public List<UploadMapping> getUploadMappings(){
		return uploadMappings;
	}
}
