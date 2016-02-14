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
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;
import lc.buyplus.R;
import lc.buyplus.activities.AddFriendActivity;
import lc.buyplus.activities.LoginActivity;
import lc.buyplus.activities.ShopFriendActivity;
import lc.buyplus.activities.ShopInfoActivity;
import lc.buyplus.adapter.OnLoadMoreListener;
import lc.buyplus.adapter.ShopFriendAdapter;
import lc.buyplus.application.MonApplication;
import lc.buyplus.cores.CoreActivity;
import lc.buyplus.cores.CoreFragment;
import lc.buyplus.cores.HandleRequest;
import lc.buyplus.customizes.DialogMessage;
import lc.buyplus.models.Announcement;
import lc.buyplus.models.Friend;
import lc.buyplus.models.Gift;
import lc.buyplus.models.Notification;
import lc.buyplus.models.Shop;
import lc.buyplus.models.Store;
public class ShopFriendFragment  extends CoreFragment implements OnRefreshListener {
	private ListView listView;
	private ShopFriendAdapter friendAdapter;
	private LayoutInflater inflaterActivity;
	private ImageView imAdd;
	private int current_last_id = 0;
	private int old_id = 0;
	private boolean isLoading,reload;
	private ArrayList<Friend> FriendsList = new ArrayList<Friend>();
	private SwipeRefreshLayout swipeView;
	View footer;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_store_friend, container, false);
		footer = inflater.inflate(R.layout.loadmore, null);
		swipeView = (SwipeRefreshLayout) view.findViewById(R.id.swipe_view);
		swipeView.setOnRefreshListener(this);
	    swipeView.setColorSchemeColors(Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN);
	    // swipeView.setDistanceToTriggerSync(20);// in dips
	    //swipeView.setSize(SwipeRefreshLayout.DEFAULT);// LARGE also can be used
	    
		listView = (ListView) view.findViewById(R.id.listFriend);
		inflaterActivity = inflater;
		initViews(view);
		initModels();
		initAnimations();
		
		
		return view;
	}
	
	@Override
	public void onRefresh() {
		reload = true;
		swipeView.setRefreshing(true);
		api_get_shop_friends(Store.current_shop_id,0,Store.limit);
	}
	
	@Override
	public void onClick(View view) {
		switch (view.getId()) {

		}
	}

	protected void initModels() {

	}

	@Override
	protected void initViews(View v) {
		listView.addFooterView(footer);
		isLoading = false;
		FriendsList.removeAll(FriendsList);
		friendAdapter = new ShopFriendAdapter(FriendsList, inflaterActivity);
		listView.setAdapter(friendAdapter);
		//api_get_shop_friends(Store.current_shop_id,0,Store.limit);
		imAdd = (ImageView) v.findViewById(R.id.imAdd);
		friendAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
            	listView.addFooterView(footer);
            	api_get_shop_friends(Store.current_shop_id,current_last_id,Store.limit);
            }
        });
		
		listView.setOnScrollListener(new OnScrollListener(){
		    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		    	
		    	if (!isLoading && firstVisibleItem + visibleItemCount == totalItemCount){
		    		isLoading = true;
		    		  friendAdapter.getOnLoadMoreListener().onLoadMore();
		    	}
		    	 
		    }
		    public void onScrollStateChanged(AbsListView view, int scrollState) {
		      // TODO Auto-generated method stub
		    }
		 });
		
		
		imAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent addFriendActivity = new Intent(mActivity, AddFriendActivity.class);
				startActivityForResult(addFriendActivity,2);
			}
		});
	}

	@Override
	protected void initAnimations() {

	}

	public void api_get_shop_friends(int shop_id, int last_id, int limit) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", CanvasFragment.mUser.getAccessToken());
		params.put("shop_id", String.valueOf(shop_id));
		params.put("last_id", String.valueOf(last_id));
		params.put("limit", String.valueOf(limit));
		RequestQueue requestQueue = MonApplication.getInstance().getRequestQueue();
		HandleRequest jsObjRequest = new HandleRequest(Method.GET,
				HandleRequest.build_link(HandleRequest.GET_SHOP_FRIENDS, params), params,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						
						try {
							if (Integer.parseInt(response.getString("error"))==2){
								DialogMessage dialog = new DialogMessage(mActivity,mActivity.getResources().getString(R.string.end_session));

								dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
								dialog.show();
								SharedPreferences pre=getmContext().getSharedPreferences("buy_pus", 0);
								SharedPreferences.Editor editor=pre.edit();
								//editor.clear();
								editor.putBoolean("immediate_login", false);
								editor.commit();
								Intent loginActivity = new Intent(mActivity,LoginActivity.class);
					            startActivity(loginActivity);
					            mActivity.finish();
								
							}else
							if (Integer.parseInt(response.getString("error"))==1){
								DialogMessage dialog = new DialogMessage(mActivity,response.getString("message"));
								dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
								dialog.show();
							}else{
								if (reload){
									FriendsList.removeAll(FriendsList);
									reload = false;
								}
								JSONArray data_aray = response.getJSONArray("data");
								for (int i = 0; i < data_aray.length(); i++) {
									Friend friend = new Friend((JSONObject) data_aray.get(i));
									current_last_id = friend.getId();
									FriendsList.add(friend);
								}
								if (old_id != current_last_id) {
									isLoading = false;
									old_id = current_last_id;
								}
								listView.removeFooterView(footer);
								friendAdapter.notifyDataSetChanged();
							}
							
						} catch (JSONException e) {

							e.printStackTrace();
						}
						swipeView.setRefreshing(false);
						Store.isConnectNetwotk = true;
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						swipeView.setRefreshing(false);
						if (Store.isConnectNetwotk == true) {
							Store.isConnectNetwotk = false;
							DialogMessage dialog = new DialogMessage(mActivity,mActivity.getResources().getString(R.string.connect_problem));
							dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
							dialog.show();
						}
					}
				});
		requestQueue.add(jsObjRequest);
	}

	public static final long serialVersionUID = 6036846677812555352L;

	public static CoreActivity mActivity;
	public static ShopFriendFragment mInstance;

	public static ShopFriendFragment getInstance(CoreActivity activity) {
		if (mInstance == null) {
			mInstance = new ShopFriendFragment();
		}
		mActivity = activity;
		return mInstance;
	}

	public static ShopFriendFragment getInstance() {
		if (mInstance == null) {
			mInstance = new ShopFriendFragment();
		}
		return mInstance;
	}

	@Override
	protected void initListener() {
		// TODO Auto-generated method stub

	}
}
