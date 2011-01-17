package com.brightcove.commons.catalog.objects;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * <p>
 *    A Custom Field object.
 * </p>
 * 
 * <p>
 *    This object is defined primarily by the Media API documentation for
 *    a Video object:
 *    <a href="http://support.brightcove.com/en/docs/media-api-objects-reference#Video">http://support.brightcove.com/en/docs/media-api-objects-reference#Video</a>.<br/>
 *    (specifically the customFields property on each video)
 * </p>
 * <p>
 *    This may be modified however from the Media API documentation so that it
 *    can also support other interfaces (e.g. batch provisioning).</p>
 * <p>
 *     <code>
 *           A map of names and values for
 *           <a href="http://support.brightcove.com/en/docs/setting-custom-metadata">custom fields set up for videos in your account</a>.
 *           <a href="http://support.brightcove.com/en/docs/setting-custom-metadata-media-api">More information and examples</a>.
 *     </code>
 * </p>
 * 
 * @author <a href="https://github.com/three4clavin">three4clavin</a>
 *
 */
public class CustomField {
	private String name;
	private String value;
	
	/**
	 * <p>
	 *    Default Constructor.
	 * </p>
	 * 
	 * <p>
	 *    All fields set to null to start - required fields must be set before
	 *    calling Write Media API.
	 * </p>
	 * 
	 */
	public CustomField(){
		initAll();
	}
	
	/**
	 * <p>
	 *    Constructor using JSON string.
	 * </p>
	 * 
	 * <p>
	 *    Given a JSON string from the Media API, attempts to construct a new
	 *    Custom Field object and fill out all of the fields defined.  All
	 *    other fields will be null.
	 * </p>
	 * 
	 */
	public CustomField(String json) throws JSONException {
		initAll();
		
		if(json == null){
			throw new JSONException("[ERR] Custom Field can not be parsed from null JSON string.");
		}
		
		JSONObject jsonObj = new JSONObject(json);
		
		String[] rootKeys = JSONObject.getNames(jsonObj);
		
		for(String rootKey : rootKeys){
			// Object rootValue = jsonObj.get(rootKey);
			
			this.name  = rootKey;
			this.value = jsonObj.getString(rootKey);
		}
	}
	
	/**
	 * <p>
	 *    Constructor using key-value pair.
	 * </p>
	 * 
	 * <p>
	 *    Given a custom name and value, attempts to construct a new Custom
	 *    Field object and fill out all of the fields defined.  All other
	 *    fields will be null.
	 * </p>
	 * 
	 */
	public CustomField(String name, String value) {
		initAll();
		
		this.name  = name;
		this.value = value;
	}
	
	/**
	 * <p>
	 *    Initializes all variables to null
	 * </p>
	 */
	public void initAll(){
		name  = null;
		value = null;
	}
	
	/**
	 * <p>
	 *    Gets the name of the Custom Field.
	 * </p>
	 * 
	 * @return Name of the Custom Field
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * <p>
	 *    Sets the name of the Custom Field.
	 * </p>
	 * 
	 * @param name Name of the Custom Field
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * <p>
	 *    Gets the value of the Custom Field.
	 * </p>
	 * 
	 * @return value of the Custom Field
	 */
	public String getValue(){
		return value;
	}
	
	/**
	 * <p>
	 *    Sets the value of the Custom Field.
	 * </p>
	 * 
	 * @param value Value of the Custom Field
	 */
	public void setId(String value){
		this.value = value;
	}
	
	/**
	 * <p>
	 *    Converts the custom field into a JSON object
	 * </p>
	 * 
	 * @return JSON object representing the custom field
	 */
	public JSONObject toJson() throws JSONException {
		JSONObject json = new JSONObject();
		
		if(name != null){
			json.put("name", name);
		}
		if(value != null){
			json.put("value", value);
		}
		
		return json;
	}
	
	/**
	 * <p>
	 *    Uses the json.org libraries to generate an XML representation of
	 *    this custom field object.
	 * </p>
	 * 
	 * @return String with XML representing the custom field object
	 * @throws JSONException Because under the covers we're using the json.org libraries to generate the XML
	 */
	public String toXmlString() throws JSONException {
		JSONObject jsono = toJson();
		if(jsono == null){ return null; }
		
		String ret = org.json.XML.toString(jsono, "CustomField");
		return ret;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		String ret = "[com.brightcove.proserve.mediaapi.wrapper.apiobjects.CustomField (\n" +
			"\tname:'"  + name  + "'\n" +
			"\tvalue:'" + value + "'\n" +
			")]";
		
		return ret;
	}
}
