package com.brightcove.commons.misc.logging;

import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogUtils {
	public static void main(String[] args){
		Logger logger = LogUtils.getLogger("foo");
		logger.info("foo");
	}
	
	public static Logger getLogger(String name){
		if(name == null){
			name = "[Anonymous Class]";
		}
		
		Logger logger = Logger.getLogger(name);
		logger.setUseParentHandlers(false);
		for(Handler handler : logger.getHandlers()){
			handler.setLevel(Level.OFF);
			logger.removeHandler(handler);
		}
		
		ConsoleHandler handler   = new ConsoleHandler();
		Formatter      formatter = new SimpleFormatter();
		
		handler.setFormatter(formatter);
		logger.addHandler(handler);
		
		return logger;
	}
}
