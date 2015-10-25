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
}
