package com.brightcove.commons.catalog.objects.enumerations;

import java.util.EnumSet;

/**
 * <p>Defines the allowable fields on a Video object.</p>
 * 
 * <p>This enumeration is defined primarily by the Media API documentation for
 *    a Video object:
 *    <a href="http://support.brightcove.com/en/docs/media-api-objects-reference#Video">http://support.brightcove.com/en/docs/media-api-objects-reference#Video</a>.
 * </p>
 * <p>This may be modified however from the Media API documentation so that it
 *    can also support other interfaces (e.g. batch provisioning).</p>
 * 
 * @author <a href="https://github.com/three4clavin">three4clavin</a>
 *
 */
public enum VideoFieldEnum {
	ID("ID", "id"),
	NAME("NAME", "name"),
	SHORTDESCRIPTION("SHORTDESCRIPTION", "shortDescription"),
	LONGDESCRIPTION("LONGDESCRIPTION", "longDescription"),
	CREATIONDATE("CREATIONDATE", "creationDate"),
	PUBLISHEDDATE("PUBLISHEDDATE", "publishedDate"),
	LASTMODIFIEDDATE("LASTMODIFIEDDATE", "lastModifiedDate"),
	STARTDATE("STARTDATE", "startDate"),
	ENDDATE("ENDDATE", "endDate"),
	LINKURL("LINKURL", "linkUrl"),
	LINKTEXT("LINKTEXT", "linkText"),
	TAGS("TAGS", "tags"),
	VIDEOSTILLURL("VIDEOSTILLURL", "videoStillUrl"),
	THUMBNAILURL("THUMBNAILURL", "thumbnailUrl"),
	REFERENCEID("REFERENCEID", "referenceId"),
	LENGTH("LENGTH", "length"),
	ECONOMICS("ECONOMICS", "economics"),
	ITEMSTATE("ITEMSTATE", "itemState"),
	PLAYSTOTAL("PLAYSTOTAL", "playsTotal"),
	PLAYSTRAILINGWEEK("PLAYSTRAILINGWEEK", "playsTrailingWeek"),
	VERSION("VERSION", "version"),
	CUEPOINTS("CUEPOINTS", "cuePoints"),
	SUBMISSIONINFO("SUBMISSIONINFO", "submissionInfo"),
	CUSTOMFIELDS("CUSTOMFIELDS", "customfields"),
	RELEASEDATE("RELEASEDATE", "releaseDate"),
	FLVURL("FLVURL", "flvUrl"),
	RENDITIONS("RENDITIONS", "renditions"),
	GEOFILTERED("GEOFILTERED", "geoFiltered"),
	GEORESTRICTED("GEORESTRICTED", "geoRestricted"),
	GEOFILTEREXCLUDE("GEOFILTEREXCLUDE", "geoFilterExclude"),
	EXCLUDELISTEDCOUNTRIES("EXCLUDELISTEDCOUNTRIES", "excludeListedCountries"),
	GEOFILTEREDCOUNTRIES("GEOFILTEREDCOUNTRIES", "geoFilteredCountries"),
	ALLOWEDCOUNTRIES("ALLOWEDCOUNTRIES", "allowedCountries"),
	ACCOUNTID("ACCOUNTID", "accountId"),
	FLVFULLLENGTH("FLVFULLLENGTH", "flvFullLength"),
	VIDEOFULLLENGTH("VIDEOFULLLENGTH", "videoFullLength"),
	ADKEYS("ADKEYS","adKeys");
	
	private final String definition;
	private final String jsonName;
	VideoFieldEnum(String definition, String jsonName){
		this.definition = definition;
		this.jsonName   = jsonName;
	}
	
	public String getDefinition() {
		return definition;
	}
	public String getJsonName() {
		return jsonName;
	}
	
	public static EnumSet<VideoFieldEnum> CreateEmptyEnumSet(){
		return EnumSet.noneOf(VideoFieldEnum.class);
	}
	
	public static EnumSet<VideoFieldEnum> CreateFullEnumSet(){
		return EnumSet.allOf(VideoFieldEnum.class);
	}
}
