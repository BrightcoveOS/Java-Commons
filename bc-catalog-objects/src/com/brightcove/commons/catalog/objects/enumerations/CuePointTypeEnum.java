package com.brightcove.commons.catalog.objects.enumerations;

/**
 * <p>The type for a Cue Point on a video.</p>
 * 
 * <p>This enumeration is defined primarily by the Media API documentation for
 *    a Cue Point object:
 *    <a href="http://support.brightcove.com/en/docs/media-api-objects-reference#CuePoint">http://support.brightcove.com/en/docs/media-api-objects-reference#CuePoint</a>.
 * </p>
 * <p>This may be modified however from the Media API documentation so that it
 *    can also support other interfaces (e.g. batch provisioning).</p>
 * 
 * @author <a href="https://github.com/three4clavin">three4clavin</a>
 *
 */
public enum CuePointTypeEnum {
	AD(0, "AD"),
	CODE(1, "CODE"),
	CHAPTER(2, "CHAPTER");
	
	private final String  name;
	private final Integer code;
	CuePointTypeEnum(Integer code, String name){
		this.code = code;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	public Integer getCode() {
		return code;
	}
}
