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
	DISPLAY_NAME,        // (video only) 	Enum 	read-only 	Name of the title.
	REFERENCE_ID,        // (video only) 	Enum 	read-only 	Reference ID of the title.
	PLAYS_TOTAL,         // (video only) 	Enum 	read-only 	Number of times this title has been viewed.
	PLAYS_TRAILING_WEEK, // (video only) 	Enum 	read-only 	Number of times this title has been viewed in the past 7 days (excluding today)
	START_DATE,          // (video only) 	Enum 	read-only 	Date title is scheduled to be available.
	PUBLISH_DATE,        //              	Enum 	read-only 	Date title was published.
	CREATION_DATE,       //              	Enum 	read-only 	Date title was created.
	MODIFIED_DATE        //              	Enum 	read-only 	Date title was last modified.
	// PLAYS_TOTAL,         //              	Enum 	read-only 	Number of times this title has been viewed.
	// PLAYS_TRAILING_WEEK  //              	Enum 	read-only 	Number of times this title has been viewed in the past 7 days (excluding today)
}
