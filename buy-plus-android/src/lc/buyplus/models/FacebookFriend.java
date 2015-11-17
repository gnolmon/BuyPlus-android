package lc.buyplus.models;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class FacebookFriend {
	int id;
	String name;
	String picture;
	
	public FacebookFriend() {
		super();
	}
	
	public FacebookFriend(JSONObject data) {
		try {
			if (data.optString ("id") != "") {
				id = Integer.parseInt(data.getString("id"));
	        }
			if (data.optString ("name") != "") {
				name = data.getString("name");
	        }
			if (data.optJSONObject("picture") != null) {
				JSONObject yy = data.getJSONObject("picture");
				JSONObject kk = yy.getJSONObject("data");
				picture = kk.getString("url");
	        }

		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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
