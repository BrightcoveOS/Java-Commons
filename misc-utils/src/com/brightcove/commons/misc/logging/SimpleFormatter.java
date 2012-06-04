package com.brightcove.commons.misc.logging;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.LogRecord;

public class SimpleFormatter extends java.util.logging.Formatter {
	public String format(LogRecord log) {
		Date date = new Date();
		date.setTime(log.getMillis());
		
		SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z zzzz");
		String dateString = sdf.format(date);
		
		String level = log.getLevel().getName();
		String logmessage = "{"+level+"} {" + log.getLoggerName() + "} {"+dateString+"} " + log.getMessage() + "\n";
		
		Throwable thrown = log.getThrown();
		if (thrown != null) {
			logmessage = logmessage + "\n(Thrown " + thrown.toString() + ")";
		}
		return logmessage;
	}
}
