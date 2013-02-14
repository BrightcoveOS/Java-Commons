package com.brightcove.commons.catalog.objects.enumerations;

/**
 * <p>The Inclusion Rule for a playlist.</p>
 * 
 * <p>This enumeration is defined primarily by the Media API documentation for
 *    a Playlist object:
 *    <a href="http://support.brightcove.com/en/video-cloud/docs/media-api-objects-reference#Playlist">http://support.brightcove.com/en/video-cloud/docs/media-api-objects-reference#Playlist</a>.
 * </p>
 * <p>This may be modified however from the Media API documentation so that it
 *    can also support other interfaces (e.g. batch provisioning).</p>
 * 
 * @author <a href="https://github.com/three4clavin">three4clavin</a>
 *
 */
public enum InclusionRuleEnum {
	AND, OR
}
