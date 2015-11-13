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

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import lc.buyplus.R;
import lc.buyplus.activities.ShopInfoActivity;
import lc.buyplus.adapter.StoreAdapter;
import lc.buyplus.cores.CoreActivity;
import lc.buyplus.cores.CoreFragment;
import lc.buyplus.cores.HandleRequest;
import lc.buyplus.models.Announcement;
import lc.buyplus.models.FacebookFriend;
import lc.buyplus.models.Friend;
import lc.buyplus.models.Gift;
import lc.buyplus.models.Notification;
import lc.buyplus.models.Photo;
import lc.buyplus.models.Shop;
import lc.buyplus.models.Store;
import lc.buyplus.models.UserAccount;
import android.widget.AdapterView.OnItemClickListener;

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
		api_get_all_shop(1,0,0);
		api_search_friends_for_shop(1,"");
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
							listView.setOnItemClickListener(new OnItemClickListener() {
							      public void onItemClick(AdapterView<?> parent, View view,
							          int position, long id) {
							             Intent shopInfoActivity = new Intent(mActivity,ShopInfoActivity.class);
							             Bundle b = new Bundle();
							             b.putInt("shop_id", storeAdapter.getItem_id(position)); //Your id
							             shopInfoActivity.putExtras(b); //Put your id to your next Intent
							             startActivity(shopInfoActivity);
							      }
							    });
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
	
public void api_search_friends_for_shop(int shop_id, String search){
	 	
    	Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", CanvasFragment.mUser.getAccessToken());
		params.put("shop_id", String.valueOf(shop_id));
		params.put("search", String.valueOf(search));
			RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
			HandleRequest jsObjRequest = new HandleRequest(Method.GET,
					HandleRequest.build_link(HandleRequest.SEARCH_FRIENDS_FOR_SHOP, params), params, 
					new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						try {
							Log.d("api_search_friends_for_shop",response.toString());
							JSONArray data_aray = response.getJSONArray("data");
							ArrayList<Friend> FriendsList = new ArrayList<Friend>();
							for (int i = 0; i < data_aray.length(); i++) {								 
	                            Friend friend = new Friend((JSONObject) data_aray.get(i));
	                            	if (friend != null){
	                            		FriendsList.add(friend);
	                            	}
	                        }
							storeAdapter = new StoreAdapter(Store.ShopsList, inflaterActivity);
							
							listView.setAdapter(storeAdapter);
							listView.setOnItemClickListener(new OnItemClickListener() {
							      public void onItemClick(AdapterView<?> parent, View view,
							          int position, long id) {
							             Intent shopInfoActivity = new Intent(mActivity,ShopInfoActivity.class);
							             Bundle b = new Bundle();
							             b.putInt("shop_id", storeAdapter.getItem_id(position)); //Your id
							             shopInfoActivity.putExtras(b); //Put your id to your next Intent
							             startActivity(shopInfoActivity);
							      }
							    });
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
