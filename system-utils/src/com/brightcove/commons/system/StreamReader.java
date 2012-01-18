package com.brightcove.commons.system;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * <p>
 *    Utility class to consume output from system calls properly (so they
 *    don't block waiting to read/write)
 * </p>
 *
 * @author <a href="https://github.com/three4clavin">three4clavin</a>
 *
 */
class StreamReader extends Thread {
	InputStream is;
	String      type;
	String      buffer;
	String      rawBuffer;
	Boolean     finished;
	
	/**
	 * <p>
	 *    Constructor - Provide an InputStream to consume, and a "type" string
	 *    to print before each output line (to distinguish STDERR from STDOUT
	 *    for example)
	 * </p>
	 * 
	 * @param is InputStream to consume
	 * @param type Type string to print before each output line
	 */
	public StreamReader(InputStream is, String type) {
		this.is        = is;
		this.type      = type;
		this.buffer    = "";
		this.rawBuffer = "";
		this.finished  = false;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		finished = false;
		
		try {
			InputStreamReader isr  = new InputStreamReader(is);
			BufferedReader    br   = new BufferedReader(isr);
			String            line = br.readLine();
			while (line != null){
				buffer    += type + " > " + line + "\n";
				rawBuffer += line + "\n";
				line = br.readLine();
			}
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
		finished = true;
	}
	
	/**
	 * <p>
	 *    Determines if this stream is finished reading or not.
	 * </p>
	 * 
	 * @return
	 */
	public Boolean isFinished(){
		return finished;
	}
	
	/**
	 * <p>
	 *    Gets the consumed output written into the internal buffer
	 * </p>
	 * 
	 * @return Buffer String
	 */
	public String getBuffer(){
		return buffer;
	}
	
	/**
	 * <p>
	 *    Gets the consumed output written into the internal buffer, without
	 *    any adornment
	 * </p>
	 * 
	 * @return Buffer String
	 */
	public String getRawBuffer(){
		return rawBuffer;
	}
}
