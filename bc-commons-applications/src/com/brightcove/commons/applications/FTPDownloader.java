package com.brightcove.commons.applications;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

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
	private FTPDownloaderThread ftpdt               = null;
	private File                configFile          = null;
	private Long                timeoutMilliseconds = null;
	private Integer             maxRetries          = null;
	
	private String remoteDirectory = null;
	private String remoteFile      = null;
	private String localDirectory  = null;
	private String localFile       = null;
	
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
		
		ftpd.allowNormalArgument("config-file",          "--config-file <path>",          "--config-file:          Path to configuration file",                                  false);
		ftpd.allowNormalArgument("max-retries",          "--max-retries <integer>",       "--max-retries:          Number of times to retry a download that times out",          false);
		ftpd.allowNormalArgument("timeout-milliseconds", "--timeout-milliseconds <long>", "--timeout-milliseconds: Number of milliseconds to wait before timing out a download", false);
		ftpd.allowNormalArgument("server-name",          "--server-name <string>",        "--server-name:          Server address to connect to",                                false);
		ftpd.allowNormalArgument("server-port",          "--server-port <integer>",       "--server-port:          Server port to connect to",                                   false);
		ftpd.allowNormalArgument("username",             "--username <string>",           "--username:             Username to connect with",                                    false);
		ftpd.allowNormalArgument("password",             "--password <string>",           "--password:             Password to connect with",                                    false);
		ftpd.allowNormalArgument("skip-transfer",        "--skip-transfer <boolean>",     "--skip-transfer:        Skip actual transfer (test settings)",                        false);
		ftpd.allowNormalArgument("remove-source",        "--remove-source <boolean>",     "--remove-source:        Remove remote file after download",                           false);
		ftpd.allowNormalArgument("passive-transfer",     "--passive-transfer <boolean>",  "--passive-transfer:     Passive or active connection mode",                           false);
		ftpd.allowNormalArgument("debug",                "--debug <boolean>",             "--debug:                Verbose connection output",                                   false);
		ftpd.allowNormalArgument("remote-directory",     "--remote-directory <string>",   "--remote-directory:     Directory to download from on remote server",                 false);
		ftpd.allowNormalArgument("remote-file",          "--remote-file <string>",        "--remote-file:          File to download from remote server",                         false);
		ftpd.allowNormalArgument("local-directory",      "--local-directory <string>",    "--local-directory:      Local directory to download to",                              false);
		ftpd.allowNormalArgument("local-file",           "--local-file <string>",         "--local-file:           Local file to download to (ignores --local-directory)",       false);
		
		ftpd.setMaxNakedArguments(0);
		ftpd.setMinNakedArguments(0);
		
		ftpd.parseArguments(args);
		
		if(ftpd.getNormalArgument("config-file") != null){
			ftpd.setConfigFile(new File(ftpd.getNormalArgument("config-file")));
			try {
				ftpd.parseConfigFile(ftpd.getConfigFile());
			}
			catch (Exception e) {
				ftpd.usage(e);
			}
		}
		
		if(ftpd.getNormalArgument("server-name") != null){
			ftpd.getFtpDownloaderThread().setServerName(ftpd.getNormalArgument("server-name"));
		}
		if(ftpd.getNormalArgument("server-port") != null){
			ftpd.getFtpDownloaderThread().setServerPort(Integer.parseInt(ftpd.getNormalArgument("server-port")));
		}
		if(ftpd.getNormalArgument("username") != null){
			ftpd.getFtpDownloaderThread().setUsername(ftpd.getNormalArgument("username"));
		}
		if(ftpd.getNormalArgument("password") != null){
			ftpd.getFtpDownloaderThread().setPassword(ftpd.getNormalArgument("password"));
		}
		if(ftpd.getNormalArgument("skip-transfer") != null){
			ftpd.getFtpDownloaderThread().setSkipTransfer(Boolean.parseBoolean(ftpd.getNormalArgument("skip-transfer")));
		}
		if(ftpd.getNormalArgument("remove-source") != null){
			ftpd.getFtpDownloaderThread().setRemoveSource(Boolean.parseBoolean(ftpd.getNormalArgument("remove-source")));
		}
		if(ftpd.getNormalArgument("passive-transfer") != null){
			ftpd.getFtpDownloaderThread().setPassiveTransfer(Boolean.parseBoolean(ftpd.getNormalArgument("passive-transfer")));
		}
		if(ftpd.getNormalArgument("debug") != null){
			ftpd.getFtpDownloaderThread().setDebug(Boolean.parseBoolean(ftpd.getNormalArgument("debug")));
		}
		
		ftpd.calculateDownloadMappings(
			ftpd.getNormalArgument("remote-directory"),
			ftpd.getNormalArgument("remote-file"),
			ftpd.getNormalArgument("local-directory"),
			ftpd.getNormalArgument("local-file")
		);
		
		if(ftpd.getNormalArgument("max-retries") != null){
			ftpd.setMaxRetries(Integer.parseInt(ftpd.getNormalArgument("max-retries")));
		}
		if(ftpd.getNormalArgument("timeout-milliseconds") != null){
			ftpd.setTimeoutMilliseconds(Long.parseLong(ftpd.getNormalArgument("timeout-milliseconds")));
		}
		
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
		
		getFtpDownloaderThread().setServerName(serverName);
		getFtpDownloaderThread().setServerPort(21);
		getFtpDownloaderThread().setUsername(username);
		getFtpDownloaderThread().setPassword(password);
		getFtpDownloaderThread().setSkipTransfer(skipTransfer);
		getFtpDownloaderThread().setRemoveSource(removeSource);
		getFtpDownloaderThread().setPassiveTransfer(passiveTransfer);
		getFtpDownloaderThread().setDownloadMappings(new ArrayList<DownloadMapping>());
		getFtpDownloaderThread().setDebug(debug);
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
		
		getFtpDownloaderThread().setServerName(serverName);
		getFtpDownloaderThread().setServerPort(serverPort);
		getFtpDownloaderThread().setUsername(username);
		getFtpDownloaderThread().setPassword(password);
		getFtpDownloaderThread().setSkipTransfer(skipTransfer);
		getFtpDownloaderThread().setRemoveSource(removeSource);
		getFtpDownloaderThread().setPassiveTransfer(passiveTransfer);
		getFtpDownloaderThread().setDownloadMappings(new ArrayList<DownloadMapping>());
		getFtpDownloaderThread().setDebug(debug);
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
		
		calculateDownloadMappings(remoteDirectory, remoteFile, localDirectory, localFile);
	}
	
	private void parseConfigFile(File configFile) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		Document configDoc = XalanUtils.parseXml(configFile, false);
		
		setTimeoutMilliseconds(getLongSetting(configDoc, "FTP_DOWNLOAD_TIMEOUT_MILLISECONDS"));
		
		String  serverName = getStringSetting(configDoc, "FTP_DOWNLOAD_SERVER");
		Integer serverPort = getIntegerSetting(configDoc, "FTP_DOWNLOAD_PORT");
		String  username   = getStringSetting(configDoc, "FTP_DOWNLOAD_USER");
		String  password   = getStringSetting(configDoc, "FTP_DOWNLOAD_PASSWORD");
		
		if(serverPort == null){
			serverPort = 21;
		}
		
		getFtpDownloaderThread().setServerName(serverName);
		getFtpDownloaderThread().setServerPort(serverPort);
		getFtpDownloaderThread().setUsername(username);
		getFtpDownloaderThread().setPassword(password);
		
		Boolean skipTransfer    = getBooleanSetting(configDoc, "FTP_DOWNLOAD_SKIP");
		Boolean removeSource    = getBooleanSetting(configDoc, "FTP_DOWNLOAD_REMOVE_SOURCE");
		Boolean passiveTransfer = getBooleanSetting(configDoc, "FTP_DOWNLOAD_USE_PASSIVE_TRANSFER");
		Boolean debug           = getBooleanSetting(configDoc, "FTP_DOWNLOAD_DEBUG");
		
		if(skipTransfer    == null){ skipTransfer    = false; }
		if(removeSource    == null){ removeSource    = false; }
		if(passiveTransfer == null){ passiveTransfer = false; }
		if(debug           == null){ debug           = false; }
		
		getFtpDownloaderThread().setSkipTransfer(skipTransfer);
		getFtpDownloaderThread().setRemoveSource(removeSource);
		getFtpDownloaderThread().setPassiveTransfer(passiveTransfer);
		getFtpDownloaderThread().setDebug(debug);
		
		getFtpDownloaderThread().setDownloadMappings(new ArrayList<DownloadMapping>());
		
		String downloadDirectory = getStringSetting(configDoc, "FTP_DOWNLOAD_REMOTE_DIRECTORY");
		String downloadFile      = getStringSetting(configDoc, "FTP_DOWNLOAD_REMOTE_FILE");
		String localDirectory    = getStringSetting(configDoc, "FTP_DOWNLOAD_LOCAL_DIRECTORY");
		
		setRemoteDirectory(downloadDirectory);
		setRemoteFile(downloadFile);
		setLocalDirectory(localDirectory);
		setLocalFile(null);
	}
	
	public void calculateDownloadMappings(String remoteDirectory, String remoteFile, String localDirectory, String localFile){
		if(remoteDirectory != null){
			this.remoteDirectory = remoteDirectory;
		}
		if(remoteFile != null){
			this.remoteFile = remoteFile;
		}
		if(localDirectory != null){
			this.localDirectory = localDirectory;
		}
		if(localFile != null){
			this.localFile = localFile;
		}
		
		if(this.remoteDirectory == null){
			this.remoteDirectory = "/";
		}
		if(! this.remoteDirectory.endsWith("/")){
			this.remoteDirectory += "/";
		}
		String remotePath = this.remoteDirectory + this.remoteFile;
		
		if(this.localFile == null){
			this.localFile = this.remoteFile;
		}
		String localPath = this.localFile;
		
		if(this.localDirectory != null){
			if(! this.localDirectory.endsWith("/")){
				this.localDirectory += "/";
			}
			localPath = this.localDirectory + this.localFile;
		}
		
		if(localPath != null){
			ftpdt.addDownloadMapping(remotePath, new File(localPath));
		}
	}
	
	//private void addDownload(String remoteDirectory, String remoteFile, File localFile){
	//	String remotePath = remoteDirectory;
	//	if(remotePath == null){
	//		remotePath = "/";
	//	}
	//	if(! remotePath.endsWith("/")){
	//		remotePath += "/";
	//	}
	//	remotePath += remoteFile;
	//	
	//	if(localFile.isDirectory()){
	//		File f = new File(localFile, remoteFile);
	//		localFile = f;
	//	}
	//	
	//	ftpdt.addDownloadMapping(remotePath, localFile);
	//}
	
	/* (non-Javadoc)
	 * @see com.brightcove.commons.system.commandLine.CommandLineProgram#run(java.lang.String[])
	 */
	public void run(String[] args){
		setCaller(this.getClass().getCanonicalName());
		
		if(maxRetries == null){
			maxRetries = 0;
		}
		
		Integer   attempt       = 0;
		Exception lastException = null;
		while(attempt <= maxRetries){
			try {
				doDownload();
				this.getLogger().info("Download complete.");
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
		setTimeoutMilliseconds(0l);
		
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
	 *    Performs the download.  Actual download is done by a background
	 *    thread, so it can be killed if it runs for too long.
	 * </p>
	 * 
	 * @throws Exception If thread is interrupted (mainly if download times out)
	 */
	public void doDownload() throws Exception {
		this.getLogger().info("Starting new thread '" + ftpdt + "'.");
		ftpdt.start();
		
		if(timeoutMilliseconds == null){
			timeoutMilliseconds = 1000l * 60l * 60l * 24l; // 1 day
		}
		
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
					if(timeDiff > timeoutMilliseconds){
						this.getLogger().severe("Waited " + timeDiff + " for download to complete without success.  Terminating.");
						
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
		
		this.getLogger().info("Thread completed.  Checking for exceptions.");
		
		if(ftpdt.getException() != null){
			this.getLogger().severe("Thread threw exception '" + ftpdt.getException() + "'.");
			throw ftpdt.getException();
		}
		
		this.getLogger().info("Upload complete.");
	}
	
	public void setFtpDownloaderThread(FTPDownloaderThread ftpdt){
		this.ftpdt = ftpdt;
	}
	
	public FTPDownloaderThread getFtpDownloaderThread(){
		return ftpdt;
	}
	
	public void setConfigFile(File configFile){
		this.configFile = configFile;
	}
	
	public File getConfigFile(){
		return configFile;
	}
	
	public void setTimeoutMilliseconds(Long timeoutMilliseconds){
		this.timeoutMilliseconds = timeoutMilliseconds;
	}
	
	public Long getTimeoutMilliseconds(){
		return timeoutMilliseconds;
	}
	
	public void setMaxRetries(Integer maxRetries){
		this.maxRetries = maxRetries;
	}
	
	public Integer getMaxRetries(){
		return maxRetries;
	}
	
	public String getRemoteDirectory(){
		return remoteDirectory;
	}
	
	public void setRemoteDirectory(String remoteDirectory){
		this.remoteDirectory = remoteDirectory;
	}
	
	public String getRemoteFile(){
		return remoteFile;
	}
	
	public void setRemoteFile(String remoteFile){
		this.remoteFile = remoteFile;
	}
	
	public String getLocalDirectory(){
		return localDirectory;
	}
	
	public void setLocalDirectory(String localDirectory){
		this.localDirectory = localDirectory;
	}
	
	public String getLocalFile(){
		return localFile;
	}
	
	public void setLocalFile(String localFile){
		this.localFile = localFile;
	}
}
