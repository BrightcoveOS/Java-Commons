package com.brightcove.commons.applications;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.brightcove.commons.ftp.FTPUploaderThread;
import com.brightcove.commons.ftp.UploadMapping;
import com.brightcove.commons.system.commandLine.CommandLineProgram;
import com.brightcove.commons.xml.XalanUtils;

/**
 * <p>
 *    Utility class to upload files to an FTP server.  Includes logic to
 *    thread the upload and retry if the request takes too long.
 * </p>
 * 
 * @author <a href="https://github.com/three4clavin">three4clavin</a>
 *
 */
public class FTPUploader extends CommandLineProgram {
	Long              timeoutMilliseconds = null;
	Integer           maxRetries          = null;
	File              configFile          = null;
	FTPUploaderThread ftput               = null;
	
	/**
	 * <p>
	 *    Main command line execution.  This should not be called from another
	 *    class/object - it is meant only as a command line bootstrap.
	 * </p>
	 * 
	 * <p>
	 *    Accepted command line arguments:<ul>
	 *        <li>--config-file:     Path to config file with FTP options</li>
	 *        <li>--timeout-retries: Number of times to retry a timed out request</li>
	 *    </ul>
	 * </p>
	 * 
	 * @param args Command line arguments
	 */
	public static void main(String[] args) {
		FTPUploader ftpu = new FTPUploader();
		
		ftpu.allowNormalArgument("config-file",          "--config-file <path>",            "--config-file:          Path to configuration file",                                                                  false);
		ftpu.allowNormalArgument("max-retries",          "--max-retries <integer>",         "--max-retries:          Number of times to retry an upload that times out",                                           false);
		ftpu.allowNormalArgument("timeout-milliseconds", "--timeout-milliseconds <long>",   "--timeout-milliseconds: Override config file setting - number of milliseconds to wait before timing out FTP request", false);
		ftpu.allowNormalArgument("server-name",          "--server-name <host or ip>",      "--server-name:          Override config file setting - host or ip address of server to upload to",                    false);
		ftpu.allowNormalArgument("server-port",          "--server-port <integer>",         "--server-port:          Override config file setting - port of FTP server to connect to",                             false);
		ftpu.allowNormalArgument("username",             "--username <string>",             "--username:             Override config file setting - username to log in with",                                      false);
		ftpu.allowNormalArgument("password",             "--password <string>",             "--password:             Override config file setting - password to log in with",                                      false);
		ftpu.allowNormalArgument("skip-transfer",        "--skip-transfer <TRUE|FALSE>",    "--skip-transfer:        Override config file setting - if TRUE, upload will be skipped (testing mode)",               false);
		ftpu.allowNormalArgument("remove-source",        "--remove-source <TRUE|FALSE>",    "--remove-source:        Override config file setting - if TRUE, uploaded files will be deleted locally",              false);
		ftpu.allowNormalArgument("passive-transfer",     "--passive-transfer <TRUE|FALSE>", "--passive-transfer:     Override config file setting - if TRUE, FTP passive mode will be used (active otherwise)",    false);
		ftpu.allowNormalArgument("debug",                "--debug <TRUE|FALSE>",            "--debug:                Override config file setting - if TRUE, verbose logging about FTP connections will be given", false);
		ftpu.allowNormalArgument("local-directory",      "--local-directory <path>",        "--local-directory:      Override config file setting - local directory to upload from",                               false);
		ftpu.allowNormalArgument("local-regex",          "--local-regex <string>",          "--local-regex:          Override config file setting - files to select from local directory for upload",              false);
		ftpu.allowNormalArgument("local-file",           "--local-file <path>",             "--local-file:           Override config file setting - specific file to upload",                                      false);
		ftpu.allowNormalArgument("remote-directory",     "--remote-directory <path>",       "--remote-directory:     Override config file setting - remote directory to upload to",                                false);
		
		ftpu.setMaxNakedArguments(0);
		ftpu.setMinNakedArguments(0);
		
		ftpu.parseArguments(args);
		
		if(ftpu.getNormalArgument("config-file") != null){
			File configFile = new File(ftpu.getNormalArgument("config-file"));
			try {
				ftpu.parseConfigFile(configFile);
			}
			catch (Exception e) {
				ftpu.usage(e);
			}
		}
		
		if(ftpu.getNormalArgument("server-name") != null){
			ftpu.getFtpUploaderThread().setServerName(ftpu.getNormalArgument("server-name"));
		}
		if(ftpu.getNormalArgument("server-port") != null){
			ftpu.getFtpUploaderThread().setServerPort(Integer.parseInt(ftpu.getNormalArgument("server-port")));
		}
		if(ftpu.getNormalArgument("username") != null){
			ftpu.getFtpUploaderThread().setUsername(ftpu.getNormalArgument("username"));
		}
		if(ftpu.getNormalArgument("password") != null){
			ftpu.getFtpUploaderThread().setPassword(ftpu.getNormalArgument("password"));
		}
		if(ftpu.getNormalArgument("skip-transfer") != null){
			ftpu.getFtpUploaderThread().setSkipTransfer(Boolean.parseBoolean(ftpu.getNormalArgument("skip-transfer")));
		}
		if(ftpu.getNormalArgument("remove-source") != null){
			ftpu.getFtpUploaderThread().setRemoveSource(Boolean.parseBoolean(ftpu.getNormalArgument("remove-source")));
		}
		if(ftpu.getNormalArgument("passive-transfer") != null){
			ftpu.getFtpUploaderThread().setPassiveTransfer(Boolean.parseBoolean(ftpu.getNormalArgument("passive-transfer")));
		}
		if(ftpu.getNormalArgument("debug") != null){
			ftpu.getFtpUploaderThread().setDebug(Boolean.parseBoolean(ftpu.getNormalArgument("debug")));
		}
		
		if(ftpu.getNormalArgument("local-directory") != null){
			ftpu.getFtpUploaderThread().setUploadMappings(new ArrayList<UploadMapping>());
			
			File uploadDir = new File(ftpu.getNormalArgument("local-directory"));
			if(! uploadDir.exists()){
				ftpu.usage("Upload directory '" + uploadDir.getAbsolutePath() + "' does not exist.");
			}
			if(! uploadDir.isDirectory()){
				ftpu.usage("Path '" + uploadDir.getAbsolutePath() + "' is not a directory.");
			}
			
			String remoteDirectory = ftpu.getNormalArgument("remote-directory");
			
			if(ftpu.getNormalArgument("local-file") != null){
				ftpu.addUpload(uploadDir, ftpu.getNormalArgument("local-file"), remoteDirectory);
			}
			else{
				String uploadRegex = ftpu.getNormalArgument("local-regex");
				for(File localFile : uploadDir.listFiles()){
					Pattern pattern = Pattern.compile(uploadRegex);
					Matcher matcher = pattern.matcher(localFile.getName());
					if(matcher.find()){
						ftpu.addUpload(localFile, remoteDirectory);
					}
				}
			}
		}
		
		if(ftpu.getNormalArgument("max-retries") != null){
			ftpu.setMaxRetries(Integer.parseInt(ftpu.getNormalArgument("max-retries")));
		}
		if(ftpu.getNormalArgument("timeout-milliseconds") != null){
			ftpu.setTimeoutMilliseconds(Long.parseLong(ftpu.getNormalArgument("timeout-milliseconds")));
		}
		
		ftpu.getLogger().info("Configuration:\n" + 
			"Config file:          '" + ftpu.getNormalArgument("config-file")          + "'\n" + 
			"Max retries:          '" + ftpu.getNormalArgument("max-retries")          + "'\n" + 
			"Timeout milliseconds: '" + ftpu.getNormalArgument("timeout-milliseconds") + "'\n" + 
			"Server name:          '" + ftpu.getNormalArgument("server-name")          + "'\n" + 
			"Server port:          '" + ftpu.getNormalArgument("server-port")          + "'\n" + 
			"Username:             '" + ftpu.getNormalArgument("username")             + "'\n" + 
			"Password:             '" + ftpu.getNormalArgument("password")             + "'\n" + 
			"Skip transfer:        '" + ftpu.getNormalArgument("skip-transfer")        + "'\n" + 
			"Remove source:        '" + ftpu.getNormalArgument("remove-source")        + "'\n" +
			"Debug:                '" + ftpu.getNormalArgument("debug")                + "'\n" + 
			"Local directory:      '" + ftpu.getNormalArgument("local-directory")      + "'\n" + 
			"Local regex:          '" + ftpu.getNormalArgument("local-regex")          + "'\n" + 
			"Local file:           '" + ftpu.getNormalArgument("local-file")           + "'\n" + 
			"Remote directory:     '" + ftpu.getNormalArgument("remote-directory")     + "'."
		);
		
		ftpu.run(args);
	}
	
