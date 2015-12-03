package lc.buyplus.models;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Friend {
		int id;
		int active;
		String name;
		String image;
		String image_thumbnail;
		int has_requested = -1;
		int has_joined = -1;
		int circle_id = -1;
		String created_time;
		String join_time;
		String request_time;
		String circle_time;
		public Friend() {
			super();
		}
		

		public Friend(JSONObject data) {
			has_requested = -1;
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
				if (data.optString ("has_requested") != "" && data.optString ("has_requested") != "null") {
					has_requested = Integer.parseInt(data.getString("has_requested"));
		        }
				if (data.optString ("has_joined") != "" && data.optString ("has_joined") != "null") {
					has_joined = Integer.parseInt(data.getString("has_joined"));
		        }				
				if (data.optString ("circle_id") != "" && data.optString ("circle_time") != "null") {
					circle_id = Integer.parseInt(data.getString("circle_id"));
		        }
				if (data.optString ("created_time") != "") {
					created_time = data.getString("created_time");
		        }
				if (data.optString ("join_time") != "") {
					join_time = data.getString("join_time");
		        }
				if (data.optString ("request_time") != "") {
					request_time = data.getString("request_time");
		        }
				if (data.optString ("circle_time") != "") {
					circle_time = data.getString("circle_time");
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


		public int getHas_requested() {
			return has_requested;
		}


		public void setHas_requested(int has_requested) {
			this.has_requested = has_requested;
		}


		public int getHas_joined() {
			return has_joined;
		}


		public void setHas_joined(int has_joined) {
			this.has_joined = has_joined;
		}


		public int getCircle_id() {
			return circle_id;
		}


		public void setCircle_id(int circle_id) {
			this.circle_id = circle_id;
		}


		public String getCreated_time() {
			return created_time;
		}


		public void setCreated_time(String created_time) {
			this.created_time = created_time;
		}


		public String getJoin_time() {
			return join_time;
		}


		public void setJoin_time(String join_time) {
			this.join_time = join_time;
		}


		public String getRequest_time() {
			return request_time;
		}


		public void setRequest_time(String request_time) {
			this.request_time = request_time;
		}


		public String getCircle_time() {
			return circle_time;
		}


		public void setCircle_time(String circle_time) {
			this.circle_time = circle_time;
		}
		
}
