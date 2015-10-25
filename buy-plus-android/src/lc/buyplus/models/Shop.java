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
	
}
