package com.brightcove.commons.system;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * <p>
 *    Utility class to interact with the system (e.g. execute command line
 *    calls)
 * </p>
 * 
 * @author <a href="https://github.com/three4clavin">three4clavin</a>
 * 
 */
class SystemUtils {
	
	public static final Boolean VERBOSE = true;
	public static final Boolean QUIET   = false;
	
	Logger log;
	
	/**
	 * <p>
	 *    Default constructor
	 * </p>
	 */
	public SystemUtils(){
		init(null);
	}
	
	/**
	 * <p>
	 *    Constructor including a logger object
	 * </p>
	 * 
	 * @param log - Logger to send all messages to
	 */
	public SystemUtils(Logger log){
		init(log);
	}
	
	private void init(Logger log){
		if(log == null){
			log = Logger.getLogger(this.getClass().getCanonicalName());
		}
		this.log = log;
	}
	
	/**
	 * <p>
	 *    Fires off static call to sysExec using internal Logger object
	 * </p>
	 * 
	 * @param cmd Command to execute
	 * @param options Command line options to pass to command
	 * @param verbose Prints all output from command if set to true
	 * @return ExitReturn object with output and exit code from command
	 */
	public ExecReturn sysExec(String cmd, List<String> options, boolean verbose) {
		return SystemUtils.sysExec(cmd, options, verbose, log);
	}
	
	/**
	 * <p>
	 *    Executes the specified command with options, and optionally prints
	 *    any output from the command to stdout/stderr
	 * </p>
	 * 
	 * @param cmd Command to execute
	 * @param options Command line options to pass to command
	 * @param verbose Prints all output from command if set to true
	 * @param log Logger object to print any messages to
	 * @return ExitReturn object with output and exit code from command
	 */
	public static ExecReturn sysExec(String cmd, List<String> options, boolean verbose, Logger log) {
		List<String> exec = new ArrayList<String>();
		exec.add(cmd);
		if(options != null){
			for(String option : options){
				exec.add(option);
			}
		}
		
		Runtime rt = Runtime.getRuntime();
		
		if(verbose){
			log.info("[sysExec] > " + exec);
		}
		try{
			String[]     execString = (String[])exec.toArray(new String[exec.size()]);
			Process      proc       = rt.exec(execString);
			StreamReader error      = new StreamReader(proc.getErrorStream(), "[ERR]");
			StreamReader output     = new StreamReader(proc.getInputStream(), "[INF]");
			
			error.start();
			output.start();
			
			int exitVal = proc.waitFor();
			while(! error.isFinished()){ try { Thread.sleep(1000); } catch(Exception e) {} }
			while(! output.isFinished()){ try { Thread.sleep(1000); } catch(Exception e) {} }
			
			ExecReturn execReturn = new ExecReturn(output.getRawBuffer(), error.getRawBuffer(), exitVal);
			
			if(verbose){
				log.info("[sysExec] " + execReturn.getOutputStream());
				log.warning("[sysExec] " + execReturn.getErrorStream());
				log.info("[sysExec] ExitValue: " + execReturn.getExitCode());
			}
			
			return execReturn;
		}
		catch(IOException ioe){
			log.warning("Exception caught: " + ioe);
		}
		catch(InterruptedException ie){
			log.warning("Exception caught: " + ie);
		}
		
		return new ExecReturn();
	}
}
