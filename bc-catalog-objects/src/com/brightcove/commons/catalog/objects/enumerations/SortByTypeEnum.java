package com.brightcove.commons.catalog.objects.enumerations;

/**
 * <p>The field to sort a video list on.</p>
 * 
 * <p>This enumeration is defined primarily by the Media API documentation for
 *    a SortByType object:
 *    <a href="http://docs.brightcove.com/en/media/#SortByType">http://docs.brightcove.com/en/media/#SortByType</a>.
 * </p>
 * <p>This may be modified however from the Media API documentation so that it
 *    can also support other interfaces (e.g. batch provisioning).</p>
 * 
 * @author <a href="https://github.com/three4clavin">three4clavin</a>
 *
 */
public enum SortByTypeEnum {
	PUBLISH_DATE, CREATION_DATE, MODIFIED_DATE, PLAYS_TOTAL, PLAYS_TRAILING_WEEK
}
