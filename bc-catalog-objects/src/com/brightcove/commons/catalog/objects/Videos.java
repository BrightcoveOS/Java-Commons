package com.brightcove.commons.catalog.objects;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * <p>
 *    This class is basically just an ArrayList with some extra utility
 *    methods that makes working with a List of Videos easier.
 * </p>
 * 
 * @author <a href="https://github.com/three4clavin">three4clavin</a>
 *
 */
public class Videos extends ArrayList<Video> {
	private static final long serialVersionUID = -1603397104852631362L;
	
	private Integer totalCount = 0;
	
	public Videos(JSONObject jsonObj) throws JSONException {
		JSONArray jsonItems = jsonObj.getJSONArray("items");
		for(int itemIdx=0;itemIdx<jsonItems.length();itemIdx++){
			JSONObject jsonItem = (JSONObject)jsonItems.get(itemIdx);
			Video video = new Video(jsonItem);
			add(video);
		}
		
		try{
			totalCount = jsonObj.getInt("total_count");
		}
		catch(JSONException jsone){
			// Don't fail altogether
			totalCount = -1;
		}
	}
	
	public Integer getTotalCount(){
		return this.totalCount;
	}
}
