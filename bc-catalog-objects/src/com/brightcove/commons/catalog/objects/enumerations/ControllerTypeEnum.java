package com.brightcove.commons.catalog.objects.enumerations;

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
	LIMELIGHT_LIVE, AKAMAI_LIVE, DEFAULT
}
