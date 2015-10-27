package lc.buyplus.models;

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
	public String current_customer_shop_point;
	public String current_customer_shop_created_time;
	
	public LowestPointGift lowestPointGift;
	
	public Shop() {
		super();
	}

	public Shop(int id, String name, String address, String description,
			String email, String phone, String website, String allow_circle,
			String max_friend_in_circle, String facebook_id, String lat,
			String lng, String image, String image_thumbnail,
			String current_customer_shop_id,
			String current_customer_shop_point,
			String current_customer_shop_created_time,
			LowestPointGift lowestPointGift) {
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

	public String getCurrent_customer_shop_point() {
		return current_customer_shop_point;
	}

	public void setCurrent_customer_shop_point(String current_customer_shop_point) {
		this.current_customer_shop_point = current_customer_shop_point;
	}

	public String getCurrent_customer_shop_created_time() {
		return current_customer_shop_created_time;
	}

	public void setCurrent_customer_shop_created_time(String current_customer_shop_created_time) {
		this.current_customer_shop_created_time = current_customer_shop_created_time;
	}

	public LowestPointGift getLowestPointGift() {
		return lowestPointGift;
	}

	public void setLowestPointGift(LowestPointGift lowestPointGift) {
		this.lowestPointGift = lowestPointGift;
	}
	
}
