package lc.buyplus.models;

public class Photo {

	public int id;							
	public int active;
	public String caption;
	public String image;
	
	public Photo() {
		super();
	}
	
	public Photo(int id, int active, String caption, String image) {
		super();
		this.id = id;
		this.active = active;
		this.caption = caption;
		this.image = image;
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

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	
}
