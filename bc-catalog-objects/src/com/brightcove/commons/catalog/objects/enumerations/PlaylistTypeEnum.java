package com.brightcove.commons.catalog.objects.enumerations;

/**
 * <p>The type for a playlist.</p>
 * 
 * <p>This enumeration is defined primarily by the Media API documentation for
 *    a Playlist object:
 *    <a href="http://support.brightcove.com/en/docs/media-api-objects-reference#Playlist">http://support.brightcove.com/en/docs/media-api-objects-reference#Playlist</a>.
 * </p>
 * <p>This may be modified however from the Media API documentation so that it
 *    can also support other interfaces (e.g. batch provisioning).</p>
 * 
 * @author <a href="https://github.com/three4clavin">three4clavin</a>
 *
 */
public enum PlaylistTypeEnum {
	OLDEST_TO_NEWEST, NEWEST_TO_OLDEST, ALPHABETICAL, PLAYSTOTAL, PLAYS_TRAILING_WEEK, EXPLICIT
}
