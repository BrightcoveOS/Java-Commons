package com.brightcove.commons.catalog.objects;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * <p>
 *    This class is basically just an ArrayList with some extra utility
 *    methods that makes working with a List of Playlists easier.
 * </p>
 * 
 * @author <a href="https://github.com/three4clavin">three4clavin</a>
 *
 */
public class Playlists extends ArrayList<Playlist> {
	private static final long serialVersionUID = 232810143858103556L;
	
	private Integer totalCount = 0;
	
	public Playlists(JSONObject jsonObj) throws JSONException {
		if((jsonObj == null) || jsonObj.equals(JSONObject.NULL)){
			totalCount = -1;
			return;
		}
		
		try{
			totalCount = jsonObj.getInt("total_count");
		}
		catch(JSONException jsone){
			// Don't fail altogether
			totalCount = -1;
		}
		
		JSONArray jsonItems = jsonObj.getJSONArray("items");
		if((jsonItems == null) || jsonItems.equals(JSONObject.NULL)){
			totalCount = -1;
			return;
		}
		
		for(int itemIdx=0;itemIdx<jsonItems.length();itemIdx++){
			Object jsonObject = jsonItems.get(itemIdx);
			if((jsonObject == null) || jsonObject.equals(JSONObject.NULL)){
				// Strange...?
				// Playlist playlist = new Playlist();
				// add(playlist);
			}
			else{
				JSONObject jsonItem = (JSONObject)jsonObject;
				Playlist playlist = new Playlist(jsonItem);
				add(playlist);
			}
		}
	}
	
	// T.B.D. tostring and tojson and toxmlstring...
	
	public Integer getTotalCount(){
		return this.totalCount;
	}
}
