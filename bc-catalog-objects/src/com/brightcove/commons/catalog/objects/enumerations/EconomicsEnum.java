package com.brightcove.commons.catalog.objects.enumerations;

/**
 * <p>The economic model for a video.</p>
 * 
 * <p>This enumeration is defined primarily by the Media API documentation for
 *    an EconomicsEnum object:
 *    <a href="http://docs.brightcove.com/en/media/#EconomicsEnum">http://docs.brightcove.com/en/media/#EconomicsEnum</a>.
 * </p>
 * <p>This may be modified however from the Media API documentation so that it
 *    can also support other interfaces (e.g. batch provisioning).</p>
 * 
 * @author <a href="https://github.com/three4clavin">three4clavin</a>
 *
 */
public enum EconomicsEnum {
	FREE, AD_SUPPORTED
}
