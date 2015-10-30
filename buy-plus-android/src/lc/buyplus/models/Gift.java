package lc.buyplus.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Gift {
	private int id;
	private int active;
	private String created_time;
	private String updated_time;
	private String name;
	private String image;
	private String image_thumbnail;
	private int point;
	private String description;
	
	public Gift() {
		super();
	}
	
	public Gift(JSONObject data) {
		try {
			if (data.optString ("id") != "") {
				id = Integer.parseInt(data.getString("id"));
	        }
			if (data.optString ("name") != "") {
				name = data.getString("name");
	        }
			if (data.optString ("image") != "") {
				image = data.getString("image");
	        }
			if (data.optString ("image_thumbnail") != "") {
				image_thumbnail = data.getString("image_thumbnail");
	        }
			if (data.optString ("active") != "") {
				active = Integer.parseInt(data.getString("active"));
	        }
			if (data.optString ("point") != "") {
				point = Integer.parseInt(data.getString("point"));
	        }
			if (data.optString ("created_time") != "") {
				created_time = data.getString("created_time");
	        }
			if (data.optString ("updated_time") != "") {
				updated_time = data.getString("updated_time");
	        }	
			if (data.optString ("description") != "") {
				description = data.getString("description");
	        }
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public Gift(int id, int active, String created_time, String updated_time,
			String name, String image, String image_thumbnail, int point,
			String description) {
		super();
		this.id = id;
		this.active = active;
		this.created_time = created_time;
		this.updated_time = updated_time;
		this.name = name;
		this.image = image;
		this.image_thumbnail = image_thumbnail;
		this.point = point;
		this.description = description;
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

	public String getCreated_time() {
		return created_time;
	}

	public void setCreated_time(String created_time) {
		this.created_time = created_time;
	}

	public String getUpdated_time() {
		return updated_time;
	}

	public void setUpdated_time(String updated_time) {
		this.updated_time = updated_time;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getImage_thumbnail() {
		return image_thumbnail;
	}

	public void setImage_thumbnail(String image_thumbnail) {
		this.image_thumbnail = image_thumbnail;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
