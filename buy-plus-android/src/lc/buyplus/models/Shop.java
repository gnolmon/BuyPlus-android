package lc.buyplus.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Shop {
	public int id;
	public String name;
	public String address;
	public String description;
	public String email;
	public String phone;
	public String website;
	public String allow_circle;
	public String max_friend_in_circle;
	public String facebook_id;
	public String lat;
	public String lng;
	public String image;
	public String image_thumbnail;
	
	public String current_customer_shop_id;
	public int current_customer_shop_point = 0;
	public String current_customer_shop_created_time;
	
	public Gift lowestPointGift;
	
	public ArrayList<Gift> GiftsList = new ArrayList<Gift>();
	public ArrayList<Friend> FriendsList = new ArrayList<Friend>();
	public ArrayList<Announcement> AnnouncementsList = new ArrayList<Announcement>();
	public boolean canEx = false;
	public Shop() {
		super();
	}
	
	public Shop(JSONObject data) {
		try {
			
			if (data.optString ("id") != "") {
				id = Integer.parseInt(data.getString("id"));
			}
			
			if (data.optString ("name") != "") {
				name = data.getString("name");
			}
			if (data.optString ("address") != "") {
				address = data.getString("address");
			}
			if (data.optString ("description") != "") {
				description = data.getString("description");
			}
			if (data.optString ("email") != "") {
				email = data.getString("email");
			}
			if (data.optString ("phone") != "") {
				phone = data.getString("phone");
			}
			if (data.optString ("website") != "") {
				website = data.getString("website");
			}
			if (data.optString ("allow_circle") != "") {
				allow_circle = data.getString("allow_circle");
			}
			if (data.optString ("max_friend_in_circle") != "") {
				max_friend_in_circle = data.getString("max_friend_in_circle");
			}
			if (data.optString ("facebook_id") != "") {
				facebook_id = data.getString("facebook_id");
			}
			if (data.optString ("lat") != "") {
				lat = data.getString("lat");
			}
			if (data.optString ("lat") != "") {
				image = data.getString("image");
			}
			if (data.optString ("lng") != "") {
				lng = data.getString("lng");
			}
			if (data.optString ("current_customer_shop") != "") {
				image_thumbnail = data.getString("image_thumbnail");
			}
			
			
	        
			if (data.optJSONObject("current_customer_shop") != null) {
	        	JSONObject current_customer_shop = data.getJSONObject("current_customer_shop");
				current_customer_shop_id = current_customer_shop.getString("id");
				current_customer_shop_point = Integer.parseInt(current_customer_shop.getString("point"));
				current_customer_shop_created_time = current_customer_shop.getString("created_time");
	        }
	        
			Log.d("sad","sad");
			if (data.optJSONObject("lowest_point_gift") != null) {
	        	lowestPointGift = new Gift(data.getJSONObject("lowest_point_gift"));
	        	Log.d("sad",String.valueOf(lowestPointGift.getPoint()));
	        	if (current_customer_shop_point > lowestPointGift.getPoint()) canEx = true;
		    }
			
			if (data.optJSONArray("friends") != null) {
				JSONArray friends = data.getJSONArray("friends");
				for (int i = 0; i < friends.length(); i++) {								 
	                Friend friend = new Friend((JSONObject) friends.get(i));
	                FriendsList.add(friend);
				}
			}
					
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
								

	}
	
	 
	public boolean isCanEx() {
		return canEx;
	}

	public void setCanEx(boolean canEx) {
		this.canEx = canEx;
	}

	public Shop(int id, String name, String address, String description,
			String email, String phone, String website, String allow_circle,
			String max_friend_in_circle, String facebook_id, String lat,
			String lng, String image, String image_thumbnail,
			String current_customer_shop_id,
			int current_customer_shop_point,
			String current_customer_shop_created_time,
			Gift lowestPointGift) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.description = description;
		this.email = email;
		this.phone = phone;
		this.website = website;
		this.allow_circle = allow_circle;
		this.max_friend_in_circle = max_friend_in_circle;
		this.facebook_id = facebook_id;
		this.lat = lat;
		this.lng = lng;
		this.image = image;
		this.image_thumbnail = image_thumbnail;
		this.current_customer_shop_id = current_customer_shop_id;
		this.current_customer_shop_point = current_customer_shop_point;
		this.current_customer_shop_created_time = current_customer_shop_created_time;
		this.lowestPointGift = lowestPointGift;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getAllow_circle() {
		return allow_circle;
	}

	public void setAllow_circle(String allow_circle) {
		this.allow_circle = allow_circle;
	}

	public String getMax_friend_in_circle() {
		return max_friend_in_circle;
	}

	public void setMax_friend_in_circle(String max_friend_in_circle) {
		this.max_friend_in_circle = max_friend_in_circle;
	}

	public String getFacebook_id() {
		return facebook_id;
	}

	public void setFacebook_id(String facebook_id) {
		this.facebook_id = facebook_id;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
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

	public String getCurrent_customer_shop_id() {
		return current_customer_shop_id;
	}

	public void setCurrent_customer_shop_id(String current_customer_shop_id) {
		this.current_customer_shop_id = current_customer_shop_id;
	}

	public int getCurrent_customer_shop_point() {
		return current_customer_shop_point;
	}

	public void setCurrent_customer_shop_point(int current_customer_shop_point) {
		this.current_customer_shop_point = current_customer_shop_point;
	}

	public String getCurrent_customer_shop_created_time() {
		return current_customer_shop_created_time;
	}

	public void setCurrent_customer_shop_created_time(String current_customer_shop_created_time) {
		this.current_customer_shop_created_time = current_customer_shop_created_time;
	}

	public Gift getLowestPointGift() {
		return lowestPointGift;
	}

	public void setLowestPointGift(Gift lowestPointGift) {
		this.lowestPointGift = lowestPointGift;
	}
	
}
