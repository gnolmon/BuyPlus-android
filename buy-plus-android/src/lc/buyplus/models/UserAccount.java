package lc.buyplus.models;

public class UserAccount {
	String accessToken;
	private int id;
	private String phonenumbers;
	private String email;
	private String username;
	private String imageUrl, imageThumbnail;
	private int active;
	private String login_name;
	
	public UserAccount() {
		super();
	}
	
	public UserAccount(String accessToken, int id, String phonenumbers,
			String email, String username, String imageUrl,
			String imageThumbnail, String name, int active) {
		super();
		this.accessToken = accessToken;
		this.id = id;
		this.phonenumbers = phonenumbers;
		this.email = email;
		this.username = username;
		this.imageUrl = imageUrl;
		this.imageThumbnail = imageThumbnail;
		this.active = active;
		this.login_name = name;
	}
	
	
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPhonenumbers() {
		return phonenumbers;
	}
	public void setPhonenumbers(String phonenumbers) {
		this.phonenumbers = phonenumbers;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getImageThumbnail() {
		return imageThumbnail;
	}
	public void setImageThumbnail(String imageThumbnail) {
		this.imageThumbnail = imageThumbnail;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
	public String getLogin_name() {
		return login_name;
	}
	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}
}
