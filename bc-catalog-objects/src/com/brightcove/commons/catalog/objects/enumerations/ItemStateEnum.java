package com.brightcove.commons.catalog.objects.enumerations;

/**
 * <p>The state a video is in.</p>
 * 
 * <p>This enumeration is defined primarily by the Media API documentation for
 *    an ItemStateEnum object:
 *    <a href="http://docs.brightcove.com/en/media/#ItemStateEnum">http://docs.brightcove.com/en/media/#ItemStateEnum</a>.
 * </p>
 * <p>This may be modified however from the Media API documentation so that it
 *    can also support other interfaces (e.g. batch provisioning).</p>
 * 
 * @author <a href="https://github.com/three4clavin">three4clavin</a>
 *
 */
public enum ItemStateEnum {
	ACTIVE, INACTIVE, DELETED
}
