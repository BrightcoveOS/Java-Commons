package com.brightcove.commons.catalog.objects;

import java.util.logging.Logger;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.brightcove.commons.catalog.objects.enumerations.ControllerTypeEnum;
import com.brightcove.commons.catalog.objects.enumerations.VideoCodecEnum;
import com.brightcove.commons.xml.W3CXMLUtils;

/**
 * <p>
 *    A Rendition object (the metadata defining a rendition).
 * </p>
 * 
 * <p>
 *    This object is defined primarily by the Media API documentation for
 *    a Rendition object:
 *    <a href="http://support.brightcove.com/en/docs/media-api-objects-reference#Rendition">http://support.brightcove.com/en/docs/media-api-objects-reference#Rendition</a>.
 * </p>
 * <p>
 *    This may be modified however from the Media API documentation so that it
 *    can also support other interfaces (e.g. batch provisioning).</p>
 * <p>
 *     <code>
 *           The Rendition object represents one of the multi-bitrate
 *           streaming renditions of a video. A Video should have not more
 *           than 10 Rendition objects. For more information, see
 *           <a href="http://support.brightcove.com/en/docs/using-multi-bitrate-streaming">Using multi-bitrate streaming</a>
 *           and
 *           <a href="http://support.brightcove.com/en/docs/creating-videos-multi-bitrate-streaming-media-api">Creating videos for multi-bitrate streaming</a>.
 *     </code>
 * </p>
 * 
 * @author <a href="https://github.com/three4clavin">three4clavin</a>
 *
 */
public class Rendition {
	private String             url;
	private ControllerTypeEnum controllerType;
	private Integer            encodingRate;
	private String             displayName;
	private Integer            frameHeight;
	private Integer            frameWidth;
	private Long               size;
	private String             remoteUrl;
	private String             remoteStreamName;
	private Long               videoDuration;
	private Long               id;
	private Boolean            audioOnly;
	private String             referenceId;
	private Long               uploadTimestampMillis;
	private String             videoContainer;
	
	private VideoCodecEnum videoCodec;
	
	/**
	 * <p>
	 *    Default Constructor.
	 * </p>
	 * 
	 * <p>
	 *    All fields set to null to start - required fields must be set before calling Write Media API.
	 * </p>
	 * 
	 */
	public Rendition(){
		initAll();
	}
	
