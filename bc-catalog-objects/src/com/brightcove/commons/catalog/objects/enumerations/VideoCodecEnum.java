package com.brightcove.commons.catalog.objects.enumerations;

import java.util.EnumSet;

/**
 * <p>The codec used for encoding a video.</p>
 * 
 * <p>This enumeration is defined primarily by the Media API documentation for
 *    a VideoCodecEnum object:
 *    <a href="http://docs.brightcove.com/en/media/#VideoCodecEnum">http://docs.brightcove.com/en/media/#VideoCodecEnum</a>.
 * </p>
 * <p>This may be modified however from the Media API documentation so that it
 *    can also support other interfaces (e.g. batch provisioning).</p>
 * 
 * @author <a href="https://github.com/three4clavin">three4clavin</a>
 *
 */
public enum VideoCodecEnum {
	NONE,
	SORENSON,
	ON2,
	H264;
	
	public static VideoCodecEnum lookupByName(String name){
		if(name == null){
			return null;
		}
		
		String upperName = name.toUpperCase();
		for(VideoCodecEnum codec : EnumSet.allOf(VideoCodecEnum.class)){
			if(codec.toString().toUpperCase().equals(upperName)){
				return codec;
			}
		}
		
		return null;
	}
}
