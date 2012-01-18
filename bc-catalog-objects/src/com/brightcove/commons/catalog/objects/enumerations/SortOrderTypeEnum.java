package com.brightcove.commons.catalog.objects.enumerations;

/**
 * <p>The order (direction) to sort a video list in.</p>
 * 
 * <p>This enumeration is defined primarily by the Media API documentation for
 *    a SortOrder object:
 *    <a href="http://docs.brightcove.com/en/media/#SortOrderType">http://docs.brightcove.com/en/media/#SortOrderType</a>.
 * </p>
 * <p>This may be modified however from the Media API documentation so that it
 *    can also support other interfaces (e.g. batch provisioning).</p>
 * 
 * @author <a href="https://github.com/three4clavin">three4clavin</a>
 *
 */
public enum SortOrderTypeEnum {
	ASC, DESC
}