	/**
	 * <p>
	 *    Constructor using JSON string.
	 * </p>
	 * 
	 * <p>
	 *    Given a JSON string from the Media API, attempts to construct a new
	 *    Rendition object and fill out all of the fields defined.  All other
	 *    fields will be null.
	 * </p>
	 * 
	 */
	public Rendition(String json) throws JSONException {
		initAll();
		
		if(json == null){
			throw new JSONException("[ERR] Rendition can not be parsed from null JSON string.");
		}
		
		JSONObject jsonObj = new JSONObject(json);
		
		String[] rootKeys = JSONObject.getNames(jsonObj);
		
		for(String rootKey : rootKeys){
			Object rootValue = jsonObj.get(rootKey);
			
			if((rootValue == null) || ("null".equals(rootValue.toString()))){
				// Don't bother setting the attribute, it should already be null
			}
			else if("encodingRate".equals(rootKey)){
				encodingRate = jsonObj.getInt(rootKey);
			}
			else if("referenceId".equals(rootKey)){
				referenceId = rootValue.toString();
			}
			else if("url".equals(rootKey)){
				url = rootValue.toString();
			}
			else if("controllerType".equals(rootKey)){
				if("AKAMAI_LIVE".equals(rootValue.toString())){
					controllerType = ControllerTypeEnum.AKAMAI_LIVE;
				}
				else if("LIMELIGHT_LIVE".equals(rootValue.toString())){
					controllerType = ControllerTypeEnum.LIMELIGHT_LIVE;
				}
				else if("DEFAULT".equals(rootValue.toString())){
					controllerType = ControllerTypeEnum.DEFAULT;
				}
				else{
					throw new JSONException("[ERR] Media API specified invalid value for controller type '" + rootValue + "'.  Acceptable values are 'AKAMAI_LIVE' and 'LIMELIGHT_LIVE'.");
				}
			}
			else if("size".equals(rootKey)){
				size = jsonObj.getLong(rootKey);
			}
			else if("id".equals(rootKey)){
				id = jsonObj.getLong(rootKey);
			}
			else if("uploadTimestampMillis".equals(rootKey)){
				uploadTimestampMillis = jsonObj.getLong(rootKey);
			}
			else if("frameWidth".equals(rootKey)){
				frameWidth = jsonObj.getInt(rootKey);
			}
			else if("remoteUrl".equals(rootKey)){
				remoteUrl = rootValue.toString();
			}
			else if("remoteStreamName".equals(rootKey)){
				remoteStreamName = rootValue.toString();
			}
			else if("displayName".equals(rootKey)){
				displayName = rootValue.toString();
			}
			else if("videoCodec".equals(rootKey)){
				if(rootValue.toString().equals("NONE")){
					videoCodec = VideoCodecEnum.NONE;
				}
				else if(rootValue.toString().equals("ON2")){
					videoCodec = VideoCodecEnum.ON2;
				}
				else if(rootValue.toString().equals("H264")){
					videoCodec = VideoCodecEnum.H264;
				}
				else if(rootValue.toString().equals("SORENSON")){
					videoCodec = VideoCodecEnum.SORENSON;
				}
				else{
					throw new JSONException("[ERR] Media API specified invalid value for video codec '" + rootValue + "'.  Acceptable values are 'NONE', 'ON2', 'H264' and 'SORENSON'.");
				}
			}
			else if("videoDuration".equals(rootKey)){
				videoDuration = jsonObj.getLong(rootKey);
			}
			else if("frameHeight".equals(rootKey)){
				frameHeight = jsonObj.getInt(rootKey);
			}
			else if("audioOnly".equals(rootKey)){
				audioOnly = jsonObj.getBoolean(rootKey);
			}
			else if("videoContainer".equals(rootKey)){
				videoContainer = rootValue.toString();
			}
			else{
				// Disabling exception throw for now - unknown keys will simply junk up the log instead of failing the program
				// throw new JSONException("[ERR] Unknown root key '" + rootKey + "'='" + rootValue + "'.");
				Logger.getLogger(this.getClass().getCanonicalName()).warning("[ERR] Unknown root key '" + rootKey + "'='" + rootValue + "'.");
			}
		}
	}
	
