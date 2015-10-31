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
import com.android.volley.Request.Method;
import com.android.volley.toolbox.Volley;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import lc.buyplus.R;
import lc.buyplus.adapter.StoreAdapter;
import lc.buyplus.cores.CoreActivity;
import lc.buyplus.cores.CoreFragment;
import lc.buyplus.cores.HandleRequest;
import lc.buyplus.models.Announcement;
import lc.buyplus.models.Friend;
import lc.buyplus.models.Gift;
import lc.buyplus.models.Notification;
import lc.buyplus.models.Photo;
import lc.buyplus.models.Shop;
import lc.buyplus.models.Store;
import lc.buyplus.models.UserAccount;

public class HomeFragment extends CoreFragment {
	private ListView listView;
	private StoreAdapter storeAdapter;
	private LayoutInflater inflaterActivity;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home, container, false);
		initViews(view);
		initModels();
		initAnimations();
		listView = (ListView) view.findViewById(R.id.listStore);
		inflaterActivity = inflater;
		api_get_all_shop(0,0,0);
		//api_get_all_announcements(2,0,0,0,0);
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

	public void api_get_all_shop(int latest_id, int oldest_id, int search){
	 	
    	Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", CanvasFragment.mUser.getAccessToken());
		params.put("latest_id", String.valueOf(latest_id));
		params.put("oldest_id", String.valueOf(oldest_id));
		params.put("search", String.valueOf(search));
			RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
			HandleRequest jsObjRequest = new HandleRequest(Method.GET,
					HandleRequest.build_link(HandleRequest.GET_ALL_SHOP, params), params, 
					new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						try {
							Log.d("api_get_all_shop",response.toString());
							JSONArray data_aray = response.getJSONArray("data");
							for (int i = 0; i < data_aray.length(); i++) {								 
	                            Shop shop = new Shop((JSONObject) data_aray.get(i));
	                            	if (shop != null){
	                            		Store.ShopsList.add(shop);
	                            	}
	                        }
							storeAdapter = new StoreAdapter(Store.ShopsList, inflaterActivity);
							
							listView.setAdapter(storeAdapter);
							storeAdapter.notifyDataSetChanged();
							
						} catch (JSONException e) {
							e.printStackTrace();
						}	
					}
				}, 
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.d("api_get_all_shop",error.toString());
					}
				});
			requestQueue.add(jsObjRequest);
	}

	public void api_get_my_shop(int mode){
	 	
    	Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", CanvasFragment.mUser.getAccessToken());
		//params.put("mode", String.valueOf(mode));
			RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
			HandleRequest jsObjRequest = new HandleRequest(Method.GET,
					HandleRequest.build_link(HandleRequest.GET_MY_SHOP, params), params, 
					new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.d("api_get_my_shop",response.toString());
						try {
							JSONArray data_aray = response.getJSONArray("data");
							for (int i = 0; i < data_aray.length(); i++) {								 
								Shop shop = new Shop((JSONObject) data_aray.get(i));
	                        }
							///////////////////////////
							//code here
							//////////////////////////
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
			HandleRequest jsObjRequest = new HandleRequest(Method.GET,
					HandleRequest.build_link(HandleRequest.GET_SHOP_INFO, params), params, 
					new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.d("api_get_shop_info",response.toString());
						try {
							Shop shop = new Shop(response.getJSONObject("data"));	
							///////////////////////////
							//code here
							//////////////////////////
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

	public void api_get_all_announcements(int type, int latest_id, int oldest_id, int mode, int search){
	 	
    	Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", CanvasFragment.mUser.getAccessToken());
		params.put("type", String.valueOf(type));
		params.put("latest_id", String.valueOf(latest_id));
		params.put("oldest_id", String.valueOf(oldest_id));
		params.put("mode", String.valueOf(mode));
		params.put("search", String.valueOf(search));
			RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
			HandleRequest jsObjRequest = new HandleRequest(Method.GET,
					HandleRequest.build_link(HandleRequest.GET_ALL_ANNOUNCEMENTS, params), params, 
					new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						try {
							Log.d("api_get_all_announcements",response.toString());
							JSONArray data_aray = response.getJSONArray("data");
							for (int i = 0; i < data_aray.length(); i++) {
								Announcement announcement = new Announcement((JSONObject) data_aray.get(i));
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
			HandleRequest jsObjRequest = new HandleRequest(Method.GET,
					HandleRequest.build_link(HandleRequest.GET_SHOP_ANNOUNCEMENTS, params), params, 
					new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.d("api_get_shop_announcements",response.toString());
						try {
							ArrayList<Announcement> AnnouncementsList = new ArrayList<Announcement>();
							JSONArray data_aray = response.getJSONArray("data");
							for (int i = 0; i < data_aray.length(); i++) {
								Announcement announcement = new Announcement((JSONObject) data_aray.get(i));
								AnnouncementsList.add(announcement);
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
			HandleRequest jsObjRequest = new HandleRequest(Method.GET,
					HandleRequest.build_link(HandleRequest.GET_SHOP_GIFTS, params), params, 
					new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.d("api_get_shop_gifts",response.toString());
						try {
							ArrayList<Gift> GiftsList = new ArrayList<Gift>();
							JSONArray data_aray = response.getJSONArray("data");
							for (int i = 0; i < data_aray.length(); i++) {
								Gift gift = new Gift((JSONObject) data_aray.get(i));
								GiftsList.add(gift);
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

	public void api_get_shop_friends(int shop_id){
	 	
    	Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", CanvasFragment.mUser.getAccessToken());
		params.put("shop_id", String.valueOf(shop_id));
			RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
			HandleRequest jsObjRequest = new HandleRequest(Method.GET,
					HandleRequest.build_link(HandleRequest.GET_SHOP_FRIENDS, params), params, 
					new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.d("api_get_shop_gifts",response.toString());
						try {
							ArrayList<Friend>FriendsList = new ArrayList<Friend>();
							JSONArray data_aray = response.getJSONArray("data");
							for (int i = 0; i < data_aray.length(); i++) {
								Friend friend = new Friend((JSONObject) data_aray.get(i));
								FriendsList.add(friend);
							}
						} catch (JSONException e) {

							e.printStackTrace();
						}	
						//code here
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
			HandleRequest jsObjRequest = new HandleRequest(Method.GET,
					HandleRequest.build_link(HandleRequest.GET_NOTIFICATIONS, params), params, 
					new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.d("api_get_notifications",response.toString());
						try {
							JSONArray data_aray = response.getJSONArray("data");
							for (int i = 0; i < data_aray.length(); i++) {
								Notification notification = new Notification((JSONObject) data_aray.get(i));
								Store.NotificationsList.add(notification);
							}					
						} catch (JSONException e) {

							e.printStackTrace();
						}	
						//code here
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
			HandleRequest jsObjRequest = new HandleRequest(Method.POST,
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
			HandleRequest jsObjRequest = new HandleRequest(Method.POST,
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
			HandleRequest jsObjRequest = new HandleRequest(Method.POST,
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
			HandleRequest jsObjRequest = new HandleRequest(Method.POST,
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
			HandleRequest jsObjRequest = new HandleRequest(Method.POST,
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
			HandleRequest jsObjRequest = new HandleRequest(Method.POST,
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
			HandleRequest jsObjRequest = new HandleRequest(Method.POST,
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
