package com.brightcove.commons.catalog.objects.enumerations;

import java.util.EnumSet;

/**
 * <p>Defines the allowable fields on a Playlist object.</p>
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
public enum PlaylistFieldEnum {
	ID("ID", "id"),
	REFERENCEID("REFERENCEID", "referenceId"),
	ACCOUNTID("ACCOUNTID", "accountId"),
	NAME("NAME", "name"),
	SHORTDESCRIPTION("SHORTDESCRIPTION", "shortDescription"),
	VIDEOIDS("VIDEOIDS", "videoIds"),
	VIDEOS("VIDEOS", "videos"),
	PLAYLISTTYPE("PLAYLISTTYPE", "playlistType"),
	FILTERTAGS("FILTERTAGS", "filterTags"),
	THUMBNAILURL("THUMBNAILURL", "thumbnailUrl");
	
	private final String definition;
	private final String jsonName;
	PlaylistFieldEnum(String definition, String jsonName){
		this.definition = definition;
		this.jsonName   = jsonName;
	}
	
	public String getDefinition() {
		return definition;
	}
	public String getJsonName() {
		return jsonName;
	}
	
	public static EnumSet<PlaylistFieldEnum> CreateEmptyEnumSet(){
		return EnumSet.noneOf(PlaylistFieldEnum.class);
	}
	
	public static EnumSet<PlaylistFieldEnum> CreateFullEnumSet(){
		return EnumSet.allOf(PlaylistFieldEnum.class);
	}
}
