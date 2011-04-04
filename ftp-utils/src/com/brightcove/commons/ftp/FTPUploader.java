package com.brightcove.commons.ftp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.brightcove.commons.xml.XalanUtils;

public class FTPUploader {
	Logger              log;
	List<UploadMapping> uploadMappings;
	String              serverName;
	Integer             serverPort;
	String              username;
	String              password;
	Boolean             skipUpload;
	Boolean             removeSource;
	Boolean             passiveTransfer;
	Long                uploadTimeoutMillis;
	Boolean             debug;
	
	public FTPUploader(String serverName, String username, String password, Boolean skipUpload, Boolean removeSource, Boolean passiveTransfer, Long uploadTimeoutMillis, Boolean debug) {
		init();
		
		this.serverName          = serverName;
		this.serverPort          = 21;
		this.username            = username;
		this.password            = password;
		this.skipUpload          = skipUpload;
		this.removeSource        = removeSource;
		this.passiveTransfer     = passiveTransfer;
		this.uploadTimeoutMillis = uploadTimeoutMillis;
		this.debug               = debug;
	}
	
	public FTPUploader(String serverName, Integer serverPort, String username, String password, Boolean skipUpload, Boolean removeSource, Boolean passiveTransfer, Long uploadTimeoutMillis, Boolean debug) {
		init();
		
		this.serverName          = serverName;
		this.serverPort          = serverPort;
		this.username            = username;
		this.password            = password;
		this.skipUpload          = skipUpload;
		this.removeSource        = removeSource;
		this.passiveTransfer     = passiveTransfer;
		this.uploadTimeoutMillis = uploadTimeoutMillis;
		this.debug               = debug;
	}
	
	public FTPUploader(File configFile) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		init();
		
		Document configDoc = XalanUtils.parseXml(configFile, false);
		
		serverName = getStringSetting(configDoc, "FTP_UPLOAD_SERVER");
		serverPort = getIntegerSetting(configDoc, "FTP_UPLOAD_PORT");
		username   = getStringSetting(configDoc, "FTP_UPLOAD_USER");
		password   = getStringSetting(configDoc, "FTP_UPLOAD_PASSWORD");
		
		if(serverPort == null){
			serverPort = 21;
		}
		
		String uploadDirectory = getStringSetting(configDoc, "FTP_UPLOAD_LOCAL_DIRECTORY");
		String uploadRegex     = getStringSetting(configDoc, "FTP_UPLOAD_LOCAL_REGEX");
		String uploadFile      = getStringSetting(configDoc, "FTP_UPLOAD_LOCAL_FILE");
		String remoteDirectory = getStringSetting(configDoc, "FTP_UPLOAD_REMOTE_DIRECTORY");
		// String uploadOptions   = getStringSetting(configDoc, "FTP_UPLOAD_OPTIONS");
		
		skipUpload      = getBooleanSetting(configDoc, "FTP_UPLOAD_SKIP");
		removeSource    = getBooleanSetting(configDoc, "FTP_UPLOAD_REMOVE_SOURCE");
		passiveTransfer = getBooleanSetting(configDoc, "FTP_UPLOAD_USE_PASSIVE_TRANSFER");
		debug           = getBooleanSetting(configDoc, "FTP_UPLOAD_DEBUG");
		
		if(skipUpload      == null){ skipUpload      = false; }
		if(removeSource    == null){ removeSource    = false; }
		if(passiveTransfer == null){ passiveTransfer = false; }
		if(debug           == null){ debug           = false; }
		
		File uploadDir = new File(uploadDirectory);
		if(! uploadDir.exists()){
			throw new IOException("Upload directory '" + uploadDir.getAbsolutePath() + "' does not exist.");
		}
		if(! uploadDir.isDirectory()){
			throw new IOException("Path '" + uploadDir.getAbsolutePath() + "' is not a directory.");
		}
		
