package com.brightcove.commons.http;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;

/**
 * <p>
 *    A simple class to help working with HTTP responses
 * </p>
 * 
 * @author <a href="https://github.com/three4clavin">three4clavin</a>
 *
 */
public class HttpUtils {
	/**
	 * <p>
	 *    Parses an HTTP request into a into a plain String response
	 * </p>
	 * 
	 * @param entity HTTP entity to read from and parse
	 * @return String with the HTTP response contents
	 * @throws IOException If the entity can't be parsed
	 * @throws IllegalStateException If something went really wrong reading from the server
	 */
	public static String parseHttpEntity(HttpEntity entity) throws IOException, IllegalStateException {
		if(entity == null){
			return null;
		}
		
		String      output   = "";
		InputStream instream = entity.getContent();
		String      charSet  = "UTF-8";
		
		Header header = entity.getContentType();
		if(header != null){
			charSet = header.getValue();
			
			String charSetUpper = charSet.toUpperCase();
			int    charSetIdx   = charSetUpper.indexOf("CHARSET");
			
			if(charSetIdx < 0){
				charSet = "UTF-8";
			}
			else{
				charSet = charSet.substring(charSetIdx + "charset".length());
				charSet = charSet.split("=")[1].trim();
			}
		}
		
		output = IOUtils.toString(instream, charSet);
		
	    return output;
	}
}
