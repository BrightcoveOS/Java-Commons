package com.brightcove.commons.applications;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.brightcove.commons.ftp.FTPManifestEntry;
import com.brightcove.commons.ftp.FTPManifestEntryType;
import com.brightcove.commons.ftp.FTPManifestThread;
import com.brightcove.commons.system.commandLine.CommandLineProgram;
import com.brightcove.commons.xml.XalanUtils;

/**
 * <p>
 *    Utility class to generate a manifest of all files on an FTP server.
 *    Includes logic to thread the list operation and retry if the request
 *    takes too long.
 * </p>
 * 
 * @author <a href="https://github.com/three4clavin">three4clavin</a>
 *
 */
public class FTPManifest extends CommandLineProgram {
	Logger log;
	Long   manifestTimeoutMillis;
	
	FTPManifestThread ftpmt;
	
	String outputFile;
	
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
		FTPManifest ftpm = new FTPManifest();
		
		ftpm.allowNormalArgument("config-file",     "--config-file <path>",        "--config-file:     Path to configuration file",                        true);
		ftpm.allowNormalArgument("timeout-retries", "--timeout-retries <integer>", "--timeout-retries: Number of times to retry a listing that times out", false);
		
		ftpm.setMaxNakedArguments(0);
		ftpm.setMinNakedArguments(0);
		
		ftpm.run(args);
		
		System.out.println("Manifest generation finished.");
		
