package com.brightcove.commons.misc.logging;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.LogRecord;

public class SimpleFormatter extends java.util.logging.Formatter {
	private Map<String, String> redactStrings;
	
	public SimpleFormatter(){
		super();
		redactStrings = new HashMap<String, String>();
	}
	
	public SimpleFormatter(Map<String, String> redactStrings){
		super();
		this.redactStrings = redactStrings;
	}
	
	public String format(LogRecord log) {
		Date date = new Date();
		date.setTime(log.getMillis());
		
		SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z zzzz");
		String dateString = sdf.format(date);
		
		String level   = log.getLevel().getName();
		String message = log.getMessage();
		
		for(String redactString : redactStrings.keySet()){
			String  replaceWith = redactStrings.get(redactString);
			Integer index       = message.indexOf(redactString);
			while(index >= 0){
				message = message.substring(0, index) + replaceWith + message.substring(index + replaceWith.length());
				index = message.indexOf(redactString);
			}
		}
		
		String logmessage = "{"+level+"} {" + log.getLoggerName() + "} {"+dateString+"} " + message + "\n";
		
		Throwable thrown = log.getThrown();
		if (thrown != null) {
			logmessage = logmessage + "\n(Thrown " + thrown.toString() + ")";
		}
		return logmessage;
	}
	
	public void addRedactString(String string){
		StringBuffer b = new StringBuffer();
		for(int i=0;i<string.length();i++){
			b.append("*");
		}
		String replaceWith = b.toString();
		
		redactStrings.put(string, replaceWith);
	}
	
	public Map<String, String> getRedactStrings(){
		return redactStrings;
	}
	
	public void setRedactStrings(Map<String, String> redactStrings){
		this.redactStrings = redactStrings;
	}
}