		if(uploadFile != null){
			addUpload(uploadDir, uploadFile, remoteDirectory);
		}
		else{
			for(File localFile : uploadDir.listFiles()){
				Pattern pattern = Pattern.compile(uploadRegex);
				Matcher matcher = pattern.matcher(localFile.getName());
				if(matcher.find()){
					addUpload(localFile, remoteDirectory);
				}
			}
		}
		
		uploadTimeoutMillis = getLongSetting(configDoc, "FTP_UPLOAD_TIMEOUT_MILLISECONDS");
	}
	
	private void addUpload(File uploadFile, String remoteDir){
		String remoteFile = null;
		if(remoteDir != null){
			remoteFile = remoteDir;
			if(! remoteFile.endsWith("/")){
				remoteFile += "/";
			}
			remoteFile += uploadFile.getName();
		}
		
		addUploadMapping(uploadFile, remoteFile);
	}
	
	private void addUpload(File uploadDir, String uploadFile, String remoteDir){
		File   localFile  = new File(uploadDir, uploadFile);
		addUpload(localFile, remoteDir);
	}
	
	private String getStringSetting(Document configDoc, String settingName) throws TransformerException {
		return XalanUtils.getStringFromXPath(configDoc, "/config/setting[@name='" + settingName + "']/value");
	}
	
	private Integer getIntegerSetting(Document configDoc, String settingName) throws TransformerException {
		return XalanUtils.getIntegerFromXPath(configDoc, "/config/setting[@name='" + settingName + "']/value");
	}
	
	private Boolean getBooleanSetting(Document configDoc, String settingName) throws TransformerException {
		return XalanUtils.getBooleanFromXPath(configDoc, "/config/setting[@name='" + settingName + "']/value");
	}
	
	private Long getLongSetting(Document configDoc, String settingName) throws TransformerException {
		return XalanUtils.getLongFromXPath(configDoc, "/config/setting[@name='" + settingName + "']/value");
	}
	
	private void init(){
		log            = Logger.getLogger(this.getClass().getCanonicalName());
		uploadMappings = new ArrayList<UploadMapping>();
	}
	
	public void clearUploadMappings(){
		uploadMappings = new ArrayList<UploadMapping>();
	}
	
	public void addUploadMapping(UploadMapping mapping){
		uploadMappings.add(mapping);
	}
	
	public void addUploadMapping(File source, String destination){
		UploadMapping mapping = new UploadMapping(source, destination);
		addUploadMapping(mapping);
	}
	
	public List<UploadMapping> getUploadMappings(){
		return uploadMappings;
	}
	
	public void doUpload() throws Exception {
		FTPUploaderThread thread = new FTPUploaderThread(serverName, username, password, skipUpload, removeSource, passiveTransfer, uploadMappings, debug);
		
		log.info("Starting new thread '" + thread + "'.");
		thread.start();
		
		if (thread.isAlive()) {
			// Thread has not finished
			Long    waitInterval = 5000l; // 5 seconds
			Boolean cont         = true;
			Long    threadStart  = (new Date()).getTime();
			while(cont){
				thread.join(waitInterval);
				
				if(thread.isAlive()){
					Long now      = (new Date()).getTime();
					Long timeDiff = now - threadStart;
					if(timeDiff > uploadTimeoutMillis){
						log.severe("Waited " + timeDiff + " for upload to complete without success.  Terminating.");
						
						thread.interrupt();
						throw new InterruptedException("Stopped upload after " + timeDiff + " milliseconds.  Upload most likely was partially but not fully complete.");
					}
					
					// Wait some more...
				}
				else{
					cont = false;
				}
			}
		}
		else {
			// Finished
		}
		
		log.info("Thread completed.  Checking for exceptions.");
		
		if(thread.getException() != null){
			log.severe("Thread threw exception '" + thread.getException() + "'.");
			throw thread.getException();
		}
		
		log.info("Upload complete.");
	}
}
