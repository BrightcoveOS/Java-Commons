package com.brightcove.commons.catalog.objects.enumerations;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>States a video can be in, to be used for filtering find_x Media API
 *    calls.</p>
 * 
 * <p>This enumeration is defined primarily by the Media API documentation for
 *    the Video Read API:
 *    <a href="http://docs.brightcove.com/en/media/#Video_Read">http://docs.brightcove.com/en/media/#Video_Read</a><br/>
 *    (specifically the find_modified_videos method).
 * </p>
 * <p>This may be modified however from the Media API documentation so that it
 *    can also support other interfaces (e.g. batch provisioning).</p>
 * 
 * @author <a href="https://github.com/three4clavin">three4clavin</a>
 *
 */
public enum VideoStateFilterEnum {
	PLAYABLE, UNSCHEDULED, INACTIVE, DELETED;
	
	public static Set<VideoStateFilterEnum> CreateEmptySet(){
		return ((Set<VideoStateFilterEnum>)(new HashSet<VideoStateFilterEnum>()));
	}
	
	public static Set<VideoStateFilterEnum> CreateFullSet(){
		Set<VideoStateFilterEnum> ret = new HashSet<VideoStateFilterEnum>();
		ret.add(PLAYABLE);
		ret.add(UNSCHEDULED);
		ret.add(INACTIVE);
		ret.add(DELETED);
		return ret;
	}
}
