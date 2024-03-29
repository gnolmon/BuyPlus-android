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
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import lc.buyplus.R;
import lc.buyplus.activities.LoginActivity;
import lc.buyplus.activities.ShopFriendActivity;
import lc.buyplus.activities.ShopInfoActivity;
import lc.buyplus.adapter.AnnounmentAdapter;
import lc.buyplus.adapter.OnLoadMoreListener;
import lc.buyplus.adapter.RedeemAdapter;
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

public class LoyaltyCardFragment extends CoreFragment implements OnRefreshListener {
	private LayoutInflater inflaterActivity;
	private ListView listView;
	private RedeemAdapter redeemAdapter;
	private int current_last_id = 0;
	private boolean isLoadMore,isLoading,reload;
	private SwipeRefreshLayout swipeView;
	private int old_id = 0;
	public static boolean check = true;
	View footer;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_redeem, container, false);
		footer = inflater.inflate(R.layout.loadmore, null);
		swipeView = (SwipeRefreshLayout) view.findViewById(R.id.swipe_view);
		swipeView.setOnRefreshListener(this);
	    swipeView.setColorSchemeColors(Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN);
	    // swipeView.setDistanceToTriggerSync(20);// in dips
	    //swipeView.setSize(SwipeRefreshLayout.DEFAULT);// LARGE also can be used
	    
		listView = (ListView) view.findViewById(R.id.listRedeem);
		inflaterActivity = inflater;
		initViews(view);
		initModels();
		initAnimations();
		;
		return view;
	}
	
	@Override
	public void onRefresh() {
		reload = true;
		swipeView.setRefreshing(true);
		api_get_my_shop(0,Store.limit,0);
	}
	
	@Override
	public void onResume(){
	    super.onResume();
	    Log.d("resume","resume");
	    Store.MyShopsList.removeAll(Store.MyShopsList);
		if(!check){
			api_get_my_shop(0,Store.limit,0);
		}
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
		listView.addFooterView(footer);
		redeemAdapter = new RedeemAdapter(Store.MyShopsList, inflaterActivity);
		listView.setAdapter(redeemAdapter);
		
		//api_get_my_shop(0,Store.limit,0);
		listView.setOnItemClickListener(new OnItemClickListener() {
		      public void onItemClick(AdapterView<?> parent, View view,
		          int position, long id) {
		             Store.current_shop =((Shop)redeemAdapter.getItem(position));
		             Store.current_shop_id = Store.current_shop.getId();
						Intent shopFriendActivity = new Intent(mActivity,ShopFriendActivity.class);
			            Store.current_shop_name =  Store.current_shop.getName();
			            startActivity(shopFriendActivity);
		      }
		    });
		redeemAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
            	listView.addFooterView(footer);
            	api_get_my_shop(current_last_id,Store.limit,0);
            }
        });
		
		listView.setOnScrollListener(new OnScrollListener(){
		    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		    	
		    	if (!isLoading && firstVisibleItem + visibleItemCount == totalItemCount){
		    		isLoading = true;
		    		redeemAdapter.getOnLoadMoreListener().onLoadMore();
		    	}
		    	 
		    }
		    public void onScrollStateChanged(AbsListView view, int scrollState) {
		      // TODO Auto-generated method stub
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
		RequestQueue requestQueue = MonApplication.getInstance().getRequestQueue();
		HandleRequest jsObjRequest = new HandleRequest(Method.GET,
				HandleRequest.build_link(HandleRequest.GET_MY_SHOP, params), params,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.d("api_get_my_shop", response.toString());
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

							}
							if (Integer.parseInt(response.getString("error"))==1){
								DialogMessage dialog = new DialogMessage(mActivity,response.getString("message"));
								dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
								dialog.show();
							}else{
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
								listView.removeFooterView(footer);
								if (Store.MyShopsList.size() < 3 && check) {
									check = false;
									CanvasFragment.mPager.setCurrentItem(1);
								}
								redeemAdapter.notifyDataSetChanged();
								if (old_id != current_last_id) {
									isLoading = false;
									old_id = current_last_id;
								}
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
						Store.isConnectNetwotk = true;
						swipeView.setRefreshing(false);
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						swipeView.setRefreshing(false);
						isLoading = false;
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
