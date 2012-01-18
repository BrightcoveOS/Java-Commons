package com.brightcove.commons.system.commandLine;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * <p>
 *    Provides a shell to extend to create a command line driven application
 * </p>
 * 
 * @author <a href="https://github.com/three4clavin">three4clavin</a>
 *
 */
public class CommandLineProgram {
	List<Argument> normalArguments;
	List<Argument> singletonArguments;
	List<Argument> nakedArguments;
	
	Integer maxNakedArguments;
	Integer minNakedArguments;
	Integer numNakedArguments;
	
	String[] args;
	
	Object caller;
	
	Logger log;
	
	/**
	 * <p>
	 *    Kick off execution from command line
	 * </p>
	 * 
	 * @param args Arguments passed in on the command line
	 */
	public static void main(String[] args){
		CommandLineProgram clp = new CommandLineProgram();
		clp.run(args);
	}
	
	/**
	 * <p>
	 *    Default constructor
	 * </p>
	 */
	public CommandLineProgram(){
		init(null);
	}
	
	/**
	 * <p>
	 *    Constructor including a logger object
	 * </p>
	 * 
	 * @param log - Logger to send all messages to
	 */
	public CommandLineProgram(Logger log){
		init(log);
	}
	
	/**
	 * <p>
	 *    Performs all of the actual initialization tasks
	 * </p>
	 * 
	 * @param log Logger object to log all messages to
	 */
	private void init(Logger log){
		this.normalArguments    = new ArrayList<Argument>();
		this.singletonArguments = new ArrayList<Argument>();
		this.nakedArguments     = new ArrayList<Argument>();
		
		maxNakedArguments = Integer.MAX_VALUE;
		minNakedArguments = 0;
		numNakedArguments = 0;
		
		caller = null;
		
		if(log == null){
			this.log = Logger.getLogger(this.getClass().getCanonicalName());
		}
		else{
			this.log = log;
		}
	}
	
	/**
	 * <p>
	 *    Parses the command line arguments and kills program execution if
	 *    required arguments aren't provided.
	 * </p>
	 * 
	 * @param args Arguments passed in on the command line
	 */
	public void run(String[] args){
		parseArguments(args);
		
		System.out.println("Normal    arguments: " + normalArguments    + ".");
		System.out.println("Singleton arguments: " + singletonArguments + ".");
		System.out.println("Naked     arguments: " + nakedArguments     + ".");
	}
	
	/**
	 * <p>
	 *    Allows normal (--key value) argument on the command line.
	 * </p>
	 * 
	 * @param flag              Argument name expected on the command line
	 * @param headerDescription Brief usage header description
	 * @param bodyDescription   Detailed usage body description
	 * @param required          System will exit if required and not provided
	 */
	public void allowNormalArgument(String flag, String headerDescription, String bodyDescription, Boolean required){
		Argument cla = new Argument(ArgumentType.NORMAL, flag, headerDescription, bodyDescription, required);
		normalArguments.add(cla);
	}
	
	/**
	 * <p>
	 *    Allows singleton (-key) argument on the command line.
	 * </p>
	 * 
	 * @param flag              Argument name expected on the command line
	 * @param headerDescription Brief usage header description
	 * @param bodyDescription   Detailed usage body description
	 * @param required          System will exit if required and not provided
	 */
	public void allowSingletonArgument(String flag, String headerDescription, String bodyDescription, Boolean required){
		Argument cla = new Argument(ArgumentType.SINGLETON, flag, headerDescription, bodyDescription, required);
		singletonArguments.add(cla);
	}
	
	/**
	 * <p>
	 *    Sets the minimum number of naked arguments required on the command
	 *    line.
	 * </p>
	 * 
	 * @param minNakedArguments Minimum number of arguments allowed
	 */
	public void setMinNakedArguments(Integer minNakedArguments){
		this.minNakedArguments = minNakedArguments;
	}
	
	/**
	 * <p>
	 *    Sets the maximum number of naked arguments required on the command
	 *    line.
	 * </p>
	 * 
	 * @param maxNakedArguments Maximum number of arguments allowed
	 */
	public void setMaxNakedArguments(Integer maxNakedArguments){
		this.maxNakedArguments = maxNakedArguments;
	}
	