	/**
	 * <p>
	 *    Constructs a new rendition from an XML representation (generated by Videos.toXml()).
	 * </p>
	 * 
	 * @param element XML Element representing the rendition object 
	 */
	public Rendition(Element element) {
		initAll();
		
		Element child = W3CXMLUtils.getFirstElementChild(element);
		while(child != null){
			String nodeName  = child.getNodeName();
			String nodeValue = W3CXMLUtils.getStringValue(child);
			
			if(Node.ELEMENT_NODE == child.getNodeType()){
				if(nodeName.equals("url")){
					if(nodeValue != null){
						url = nodeValue;
					}
				}
				else if(nodeName.equals("controllerType")){
					if(nodeValue  != null){
						if(ControllerTypeEnum.AKAMAI_LIVE.toString().equals(nodeValue)){
							controllerType = ControllerTypeEnum.AKAMAI_LIVE;
						}
						else if(ControllerTypeEnum.LIMELIGHT_LIVE.toString().equals(nodeValue)){
							controllerType = ControllerTypeEnum.LIMELIGHT_LIVE;
						}
						else if(ControllerTypeEnum.DEFAULT.toString().equals(nodeValue)){
							controllerType = ControllerTypeEnum.DEFAULT;
						}
					}
				}
				else if(nodeName.equals("encodingRate")){
					if(nodeValue != null){
						encodingRate = Integer.parseInt(nodeValue);
					}
				}
				else if(nodeName.equals("displayName")){
					if(nodeValue != null){
						displayName = nodeValue;
					}
				}
				else if(nodeName.equals("frameHeight")){
					if(nodeValue != null){
						frameHeight = Integer.parseInt(nodeValue);
					}
				}
				else if(nodeName.equals("frameWidth")){
					if(nodeValue != null){
						frameWidth = Integer.parseInt(nodeValue);
					}
				}
				else if(nodeName.equals("size")){
					if(nodeValue != null){
						size = Long.parseLong(nodeValue);
					}
				}
				else if(nodeName.equals("remoteUrl")){
					if(nodeValue != null){
						remoteUrl = nodeValue;
					}
				}
				else if(nodeName.equals("remoteStreamName")){
					if(nodeValue != null){
						remoteStreamName = nodeValue;
					}
				}
				else if(nodeName.equals("videoDuration")){
					if(nodeValue != null){
						videoDuration = Long.parseLong(nodeValue);
					}
				}
				else if(nodeName.equals("id")){
					if(nodeValue != null){
						id = Long.parseLong(nodeValue);
					}
				}
				else if(nodeName.equals("audioOnly")){
					if(nodeValue != null){
						audioOnly = Boolean.parseBoolean(nodeValue);
					}
				}
				else if(nodeName.equals("referenceId")){
					if(nodeValue != null){
						referenceId = nodeValue;
					}
				}
				else if(nodeName.equals("uploadTimestampMillis")){
					if(nodeValue != null){
						uploadTimestampMillis = Long.parseLong(nodeValue);
					}
				}
				else if(child.getNodeName().equals("videoContainer")){
					if(nodeValue != null){
						videoContainer = nodeValue;
					}
				}
				else if(nodeName.equals("videoCodec")){
					if(nodeValue != null){
						if(VideoCodecEnum.H264.toString().equals(nodeValue)){
							videoCodec = VideoCodecEnum.H264;
						}
						else if(VideoCodecEnum.ON2.toString().equals(nodeValue)){
							videoCodec = VideoCodecEnum.ON2;
						}
						else if(VideoCodecEnum.SORENSON.toString().equals(nodeValue)){
							videoCodec = VideoCodecEnum.SORENSON;
						}
						else if(VideoCodecEnum.NONE.toString().equals(nodeValue)){
							videoCodec = VideoCodecEnum.NONE;
						}
					}
				}
			}
			
			child = W3CXMLUtils.getNextElementSibling(child);
		}
	}
	
	/**
	 * <p>
	 *    Sets all variables to null.
	 * </p>
	 */
	public void initAll(){
		url                   = null;
		controllerType        = null;
		encodingRate          = null;
		displayName           = null;
		frameHeight           = null;
		frameWidth            = null;
		size                  = null;
		remoteUrl             = null;
		remoteStreamName      = null;
		videoDuration         = null;
		id                    = null;
		audioOnly             = null;
		referenceId           = null;
		uploadTimestampMillis = null;
		videoContainer        = null;
		
		videoCodec = null;
	}
	
	/**
	 * <p>
	 *    Gets the URL for this Rendition.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: url<br/>
	 *          Type:          string<br/>
	 *          Read only?:    yes<br/>
	 *          Description:   The URL of the rendition file.
	 *    </code>
	 * </p>
	 * 
	 * @return Rendition URL as a String
	 */
	public String  getUrl(){
		return url;
	}
	
	/**
	 * <p>
	 *    Sets the URL for this Rendition.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: url<br/>
	 *          Type:          string<br/>
	 *          Read only?:    yes<br/>
	 *          Description:   The URL of the rendition file.
	 *    </code>
	 * </p>
	 * 
	 * @param url String representing the Rendition URL
	 */
	public void setUrl(String url){
		this.url = url;
	}
	
