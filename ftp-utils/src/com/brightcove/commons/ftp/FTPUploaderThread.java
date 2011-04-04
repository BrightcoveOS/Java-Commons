package com.brightcove.commons.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPConnectionClosedException;

public class FTPUploaderThread extends Thread {
	Logger              log;
	List<UploadMapping> uploadMappings;
	String              serverName;
	Integer             serverPort;
	String              username;
	String              password;
	Boolean             skipUpload;
	Boolean             removeSource;
	Boolean             passiveTransfer;
	Boolean             debug;
	Exception           exception;
	
	public FTPUploaderThread(String serverName, String username, String password, Boolean skipUpload, Boolean removeSource, Boolean passiveTransfer, List<UploadMapping> uploadMappings, Boolean debug) {
		log = Logger.getLogger(this.getClass().getCanonicalName());
		
		this.uploadMappings = uploadMappings;
		
		this.serverName          = serverName;
		this.serverPort          = 21;
		this.username            = username;
		this.password            = password;
		this.skipUpload          = skipUpload;
		this.removeSource        = removeSource;
		this.passiveTransfer     = passiveTransfer;
		this.debug               = debug;
		
		exception = null;
	}
	
	public void run() {
		try{
			FTPClient ftp = new FTPClient();
			
			ftp.connect(serverName, serverPort);
			printFTPCommandInfo(ftp, "connect");
			
			ftp.login(username, password);
			printFTPCommandInfo(ftp, "login");
			
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			printFTPCommandInfo(ftp, "set file type");
			
			if(passiveTransfer){
				ftp.enterLocalPassiveMode();
				printFTPCommandInfo(ftp, "enter passive mode");
			}
			else{
				ftp.enterLocalActiveMode();
				printFTPCommandInfo(ftp, "enter active mode");
			}
			
			log.info("Uploading to server \"" + serverName + "\".");
			for(UploadMapping uploadMapping : uploadMappings){
				File   localFile  = uploadMapping.getSource();
				String remoteFile = uploadMapping.getDestination();
				
				log.info("\tUploading file \"" + localFile.getAbsolutePath() + "\".");
				
				FileInputStream fis = new FileInputStream(localFile);
				ftp.storeFile(remoteFile, fis);
				printFTPCommandInfo(ftp, "store file (" + remoteFile + ")");
				fis.close();
				
				if(removeSource){
					log.info("\tRemoving source file \"" + localFile.getAbsolutePath() + "\" per request.");
					if(! localFile.delete()){
						throw new IOException("Couldn't delete file \"" + localFile.getAbsolutePath() + "\".");
					}
				}
			}
			
			printFTPCommandInfo(ftp, "end of file transfers");
			
			try{
				ftp.logout();
				printFTPCommandInfo(ftp, "logout");
			}
			catch(FTPConnectionClosedException ftpcce){
				// Don't worry about it - connection was already closed.
				if(debug){
					log.warning("Tried to logout, but connection was already closed (" + ftpcce + ").");
					printFTPCommandInfo(ftp, "logout exception caught");
				}
			}
			finally {
				try{
					ftp.disconnect();
					printFTPCommandInfo(ftp, "disconnect");
				}
				catch(IOException ioe){
					if(debug){
						log.warning("Caught exception trying to disconnect: " + ioe + ".");
						printFTPCommandInfo(ftp, "disconnect exception caught");
					}
				}
			}
		}
		catch(Exception e){
			exception = e;
		}
	}
	
	public Exception getException(){
		return exception;
	}
	
	public void resetException(){
		exception = null;
	}
	
	/**
	 * <p>
	 *    Utility method to print more detailed information about an FTP command
	 * </p>
	 * 
	 * @param ftpc Connected FTP client to issue the command
	 * @param command Command to issue to the server
	 */
	private void printFTPCommandInfo(FTPClient ftpc, String command) {
		if(debug){
			log.info("Status from issuing command \"" + command + "\":");
			log.info("    Response code:   \"" + ftpc.getReplyCode() + "\".");
			log.info("    Response string: \"" + ftpc.getReplyString() + "\".");
		}
	}
}
