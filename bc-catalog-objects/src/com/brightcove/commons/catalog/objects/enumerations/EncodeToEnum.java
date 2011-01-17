package com.brightcove.commons.catalog.objects.enumerations;


/**
 * <p>The encoding to transcode a new video to after initial creation.</p>
 * 
 * <p>This enumeration is defined primarily by the Media API documentation for
 *    the Video Write API:
 *    <a href="http://docs.brightcove.com/en/media/#Video_Write">http://docs.brightcove.com/en/media/#Video_Write</a><br/>
 *    (specifically the create_video method defines the allowable values).
 * </p>
 * <p>This may be modified however from the Media API documentation so that it
 *    can also support other interfaces (e.g. batch provisioning).</p>
 * 
 * @author <a href="https://github.com/three4clavin">three4clavin</a>
 *
 */
public enum EncodeToEnum {
	FLV,MP4
}
