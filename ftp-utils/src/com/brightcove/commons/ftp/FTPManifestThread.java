package com.brightcove.commons.ftp;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

/**
 * <p>
 *    Utility class to list the files on an FTP server.  Not intended
 *    to be called directly, but critical for the FTPManifest class.
 * </p>
 * 
 * @author <a href="https://github.com/three4clavin">three4clavin</a>
 *
 */
public class FTPManifestThread extends FTPThread {
	String startDirectory;
	
	Map<String, FTPManifestEntry> processedDirectories;
	
	public FTPManifestThread(String serverName, Integer serverPort, String username, String password, Boolean passiveTransfer, String startDirectory, Map<String, FTPManifestEntry> processedDirectories, Boolean debug) {
		log = Logger.getLogger(this.getClass().getCanonicalName());
		
		this.startDirectory       = startDirectory;
		this.processedDirectories = processedDirectories;
		
		this.serverName          = serverName;
		this.serverPort          = serverPort;
		this.username            = username;
		this.password            = password;
		this.passiveTransfer     = passiveTransfer;
		this.debug               = debug;
		
		this.skipTransfer        = null;
		this.removeSource        = null;
		
		exception = null;
		
		ftpc = new FTPClient();
	}
	
	public void run() {
		ftpc = new FTPClient();
		
		log.info("Connecting to '" + serverName + "' as user '" + username + "'.");
		
		if(! connect()){
			// Exception trying to connect
			return;
		}
		
		log.info("Generating a manifest for files on server \"" + serverName + "\".");
		try{
			traverseDirectory(startDirectory);
			printFTPCommandInfo("end of manifest generation");
		}
		catch(Exception e){
			exception = e;
		}
		
		disconnect();
	}
	
	private void traverseDirectory(String directory) throws IOException {
		log.info("---------- Traversing directory '" + directory + "'.");
		
		ftpc.changeWorkingDirectory(directory);
		printFTPCommandInfo("change directory (" + directory + ")");
		
		FTPFile[] files = ftpc.listFiles();
		printFTPCommandInfo("list files (" + directory + ")");
		
		if(! directory.endsWith("/")){
			directory += "/";
		}
		
		String fileList = "";
		for(FTPFile file : files){
			if(! fileList.equals("")){
				fileList += ",";
			}
			fileList += file.getName();
		}
		log.info("**********     Files: [" + fileList + "].");
		
		for(FTPFile file : files){
			String path = directory + file.getName();
			
			Boolean complete = false;
			if(processedDirectories.containsKey(path)){
				FTPManifestEntry existingEntry = processedDirectories.get(path);
				complete = existingEntry.getCompleted();
			}
			
			if(! complete){
				FTPManifestEntryType type = FTPManifestEntryType.FILE;
				if(file.getType() == FTPFile.DIRECTORY_TYPE){
					type = FTPManifestEntryType.DIRECTORY;
				}
				
				FTPManifestEntry entry = new FTPManifestEntry(path, file.getName(), type, false);
				
				processedDirectories.put(directory + file.getName(), entry);
				
				if(type.equals(FTPManifestEntryType.DIRECTORY)){
					traverseDirectory(path);
				}
				
				log.info("Completed entry '" + path + "'.");
				entry.setCompleted(true);
			}
		}
	}
	
	public Map<String, FTPManifestEntry> getProcessedDirectories(){
		return processedDirectories;
	}
	
	public String getStartDirectory(){
		return startDirectory;
	}
}
