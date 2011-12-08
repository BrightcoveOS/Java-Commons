package com.brightcove.commons.applications;

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
import org.w3c.dom.Node;
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
	Logger log;
	Long   uploadTimeoutMillis;
	
	FTPUploaderThread ftput;
	
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
		
		ftpu.allowNormalArgument("config-file",                            "--config-file <path>",                                  "--config-file:     Path to configuration file",                        true);
		ftpu.allowNormalArgument("timeout-retries",                        "--timeout-retries <integer>",                           "--timeout-retries: Number of times to retry an upload that times out", false);
		ftpu.allowNormalArgument("CONFIG_FTP_UPLOAD_TIMEOUT_MILLISECONDS", "--CONFIG_FTP_UPLOAD_TIMEOUT_MILLISECONDS <long int>",   "--CONFIG_FTP_UPLOAD_TIMEOUT_MILLISECONDS: Override config file setting - number of milliseconds to wait before timing out FTP request", false);
		ftpu.allowNormalArgument("CONFIG_FTP_UPLOAD_SERVER",               "--CONFIG_FTP_UPLOAD_SERVER <host or ip>",               "--CONFIG_FTP_UPLOAD_SERVER:               Override config file setting - host or ip address of server to upload to",                    false);
		ftpu.allowNormalArgument("CONFIG_FTP_UPLOAD_PORT",                 "--CONFIG_FTP_UPLOAD_PORT <integer>",                    "--CONFIG_FTP_UPLOAD_PORT:                 Override config file setting - port of FTP server to connect to",                             false);
		ftpu.allowNormalArgument("CONFIG_FTP_UPLOAD_USER",                 "--CONFIG_FTP_UPLOAD_USER <string>",                     "--CONFIG_FTP_UPLOAD_USER:                 Override config file setting - username to log in with",                                      false);
		ftpu.allowNormalArgument("CONFIG_FTP_UPLOAD_PASSWORD",             "--CONFIG_FTP_UPLOAD_PASSWORD <string>",                 "--CONFIG_FTP_UPLOAD_PASSWORD:             Override config file setting - password to log in with",                                      false);
		ftpu.allowNormalArgument("CONFIG_FTP_UPLOAD_SKIP",                 "--CONFIG_FTP_UPLOAD_SKIP <TRUE|FALSE>",                 "--CONFIG_FTP_UPLOAD_SKIP:                 Override config file setting - if TRUE, upload will be skipped (testing mode)",               false);
		ftpu.allowNormalArgument("CONFIG_FTP_UPLOAD_REMOVE_SOURCE",        "--CONFIG_FTP_UPLOAD_REMOVE_SOURCE <TRUE|FALSE>",        "--CONFIG_FTP_UPLOAD_REMOVE_SOURCE:        Override config file setting - if TRUE, uploaded files will be deleted locally",              false);
		ftpu.allowNormalArgument("CONFIG_FTP_UPLOAD_USE_PASSIVE_TRANSFER", "--CONFIG_FTP_UPLOAD_USE_PASSIVE_TRANSFER <TRUE|FALSE>", "--CONFIG_FTP_UPLOAD_USE_PASSIVE_TRANSFER: Override config file setting - if TRUE, FTP passive mode will be used (active otherwise)",    false);
		ftpu.allowNormalArgument("CONFIG_FTP_UPLOAD_DEBUG",                "--CONFIG_FTP_UPLOAD_DEBUG <TRUE|FALSE>",                "--CONFIG_FTP_UPLOAD_DEBUG:                Override config file setting - if TRUE, verbose logging about FTP connections will be given", false);
		ftpu.allowNormalArgument("CONFIG_FTP_UPLOAD_LOCAL_DIRECTORY",      "--CONFIG_FTP_UPLOAD_LOCAL_DIRECTORY <path>",            "--CONFIG_FTP_UPLOAD_LOCAL_DIRECTORY:      Override config file setting - local directory to upload from",                               false);
		ftpu.allowNormalArgument("CONFIG_FTP_UPLOAD_LOCAL_REGEX",          "--CONFIG_FTP_UPLOAD_LOCAL_REGEX <string>",              "--CONFIG_FTP_UPLOAD_LOCAL_REGEX:          Override config file setting - files to select from local directory for upload",              false);
		ftpu.allowNormalArgument("CONFIG_FTP_UPLOAD_LOCAL_FILE",           "--CONFIG_FTP_UPLOAD_LOCAL_FILE <path>",                 "--CONFIG_FTP_UPLOAD_LOCAL_FILE:           Override config file setting - specific file to upload",                                      false);
		ftpu.allowNormalArgument("CONFIG_FTP_UPLOAD_REMOTE_DIRECTORY",     "--CONFIG_FTP_UPLOAD_REMOTE_DIRECTORY <path>",           "--CONFIG_FTP_UPLOAD_REMOTE_DIRECTORY:     Override config file setting - remote directory to upload to",                                false);
		
		ftpu.setMaxNakedArguments(0);
		ftpu.setMinNakedArguments(0);
		
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
		
		uploadTimeoutMillis = 0l;
		
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
		
		this.uploadTimeoutMillis = uploadTimeoutMillis;
		
		ftput = new FTPUploaderThread(
			serverName,       // Server name
			21,               // Server port
			username,         // Username
			password,         // Password
			skipTransfer,     // Skip transfer
			removeSource,     // Remove source
			passiveTransfer,  // Passive transfer
			new ArrayList<UploadMapping>(),
			debug             // Debug
		);
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
		
		this.uploadTimeoutMillis = uploadTimeoutMillis;
		
		ftput = new FTPUploaderThread(
			serverName,       // Server name
			serverPort,       // Server port
			username,         // Username
			password,         // Password
			skipTransfer,     // Skip transfer
			removeSource,     // Remove source
			passiveTransfer,  // Passive transfer
			new ArrayList<UploadMapping>(),
			debug             // Debug
		);
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
		log.info("Parsing config file '" + configFile.getAbsolutePath() + "'.");
		
		Document configDoc = XalanUtils.parseXml(configFile, false);
		
		String FTP_UPLOAD_TIMEOUT_MILLISECONDS = getNormalArgument("CONFIG_FTP_UPLOAD_TIMEOUT_MILLISECONDS");
		String FTP_UPLOAD_SERVER               = getNormalArgument("CONFIG_FTP_UPLOAD_SERVER");
		String FTP_UPLOAD_PORT                 = getNormalArgument("CONFIG_FTP_UPLOAD_PORT");
		String FTP_UPLOAD_USER                 = getNormalArgument("CONFIG_FTP_UPLOAD_USER");
		String FTP_UPLOAD_PASSWORD             = getNormalArgument("CONFIG_FTP_UPLOAD_PASSWORD");
		String FTP_UPLOAD_SKIP                 = getNormalArgument("CONFIG_FTP_UPLOAD_SKIP");
		String FTP_UPLOAD_REMOVE_SOURCE        = getNormalArgument("CONFIG_FTP_UPLOAD_REMOVE_SOURCE");
		String FTP_UPLOAD_USE_PASSIVE_TRANSFER = getNormalArgument("CONFIG_FTP_UPLOAD_USE_PASSIVE_TRANSFER");
		String FTP_UPLOAD_DEBUG                = getNormalArgument("CONFIG_FTP_UPLOAD_DEBUG");
		String FTP_UPLOAD_LOCAL_DIRECTORY      = getNormalArgument("CONFIG_FTP_UPLOAD_LOCAL_DIRECTORY");
		String FTP_UPLOAD_LOCAL_REGEX          = getNormalArgument("CONFIG_FTP_UPLOAD_LOCAL_REGEX");
		String FTP_UPLOAD_LOCAL_FILE           = getNormalArgument("CONFIG_FTP_UPLOAD_LOCAL_FILE");
		String FTP_UPLOAD_REMOTE_DIRECTORY     = getNormalArgument("CONFIG_FTP_UPLOAD_REMOTE_DIRECTORY");
		
		if(FTP_UPLOAD_TIMEOUT_MILLISECONDS == null){
			FTP_UPLOAD_TIMEOUT_MILLISECONDS = getStringSetting(configDoc, "FTP_UPLOAD_TIMEOUT_MILLISECONDS");
		}
		if(FTP_UPLOAD_SERVER == null){
			FTP_UPLOAD_SERVER = getStringSetting(configDoc, "FTP_UPLOAD_SERVER");
		}
		if(FTP_UPLOAD_PORT == null){
			FTP_UPLOAD_PORT = getStringSetting(configDoc, "FTP_UPLOAD_PORT");
		}
		if(FTP_UPLOAD_USER == null){
			FTP_UPLOAD_USER = getStringSetting(configDoc, "FTP_UPLOAD_USER");
		}
		if(FTP_UPLOAD_PASSWORD == null){
			FTP_UPLOAD_PASSWORD = getStringSetting(configDoc, "FTP_UPLOAD_PASSWORD");
		}
		if(FTP_UPLOAD_SKIP == null){
			FTP_UPLOAD_SKIP = getStringSetting(configDoc, "FTP_UPLOAD_SKIP");
		}
		if(FTP_UPLOAD_REMOVE_SOURCE == null){
			FTP_UPLOAD_REMOVE_SOURCE = getStringSetting(configDoc, "FTP_UPLOAD_REMOVE_SOURCE");
		}
		if(FTP_UPLOAD_USE_PASSIVE_TRANSFER == null){
			FTP_UPLOAD_USE_PASSIVE_TRANSFER = getStringSetting(configDoc, "FTP_UPLOAD_USE_PASSIVE_TRANSFER");
		}
		if(FTP_UPLOAD_DEBUG == null){
			FTP_UPLOAD_DEBUG = getStringSetting(configDoc, "FTP_UPLOAD_DEBUG");
		}
		if(FTP_UPLOAD_LOCAL_DIRECTORY == null){
			FTP_UPLOAD_LOCAL_DIRECTORY = getStringSetting(configDoc, "FTP_UPLOAD_LOCAL_DIRECTORY");
		}
		if(FTP_UPLOAD_LOCAL_REGEX == null){
			FTP_UPLOAD_LOCAL_REGEX = getStringSetting(configDoc, "FTP_UPLOAD_LOCAL_REGEX");
		}
		if(FTP_UPLOAD_LOCAL_FILE == null){
			FTP_UPLOAD_LOCAL_FILE = getStringSetting(configDoc, "FTP_UPLOAD_LOCAL_FILE");
		}
		if(FTP_UPLOAD_REMOTE_DIRECTORY == null){
			FTP_UPLOAD_REMOTE_DIRECTORY = getStringSetting(configDoc, "FTP_UPLOAD_REMOTE_DIRECTORY");
		}
		
		if("".equals(FTP_UPLOAD_LOCAL_DIRECTORY)){
			FTP_UPLOAD_LOCAL_DIRECTORY = null;
		}
		if("".equals(FTP_UPLOAD_LOCAL_REGEX)){
			FTP_UPLOAD_LOCAL_REGEX = null;
		}
		if("".equals(FTP_UPLOAD_LOCAL_FILE)){
			FTP_UPLOAD_LOCAL_FILE = null;
		}
		if("".equals(FTP_UPLOAD_REMOTE_DIRECTORY)){
			FTP_UPLOAD_REMOTE_DIRECTORY = null;
		}
		
		log.info(
			"Upload settings:\n"+
			"\tFTP_UPLOAD_TIMEOUT_MILLISECONDS: '" + FTP_UPLOAD_TIMEOUT_MILLISECONDS + "'\n" +
			"\tFTP_UPLOAD_SERVER:               '" + FTP_UPLOAD_SERVER + "'\n" +
			"\tFTP_UPLOAD_PORT:                 '" + FTP_UPLOAD_PORT + "'\n" +
			"\tFTP_UPLOAD_USER:                 '" + FTP_UPLOAD_USER + "'\n" +
			"\tFTP_UPLOAD_PASSWORD:             '" + FTP_UPLOAD_PASSWORD + "'\n" +
			"\tFTP_UPLOAD_SKIP:                 '" + FTP_UPLOAD_SKIP + "'\n" +
			"\tFTP_UPLOAD_REMOVE_SOURCE:        '" + FTP_UPLOAD_REMOVE_SOURCE + "'\n" +
			"\tFTP_UPLOAD_USE_PASSIVE_TRANSFER: '" + FTP_UPLOAD_USE_PASSIVE_TRANSFER + "'\n" +
			"\tFTP_UPLOAD_DEBUG:                '" + FTP_UPLOAD_DEBUG + "'\n" +
			"\tFTP_UPLOAD_LOCAL_DIRECTORY:      '" + FTP_UPLOAD_LOCAL_DIRECTORY + "'\n" +
			"\tFTP_UPLOAD_LOCAL_REGEX:          '" + FTP_UPLOAD_LOCAL_REGEX + "'\n" +
			"\tFTP_UPLOAD_LOCAL_FILE:           '" + FTP_UPLOAD_LOCAL_FILE + "'\n" +
			"\tFTP_UPLOAD_REMOTE_DIRECTORY:     '" + FTP_UPLOAD_REMOTE_DIRECTORY + "'\n"
		);
		
		uploadTimeoutMillis = Long.parseLong(FTP_UPLOAD_TIMEOUT_MILLISECONDS);
		
		String  serverName = FTP_UPLOAD_SERVER;
		Integer serverPort = Integer.parseInt(FTP_UPLOAD_PORT);
		String  username   = FTP_UPLOAD_USER;
		String  password   = FTP_UPLOAD_PASSWORD;
		
		if(serverPort == null){
			serverPort = 21;
		}
		
		Boolean skipTransfer    = Boolean.parseBoolean(FTP_UPLOAD_SKIP);
		Boolean removeSource    = Boolean.parseBoolean(FTP_UPLOAD_REMOVE_SOURCE);
		Boolean passiveTransfer = Boolean.parseBoolean(FTP_UPLOAD_USE_PASSIVE_TRANSFER);
		Boolean debug           = Boolean.parseBoolean(FTP_UPLOAD_DEBUG);
		
		if(skipTransfer    == null){ skipTransfer    = false; }
		if(removeSource    == null){ removeSource    = false; }
		if(passiveTransfer == null){ passiveTransfer = false; }
		if(debug           == null){ debug           = false; }
		
		ftput = new FTPUploaderThread(
			serverName,       // Server name
			serverPort,       // Server port
			username,         // Username
			password,         // Password
			skipTransfer,     // Skip transfer
			removeSource,     // Remove source
			passiveTransfer,  // Passive transfer
			new ArrayList<UploadMapping>(),
			debug             // Debug
		);
		
		String uploadDirectory = FTP_UPLOAD_LOCAL_DIRECTORY;
		String uploadRegex     = FTP_UPLOAD_LOCAL_REGEX;
		String uploadFile      = FTP_UPLOAD_LOCAL_FILE;
		String remoteDirectory = FTP_UPLOAD_REMOTE_DIRECTORY;
		// String uploadOptions   = getStringSetting(configDoc, "FTP_UPLOAD_OPTIONS");
		
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
		parseArguments(args);
		
		File configFile = new File(getNormalArgument("config-file"));
		if(! configFile.exists()){
			usage("Config file '" + configFile.getAbsolutePath() + "' doesn't exist.");
		}
		
		try{
			parseConfigFile(configFile);
		}
		catch(Exception e){
			usage(e);
		}
		
		String  retryArg = getNormalArgument("timeout-retries");
		Integer retries  = 0;
		if((retryArg != null) && (! "".equals(retryArg))){
			retries = new Integer(retryArg);
		}
		
		Integer   attempt       = 0;
		Exception lastException = null;
		while(attempt <= retries){
			try {
				doUpload();
				log.info("Upload complete.");
				return;
			}
			catch (Exception e) {
				log.severe("Exception caught: '" + e + "'.");
				lastException = e;
				
				if(attempt < retries){
					log.info("Will retry request");
				}
				else{
					log.severe("Maximum number of retries (" + retries + ") reached.  Request will not be retried.");
				}
			}
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
	
	private String getStringSetting(Document configDoc, String settingName) throws TransformerException {
		String xpath = "/config/setting[@name='" + settingName + "']/value";
		log.info("Looking up setting '"+settingName+"' with xpath '" + xpath + "'.");
		
		List<Node> val = XalanUtils.getNodesFromXPath(configDoc, "/config/setting[@name='" + settingName + "']/value");
		
		if(val == null){
			log.info("    No values found.");
			return "";
		}
		
		log.info("    Found '" + val.size() + "' values.");
		if(val.size() < 1){
			return "";
		}
		
		Node textNode = val.get(0).getFirstChild();
		if(textNode == null){
			log.info("        No text child nodes found.");
			return "";
		}
		if(textNode.getNodeValue() == null){
			log.info("        No text value found on child node.");
			return "";
		}
		
		log.info("        Returning value '" + textNode.getNodeValue() + "'.");
		return textNode.getNodeValue();
	}
	
	//private String getStringSetting(Document configDoc, String settingName) throws TransformerException {
	//	return XalanUtils.getStringFromXPath(configDoc, "/config/setting[@name='" + settingName + "']/value");
	//}
	//
	//private Integer getIntegerSetting(Document configDoc, String settingName) throws TransformerException {
	//	return XalanUtils.getIntegerFromXPath(configDoc, "/config/setting[@name='" + settingName + "']/value");
	//}
	//
	//private Boolean getBooleanSetting(Document configDoc, String settingName) throws TransformerException {
	//	return XalanUtils.getBooleanFromXPath(configDoc, "/config/setting[@name='" + settingName + "']/value");
	//}
	//
	//private Long getLongSetting(Document configDoc, String settingName) throws TransformerException {
	//	return XalanUtils.getLongFromXPath(configDoc, "/config/setting[@name='" + settingName + "']/value");
	//}
	
	private void init(){
		log = Logger.getLogger(this.getClass().getCanonicalName());
		// uploadMappings = new ArrayList<UploadMapping>();
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
		log.info("Starting new thread '" + ftput + "'.");
		ftput.start();
		
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
					if(timeDiff > uploadTimeoutMillis){
						log.severe("Waited " + timeDiff + " for upload to complete without success.  Terminating.");
						
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
		
		log.info("Thread completed.  Checking for exceptions.");
		
		if(ftput.getException() != null){
			log.severe("Thread threw exception '" + ftput.getException() + "'.");
			throw ftput.getException();
		}
		
		log.info("Upload complete.");
	}
}
