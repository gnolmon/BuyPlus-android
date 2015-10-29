package lc.buyplus.models;

public class Notification {
	private int id;
	private int active;
	private String created_time;
	private String updated_time;
	private int customer_id;
	private int type;
	private String params;
	private String is_read;
	
	public Notification() {
		super();
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
}
