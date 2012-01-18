package com.brightcove.commons.ftp

import com.brightcove.commons.ftp.FTPUploader

public class test {
	static void main(def args) {
		def configFile  = new File("./config", "ftp-upload-config.xml")
		def ftpUploader = new FTPUploader(configFile)
		
		try{
			ftpUploader.doUpload()
		}
		catch(Exception e){
			println("Exception caught trying to upload: '" + e + "'.")
		}
	}
}