	/**
	 * <p>
	 *    Gets the Controller Type for this Rendition.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: controllerType<br/>
	 *          Type:          enum<br/>
	 *          Read only?:    no<br/>
	 *          Description:   [Optional — required for
	 *                         <a href="http://support.brightcove.com/en/docs/delivering-live-video">live streaming</a>
	 *                         only] Depending on your CDN, one of the
	 *                         following values: LIMELIGHT_LIVE or
	 *                         AKAMAI_LIVE.
	 *    </code>
	 * </p>
	 * 
	 * @return Controller Type enumeration value
	 */
	public ControllerTypeEnum getControllerType(){
		return controllerType;
	}
	
	/**
	 * <p>
	 *    Sets the Controller Type for this Rendition.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: controllerType<br/>
	 *          Type:          enum<br/>
	 *          Read only?:    no<br/>
	 *          Description:   [Optional — required for
	 *                         <a href="http://support.brightcove.com/en/docs/delivering-live-video">live streaming</a>
	 *                         only] Depending on your CDN, one of the
	 *                         following values: LIMELIGHT_LIVE or
	 *                         AKAMAI_LIVE.
	 *    </code>
	 * </p>
	 * 
	 * @param controllerType Enumeration value for the controller type
	 */
	public void setControllerType(ControllerTypeEnum controllerType){
		this.controllerType = controllerType;
	}
	
	/**
	 * <p>
	 *    Gets the encoding rate for this Rendition.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: encodingRate<br/>
	 *          Type:          int<br/>
	 *          Read only?:    yes<br/>
	 *          Description:   The rendition's encoding rate, in bits per
	 *                         second.
	 *    </code>
	 * </p>
	 * 
	 * @return Encoding rate for this Rendition, in bits/second
	 */
	public Integer getEncodingRate(){
		return encodingRate;
	}
	
	/**
	 * <p>
	 *    Sets the encoding rate for this Rendition.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: encodingRate<br/>
	 *          Type:          int<br/>
	 *          Read only?:    yes<br/>
	 *          Description:   The rendition's encoding rate, in bits per
	 *                         second.
	 *    </code>
	 * </p>
	 * 
	 * @param encodingRate Encoding rate for this Rendition, in bits/second
	 */
	public void setEncodingRate(Integer encodingRate){
		this.encodingRate = encodingRate;
	}
	
	/**
	 * <p>
	 *    Gets the display name for this Rendition.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          undocumented...
	 *    </code>
	 * </p>
	 * 
	 * @return Rendition display name as a String
	 */
	public String  getDisplayName(){
		return displayName;
	}
	
	/**
	 * <p>
	 *    Sets the display name for this Rendition.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          undocumented...
	 *    </code>
	 * </p>
	 * 
	 * @param displayName
	 */
	public void setDisplayName(String displayName){
		this.displayName = displayName;
	}
	
	/**
	 * <p>
	 *    Gets the frame height for this Rendition in pixels.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: frameHeight<br/>
	 *          Type:          int<br/>
	 *          Read only?:    yes<br/>
	 *          Description:   The rendition's display height, in pixels.
	 *    </code>
	 * </p>
	 * 
	 * @return Frame height for this Rendition in pixels
	 */
	public Integer getFrameHeight(){
		return frameHeight;
	}
	
	/**
	 * <p>
	 *    Sets the frame height for this Rendition in pixels.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: frameHeight<br/>
	 *          Type:          int<br/>
	 *          Read only?:    yes<br/>
	 *          Description:   The rendition's display height, in pixels.
	 *    </code>
	 * </p>
	 * 
	 * @param frameHeight Frame height for this Rendition in pixels
	 */
	public void setFrameHeight(Integer frameHeight){
		this.frameHeight = frameHeight;
	}
	
	/**
	 * <p>
	 *    Gets the frame width for this Rendition in pixels.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: frameWidth<br/>
	 *          Type:          int<br/>
	 *          Read only?:    yes<br/>
	 *          Description:   The rendition's display width, in pixels.
	 *    </code>
	 * </p>
	 * 
	 * @return Frame width for this Rendition in pixels
	 */
	public Integer getFrameWidth(){
		return frameWidth;
	}
	
