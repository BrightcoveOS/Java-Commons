package com.brightcove.commons.system;

/**
 * <p>
 *    Encapsulates return data from a system exec call
 * </p>
 * 
 * @author <a href="https://github.com/three4clavin">three4clavin</a>
 *
 */
class ExecReturn {
	String  outputStream;
	String  errorStream;
	Integer exitCode;
	
	/**
	 * <p>
	 *    Default constructor
	 * </p>
	 */
	public ExecReturn() {
		this.outputStream = null;
		this.errorStream  = null;
		this.exitCode     = null;
	}
	
	/**
	 * <p>
	 *    Full constructor
	 * </p>
	 * 
	 * @param outputStream - STDOUT from exec call
	 * @param errorStream - STDERR from exec call
	 * @param exitCode - Return code from exec call
	 */
	public ExecReturn(String outputStream, String errorStream, Integer exitCode){
		this.outputStream = outputStream;
		this.errorStream  = errorStream;
		this.exitCode     = exitCode;
	}
	
	/**
	 * <p>
	 *    Sets the STDOUT return from an exec call
	 * </p>
	 * 
	 * @param outputStream STDOUT output
	 */
	public void setOutputStream(String outputStream){
		this.outputStream = outputStream;
	}
	
	/**
	 * <p>
	 *    Gets the STDOUT return from an exec call
	 * </p>
	 * 
	 * @return STDOUT output
	 */
	public String getOutputStream(){
		return outputStream;
	}
	
	/**
	 * <p>
	 *    Sets the STDERR return from an exec call
	 * </p>
	 * 
	 * @param errorStream STDERR output
	 */
	public void setErrorStream(String errorStream){
		this.errorStream = errorStream;
	}
	
	/**
	 * <p>
	 *    Gets the STDERR return from an exec call
	 * </p>
	 * 
	 * @return STDERR output
	 */
	public String getErrorStream(){
		return errorStream;
	}
	
	/**
	 * <p>
	 *    Sets the exit code return from an exec call
	 * </p>
	 * 
	 * @param exitCode return value from exec call
	 */
	public void setExitCode(Integer exitCode){
		this.exitCode = exitCode;
	}
	
	/**
	 * <p>Gets the exit code return from an exec call</p>
	 * 
	 * @return return value from exec call
	 */
	public Integer getExitCode(){
		return exitCode;
	}
}
