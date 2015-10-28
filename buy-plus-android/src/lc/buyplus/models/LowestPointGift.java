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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
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

	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
