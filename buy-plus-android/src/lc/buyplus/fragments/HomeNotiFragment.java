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
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import lc.buyplus.R;
import lc.buyplus.adapter.AddFriendAdapter;
import lc.buyplus.adapter.FriendAdapter;
import lc.buyplus.cores.CoreActivity;
import lc.buyplus.cores.CoreFragment;
import lc.buyplus.cores.HandleRequest;
import lc.buyplus.customizes.DialogMessage;
import lc.buyplus.models.Friend;
import lc.buyplus.models.Store;

public class HomeNotiFragment extends CoreFragment {
	private LinearLayout mHomeTab, mPersonalTab, mLoyaltyCardTab, mNotiTab, mSettingTab;
	Display display;
	private ImageView imFb, imSearchFriend,mBack;
	private ListView listFriendFb;
	private LayoutInflater inflaterActivity;
	private FriendAdapter friendAdapter;
	private AddFriendAdapter addFriendAdapter;
	private EditText txtFind;
	private String search_params;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_noti_home, container, false);
		inflaterActivity = inflater;
		initViews(view);
		initModels();
		initAnimations();
		return view;
	}

	@Override
	public void onClick(View view) {
		Intent returnIntent;
		switch (view.getId()) {
		case R.id.fragment_canvas_back:
			mActivity.finish();
			break;
		case R.id.fragment_canvas_home_tab:
			CanvasFragment.mPager.setCurrentItem(0);
			returnIntent = new Intent();
			mActivity.setResult(2, returnIntent);
			mActivity.finish();
			break;
		case R.id.fragment_canvas_personal_tab:
			CanvasFragment.mPager.setCurrentItem(2);
			returnIntent = new Intent();
			mActivity.setResult(2, returnIntent);
			mActivity.finish();
			break;
		case R.id.fragment_canvas_loyaltycard_tab:
			CanvasFragment.mPager.setCurrentItem(3);
			returnIntent = new Intent();
			mActivity.setResult(2, returnIntent);
			mActivity.finish();
			break;
		case R.id.fragment_canvas_notifications_tab:
			CanvasFragment.mPager.setCurrentItem(4);
			returnIntent = new Intent();
			mActivity.setResult(2, returnIntent);
			mActivity.finish();
			break;
		case R.id.fragment_canvas_setting_tab:
			CanvasFragment.mPager.setCurrentItem(5);
			returnIntent = new Intent();
			mActivity.setResult(2, returnIntent);
			mActivity.finish();
			break;
		default:
			break;
		}
	}

	protected void initModels() {

	}

	@Override
	protected void initViews(View v) {
		mHomeTab = (LinearLayout) v.findViewById(R.id.fragment_canvas_home_tab);
		mPersonalTab = (LinearLayout) v.findViewById(R.id.fragment_canvas_personal_tab);
		mLoyaltyCardTab = (LinearLayout) v.findViewById(R.id.fragment_canvas_loyaltycard_tab);
		mNotiTab = (LinearLayout) v.findViewById(R.id.fragment_canvas_notifications_tab);
		mSettingTab = (LinearLayout) v.findViewById(R.id.fragment_canvas_setting_tab);
		listFriendFb = (ListView) v.findViewById(R.id.listAddFriend);
		mBack = (ImageView) v.findViewById(R.id.fragment_canvas_back);
		
		
		imFb = (ImageView) v.findViewById(R.id.imFb);
		txtFind = (EditText) v.findViewById(R.id.search_params);
		txtFind.setOnKeyListener(new OnKeyListener() {
		    public boolean onKey(View v, int keyCode, KeyEvent event) {
		        // If the event is a key-down event on the "enter" button
		        if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
		            (keyCode == KeyEvent.KEYCODE_ENTER)) {
		        	search_params = String.valueOf(txtFind.getText());
					api_search_friends_for_shop(Store.current_shop_id, search_params);
		          return true;
		        }
		        return false;
		    }
		});
		
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
				api_search_friends_for_shop(Store.current_shop_id, search_params);
			}
		});
		
		mHomeTab.setOnClickListener(this);
		mPersonalTab.setOnClickListener(this);
		mLoyaltyCardTab.setOnClickListener(this);
		mNotiTab.setOnClickListener(this);
		mSettingTab.setOnClickListener(this);
		mBack.setOnClickListener(this);
	}

	@Override
	protected void initAnimations() {

	}

	public static final long serialVersionUID = 6036846677812555352L;

	public static CoreActivity mActivity;
	public static HomeNotiFragment mInstance;

	public static HomeNotiFragment getInstance(CoreActivity activity) {
		if (mInstance == null) {
			mInstance = new HomeNotiFragment();
		}
		mActivity = activity;
		return mInstance;
	}

	public static HomeNotiFragment getInstance() {
		if (mInstance == null) {
			mInstance = new HomeNotiFragment();
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
	
public void api_search_friends_for_shop(int shop_id, String search){
	 	
    	Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", Store.user.getAccessToken());
		params.put("search", String.valueOf(search));
		params.put("shop_id", String.valueOf(shop_id));
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
							if (data_aray.length()==0){
								DialogMessage dialog = new DialogMessage(mActivity, "Người dùng này hiện không tồn tại trong hệ thống");
								dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
								dialog.show();
							}
							else{
								for (int i = 0; i < data_aray.length(); i++) {
									Friend friend = new Friend((JSONObject) data_aray.get(i));
									FriendsList.add(friend);
									
		                        }
								addFriendAdapter = new AddFriendAdapter(FriendsList, inflaterActivity);
								listFriendFb.setAdapter(addFriendAdapter);
								addFriendAdapter.notifyDataSetChanged();	
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
	
}
