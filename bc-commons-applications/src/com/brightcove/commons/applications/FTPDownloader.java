package com.brightcove.commons.applications;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.brightcove.commons.ftp.DownloadMapping;
import com.brightcove.commons.ftp.FTPDownloaderThread;
import com.brightcove.commons.system.commandLine.CommandLineProgram;
import com.brightcove.commons.xml.XalanUtils;

/**
 * <p>
 *    Utility class to download files from an FTP server.  Includes logic to
 *    thread the upload and retry if the request takes too long.
 * </p>
 * 
 * @author <a href="https://github.com/three4clavin">three4clavin</a>
 *
 */
public class FTPDownloader extends CommandLineProgram {
	Logger log;
	Long   downloadTimeoutMillis;
	
	FTPDownloaderThread ftpdt;
	
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
		FTPDownloader ftpd = new FTPDownloader();
		
		ftpd.allowNormalArgument("config-file",     "--config-file <path>",        "--config-file:     Path to configuration file",                         true);
		ftpd.allowNormalArgument("timeout-retries", "--timeout-retries <integer>", "--timeout-retries: Number of times to retry a download that times out", false);
		
		ftpd.setMaxNakedArguments(0);
		ftpd.setMinNakedArguments(0);
		
		ftpd.run(args);
	}
	
	/**
	 * <p>
	 *    Default constructor.  Not intended to be called by other
	 *    classes/objects - for command line use mainly.
	 * </p>
	 */
	public FTPDownloader(){
		init();
		
		downloadTimeoutMillis = 0l;
		
		ftpdt = new FTPDownloaderThread(
			"",        // Server name
			21,        // Server port
			"",        // Username
			"",        // Password
			true,      // Skip transfer
			false,     // Remove source
			false,     // Passive transfer
			new ArrayList<DownloadMapping>(),
			true       // Debug
		);
	}
	
	/**
	 * <p>
	 *    Constructor specifying everything except server port (assumes port 21).
	 * </p>
	 * 
	 * @param serverName            Server host name or ip address to connect to
	 * @param username              Username to connect with
	 * @param password              Password to connect with
	 * @param skipTransfer          Skip transfer (usually used for testing)
	 * @param removeSource          Remove local file after uploading
	 * @param passiveTransfer       Use passive transfer mode if true instead of active
	 * @param downloadTimeoutMillis Time in milliseconds to timeout a request if it hasn't completed
	 * @param debug                 Verbose debugging messages on or off
	 */
	public FTPDownloader(String serverName, String username, String password, Boolean skipTransfer, Boolean removeSource, Boolean passiveTransfer, Long downloadTimeoutMillis, Boolean debug) {
		init();
		
		downloadTimeoutMillis = 0l;
		
		ftpdt = new FTPDownloaderThread(
			serverName,      // Server name
			21,              // Server port
			username,        // Username
			password,        // Password
			skipTransfer,    // Skip transfer
			removeSource,    // Remove source
			passiveTransfer, // Passive transfer
			new ArrayList<DownloadMapping>(),
			debug            // Debug
		);
	}
	
	/**
	 * <p>
	 *    Constructor specifying everything.
	 * </p>
	 * 
	 * @param serverName            Server host name or ip address to connect to
	 * @param serverPort            Server port to connect to
	 * @param username              Username to connect with
	 * @param password              Password to connect with
	 * @param skipTransfer          Skip transfer (usually used for testing)
	 * @param removeSource          Remove local file after uploading
	 * @param passiveTransfer       Use passive transfer mode if true instead of active
	 * @param downloadTimeoutMillis Time in milliseconds to timeout a request if it hasn't completed
	 * @param debug                 Verbose debugging messages on or off
	 */
	public FTPDownloader(String serverName, Integer serverPort, String username, String password, Boolean skipTransfer, Boolean removeSource, Boolean passiveTransfer, Long downloadTimeoutMillis, Boolean debug) {
		init();
		
		downloadTimeoutMillis = 0l;
		
		ftpdt = new FTPDownloaderThread(
			serverName,      // Server name
			serverPort,      // Server port
			username,        // Username
			password,        // Password
			skipTransfer,    // Skip transfer
			removeSource,    // Remove source
			passiveTransfer, // Passive transfer
			new ArrayList<DownloadMapping>(),
			debug            // Debug
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
	public FTPDownloader(File configFile) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		init();
		
		parseConfigFile(configFile);
	}
	
	private void parseConfigFile(File configFile) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		Document configDoc = XalanUtils.parseXml(configFile, false);
		
		downloadTimeoutMillis = getLongSetting(configDoc, "FTP_DOWNLOAD_TIMEOUT_MILLISECONDS");
		
		String  serverName = getStringSetting(configDoc, "FTP_DOWNLOAD_SERVER");
		Integer serverPort = getIntegerSetting(configDoc, "FTP_DOWNLOAD_PORT");
		String  username   = getStringSetting(configDoc, "FTP_DOWNLOAD_USER");
		String  password   = getStringSetting(configDoc, "FTP_DOWNLOAD_PASSWORD");
		
		if(serverPort == null){
			serverPort = 21;
		}
		
		Boolean skipTransfer    = getBooleanSetting(configDoc, "FTP_DOWNLOAD_SKIP");
		Boolean removeSource    = getBooleanSetting(configDoc, "FTP_DOWNLOAD_REMOVE_SOURCE");
		Boolean passiveTransfer = getBooleanSetting(configDoc, "FTP_DOWNLOAD_USE_PASSIVE_TRANSFER");
		Boolean debug           = getBooleanSetting(configDoc, "FTP_DOWNLOAD_DEBUG");
		
		if(skipTransfer    == null){ skipTransfer    = false; }
		if(removeSource    == null){ removeSource    = false; }
		if(passiveTransfer == null){ passiveTransfer = false; }
		if(debug           == null){ debug           = false; }
		
		ftpdt = new FTPDownloaderThread(
			serverName,      // Server name
			serverPort,      // Server port
			username,        // Username
			password,        // Password
			skipTransfer,    // Skip transfer
			removeSource,    // Remove source
			passiveTransfer, // Passive transfer
			new ArrayList<DownloadMapping>(),
			debug            // Debug
		);
		
		String downloadDirectory = getStringSetting(configDoc, "FTP_DOWNLOAD_REMOTE_DIRECTORY");
		String downloadFile      = getStringSetting(configDoc, "FTP_DOWNLOAD_REMOTE_FILE");
		String localDirectory    = getStringSetting(configDoc, "FTP_DOWNLOAD_LOCAL_DIRECTORY");
		// String uploadOptions   = getStringSetting(configDoc, "FTP_UPLOAD_OPTIONS");
		
		File localDir = new File(localDirectory);
		if(! localDir.exists()){
			throw new IOException("Local directory '" + localDir.getAbsolutePath() + "' does not exist.");
		}
		if(! localDir.isDirectory()){
			throw new IOException("Path '" + localDir.getAbsolutePath() + "' is not a directory.");
		}
		
		addDownload(downloadDirectory, downloadFile, localDir);
		
	}
	
	/* (non-Javadoc)
	 * @see com.brightcove.commons.system.commandLine.CommandLineProgram#run(java.lang.String[])
	 */
	public void run(String[] args){
		// This is the main execution from the command line call - not meant
		// to be called from another class or object - see doDownload() instead
		
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
				doDownload();
				log.info("Download complete.");
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
	
	private void addDownload(String remoteDirectory, String remoteFile, File localFile){
		String remotePath = remoteDirectory;
		if(remotePath == null){
			remotePath = "/";
		}
		if(! remotePath.endsWith("/")){
			remotePath += "/";
		}
		remotePath += remoteFile;
		
		ftpdt.addDownloadMapping(remotePath, localFile);
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
		log              = Logger.getLogger(this.getClass().getCanonicalName());
		// downloadMappings = new ArrayList<DownloadMapping>();
	}
	
	/**
	 * <p>
	 *    Performs the download.  Actual download is done by a background
	 *    thread, so it can be killed if it runs for too long.
	 * </p>
	 * 
	 * @throws Exception If thread is interrupted (mainly if download times out)
	 */
	public void doDownload() throws Exception {
		log.info("Starting new thread '" + ftpdt + "'.");
		ftpdt.start();
		
		if (ftpdt.isAlive()) {
			// Thread has not finished
			Long    waitInterval = 5000l; // 5 seconds
			Boolean cont         = true;
			Long    threadStart  = (new Date()).getTime();
			while(cont){
				ftpdt.join(waitInterval);
				
				if(ftpdt.isAlive()){
					Long now      = (new Date()).getTime();
					Long timeDiff = now - threadStart;
					if(timeDiff > downloadTimeoutMillis){
						log.severe("Waited " + timeDiff + " for download to complete without success.  Terminating.");
						
						ftpdt.interrupt();
						throw new InterruptedException("Stopped download after " + timeDiff + " milliseconds.  Download most likely was partially but not fully complete.");
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
		
		if(ftpdt.getException() != null){
			log.severe("Thread threw exception '" + ftpdt.getException() + "'.");
			throw ftpdt.getException();
		}
		
		log.info("Upload complete.");
	}
}
