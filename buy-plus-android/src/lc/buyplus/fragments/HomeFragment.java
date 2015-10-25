package lc.buyplus.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import lc.buyplus.R;
import lc.buyplus.cores.CoreActivity;
import lc.buyplus.cores.CoreFragment;
import lc.buyplus.cores.HandleRequest;
import lc.buyplus.models.Announcement;
import lc.buyplus.models.LowestPointGift;
import lc.buyplus.models.Photo;
import lc.buyplus.models.Shop;
import lc.buyplus.models.Store;

public class HomeFragment extends CoreFragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home, container, false);
		initViews(view);
		initModels();
		initAnimations();
		api_get_all_shop(0,0);
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(500);
					// load data
//					switchFragmentWithAnimation(CanvasFragment.getInstance(mActivity), 100, 200);
					mFragmentManager.beginTransaction().replace(R.id.canvas, LoginFragment.getInstance(mActivity)).commit();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
		return view;
	}
	@Override
	public void onClick(View view) {
		switch(view.getId()) {

		}
	}
	
	protected void initModels() {

	}

	@Override
	protected void initViews(View v) {

	}

	@Override
	protected void initAnimations() {
		
	}
	
	public void api_get_all_shop(int latest_id, int oldest_id){
	 	
    	Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", CanvasFragment.mUser.getAccessToken());
		params.put("latest_id", String.valueOf(latest_id));
		params.put("oldest_id", String.valueOf(oldest_id));
			RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
			HandleRequest jsObjRequest = new HandleRequest(
					HandleRequest.GET_ALL_SHOP, params, 
					new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						try {
							Log.d("api_get_all_shop",response.toString());
							JSONArray data_aray = response.getJSONArray("data");
							for (int i = 0; i < data_aray.length(); i++) {								 
	                            JSONObject data = (JSONObject) data_aray.get(i);
	                            Shop shop = new Shop();
	                            shop.id = Integer.parseInt(data.getString("id"));
	                            shop.name = data.getString("name");
	                            shop.address = data.getString("email");
	                            shop.description = data.getString("description");
	                            shop.email = data.getString("email");
	                            shop.phone = data.getString("phone");
	                            shop.website = data.getString("website");
	                            shop.allow_circle = data.getString("allow_circle");
	                            shop.max_friend_in_circle = data.getString("max_friend_in_circle");
	                            shop.facebook_id = data.getString("facebook_id");
	                            shop.lat = data.getString("lat");
	                            shop.lng = data.getString("lng");
	                            shop.image = data.getString("image");
	                            shop.image_thumbnail = data.getString("image_thumbnail");

								JSONObject current_customer_shop = data.getJSONObject("current_customer_shop");
								shop.current_customer_shop_id = current_customer_shop.getString("id");
								shop.current_customer_shop_point = current_customer_shop.getString("point");
								shop.current_customer_shop_created_time = current_customer_shop.getString("created_time");
								
								LowestPointGift lowestPointGift = new LowestPointGift();
								JSONObject lowest_point_gift = data.getJSONObject("lowest_point_gift");
								lowestPointGift.id = Integer.parseInt(lowest_point_gift.getString("id"));
								lowestPointGift.name = lowest_point_gift.getString("name");
								lowestPointGift.point = lowest_point_gift.getString("point");
								lowestPointGift.image = lowest_point_gift.getString("image");
								lowestPointGift.image_thumbnail = lowest_point_gift.getString("image_thumbnail");
								shop.lowestPointGift = lowestPointGift;
								
								Store.ShopsList.add(shop);
	                        }
							/////////////////////////////////////////////////////////////
							//code here
							/////////////////////////////////////////////////////////////
						} catch (JSONException e) {

							e.printStackTrace();
						}	
					}
				}, 
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
					}
				});
			requestQueue.add(jsObjRequest);
	}

	public void api_get_my_shop(){
	 	
    	Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", CanvasFragment.mUser.getAccessToken());
			RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
			HandleRequest jsObjRequest = new HandleRequest(
					HandleRequest.GET_MY_SHOP, params, 
					new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.d("api_get_my_shop",response.toString());
						try {
							JSONArray data_aray = response.getJSONArray("data");
							for (int i = 0; i < data_aray.length(); i++) {								 
	                            JSONObject data = (JSONObject) data_aray.get(i);
	                            
								int id = Integer.parseInt(data.getString("id"));
								String name = data.getString("name");
								String address = data.getString("email");
								String description = data.getString("description");
								String email = data.getString("email");
								String phone = data.getString("phone");
								String website = data.getString("website");
								String allow_circle = data.getString("allow_circle");
								String max_friend_in_circle = data.getString("max_friend_in_circle");
								String facebook_id = data.getString("facebook_id");
								String lat = data.getString("lat");
								String lng = data.getString("lng");
								String image = data.getString("image");
								String image_thumbnail = data.getString("image_thumbnail");
								// code here
								
								Log.d("api_get_my_shop",image_thumbnail);
								JSONObject current_customer_shop = data.getJSONObject("current_customer_shop");
								String current_customer_shop_id = data.getString("id");
								String current_customer_shop_point = data.getString("point");
								String current_customer_shop_created_time = data.getString("created_time");
								// code here
								
								JSONObject lowest_point_gift = data.getJSONObject("lowest_point_gift");
								String lowest_point_gift_id = data.getString("id");
								String lowest_point_gift_name = data.getString("name");
								String lowest_point_gift_point = data.getString("point");
								String lowest_point_gift_image = data.getString("image");
								String lowest_point_gift_image_thumbnail = data.getString("image_thumbnail");
								// code here
	                        }
						} catch (JSONException e) {

							e.printStackTrace();
						}	
					}
				}, 
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
					}
				});
			requestQueue.add(jsObjRequest);
	}

	public void api_get_shop_info(int shop_id){
	 	
    	Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", CanvasFragment.mUser.getAccessToken());
		params.put("shop_id", String.valueOf(shop_id));
			RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
			HandleRequest jsObjRequest = new HandleRequest(
					HandleRequest.GET_MY_SHOP, params, 
					new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.d("api_get_shop_info",response.toString());
						try {
									 
							JSONObject data = response.getJSONObject("data");
                            
							int id = Integer.parseInt(data.getString("id"));
							String name = data.getString("name");
							String address = data.getString("email");
							String description = data.getString("description");
							String email = data.getString("email");
							String phone = data.getString("phone");
							String website = data.getString("website");
							String allow_circle = data.getString("allow_circle");
							String max_friend_in_circle = data.getString("max_friend_in_circle");
							String facebook_id = data.getString("facebook_id");
							String lat = data.getString("lat");
							String lng = data.getString("lng");
							String image = data.getString("image");
							String image_thumbnail = data.getString("image_thumbnail");
							Log.d("api_get_shop_info",image_thumbnail);
							
							JSONObject current_customer_shop = data.getJSONObject("current_customer_shop");
							String current_customer_shop_id = data.getString("id");
							String current_customer_shop_point = data.getString("point");
							String current_customer_shop_created_time = data.getString("created_time");
							// code here
							
							JSONArray friends = response.getJSONArray("friends");
							for (int i = 0; i < friends.length(); i++) {								 
	                            JSONObject friend = (JSONObject) friends.get(i);
								JSONObject lowest_point_gift = friend.getJSONObject("lowest_point_gift");
								String friend_id = friend.getString("id");
								String friend_active = friend.getString("active");
								String friend_name = friend.getString("name");
								String friend_image = friend.getString("image");
								String friend_image_thumbnail = friend.getString("image_thumbnail");
								// code here
							}
						} catch (JSONException e) {

							e.printStackTrace();
						}	
					}
				}, 
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
					}
				});
			requestQueue.add(jsObjRequest);
	}

	public void api_get_all_announcements(int type, int latest_id, int oldest_id){
	 	
    	Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", CanvasFragment.mUser.getAccessToken());
		params.put("type", String.valueOf(type));
		params.put("latest_id", String.valueOf(latest_id));
		params.put("oldest_id", String.valueOf(oldest_id));
			RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
			HandleRequest jsObjRequest = new HandleRequest(
					HandleRequest.GET_SHOP_ANNOUNCEMENTS, params, 
					new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						try {
							Log.d("api_get_all_announcements",response.toString());
							JSONArray data_aray = response.getJSONArray("data");
							for (int i = 0; i < data_aray.length(); i++) {
								Announcement announcement = new Announcement();
	                            JSONObject data = (JSONObject) data_aray.get(i);
	                            
	                            announcement.id = Integer.parseInt(data.getString("id"));
	                            announcement.shop_id = Integer.parseInt(data.getString("shop_id"));
	                            announcement.content = data.getString("content");
	                            announcement.type = Integer.parseInt(data.getString("type"));
	                            announcement.created_time = data.getString("created_time");
	                            announcement.updated_time = data.getString("updated_time");
	                            announcement.active = Integer.parseInt(data.getString("active"));
								
								ArrayList<Photo> photosList = new ArrayList<Photo>();
								
								JSONArray photos = data.getJSONArray("photos");
								for (int j = 0; j < photos.length(); j++) {
									Photo photo = new Photo();
		                            JSONObject photo_json = (JSONObject) photos.get(i);
		                            photo.id = Integer.parseInt(photo_json.getString("id"));						
		                            photo.active = Integer.parseInt(photo_json.getString("active"));
		                            photo.caption = photo_json.getString("caption");
		                            photo.image = photo_json.getString("image");
		                            photosList.add(photo);
								}
								announcement.photos = photosList;
								
								Shop shop = new Shop();
								JSONObject shop_announcements = data.getJSONObject("shop");
								shop.id = Integer.parseInt(shop_announcements.getString("id"));						
								//shop.active = Integer.parseInt(shop_announcements.getString("active"));
								shop.name = shop_announcements.getString("name");
								shop.image_thumbnail = shop_announcements.getString("image_thumbnail");
								shop.image = shop_announcements.getString("image");
								shop.address = shop_announcements.getString("address");
								// code here
								
								announcement.start_time = data.getString("start_time");
								announcement.end_time = data.getString("end_time");
								
								Store.AnnouncementsList.add(announcement);
								
	                        }
						} catch (JSONException e) {

							e.printStackTrace();
						}	
					}
				}, 
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
					}
				});
			requestQueue.add(jsObjRequest);
	}	
	
	public void api_get_shop_announcements(int shop_id, int type, int latest_id, int oldest_id){
	 	
    	Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", CanvasFragment.mUser.getAccessToken());
		params.put("shop_id", String.valueOf(shop_id));
		params.put("type", String.valueOf(type));
		params.put("latest_id", String.valueOf(latest_id));
		params.put("oldest_id", String.valueOf(oldest_id));
			RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
			HandleRequest jsObjRequest = new HandleRequest(
					HandleRequest.GET_SHOP_ANNOUNCEMENTS, params, 
					new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.d("api_get_shop_announcements",response.toString());
						try {
							JSONArray data_aray = response.getJSONArray("data");
							for (int i = 0; i < data_aray.length(); i++) {								 
	                            JSONObject data = (JSONObject) data_aray.get(i);
	                            
								int id = Integer.parseInt(data.getString("id"));
								int shop_id = Integer.parseInt(data.getString("shop_id"));
								String content = data.getString("content");
								int type = Integer.parseInt(data.getString("type"));
								String created_time = data.getString("created_time");
								String updated_time = data.getString("updated_time");
								
								
								JSONArray photos = data.getJSONArray("photos");
								for (int j = 0; j < photos.length(); j++) {								 
		                            //JSONObject photo = (JSONObject) photos.get(i);
									//String photo_id = photo.getString("id");							
									//int photo_active = Integer.parseInt(photo.getString("active"));
									//String photo_caption = photo.getString("caption");
									//String photo_image = photo.getString("image");
								}
								// code here
								
								JSONObject shop_announcements = data.getJSONObject("shop");
								String shop_announcements_id = shop_announcements.getString("id");							
								int shop_announcements_active = Integer.parseInt(data.getString("active"));
								String shop_announcements_name = shop_announcements.getString("name");
								String shop_announcements_image_thumbnail = shop_announcements.getString("image_thumbnail");
								String shop_announcements_image = shop_announcements.getString("image");
								String shop_announcements_address = shop_announcements.getString("address");
								
								// code here
								int active = Integer.parseInt(data.getString("active"));
								String start_time = data.getString("start_time");
								String end_time = data.getString("end_time");
								
								Log.d("api_get_shop_announcements",end_time);
	                        }
						} catch (JSONException e) {

							e.printStackTrace();
						}	
					}
				}, 
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
					}
				});
			requestQueue.add(jsObjRequest);
	}	

	public void api_get_shop_gifts(int shop_id){
	 	
    	Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", CanvasFragment.mUser.getAccessToken());
		params.put("shop_id", String.valueOf(shop_id));
			RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
			HandleRequest jsObjRequest = new HandleRequest(
					HandleRequest.GET_SHOP_GIFTS, params, 
					new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.d("api_get_shop_gifts",response.toString());
						try {
									 
							JSONObject data = response.getJSONObject("data");
                            
							int id = Integer.parseInt(data.getString("id"));
							int active = Integer.parseInt(data.getString("active"));						
							String created_time = data.getString("created_time");
							String updated_time = data.getString("updated_time");
							String name = data.getString("name");
							String image = data.getString("image");
							String image_thumbnail = data.getString("image_thumbnail");
							int point = Integer.parseInt(data.getString("point"));
							String description = data.getString("description");
							
							Log.d("api_get_shop_gifts",image_thumbnail);

						} catch (JSONException e) {

							e.printStackTrace();
						}	
					}
				}, 
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
					}
				});
			requestQueue.add(jsObjRequest);
	}

	public void api_get_notifications(int latest_id, int oldest_id){
	 	
		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", CanvasFragment.mUser.getAccessToken());
		params.put("latest_id", String.valueOf(latest_id));
		params.put("oldest_id", String.valueOf(oldest_id));
			RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
			HandleRequest jsObjRequest = new HandleRequest(
					HandleRequest.GET_NOTIFICATIONS, params, 
					new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.d("api_get_notifications",response.toString());
						try {
							JSONArray data_aray = response.getJSONArray("data");
							for (int i = 0; i < data_aray.length(); i++) {								 
	                            JSONObject data = (JSONObject) data_aray.get(i);
	                            
								int id = Integer.parseInt(data.getString("id"));
								int active = Integer.parseInt(data.getString("active"));
								String created_time = data.getString("created_time");
								String updated_time = data.getString("updated_time");
								int customer_id = Integer.parseInt(data.getString("customer_id"));
								int type = Integer.parseInt(data.getString("type"));
								String params = data.getString("params");
								String is_read = data.getString("is_read");
								
								Log.d("api_get_notifications",is_read);
	                        }
						} catch (JSONException e) {

							e.printStackTrace();
						}	
					}
					}, 
					new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
						}
					});
			requestQueue.add(jsObjRequest);
	}

	public void api_read_notifications(){
	 	
		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", CanvasFragment.mUser.getAccessToken());
			RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
			HandleRequest jsObjRequest = new HandleRequest(
					HandleRequest.READ_NOTIFICATIONS, params, 
					new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.d("api_read_notifications",response.toString());
						// code here
					}
					}, 
					new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
						}
					});
			requestQueue.add(jsObjRequest);
	}

	public void api_send_request_join_shop_to_friend(int shop_id, int temp_id){
	 	
		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", CanvasFragment.mUser.getAccessToken());
		params.put("shop_id", String.valueOf(shop_id));
		params.put("temp_id", String.valueOf(temp_id));
			RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
			HandleRequest jsObjRequest = new HandleRequest(
					HandleRequest.SEND_REQUEST_JOIN_SHOP_TO_FRIEND, params, 
					new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.d("api_send_request_join_shop_to_friend",response.toString());
						// code here
					}
					}, 
					new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
						}
					});
			requestQueue.add(jsObjRequest);
	}
	
	//accept_type:  accept, deny, deny_forever	
	public void api_response_join_shop(int request_id, String accept_type){
	 	
		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", CanvasFragment.mUser.getAccessToken());
		params.put("request_id", String.valueOf(request_id));
		params.put("accept_type", accept_type);
			RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
			HandleRequest jsObjRequest = new HandleRequest(
					HandleRequest.RESPONSE_REQUEST__JOIN_SHOP, params, 
					new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.d("api_response_join_shop",response.toString());
						// code here
					}
					}, 
					new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
						}
					});
			requestQueue.add(jsObjRequest);
	}
	
	public void api_join_shop(int shop_id){
	 	
    	Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", CanvasFragment.mUser.getAccessToken());
		params.put("shop_id", String.valueOf(shop_id));
			RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
			HandleRequest jsObjRequest = new HandleRequest(
					HandleRequest.JOIN_SHOP, params, 
					new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.d("api_join_shop",response.toString());
						// code here
					}
					}, 
					new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
						}
					});
			requestQueue.add(jsObjRequest);
	}
	
	public void api_leave_shop(int shop_id){
	 	
    	Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", CanvasFragment.mUser.getAccessToken());
		params.put("shop_id", String.valueOf(shop_id));
			RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
			HandleRequest jsObjRequest = new HandleRequest(
					HandleRequest.LEAVE_SHOP, params, 
					new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.d("api_leave_shop",response.toString());
						// code here
					}
					}, 
					new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
						}
					});
			requestQueue.add(jsObjRequest);
	}
	
	public void get_added_point_in_shop(int shop_id){
	 	
    	Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", CanvasFragment.mUser.getAccessToken());
		params.put("shop_id", String.valueOf(shop_id));
			RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
			HandleRequest jsObjRequest = new HandleRequest(
					HandleRequest.GET_ADDED_POINT_IN_SHOP, params, 
					new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.d("get_added_point_in_shop",response.toString());
						// code here
					}
					}, 
					new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
						}
					});
			requestQueue.add(jsObjRequest);
	}
	
	public void get_added_point_history(int shop_id){
 	
		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", CanvasFragment.mUser.getAccessToken());
		params.put("shop_id", String.valueOf(shop_id));
			RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
			HandleRequest jsObjRequest = new HandleRequest(
					HandleRequest.GET_ADDED_POINT_HISTORY, params, 
					new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.d("get_added_point_history",response.toString());
						// code here
					}
					}, 
					new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
						}
					});
			requestQueue.add(jsObjRequest);
	}
	
	
	public static final long serialVersionUID = 6036846677812555352L;
	
	public static CoreActivity mActivity;
	public static HomeFragment mInstance;
	public static HomeFragment getInstance(CoreActivity activity) {
		if (mInstance == null) {
			mInstance = new HomeFragment();
		}
		mActivity = activity;
		return mInstance;
	}
	public static HomeFragment getInstance() {
		if (mInstance == null) {
			mInstance = new HomeFragment();
		}
		return mInstance;
	}
	@Override
	protected void initListener() {
		// TODO Auto-generated method stub
		
	}
}