	/**
	 * <p>
	 *    Sets the frame width for this Rendition in pixels.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: frameWidth<br/>
	 *          Type:          int<br/>
	 *          Read only?:    yes<br/>
	 *          Description:   The rendition's display width, in pixels.
	 *    </code>
	 * </p>
	 * 
	 * @param frameWidth Frame width for this Rendition in pixels
	 */
	public void setFrameWidth(Integer frameWidth){
		this.frameWidth = frameWidth;
	}
	
	/**
	 * <p>
	 *    Gets the file size of this Rendition in bytes.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: size<br/>
	 *          Type:          long<br/>
	 *          Read only?:    yes<br/>
	 *          Description:   Required. The file size of the rendition, in
	 *                         bytes.
	 *    </code>
	 * </p>
	 * 
	 * @return File size in bytes for this Rendition
	 */
	public Long getSize(){
		return size;
	}
	
	/**
	 * <p>
	 *    Sets the file size of this Rendition in bytes.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: size<br/>
	 *          Type:          long<br/>
	 *          Read only?:    yes<br/>
	 *          Description:   Required. The file size of the rendition, in
	 *                         bytes.
	 *    </code>
	 * </p>
	 * 
	 * @param size File size in bytes for this Rendition
	 */
	public void setSize(Long size){
		this.size = size;
	}
	
	/**
	 * <p>
	 *    Gets the remote URL attribute for this asset.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: remoteUrl<br/>
	 *          Type:          string<br/>
	 *          Read only?:    no<br/>
	 *          Description:   Required, for remote assets. The complete path
	 *                         to the file hosted on the remote server. If the
	 *                         file is served using progressive download, then
	 *                         you must include the file name and extension
	 *                         for the file. You can also use a URL that
	 *                         re-directs to a URL that includes the file name
	 *                         and extension. If the file is served using
	 *                         Flash streaming, use the remoteStreamName
	 *                         attribute to provide the stream name.
	 *    </code>
	 * </p>
	 * 
	 * @return Remote URL attribute for this asset as a String
	 */
	public String  getRemoteUrl(){
		return remoteUrl;
	}
	
	/**
	 * <p>
	 *    Sets the remote URL attribute for this asset.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: remoteUrl<br/>
	 *          Type:          string<br/>
	 *          Read only?:    no<br/>
	 *          Description:   Required, for remote assets. The complete path
	 *                         to the file hosted on the remote server. If the
	 *                         file is served using progressive download, then
	 *                         you must include the file name and extension
	 *                         for the file. You can also use a URL that
	 *                         re-directs to a URL that includes the file name
	 *                         and extension. If the file is served using
	 *                         Flash streaming, use the remoteStreamName
	 *                         attribute to provide the stream name.
	 *    </code>
	 * </p>
	 * 
	 * @param remoteUrl Remote URL attribute for this asset as a String
	 */
	public void setRemoteUrl(String remoteUrl){
		this.remoteUrl = remoteUrl;
	}
	
	/**
	 * <p>
	 *    Gets the remote stream name attribute for this Rendition.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: remoteStreamName<br/>
	 *          Type:          string<br/>
	 *          Read only?:    no<br/>
	 *          Description:   [Optional — required for streaming remote
	 *                         assets only] A stream name for Flash streaming
	 *                         appended to the value of the remoteUrl
	 *                         property.
	 *    </code>
	 * </p>
	 * 
	 * @return Remote stream name
	 */
	public String  getRemoteStreamName(){
		return remoteStreamName;
	}
	
	/**
	 * <p>
	 *    Sets the remote stream name attribute for this Rendition.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: remoteStreamName<br/>
	 *          Type:          string<br/>
	 *          Read only?:    no<br/>
	 *          Description:   [Optional — required for streaming remote
	 *                         assets only] A stream name for Flash streaming
	 *                         appended to the value of the remoteUrl
	 *                         property.
	 *    </code>
	 * </p>
	 * 
	 * @param remoteStreamName Remote stream name
	 */
	public void setRemoteStreamName(String remoteStreamName){
		this.remoteStreamName = remoteStreamName;
	}
	
