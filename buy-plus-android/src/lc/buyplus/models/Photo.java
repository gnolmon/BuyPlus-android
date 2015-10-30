package lc.buyplus.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Photo {

	public int id;							
	public int active;
	public String caption;
	public String image;
	
	public Photo() {
		super();
	}
	
	public Photo(JSONObject data) {
		try {
			
			if (data.optString ("active") != "") {
				active = Integer.parseInt(data.getString("active"));
	        }
			if (data.optString ("id") != "") {
				id = Integer.parseInt(data.getString("id"));
	        }
			if (data.optString ("image") != "") {
				image = data.getString("image");
	        }
			if (data.optString ("caption") != "") {
				caption = data.getString("caption");
	        }
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public Photo(int id, int active, String caption, String image) {
		super();
		this.id = id;
		this.active = active;
		this.caption = caption;
		this.image = image;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	
}