	/**
	 * <p>
	 *    Default constructor.  Not intended to be called by other
	 *    classes/objects - for command line use mainly.
	 * </p>
	 */
	public FTPUploader(){
		init();
	}
	
	/**
	 * <p>
	 *    Constructor specifying everything except server port (assumes port 21).
	 * </p>
	 * 
	 * @param serverName          Server host name or ip address to connect to
	 * @param username            Username to connect with
	 * @param password            Password to connect with
	 * @param skipTransfer        Skip transfer (usually used for testing)
	 * @param removeSource        Remove local file after uploading
	 * @param passiveTransfer     Use passive transfer mode if true instead of active
	 * @param uploadTimeoutMillis Time in milliseconds to timeout a request if it hasn't completed
	 * @param debug               Verbose debugging messages on or off
	 */
	public FTPUploader(String serverName, String username, String password, Boolean skipTransfer, Boolean removeSource, Boolean passiveTransfer, Long uploadTimeoutMillis, Boolean debug) {
		init();
		
		this.getFtpUploaderThread().setServerName(serverName);
		this.getFtpUploaderThread().setServerPort(21);
		this.getFtpUploaderThread().setUsername(username);
		this.getFtpUploaderThread().setPassword(password);
		this.getFtpUploaderThread().setSkipTransfer(skipTransfer);
		this.getFtpUploaderThread().setRemoveSource(removeSource);
		this.getFtpUploaderThread().setPassiveTransfer(passiveTransfer);
		this.getFtpUploaderThread().setUploadMappings(new ArrayList<UploadMapping>());
		this.getFtpUploaderThread().setDebug(debug);
	}
	
