package lc.buyplus.models;

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
	
	public Gift(JSONObject gift) {
		id = Integer.parseInt(gift.getString("id"));
		name = gift.getString("name");
		point = Integer.parseInt(gift.getString("point"));
		image = gift.getString("image");
		image_thumbnail = gift.getString("image_thumbnail");
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