	/**
	 * <p>
	 *    Gets the length of the video asset.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: videoDuration<br/>
	 *          Type:          long<br/>
	 *          Read only?:    no<br/>
	 *          Description:   Required. The length of the remote video asset
	 *                         in milliseconds.
	 *    </code>
	 * </p>
	 * 
	 * @return The length of the video asset in milliseconds
	 */
	public Long getVideoDuration(){
		return videoDuration;
	}
	
	/**
	 * <p>
	 *    Sets the length of the video asset.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: videoDuration<br/>
	 *          Type:          long<br/>
	 *          Read only?:    no<br/>
	 *          Description:   Required. The length of the remote video asset
	 *                         in milliseconds.
	 *    </code>
	 * </p>
	 * 
	 * @param videoDuration the length of the video asset in milliseconds
	 */
	public void setVideoDuration(Long videoDuration){
		this.videoDuration = videoDuration;
	}
	
	/**
	 * <p>
	 *    Gets the video codec for this Rendition.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: videoCodec<br/>
	 *          Type:          enum<br/>
	 *          Read only?:    no<br/>
	 *          Description:   Required. Valid values are SORENSON, ON2, and
	 *                         H264.
	 *    </code>
	 * </p>
	 * 
	 * @return Video codec for this Rendition
	 */
	public VideoCodecEnum getVideoCodec(){
		return videoCodec;
	}
	
	/**
	 * <p>
	 *    Sets the video codec for this Rendition.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: videoCodec<br/>
	 *          Type:          enum<br/>
	 *          Read only?:    no<br/>
	 *          Description:   Required. Valid values are SORENSON, ON2, and
	 *                         H264.
	 *    </code>
	 * </p>
	 * 
	 * @param videoCodec Video codec for this Rendition
	 */
	public void setVideoCodec(VideoCodecEnum videoCodec){
		this.videoCodec = videoCodec;
	}
	
	/**
	 * <p>
	 *    Gets whether or not this rendition is audio only.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          undocumented
	 *    </code>
	 * </p>
	 * 
	 * @return Boolean (true if the rendition is audio only, else false)
	 */
	public Boolean getAudioOnly(){
		return audioOnly;
	}
	
	/**
	 * <p>
	 *    Sets whether or not this rendition is audio only.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          undocumented
	 *    </code>
	 * </p>
	 * 
	 * @param audioOnly True if the rendition is audio only, else false
	 */
	public void setAudioOnly(Boolean audioOnly){
		this.audioOnly = audioOnly;
	}
	
	/**
	 * <p>
	 *    Gets the unique id for this rendition
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          undocumented
	 *    </code>
	 * </p>
	 * 
	 * @return Unique id for this rendition
	 */
	public Long getId(){
		return id;
	}
	
	/**
	 * <p>
	 *    Sets the unique id for this rendition
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          undocumented
	 *    </code>
	 * </p>
	 * 
	 * @param id Unique id for this rendition
	 */
	public void setId(Long id){
		this.id = id;
	}
	
	/**
	 * <p>
	 *    Gets the user supplied reference id for this rendition
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          undocumented
	 *    </code>
	 * </p>
	 * 
	 * @return Reference id for this rendition
	 */
	public String getReferenceId(){
		return referenceId;
	}
	
	/**
	 * <p>
	 *    Sets the user supplied reference id for this rendition
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          undocumented
	 *    </code>
	 * </p>
	 * 
	 * @param referenceId Reference id for this rendition
	 */
	public void setReferenceId(String referenceId){
		this.referenceId = referenceId;
	}
	
	/**
	 * <p>
	 *    Gets the timestamp for when this rendition was uploaded
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          undocumented
	 *    </code>
	 * </p>
	 * 
	 * @return Timestamp in milliseconds since epoch this rendition was uploaded
	 */
	public Long getUploadTimestampMillis(){
		return uploadTimestampMillis;
	}
	
