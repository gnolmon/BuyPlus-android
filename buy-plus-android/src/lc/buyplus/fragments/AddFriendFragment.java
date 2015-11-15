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
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import lc.buyplus.R;
import lc.buyplus.adapter.AddFriendAdapter;
import lc.buyplus.adapter.FriendAdapter;
import lc.buyplus.adapter.ShopFriendAdapter;
import lc.buyplus.cores.CoreActivity;
import lc.buyplus.cores.CoreFragment;
import lc.buyplus.cores.HandleRequest;
import lc.buyplus.models.Friend;
import lc.buyplus.models.Store;
import lc.buyplus.models.UserAccount;

public class AddFriendFragment extends CoreFragment {
	Display display;
	private ImageView imFb, imSearchFriend;
	private ListView listFriendFb;
	private LayoutInflater inflaterActivity;
	private FriendAdapter friendAdapter;
	private AddFriendAdapter addFriendAdapter;
	private EditText txtFind;
	private String search_params;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_add_friend, container, false);
		inflaterActivity = inflater;
		initViews(view);
		initModels();
		initAnimations();
		return view;
	}

	@Override
	public void onClick(View v) {

	}

	protected void initModels() {

	}

	@Override
	protected void initViews(View v) {
		listFriendFb = (ListView) v.findViewById(R.id.listAddFriend);
		imFb = (ImageView) v.findViewById(R.id.imFb);
		txtFind = (EditText) v.findViewById(R.id.search_params);
		imFb.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				friendAdapter = new FriendAdapter(Store.FacebookFriendsList, inflaterActivity);
				listFriendFb.setAdapter(friendAdapter);
				friendAdapter.notifyDataSetChanged();	
			}
		});
		
		imSearchFriend = (ImageView) v.findViewById(R.id.imSearchFriend);
		imSearchFriend.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				search_params = String.valueOf(txtFind.getText());
				api_search_friends_for_shop(search_params);
			}
		});
		
	}

	@Override
	protected void initAnimations() {

	}

	public static final long serialVersionUID = 6036846677812555352L;

	public static CoreActivity mActivity;
	public static AddFriendFragment mInstance;

	public static AddFriendFragment getInstance(CoreActivity activity) {
		if (mInstance == null) {
			mInstance = new AddFriendFragment();
		}
		mActivity = activity;
		return mInstance;
	}

	public static AddFriendFragment getInstance() {
		if (mInstance == null) {
			mInstance = new AddFriendFragment();
		}
		return mInstance;
	}

	@Override
	protected void initListener() {
		// TODO Auto-generated method stub

	}
	
	public void api_send_request_join_shop_to_friend(int shop_id, int temp_id){
	 	
		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", Store.user.getAccessToken());
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
	
public void api_search_friends_for_shop(String search){
	 	
    	Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", Store.user.getAccessToken());
		params.put("search", String.valueOf(search));
		Log.d("search", String.valueOf(search));
			RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
			HandleRequest jsObjRequest = new HandleRequest(Method.GET,
					HandleRequest.build_link(HandleRequest.SEARCH_FRIENDS_FOR_SHOP, params), params, 
					new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						try {
							Log.d("api_search_friends_for_shop", response.toString());
							ArrayList<Friend> FriendsList = new ArrayList<Friend>();
							JSONArray data_aray = response.getJSONArray("data");
							for (int i = 0; i < data_aray.length(); i++) {
								Friend friend = new Friend((JSONObject) data_aray.get(i));
								FriendsList.add(friend);
								
	                        }
							addFriendAdapter = new AddFriendAdapter(FriendsList, inflaterActivity);
							listFriendFb.setAdapter(addFriendAdapter);
							addFriendAdapter.notifyDataSetChanged();	
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
	
}
