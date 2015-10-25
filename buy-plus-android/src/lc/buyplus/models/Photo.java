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
}
