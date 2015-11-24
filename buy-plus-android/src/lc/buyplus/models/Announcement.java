package lc.buyplus.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Announcement {
	public int id;
	public int shop_id;
	public String content;
	public int type;
	public String created_time;
	public String updated_time;
	public int active;

	public ArrayList<Photo> photos = new ArrayList<Photo>();
	public Shop shop;
	public Long start_time = (long) 0;
	public String end_time;

	public Announcement() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Announcement(int id, int shop_id, String content, int type, String created_time, String updated_time,
			int active, ArrayList<Photo> photos, Shop shop, Long start_time, String end_time) {
		super();
		this.id = id;
		this.shop_id = shop_id;
		this.content = content;
		this.type = type;
		this.created_time = created_time;
		this.updated_time = updated_time;
		this.active = active;
		this.photos = photos;
		this.shop = shop;
		this.start_time = start_time;
		this.end_time = end_time;
	}

	public Announcement(JSONObject data) {
		try {
			if (data.optString("id") != "") {
				id = Integer.parseInt(data.getString("id"));
			}
			if (data.optString("shop_id") != "") {
				shop_id = Integer.parseInt(data.getString("shop_id"));
				if (data.optString("active") != "") {
					active = Integer.parseInt(data.getString("active"));
				}
			}
			if (data.optString("type") != "") {
				type = Integer.parseInt(data.getString("type"));
			}
			if (data.optString("created_time") != "") {
				created_time = data.getString("created_time");
			}
			if (data.optString("updated_time") != "") {
				updated_time = data.getString("updated_time");
			}
			if (data.optString("content") != "") {
				content = data.getString("content");
			}
			JSONArray photo_array = data.getJSONArray("photos");
			if (photo_array != null) {
				for (int j = 0; j < photo_array.length(); j++) {
					Photo photo = new Photo((JSONObject) photo_array.get(j));
					photos.add(photo);
				}
			}

			shop = new Shop();
			JSONObject shop_announcements = data.getJSONObject("shop");
			if (shop_announcements != null) {
				shop.id = Integer.parseInt(shop_announcements.getString("id"));
				shop.name = shop_announcements.getString("name");
				shop.image_thumbnail = shop_announcements.getString("image_thumbnail");
				shop.image = shop_announcements.getString("image");
				shop.address = shop_announcements.getString("address");
			}

			// code here
			
			if (data.optString("start_time") != "" && data.optString("start_time") != "null") {
				start_time = Long.valueOf(data.getString("start_time"));
			}
			if (data.optString("end_time") != "") {
				end_time = data.getString("end_time");
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

	public int getShop_id() {
		return shop_id;
	}

	public void setShop_id(int shop_id) {
		this.shop_id = shop_id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public ArrayList<Photo> getPhotos() {
		return photos;
	}

	public void setPhotos(ArrayList<Photo> photos) {
		this.photos = photos;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public Long getStart_time() {
		return start_time;
	}

	public void setStart_time(Long start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
}
