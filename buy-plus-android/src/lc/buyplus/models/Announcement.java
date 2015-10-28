package lc.buyplus.models;

import java.util.ArrayList;

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
	public String start_time;
	public String end_time;
	public Announcement() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Announcement(int id, int shop_id, String content, int type,
			String created_time, String updated_time, int active,
			ArrayList<Photo> photos, Shop shop, String start_time,
			String end_time) {
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

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
}
