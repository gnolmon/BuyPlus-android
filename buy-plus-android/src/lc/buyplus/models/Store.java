package lc.buyplus.models;

import java.util.ArrayList;

public class Store {
	public static ArrayList<Shop> ShopsList = new ArrayList<Shop>();
	public static ArrayList<Shop> ShopsListTmp = new ArrayList<Shop>();
	public static ArrayList<Announcement> AnnouncementsList = new ArrayList<Announcement>();
	public static ArrayList<Announcement> ShopAnnouncementsList = new ArrayList<Announcement>();
	public static ArrayList<Notification> NotificationsList = new ArrayList<Notification>();
	public static ArrayList<Shop> MyShopsList = new ArrayList<Shop>();
	public static ArrayList<Photo> PhotosList = new ArrayList<Photo>();
	
	public static ArrayList<FacebookFriend> FacebookFriendsList = new ArrayList<FacebookFriend>();
	
	public static boolean isConnectNetwotk = true;
	public static UserAccount user;
	
	public static int limit = 8;
	public static String current_shop_name;
	public static int current_shop_id; 
	public static Shop current_shop; 
	public static Announcement current_announcement; 
	public static Gift current_gift; 
	public static Notification current_notification; 
	public static String[] noti_params;
	public static String Shop_Search_param = "";
	public static Shop get_current_shop(){
		for (int i=0;i<ShopsList.size();i++){
			if (ShopsList.get(i).getId() == current_shop_id) return ShopsList.get(i);
		}
		return null;
	}
}
