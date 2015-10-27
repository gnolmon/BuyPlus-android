package lc.buyplus.models;

public class LowestPointGift {
	public int id;
	public String active;
	public String created_time;
	public String updated_time;
	public String name;
	public String image;
	public String image_thumbnail;
	public String point;
	public String	description;
	
	
	public LowestPointGift() {
		super();
	}

	public LowestPointGift(int id, String active, String created_time,
			String updated_time, String name, String image,
			String image_thumbnail, String point, String description) {
		super();
		//this.id = id;
		this.active = active;
		this.created_time = created_time;
		this.updated_time = updated_time;
		this.name = name;
		this.image = image;
		this.image_thumbnail = image_thumbnail;
		this.point = point;
		this.description = description;
	}
}
