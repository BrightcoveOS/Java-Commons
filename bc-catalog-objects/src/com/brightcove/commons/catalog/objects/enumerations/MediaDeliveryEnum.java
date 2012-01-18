package com.brightcove.commons.catalog.objects.enumerations;

/**
 * <p>Format to deliver media URLs in.</p>
 * 
 * <p>This enumeration is defined primarily by the Media API documentation for
 *    the Video Read API:
 *    <a href="http://docs.brightcove.com/en/media/#Video_Read">http://docs.brightcove.com/en/media/#Video_Read</a><br/>
 *    (most of the find_x methods allow you to specify media delivery).
 * </p>
 * <p>Note that in order to use this feature, Universal Delivery Service (UDS)
 *    must be enabled on your Brightcove account.  This feature can only be
 *    enabled by Brightcove Support.</p>
 * <p>This may be modified however from the Media API documentation so that it
 *    can also support other interfaces (e.g. batch provisioning).</p>
 * 
 * @author <a href="https://github.com/three4clavin">three4clavin</a>
 *
 */
public enum MediaDeliveryEnum {
	HTTP, DEFAULT;
}