		System.exit(0);
	}
	
	/**
	 * <p>
	 *    Default constructor.  Not intended to be called by other
	 *    classes/objects - for command line use mainly.
	 * </p>
	 */
	public FTPManifest(){
		init();
		
		manifestTimeoutMillis = 0l;
		
		outputFile = null;
		
		ftpmt = new FTPManifestThread(
			"",        // Server name
			21,        // Server port
			"",        // Username
			"",        // Password
			false,     // Passive transfer
			"/",       // Start directory
			new HashMap<String, FTPManifestEntry>(),
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
	 * @param passiveTransfer       Use passive transfer mode if true instead of active
	 * @param manifestTimeoutMillis Time in milliseconds to timeout a request if it hasn't completed
	 * @param startDirectory        Directory to begin listing at
	 * @param manifestEntries       Map to store all entry listings on the server
	 * @param debug                 Verbose debugging messages on or off
	 */
	public FTPManifest(String serverName, String username, String password, Boolean passiveTransfer, Long manifestTimeoutMillis, String startDirectory, Map<String, FTPManifestEntry> manifestEntries, Boolean debug) {
		init();
		
		this.manifestTimeoutMillis = manifestTimeoutMillis;
		
		outputFile = null;
		
		ftpmt = new FTPManifestThread(
			serverName,      // Server name
			21,              // Server port
			username,        // Username
			password,        // Password
			passiveTransfer, // Passive transfer
			startDirectory,  // Start directory
			manifestEntries,
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
	 * @param passiveTransfer       Use passive transfer mode if true instead of active
	 * @param manifestTimeoutMillis Time in milliseconds to timeout a request if it hasn't completed
	 * @param startDirectory        Directory to begin listing at
	 * @param manifestEntries       Map to store all entry listings on the server
	 * @param debug                 Verbose debugging messages on or off
	 */
	public FTPManifest(String serverName, Integer serverPort, String username, String password, Boolean passiveTransfer, Long manifestTimeoutMillis, String startDirectory, Map<String, FTPManifestEntry> manifestEntries, Boolean debug) {
		init();
		
		this.manifestTimeoutMillis = manifestTimeoutMillis;
		
		outputFile = null;
		
		ftpmt = new FTPManifestThread(
			serverName,      // Server name
			serverPort,      // Server port
			username,        // Username
			password,        // Password
			passiveTransfer, // Passive transfer
			startDirectory,  // Start directory
			manifestEntries,
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
	public FTPManifest(File configFile) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		init();
		
		parseConfigFile(configFile);
	}
	
	private void parseConfigFile(File configFile) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		Document configDoc = XalanUtils.parseXml(configFile, false);
		
		manifestTimeoutMillis = getLongSetting(configDoc, "FTP_MANIFEST_TIMEOUT_MILLISECONDS");
		
		outputFile = getStringSetting(configDoc, "FTP_MANIFEST_OUTPUT_FILE");
		
		String  serverName = getStringSetting(configDoc, "FTP_MANIFEST_SERVER");
		Integer serverPort = getIntegerSetting(configDoc, "FTP_MANIFEST_PORT");
		String  username   = getStringSetting(configDoc, "FTP_MANIFEST_USER");
		String  password   = getStringSetting(configDoc, "FTP_MANIFEST_PASSWORD");
		
		if(serverPort == null){
			serverPort = 21;
		}
		
		Boolean passiveTransfer = getBooleanSetting(configDoc, "FTP_MANIFEST_USE_PASSIVE_TRANSFER");
		Boolean debug           = getBooleanSetting(configDoc, "FTP_MANIFEST_DEBUG");
		
		if(passiveTransfer == null){ passiveTransfer = false; }
		if(debug           == null){ debug           = false; }
		
		String startDirectory = getStringSetting(configDoc, "FTP_MANIFEST_START_DIRECTORY");
		if(startDirectory == null){ startDirectory = "/"; }
		
		Map<String, FTPManifestEntry> manifestEntries = new HashMap<String, FTPManifestEntry>();
		
		ftpmt = new FTPManifestThread(
			serverName,      // Server name
			serverPort,      // Server port
			username,        // Username
			password,        // Password
			passiveTransfer, // Passive transfer
			startDirectory,  // Start directory
			manifestEntries,
			debug            // Debug
		);
	}
	
	public void setOutputFile(String outputFile){
		this.outputFile = outputFile;
	}
	public String getOutputFile(){
		return outputFile;
	}
	
	/* (non-Javadoc)
	 * @see com.brightcove.commons.system.commandLine.CommandLineProgram#run(java.lang.String[])
	 */
	public void run(String[] args){
		// This is the main execution from the command line call - not meant
		// to be called from another class or object - see doList() instead
		
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
				doList();
				log.info("Manifest generation complete.");
				return;
			}
			catch (Exception e) {
				log.severe("Exception caught: '" + e + "'.");
				lastException = e;
				
				if(attempt < retries){
					log.info("Will retry request");
					
					log.info("Sleeping for 5 minutes so hopefully old connections will get cleaned up by the server.");
					try{ Thread.sleep(5 * 60 * 1000l); } catch(InterruptedException ie) {};
					attempt++;
				}
				else{
					log.severe("Maximum number of retries (" + retries + ") reached.  Request will not be retried.");
				}
			}
		}
		
		usage(lastException);
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
		log = Logger.getLogger(this.getClass().getCanonicalName());
	}
	
	/**
	 * <p>
	 *    Performs the list.  Actual list is done by a background
	 *    thread, so it can be killed if it runs for too long.
	 * </p>
	 * 
	 * @throws Exception If thread is interrupted (mainly if list times out)
	 */
	public void doList() throws Exception {
		log.info("Starting new thread '" + ftpmt + "'.");
		
		if(ftpmt == null){
			usage("Worker thread must be initialized before calling doList()");
		}
		ftpmt = new FTPManifestThread(
			ftpmt.getServerName(),
			ftpmt.getServerPort(),
			ftpmt.getUsername(),
			ftpmt.getPassword(),
			ftpmt.getPassiveTransfer(),
			ftpmt.getStartDirectory(),
			ftpmt.getProcessedDirectories(),
			ftpmt.getDebug()
		);
		
		ftpmt.start();
		
		if (ftpmt.isAlive()) {
			// Thread has not finished
			Long    waitInterval = 5000l; // 5 seconds
			Boolean cont         = true;
			Long    threadStart  = (new Date()).getTime();
			while(cont){
				ftpmt.join(waitInterval);
				
				if(ftpmt.isAlive()){
					Long now      = (new Date()).getTime();
					Long timeDiff = now - threadStart;
					if(timeDiff > manifestTimeoutMillis){
						log.severe("Waited " + timeDiff + " for list to complete without success.  Terminating.");
						
						writeOutputFile();
						
						ftpmt.interrupt();
						throw new InterruptedException("Stopped manifest generation after " + timeDiff + " milliseconds.  Manifest most likely was partially but not fully complete.");
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
		
		if(ftpmt.getException() != null){
			log.severe("Thread threw exception '" + ftpmt.getException() + "'.");
			throw ftpmt.getException();
		}
		
		log.info("List complete.");
		
		writeOutputFile();
	}
	
	private void writeOutputFile() throws IOException {
		if(outputFile != null){
			Writer out = new BufferedWriter(
				new OutputStreamWriter(
					new FileOutputStream(outputFile), "UTF-8"
				)
			);
			
			try {
				Map<String, FTPManifestEntry> processedDirectories = ftpmt.getProcessedDirectories();
				for(String path : processedDirectories.keySet()){
					FTPManifestEntry entry = processedDirectories.get(path);
					if(entry.getType().equals(FTPManifestEntryType.FILE)){
						log.info("Entry: '" + path + "'.");
						out.write(path + "\n");
					}
				}
			}
			finally {
				out.flush();
				out.close();
			}
		}
	}
}
