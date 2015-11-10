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
			if (data.optString ("id") != "") {
				id =data.getString("id");
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
}
