package com.brightcove.commons.catalog.objects.enumerations;

import java.util.EnumSet;

/**
 * <p>The controller type for a rendition on a video.</p>
 * 
 * <p>This enumeration is defined primarily by the Media API documentation for
 *    a Rendition object:
 *    <a href="http://support.brightcove.com/en/docs/media-api-objects-reference#Rendition">http://support.brightcove.com/en/docs/media-api-objects-reference#Rendition</a>.
 * </p>
 * <p>This may be modified however from the Media API documentation so that it
 *    can also support other interfaces (e.g. batch provisioning).</p>
 * 
 * @author <a href="https://github.com/three4clavin">three4clavin</a>
 *
 */
public enum ControllerTypeEnum {
	AKAMAI_STREAMING,
	AKAMAI_SECURE_STREAMING,
	AKAMAI_LIVE,
	AKAMAI_HD,
	AKAMAI_HD_LIVE,
	LIMELIGHT_LIVE,
	LIMELIGHT_MEDIAVAULT,
	DEFAULT;
	
	public static ControllerTypeEnum lookupByName(String name){
		if(name == null){
			return null;
		}
		
		String upperName = name.toUpperCase();
		for(ControllerTypeEnum type : EnumSet.allOf(ControllerTypeEnum.class)){
			if(type.toString().toUpperCase().equals(upperName)){
				return type;
			}
		}
		
		return null;
	}
}
