package com.brightcove.commons.ftp;

import java.io.IOException;
import java.util.logging.Logger;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPConnectionClosedException;

public class FTPThread extends Thread {
	Logger           log;
	String           serverName;
	Integer          serverPort;
	String           username;
	String           password;
	Boolean          skipTransfer;
	Boolean          removeSource;
	Boolean          passiveTransfer;
	Boolean          debug;
	Exception        exception;
	
	FTPClient ftpc;
	
	public FTPThread() {
		log = Logger.getLogger(this.getClass().getCanonicalName());
		
		this.serverName      = null;
		this.serverPort      = 21;
		this.username        = null;
		this.password        = null;
		this.skipTransfer    = true;
		this.removeSource    = false;
		this.passiveTransfer = false;
		this.debug           = true;
		
		exception = null;
		
		ftpc = new FTPClient();
	}
	
	public FTPThread(String serverName, String username, String password, Boolean skipTransfer, Boolean removeSource, Boolean passiveTransfer, Boolean debug) {
		log = Logger.getLogger(this.getClass().getCanonicalName());
		
		this.serverName      = serverName;
		this.serverPort      = 21;
		this.username        = username;
		this.password        = password;
		this.skipTransfer    = skipTransfer;
		this.removeSource    = removeSource;
		this.passiveTransfer = passiveTransfer;
		this.debug           = debug;
		
		exception = null;
		
		ftpc = new FTPClient();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		connect();
		disconnect();
	}
	
	public Boolean connect() {
		ftpc = new FTPClient();
		
		try{
			ftpc.connect(serverName, serverPort);
			printFTPCommandInfo("connect");
			
			ftpc.login(username, password);
			printFTPCommandInfo("login");
			
			ftpc.setFileType(FTP.BINARY_FILE_TYPE);
			printFTPCommandInfo("set file type");
			
			if(passiveTransfer){
				ftpc.enterLocalPassiveMode();
				printFTPCommandInfo("enter passive mode");
			}
			else{
				ftpc.enterLocalActiveMode();
				printFTPCommandInfo("enter active mode");
			}
			
			return true;
		}
		catch(Exception e){
			exception = e;
		}
		
		return false;
	}
	
	public void disconnect() {
		log.info("Disconnecting...");
		
		try{
			ftpc.logout();
			printFTPCommandInfo("logout");
		}
		catch(FTPConnectionClosedException ftpcce){
			// Don't worry about it - connection was already closed.
			if(debug){
				log.warning("Tried to logout, but connection was already closed (" + ftpcce + ").");
				printFTPCommandInfo("logout exception caught");
			}
		}
		catch (IOException ioe) {
			// Don't worry about it...  not sure what happened, but the connection is probably closed
			if(debug){
				log.warning("Tried to logout, but connection was already closed (" + ioe + ").");
				printFTPCommandInfo("logout exception caught");
			}
		}
		finally {
			try{
				ftpc.disconnect();
				printFTPCommandInfo("disconnect");
			}
			catch(IOException ioe){
				if(debug){
					log.warning("Caught exception trying to disconnect: " + ioe + ".");
					printFTPCommandInfo("disconnect exception caught");
				}
			}
		}
	}
	
	/**
	 * <p>
	 *    Sets the last exception to null
	 * </p>
	 */
	public void resetException(){
		exception = null;
	}
	
	/**
	 * <p>
	 *    Utility method to print more detailed information about an FTP command
	 * </p>
	 * 
	 * @param command Command to issue to the server
	 */
	public void printFTPCommandInfo(String command) {
		if(debug){
			log.info("Status from issuing command \"" + command + "\":");
			log.info("    Response code:   \"" + ftpc.getReplyCode() + "\".");
			log.info("    Response string: \"" + ftpc.getReplyString() + "\".");
		}
	}
	
	public Logger    getLog()             { return log;             }
	public String    getServerName()      { return serverName;      }
	public Integer   getServerPort()      { return serverPort;      }
	public String    getUsername()        { return username;        }
	public String    getPassword()        { return password;        }
	public Boolean   getSkipTransfer()    { return skipTransfer;    }
	public Boolean   getRemoveSource()    { return removeSource;    }
	public Boolean   getPassiveTransfer() { return passiveTransfer; }
	public Boolean   getDebug()           { return debug;           }
	public Exception getException()       { return exception;       }
	public FTPClient getFtpClient()       { return ftpc;            }
	
	public void setLog(Logger log)                          { this.log             = log;             }
	public void setServerName(String serverName)            { this.serverName      = serverName;      }
	public void setServerPort(Integer serverPort)           { this.serverPort      = serverPort;      }
	public void setUsername(String username)                { this.username        = username;        }
	public void setPassword(String password)                { this.password        = password;        }
	public void setSkipTransfer(Boolean skipTransfer)       { this.skipTransfer    = skipTransfer;    }
	public void setRemoveSource(Boolean removeSource)       { this.removeSource    = removeSource;    }
	public void setPassiveTransfer(Boolean passiveTransfer) { this.passiveTransfer = passiveTransfer; }
	public void setDebug(Boolean debug)                     { this.debug           = debug;           }
	public void setException(Exception exception)           { this.exception       = exception;       }
	public void setFtpClient(FTPClient ftpc)                { this.ftpc            = ftpc;            }
}
