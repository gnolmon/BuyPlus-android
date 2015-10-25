package lc.buyplus.models;

public class Friend {
		int id;
		int active;
		String name;
		String image;
		String image_thumbnail;
		public Friend() {
			super();
		}
		
		public Friend(int id, int active, String name, String image,
				String image_thumbnail) {
			super();
			this.id = id;
			this.active = active;
			this.name = name;
			this.image = image;
			this.image_thumbnail = image_thumbnail;
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
}