	/**
	 * <p>
	 *    Constructor specifying everything.
	 * </p>
	 * 
	 * @param serverName          Server host name or ip address to connect to
	 * @param serverPort          Server port to connect to
	 * @param username            Username to connect with
	 * @param password            Password to connect with
	 * @param skipTransfer        Skip transfer (usually used for testing)
	 * @param removeSource        Remove local file after uploading
	 * @param passiveTransfer     Use passive transfer mode if true instead of active
	 * @param uploadTimeoutMillis Time in milliseconds to timeout a request if it hasn't completed
	 * @param debug               Verbose debugging messages on or off
	 */
	public FTPUploader(String serverName, Integer serverPort, String username, String password, Boolean skipTransfer, Boolean removeSource, Boolean passiveTransfer, Long uploadTimeoutMillis, Boolean debug) {
		init();
		
		this.getFtpUploaderThread().setServerName(serverName);
		this.getFtpUploaderThread().setServerPort(serverPort);
		this.getFtpUploaderThread().setUsername(username);
		this.getFtpUploaderThread().setPassword(password);
		this.getFtpUploaderThread().setSkipTransfer(skipTransfer);
		this.getFtpUploaderThread().setRemoveSource(removeSource);
		this.getFtpUploaderThread().setPassiveTransfer(passiveTransfer);
		this.getFtpUploaderThread().setUploadMappings(new ArrayList<UploadMapping>());
		this.getFtpUploaderThread().setDebug(debug);
	}
	