	/**
	 * <p>
	 *    Sets the timestamp for when this rendition was uploaded
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          undocumented
	 *    </code>
	 * </p>
	 * 
	 * @param uploadTimestampMillis Timestamp in milliseconds since epoch this rendition was uploaded
	 */
	public void setUploadTimestampMillis(Long uploadTimestampMillis){
		this.uploadTimestampMillis = uploadTimestampMillis;
	}
	
	/**
	 * <p>
	 *    Gets the video container for this rendition
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          undocumented
	 *    </code>
	 * </p>
	 * 
	 * @return String value for video container for this rendition
	 */
	public String getVideoContainer(){
		return videoContainer;
	}
	
	/**
	 * <p>
	 *    Sets the video container for this rendition
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          undocumented
	 *    </code>
	 * </p>
	 * 
	 * @param videoContainer String value for video container for this rendition
	 */
	public void setVideoContainer(String videoContainer){
		this.videoContainer = videoContainer;
	}
	
	/**
	 * <p>
	 *    Converts the rendition into a JSON object suitable for use with the Media API
	 * </p>
	 * 
	 * @return JSON object representing the rendition
	 */
	public JSONObject toJson() throws JSONException {
		JSONObject json = new JSONObject();
		
		if(url != null){
			json.put("url", url);
		}
		if(controllerType != null){
			json.put("controllerType", "" + controllerType);
		}
		if(encodingRate != null){
			json.put("encodingRate",   encodingRate);
		}
		if(displayName != null){
			json.put("displayName", displayName);
		}
		if(frameHeight != null){
			json.put("frameHeight", frameHeight);
		}
		if(frameWidth != null){
			json.put("frameWidth", frameWidth);
		}
		if(size != null){
			json.put("size", size);
		}
		if(remoteUrl != null){
			json.put("remoteUrl", remoteUrl);
		}
		if(remoteStreamName != null){
			json.put("remoteStreamName", remoteStreamName);
		}
		if(videoDuration != null){
			json.put("videoDuration", videoDuration);
		}
		if(id != null){
			json.put("id", id);
		}
		if(videoCodec != null){
			json.put("videoCodec", videoCodec);
		}
		if(audioOnly != null){
			json.put("audioOnly", audioOnly);
		}
		if(referenceId != null){
			json.put("referenceId", referenceId);
		}
		if(uploadTimestampMillis != null){
			json.put("uploadTimestampMillis", uploadTimestampMillis);
		}
		if(videoContainer != null){
			json.put("videoContainer", videoContainer);
		}
		
		return json;
	}
	
