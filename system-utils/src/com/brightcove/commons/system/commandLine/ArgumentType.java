package com.brightcove.commons.system.commandLine;

/**
 * <p>
 *    Defines the possible types of command line arguments we have
 * </p>
 * <p>
 *    <ul>
 *        <li>NORMAL    - (--key value pair)         e.g. --dir /usr/local</li>
 *        <li>SINGLETON - (-key only flag)           e.g. -help</li>
 *        <li>NAKED     - (No key, just naked value) e.g. file.txt</li>
 *    </ul>
 * </p>
 * 
 * @author <a href="https://github.com/three4clavin">three4clavin</a>
 *
 */
public enum ArgumentType {
	NORMAL, SINGLETON, NAKED
}
