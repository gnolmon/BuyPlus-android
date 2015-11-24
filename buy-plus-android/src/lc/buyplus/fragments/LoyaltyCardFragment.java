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
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import lc.buyplus.R;
import lc.buyplus.activities.ShopFriendActivity;
import lc.buyplus.activities.ShopInfoActivity;
import lc.buyplus.adapter.AnnounmentAdapter;
import lc.buyplus.adapter.OnLoadMoreListener;
import lc.buyplus.adapter.RedeemAdapter;
import lc.buyplus.cores.CoreActivity;
import lc.buyplus.cores.CoreFragment;
import lc.buyplus.cores.HandleRequest;
import lc.buyplus.models.Announcement;
import lc.buyplus.models.Friend;
import lc.buyplus.models.Gift;
import lc.buyplus.models.Notification;
import lc.buyplus.models.Shop;
import lc.buyplus.models.Store;
import lc.buyplus.pulltorefresh.PullToRefreshListView;
import lc.buyplus.pulltorefresh.PullToRefreshListView.OnRefreshListener;

public class LoyaltyCardFragment extends CoreFragment {
	private LayoutInflater inflaterActivity;
	private PullToRefreshListView listView;
	private RedeemAdapter redeemAdapter;
	private int current_last_id = 0;
	private boolean isLoadMore,isLoading,reload;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_redeem, container, false);
		listView = (PullToRefreshListView) view.findViewById(R.id.listRedeem);
		inflaterActivity = inflater;
		initViews(view);
		initModels();
		initAnimations();
		;
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
		redeemAdapter = new RedeemAdapter(Store.MyShopsList, inflaterActivity);
		listView.setAdapter(redeemAdapter);
		api_get_my_shop(0,Store.limit,0);
		listView.setOnItemClickListener(new OnItemClickListener() {
		      public void onItemClick(AdapterView<?> parent, View view,
		          int position, long id) {
		             Store.current_shop_id = ((Shop)redeemAdapter.getItem(position)).getId();
		             api_get_shop_info(Store.current_shop_id);
		      }
		    });
		redeemAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
            	api_get_my_shop(current_last_id,Store.limit,0);
            }
        });
		
		listView.setOnScrollListener(new OnScrollListener(){
		    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		    	
		    	if (!isLoading && firstVisibleItem + visibleItemCount == totalItemCount){
		    		isLoadMore = true;
		    	}
		    	 
		    }
		    public void onScrollStateChanged(AbsListView view, int scrollState) {
		      // TODO Auto-generated method stub
		      if(scrollState == 0) {
		    	  if (isLoadMore){
		    		  isLoading = true;
		    		  redeemAdapter.getOnLoadMoreListener().onLoadMore();	
		    	  }
		      }
		    }
		 });
		
		listView.setOnRefreshListener(new OnRefreshListener() {

			public void onRefresh() {
				reload = true;
				api_get_my_shop(0,Store.limit,0);
				
			}
		});
	}

	@Override
	protected void initAnimations() {
		
	}

	public void api_get_my_shop(int last_id, int limit, int mode) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", CanvasFragment.mUser.getAccessToken());
		params.put("last_id", String.valueOf(last_id));
		params.put("limit", String.valueOf(limit));
		params.put("mode", String.valueOf(mode));
		RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
		HandleRequest jsObjRequest = new HandleRequest(Method.GET,
				HandleRequest.build_link(HandleRequest.GET_MY_SHOP, params), params,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.d("api_get_my_shop", response.toString());
						try {
							if (reload){
								Store.MyShopsList.removeAll(Store.MyShopsList);
								reload = false;
							}
							JSONArray data_aray = response.getJSONArray("data");
							for (int i = 0; i < data_aray.length(); i++) {
								Shop shop = new Shop((JSONObject) data_aray.get(i));
								if (shop != null) {
									current_last_id = shop.getId();
									Store.MyShopsList.add(shop);
								}
							}
							redeemAdapter.notifyDataSetChanged();
							isLoading = false;
						} catch (JSONException e) {
							e.printStackTrace();
						}
						listView.onRefreshComplete();
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						listView.onRefreshComplete();
					}
				});
		requestQueue.add(jsObjRequest);
	}
	
	public void api_get_shop_info(int shop_id) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", CanvasFragment.mUser.getAccessToken());
		params.put("shop_id", String.valueOf(shop_id));
		RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
		HandleRequest jsObjRequest = new HandleRequest(Method.GET,
				HandleRequest.build_link(HandleRequest.GET_SHOP_INFO, params), params,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.d("api_get_shop_info", response.toString());
						try {

							Store.current_shop = new Shop(response.getJSONObject("data"));
							Intent shopInfoActivity = new Intent(mActivity,ShopInfoActivity.class);
				            Store.current_shop_name =  Store.current_shop.getName();
				            startActivity(shopInfoActivity);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
					}
				});
		requestQueue.add(jsObjRequest);
	}
	public static final long serialVersionUID = 6036846677812555352L;
	
	public static CoreActivity mActivity;
	public static LoyaltyCardFragment mInstance;
	public static LoyaltyCardFragment getInstance(CoreActivity activity) {
		if (mInstance == null) {
			mInstance = new LoyaltyCardFragment();
		}
		mActivity = activity;
		return mInstance;
	}
	public static LoyaltyCardFragment getInstance() {
		if (mInstance == null) {
			mInstance = new LoyaltyCardFragment();
		}
		return mInstance;
	}
	@Override
	protected void initListener() {
		// TODO Auto-generated method stub
		
	}
}
