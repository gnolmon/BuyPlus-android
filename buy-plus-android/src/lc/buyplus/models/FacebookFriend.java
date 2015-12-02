package lc.buyplus.models;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class FacebookFriend {
	String id;
	String name;
	String picture;
	
	public FacebookFriend() {
		super();
	}
	
	public FacebookFriend(JSONObject data) {
		
		try {

		id = data.getString("id");

		name = data.getString("name");
		Log.d("facename",name);
		Log.d("facename1",data.getString("name"));
		JSONObject yy = data.getJSONObject("picture");
		JSONObject kk = yy.getJSONObject("data");
		picture = kk.getString("url");


		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}
}
