package com.brightcove.commons.system.commandLine;

/**
 * <p>
 *    Defines a command line argument.
 * </p>
 * 
 * @author <a href="https://github.com/three4clavin">three4clavin</a>
 *
 */
public class Argument {
	ArgumentType type;
	String       flag;
	String       headerMessage;
	String       bodyMessage;
	Boolean      required;
	String       value;
	
	public static final Boolean REQUIRED_ARGUMENT   = true;
	public static final Boolean UNREQUIRED_ARGUMENT = false;
	
	/**
	 * <p>
	 *    No default constructor - all values must be passed in, even if they
	 *    are null.
	 * </p>
	 * 
	 * @param type Type of argument - either NORMAL (key=value pair like --file foo.txt), SINGLETON (just a key flag like -help) or NAKED (just an unflagged parameter like file.txt)
	 * @param flag For NORMAL and SINGLETON types only - the flag used to set the argument (e.g. for "--file foo.txt", the flag is "file") 
	 * @param headerMessage The brief description of how to set the argument in the header (e.g. "--file <path to file>")
	 * @param bodyMessage The detailed description of what the argument is used for (e.g. "--file - sets the file this program will read in")
	 * @param required Whether the parameter is required or not
	 */
	public Argument(ArgumentType type, String flag, String headerMessage, String bodyMessage, Boolean required){
		this.type          = type;
		this.flag          = flag;
		this.headerMessage = headerMessage;
		this.bodyMessage   = bodyMessage;
		this.required      = required;
		
		value = null;
	}
	
	/**
	 * <p>
	 *    Gets the type of the CommandLineArgument - Either Normal, Singleton or Naked
	 * </p>
	 * 
	 * @return Type of this argument
	 */
	public ArgumentType getType(){
		return type;
	}
	
	/**
	 * <p>
	 *    Sets the type of the CommandLineArgument
	 * </p>
	 * 
	 * @param type Type of this argumet
	 */
	public void setType(ArgumentType type){
		this.type = type;
	}
	
	/**
	 * <p>
	 *    Gets the flag that sets the argument value on the command line
	 * </p>
	 * 
	 * @return Flag to set argument value
	 */
	public String getFlag(){
		return flag;
	}
	
	/**
	 * <p>
	 *    Sets the flag that sets the argument on the command line
	 * </p>
	 * 
	 * @param flag Flag to set argument value
	 */
	public void setFlag(String flag){
		this.flag = flag;
	}
	
	/**
	 * <p>
	 *    Gets the message that is displayed on the header of the usage
	 *    message
	 * </p>
	 * 
	 * @return Message displayed in the header of the usage message for this argument
	 */
	public String getHeaderMessage(){
		return headerMessage;
	}
	
	/**
	 * <p>
	 *    Sets the message that is displayed on the header of the usage
	 *    message
	 * </p>
	 * 
	 * @param headerMessage Message displayed in header of the usage message for this argument
	 */
	public void setHeaderMessage(String headerMessage){
		this.headerMessage = headerMessage;
	}
	
	/**
	 * <p>
	 *    Gets the message that is displayed in the body of the usage message
	 * </p>
	 * 
	 * @return Message displayed in the body of the usage message for this argument
	 */
	public String getBodyMessage(){
		return bodyMessage;
	}
	
	/**
	 * <p>
	 *    Sets the message that is displayed in the body of the usage message
	 * </p>
	 * 
	 * @param bodyMessage Message displayed in the body of the usage message for this argument
	 */
	public void setBodyMessage(String bodyMessage){
		this.bodyMessage = bodyMessage;
	}
	
	/**
	 * <p>
	 *    Gets whether or not the argument is required
	 * </p>
	 * 
	 * @return True if argument is required on the command line
	 */
	public Boolean getRequired(){
		return required;
	}
	
	/**
	 * <p>
	 *    Sets whether or not the argument is required
	 * </p>
	 * 
	 * @param required True if argument is required on the command line
	 */
	public void setRequired(Boolean required){
		this.required = required;
	}
	
	/**
	 * <p>
	 *    Gets the value set on the command line
	 * </p>
	 * 
	 * @return Value set on the command line
	 */
	public String getValue(){
		return value;
	}
	
	/**
	 * <p>
	 *    Sets the value on the command line
	 * </p>
	 * 
	 * @param value Value set on the command line
	 */
	public void setValue(String value){
		this.value = value;
	}
}