	/**
	 * <p>
	 *    Uses the W3C libraries to generate an XML representation of
	 *    this object.
	 * </p>
	 * 
	 * @param root Element to append data to
	 * @param renditionName Name to assign element
	 * @return XML Element representing the object 
	 */
	public Element appendXml(Element root, String renditionName) {
		Document doc = root.getOwnerDocument();
		
		if(renditionName == null){
			renditionName = "Rendition";
		}
		
		Element renditionElement = doc.createElement(renditionName);
		root.appendChild(renditionElement);
		
		if(url != null){
			Element urlElement = doc.createElement("url");
			urlElement.appendChild(doc.createTextNode(url));
			renditionElement.appendChild(urlElement);
		}
		
		if(controllerType != null){
			Element controllerTypeElement = doc.createElement("controllerType");
			controllerTypeElement.appendChild(doc.createTextNode(""+controllerType));
			renditionElement.appendChild(controllerTypeElement);
		}
		
		if(encodingRate != null){
			Element encodingRateElement = doc.createElement("encodingRate");
			encodingRateElement.appendChild(doc.createTextNode(""+encodingRate));
			renditionElement.appendChild(encodingRateElement);
		}
		
		if(displayName != null){
			Element displayNameElement = doc.createElement("displayName");
			displayNameElement.appendChild(doc.createTextNode(""+displayName));
			renditionElement.appendChild(displayNameElement);
		}
		
		if(frameHeight != null){
			Element frameHeightElement = doc.createElement("frameHeight");
			frameHeightElement.appendChild(doc.createTextNode(""+frameHeight));
			renditionElement.appendChild(frameHeightElement);
		}
		
		if(frameWidth != null){
			Element frameWidthElement = doc.createElement("frameWidth");
			frameWidthElement.appendChild(doc.createTextNode(""+frameWidth));
			renditionElement.appendChild(frameWidthElement);
		}
		
		if(size != null){
			Element sizeElement = doc.createElement("size");
			sizeElement.appendChild(doc.createTextNode(""+size));
			renditionElement.appendChild(sizeElement);
		}
		
		if(remoteUrl != null){
			Element remoteUrlElement = doc.createElement("remoteUrl");
			remoteUrlElement.appendChild(doc.createTextNode(remoteUrl));
			renditionElement.appendChild(remoteUrlElement);
		}
		
		if(remoteStreamName != null){
			Element remoteStreamNameElement = doc.createElement("remoteStreamName");
			remoteStreamNameElement.appendChild(doc.createTextNode(remoteStreamName));
			renditionElement.appendChild(remoteStreamNameElement);
		}
		
		if(videoDuration != null){
			Element videoDurationElement = doc.createElement("videoDuration");
			videoDurationElement.appendChild(doc.createTextNode(""+videoDuration));
			renditionElement.appendChild(videoDurationElement);
		}
		
		if(id != null){
			Element idElement = doc.createElement("id");
			idElement.appendChild(doc.createTextNode(""+id));
			renditionElement.appendChild(idElement);
		}
		
		if(audioOnly != null){
			Element audioOnlyElement = doc.createElement("audioOnly");
			audioOnlyElement.appendChild(doc.createTextNode(""+audioOnly));
			renditionElement.appendChild(audioOnlyElement);
		}
		
		if(referenceId != null){
			Element referenceIdElement = doc.createElement("referenceId");
			referenceIdElement.appendChild(doc.createTextNode(""+referenceId));
			renditionElement.appendChild(referenceIdElement);
		}
		
		if(uploadTimestampMillis != null){
			Element uploadTimestampMillisElement = doc.createElement("uploadTimestampMillis");
			uploadTimestampMillisElement.appendChild(doc.createTextNode(""+uploadTimestampMillis));
			renditionElement.appendChild(uploadTimestampMillisElement);
		}
		
		if(videoContainer != null){
			Element videoContainerElement = doc.createElement("videoContainer");
			videoContainerElement.appendChild(doc.createTextNode(videoContainer));
			renditionElement.appendChild(videoContainerElement);
		}
		
		if(videoCodec != null){
			Element videoCodecElement = doc.createElement("videoCodec");
			videoCodecElement.appendChild(doc.createTextNode(""+videoCodec));
			renditionElement.appendChild(videoCodecElement);
		}
		
		return renditionElement;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		String ret = "[com.brightcove.proserve.mediaapi.wrapper.apiobjects.Rendition (\n" +
			"\turl:'"                   + url                   + "'\n" +
			"\tcontrollerType:'"        + controllerType        + "'\n" +
			"\tencodingRate:'"          + encodingRate          + "'\n" +
			"\tdisplayName:'"           + displayName           + "'\n" +
			"\tframeHeight:'"           + frameHeight           + "'\n" +
			"\tframeWidth:'"            + frameWidth            + "'\n" +
			"\tsize:'"                  + size                  + "'\n" +
			"\tremoteUrl:'"             + remoteUrl             + "'\n" +
			"\tremoteStreamName:'"      + remoteStreamName      + "'\n" +
			"\tvideoDuration:'"         + videoDuration         + "'\n" +
			"\tid:'"                    + id                    + "'\n" +
			"\taudioOnly:'"             + audioOnly             + "'\n" +
			"\treferenceId:'"           + referenceId           + "'\n" +
			"\tuploadTimestampMillis:'" + uploadTimestampMillis + "'\n" + 
			"\tvideoCodec:'"            + videoCodec            + "'\n" +
			"\tvideoContainer:'"        + videoContainer        + "'\n" +
			")]";
		
		return ret;
	}
}