	/**
	 * <p>
	 *    Parses command line arguments and validates against required arguments
	 * </p>
	 * 
	 * @param args - Command line arguments
	 */
	public void parseArguments(String[] args){
		Integer idx = 0;
		
		while(idx < args.length){
			String arg = args[idx];
			if(arg.startsWith("--")){
				// Normal argument
				idx++;
				if(idx >= args.length){
					usage("No value provided for argument " + arg + ".");
				}
				
				String value = args[idx];
				if(value.startsWith("-")){
					usage("No value provided for argument " + arg + ".");
				}
				
				arg = arg.substring(2, arg.length());
				
				Boolean set = false;
				for(Argument cla : normalArguments){
					if(cla.getFlag().equals(arg)){
						cla.setValue(value);
						set = true;
					}
				}
				if(! set){
					usage("Unknown flag --" + arg + " specified on command line.");
				}
			}
			else if(arg.startsWith("-")){
				// Singleton argument
				arg = arg.substring(1, arg.length());
				
				Boolean set = false;
				for(Argument cla : singletonArguments){
					if(cla.getFlag().equals(arg)){
						cla.setValue("TRUE");
					}
				}
				if(! set){
					usage("Unknown flag -" + arg + " specified on command line.");
				}
			}
			else{
				numNakedArguments++;
				if(numNakedArguments > maxNakedArguments){
					usage("Exceeded maximum number (" + maxNakedArguments + ") of allowed naked arguments.");
				}
				
				Argument cla = new Argument(ArgumentType.NAKED, "auto-gen", "auto-gen argument", "Automatically generated argument to accomodate naked argument passed in on the command line", false);
				nakedArguments.add(cla);
			}
			
			idx++;
		}
		
		for(Argument cla : normalArguments){
			if(cla.getRequired()){
				if(cla.getValue() == null){
					usage("No value provided for argument --" + cla.getFlag() + ".");
				}
			}
		}
		
		for(Argument cla : singletonArguments){
			if(cla.getRequired()){
				if(cla.getValue() == null){
					usage("No value provided for argument -" + cla.getFlag() + ".");
				}
			}
		}
		
		if(numNakedArguments < minNakedArguments){
			usage("Too few naked arguments passed in on the command line - require at least " + minNakedArguments + ".");
		}
	}
	
	/**
	 * <p>
	 *    Prints a usage message to the log file and exits the program
	 * </p>
	 * 
	 * @param message Message to print to the log file
	 */
	public void usage(String message){
		usage(new Exception(message));
	}
	
	/**
	 * <p>
	 *    Prints an exception to the log file and exits the program
	 * </p>
	 * 
	 * @param exception Exception to print to the log file
	 */
	public void usage(Exception exception){
		String logMessage = "Usage: ";
		
		if(caller == null){
			logMessage += "java " + this.getClass().getCanonicalName() + "";
		}
		else{
			logMessage += "" + caller + "";
		}
		
		List<Argument> allowedArguments = new ArrayList<Argument>();
		for(Argument cla : normalArguments){    allowedArguments.add(cla); }
		for(Argument cla : singletonArguments){ allowedArguments.add(cla); }
		for(Argument cla : nakedArguments){     allowedArguments.add(cla); }
		
		for(Argument cla : allowedArguments){
			String  headerMessage = cla.getHeaderMessage();
			
			logMessage += " " + headerMessage;
		}
		logMessage += "\n";
		
		for(Argument cla : allowedArguments){
			String  bodyMessage   = cla.getBodyMessage();
			
			logMessage += "\t" + bodyMessage + "\n";
		}
		logMessage += "\n";
		
		logMessage += "Exception thrown: '" + exception + "':\n";
		for(StackTraceElement ste : exception.getStackTrace()){
			logMessage += "\t" + ste.toString() + "\n";
		}
		
		logMessage += "\n";
		
		log.severe(logMessage);
		System.exit(1);
	}
	
	/**
	 * <p>
	 *    Returns value for the named argument or null if not set on the
	 *    command line
	 * </p>
	 * 
	 * @param flag Name of argument to look up
	 * @return Value for argument on command line or null if not set
	 */
	public String getNormalArgument(String flag){
		for(Argument cla : normalArguments){
			if(cla.getFlag().equals(flag)){
				return cla.getValue();
			}
		}
		
		return null;
	}
	
	/**
	 * <p>
	 *    Returns true if the named argument is set on the command line or
	 *    false if not set on the command line
	 * </p>
	 * 
	 * @param flag Name of argument to look up
	 * @return True if the argument is set on the command line
	 */
	public Boolean getSingletonArgument(String flag){
		for(Argument cla : singletonArguments){
			if(cla.getFlag().equals(flag)){
				if("TRUE".equalsIgnoreCase(cla.getValue())){
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * <p>
	 *    Gets the nth un-flagged argument set on the command line, or null
	 *    if n is <0 or >number of arguments set on the command line
	 * </p>
	 * 
	 * @param index Index of naked argument to look up
	 * @return Argument specified on command line
	 */
	public String getNakedArgument(Integer index){
		if((index == null) || (index < 0) || (index >= nakedArguments.size())){
			return null;
		}
		Argument cla = nakedArguments.get(index);
		
		if(cla == null){
			return null;
		}
		
		return cla.getValue();
	}
	
	/**
	 * <p>
	 *    Gets all of the un-flagged arguments set on the command line
	 * </p>
	 * 
	 * @return Arguments specified on command line
	 */
	public List<String> getNakedArguments(){
		List<String> ret = new ArrayList<String>();
		
		for(Argument cla : nakedArguments){
			String val = cla.getValue();
			ret.add(val);
		}
		
		return ret;
	}
	
	/**
	 * <p>
	 *    Returns the logger object this object is using
	 * </p>
	 * 
	 * @return Logger object
	 */
	public Logger getLogger(){
		return this.log;
	}
	
	/**
	 * <p>
	 *    Sets the identification of the calling class/script/etc.  This is
	 *    really only used for usage messages.
	 * </p>
	 * 
	 * @param caller String identifying caller
	 */
	public void setCaller(String caller){
		this.caller = caller;
	}
}
