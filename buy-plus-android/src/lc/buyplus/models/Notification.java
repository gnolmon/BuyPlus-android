package lc.buyplus.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Notification {
	
	private int id;
	private int active;
	private String created_time;
	private String updated_time;
	private int customer_id;
	private int type;
	private String params;
	private String is_read;
	private String message;
	private String image;
	private int response_result;


	public int getResponse_result() {
		return response_result;
	}

	public void setResponse_result(int response_result) {
		this.response_result = response_result;
	}

	public Notification() {
		super();
	}
	
	public Notification(JSONObject data) {
		try {
			response_result = 0;
			if (data.optString ("response_result") != "" && data.optString ("response_result") != "null") {
				response_result = Integer.parseInt(data.getString("response_result"));
	        }
			if (data.optString ("id") != "") {
				id = Integer.parseInt(data.getString("id"));
	        }
			if (data.optString ("is_read") != "") {
				is_read = data.getString("is_read");
	        }
			if (data.optString ("params") != "") {
				params = data.getString("params");
	        }
			else {
				params = "1:1";
			}
			if (data.optString ("active") != "") {
				active = Integer.parseInt(data.getString("active"));
	        }
			if (data.optString ("customer_id") != "") {
				customer_id = Integer.parseInt(data.getString("customer_id"));
	        }
			if (data.optString ("created_time") != "") {
				created_time = data.getString("created_time");
	        }
			if (data.optString ("updated_time") != "") {
				updated_time = data.getString("updated_time");
	        }	
			if (data.optString ("type") != "") {
				type = Integer.parseInt(data.getString("type"));
	        } 
			if (data.optString ("message") != "") {
				message = data.getString("message");
	        }
			if (data.optString ("image") != "") {
				image = data.getString("image");
	        }
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public Notification(int id, int active, String created_time,
			String updated_time, int customer_id, int type, String params,
			String is_read) {
		super();
		this.id = id;
		this.active = active;
		this.created_time = created_time;
		this.updated_time = updated_time;
		this.customer_id = customer_id;
		this.type = type;
		this.params = params;
		this.is_read = is_read;
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
	public int getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public String getIs_read() {
		return is_read;
	}
	public void setIs_read(String is_read) {
		this.is_read = is_read;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
}