	/**
	 * <p>
	 *    Constructor that pulls all of the configuration options from an XML config file
	 * </p>
	 * 
	 * @param configFile Configuration file to parse
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws TransformerException
	 */
	public FTPUploader(File configFile) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		init();
		
		parseConfigFile(configFile);
	}
	
	private void parseConfigFile(File configFile) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		setConfigFile(configFile);
		
		this.getLogger().info("Parsing config file '" + configFile.getAbsolutePath() + "'.");
		
		Document configDoc = XalanUtils.parseXml(configFile, false);
		
		String serverName      = getStringSetting(configDoc, "FTP_UPLOAD_SERVER");
		String serverPort      = getStringSetting(configDoc, "FTP_UPLOAD_PORT");
		String username        = getStringSetting(configDoc, "FTP_UPLOAD_USER");
		String password        = getStringSetting(configDoc, "FTP_UPLOAD_PASSWORD");
		String skipTransfer    = getStringSetting(configDoc, "FTP_UPLOAD_SKIP");
		String removeSource    = getStringSetting(configDoc, "FTP_UPLOAD_REMOVE_SOURCE");
		String passiveTransfer = getStringSetting(configDoc, "FTP_UPLOAD_USE_PASSIVE_TRANSFER");
		String debug           = getStringSetting(configDoc, "FTP_UPLOAD_DEBUG");
		
		if(serverPort == null){
			serverPort = "21";
		}
		if(skipTransfer == null){
			skipTransfer = "false";
		}
		if(removeSource == null){
			removeSource = "false";
		}
		if(passiveTransfer == null){
			passiveTransfer = "true";
		}
		if(debug == null){
			debug = "true";
		}
		
		this.getFtpUploaderThread().setServerName(serverName);
		this.getFtpUploaderThread().setServerPort(Integer.parseInt(serverPort));
		this.getFtpUploaderThread().setUsername(username);
		this.getFtpUploaderThread().setPassword(password);
		this.getFtpUploaderThread().setSkipTransfer(Boolean.parseBoolean(skipTransfer));
		this.getFtpUploaderThread().setRemoveSource(Boolean.parseBoolean(removeSource));
		this.getFtpUploaderThread().setPassiveTransfer(Boolean.parseBoolean(passiveTransfer));
		this.getFtpUploaderThread().setDebug(Boolean.parseBoolean(debug));
		
		String uploadDirectory = getStringSetting(configDoc, "FTP_UPLOAD_LOCAL_DIRECTORY");
		String uploadRegex     = getStringSetting(configDoc, "FTP_UPLOAD_LOCAL_REGEX");
		String uploadFile      = getStringSetting(configDoc, "FTP_UPLOAD_LOCAL_FILE");
		String remoteDirectory = getStringSetting(configDoc, "FTP_UPLOAD_REMOTE_DIRECTORY");
		
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
		
	}
	
	/* (non-Javadoc)
	 * @see com.brightcove.commons.system.commandLine.CommandLineProgram#run(java.lang.String[])
	 */
	public void run(String[] args){
		// This is the main execution from the command line call - not meant
		// to be called from another class or object - see doUpload() instead
		
		setCaller(this.getClass().getCanonicalName());
		// parseArguments(args);
		
		if(maxRetries == null){
			maxRetries = 0;
		}
		
		Integer   attempt       = 0;
		Exception lastException = null;
		while(attempt <= maxRetries){
			try {
				doUpload();
				this.getLogger().info("Upload complete.");
				return;
			}
			catch (Exception e) {
				this.getLogger().severe("Exception caught: '" + e + "'.");
				lastException = e;
				
				if(attempt < maxRetries){
					this.getLogger().info("Will retry request");
				}
				else{
					this.getLogger().severe("Maximum number of retries (" + maxRetries + ") reached.  Request will not be retried.");
				}
			}
			
			attempt++;
		}
		
		usage(lastException);
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
		
		ftput.addUploadMapping(uploadFile, remoteFile);
	}
	
	private void addUpload(File uploadDir, String uploadFile, String remoteDir){
		File   localFile  = new File(uploadDir, uploadFile);
		addUpload(localFile, remoteDir);
	}
	
	
	private void init(){
		timeoutMilliseconds = 0l;
		
		ftput = new FTPUploaderThread(
			"",        // Server name
			21,        // Server port
			"",        // Username
			"",        // Password
			true,      // Skip transfer
			false,     // Remove source
			false,     // Passive transfer
			new ArrayList<UploadMapping>(),
			true       // Debug
		);
	}
	
	/**
	 * <p>
	 *    Performs the upload.  Actual upload is done by a background
	 *    thread, so it can be killed if it runs for too long.
	 * </p>
	 * 
	 * @throws Exception If thread is interrupted (mainly if upload times out)
	 */
	public void doUpload() throws Exception {
		this.getLogger().info("Starting new thread '" + ftput + "'.");
		ftput.start();
		
		if(timeoutMilliseconds == null){
			timeoutMilliseconds = 1000l * 60l * 60l * 24l; // 1 day
		}
		
		if (ftput.isAlive()) {
			// Thread has not finished
			Long    waitInterval = 5000l; // 5 seconds
			Boolean cont         = true;
			Long    threadStart  = (new Date()).getTime();
			while(cont){
				ftput.join(waitInterval);
				
				if(ftput.isAlive()){
					Long now      = (new Date()).getTime();
					Long timeDiff = now - threadStart;
					if(timeDiff > timeoutMilliseconds){
						this.getLogger().severe("Waited " + timeDiff + " for upload to complete without success.  Terminating.");
						
						ftput.interrupt();
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
		
		this.getLogger().info("Thread completed.  Checking for exceptions.");
		
		if(ftput.getException() != null){
			this.getLogger().severe("Thread threw exception '" + ftput.getException() + "'.");
			throw ftput.getException();
		}
		
		this.getLogger().info("Upload complete.");
	}
	
	public Long getTimeoutMilliseconds(){
		return timeoutMilliseconds;
	}
	
	public void setTimeoutMilliseconds(Long timeoutMilliseconds){
		this.timeoutMilliseconds = timeoutMilliseconds;
	}
	
	public Integer getMaxRetries(){
		return maxRetries;
	}
	
	public void setMaxRetries(Integer maxRetries){
		this.maxRetries = maxRetries;
	}
	
	public File getConfigFile(){
		return configFile;
	}
	
	public void setConfigFile(File configFile){
		this.configFile = configFile;
	}
	
	public FTPUploaderThread getFtpUploaderThread(){
		return ftput;
	}
	
	public void setFtpUploaderThread(FTPUploaderThread ftput){
		this.ftput = ftput;
	}
	
	public String getStringSetting(Document configDoc, String settingName) throws TransformerException {
		return XalanUtils.getStringFromXPath(configDoc, "/config/setting[@name='" + settingName + "']/value");
	}
	
	public Integer getIntegerSetting(Document configDoc, String settingName) throws TransformerException {
		return XalanUtils.getIntegerFromXPath(configDoc, "/config/setting[@name='" + settingName + "']/value");
	}
	
	public Boolean getBooleanSetting(Document configDoc, String settingName) throws TransformerException {
		return XalanUtils.getBooleanFromXPath(configDoc, "/config/setting[@name='" + settingName + "']/value");
	}
	
	public Long getLongSetting(Document configDoc, String settingName) throws TransformerException {
		return XalanUtils.getLongFromXPath(configDoc, "/config/setting[@name='" + settingName + "']/value");
	}
}
