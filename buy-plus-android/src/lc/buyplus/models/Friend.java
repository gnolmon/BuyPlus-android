package lc.buyplus.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Friend {
		int id;
		int active;
		String name;
		String image;
		String image_thumbnail;
		public Friend() {
			super();
		}
		
		public Friend(JSONObject data) {
			try {
				if (data.optString ("id") != "") {
					id = Integer.parseInt(data.getString("id"));
		        }
				if (data.optString ("name") != "") {
					name = data.getString("name");
		        }
				if (data.optString ("image") != "") {
					image = data.getString("image");
		        }
				if (data.optString ("image_thumbnail") != "") {
					image_thumbnail = data.getString("image_thumbnail");
		        }
				if (data.optString ("active") != "") {
					active = Integer.parseInt(data.getString("active"));
		        }
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
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
