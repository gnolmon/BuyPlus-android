package lc.buyplus.fragments;

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

import android.content.Context;
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
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import lc.buyplus.R;
import lc.buyplus.activities.LoginActivity;
import lc.buyplus.activities.ShopInfoActivity;
import lc.buyplus.adapter.OnLoadMoreListener;
import lc.buyplus.adapter.StoreAdapter;
import lc.buyplus.application.MonApplication;
import lc.buyplus.cores.CoreActivity;
import lc.buyplus.cores.CoreFragment;
import lc.buyplus.cores.HandleRequest;
import lc.buyplus.customizes.DialogMessage;
import lc.buyplus.models.Shop;
import lc.buyplus.models.Store;
import android.widget.AdapterView.OnItemClickListener;

public class HomeFragment extends CoreFragment implements OnRefreshListener{
	public static ListView listView;
	public static StoreAdapter storeAdapter;
	private LayoutInflater inflaterActivity;
	public static int current_last_id = 0;
	private boolean isLoading,reload;
	private int old_id = 0;
	private SwipeRefreshLayout swipeView;
	View footer;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home, container, false);
		footer = inflater.inflate(R.layout.loadmore, null);
		swipeView = (SwipeRefreshLayout) view.findViewById(R.id.swipe_view);
		swipeView.setOnRefreshListener(this);
	    swipeView.setColorSchemeColors(Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN);
	    // swipeView.setDistanceToTriggerSync(20);// in dips
	    //swipeView.setSize(SwipeRefreshLayout.DEFAULT);// LARGE also can be used
		listView = (ListView) view.findViewById(R.id.listStore);
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
		api_get_all_shop(0,Store.limit,Store.Shop_Search_param);
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
		isLoading = false; 
		storeAdapter = new StoreAdapter(Store.ShopsList, inflaterActivity, mActivity);
		listView.setAdapter(storeAdapter);
		//api_get_all_shop(0,Store.limit,"");
		listView.setOnItemClickListener(new OnItemClickListener() {
		      public void onItemClick(AdapterView<?> parent, View view,
		          int position, long id) {
		    	  	 Store.current_shop_id = ((Shop)storeAdapter.getItem(position)).getId();
		    	  	 listView.setEnabled(false);
		    	  	 api_get_shop_info(Store.current_shop_id);
		      }
		    });
		
		storeAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
            	listView.addFooterView(footer);
            	api_get_all_shop(current_last_id, Store.limit, Store.Shop_Search_param);
            }
        });
		
		listView.setOnScrollListener(new OnScrollListener(){
		    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		    	
		    	if (!isLoading && firstVisibleItem + visibleItemCount == totalItemCount){
		    		isLoading = true;
		    		storeAdapter.getOnLoadMoreListener().onLoadMore();
		    	}
		    	 
		    }
		    public void onScrollStateChanged(AbsListView view, int scrollState) {
		      // TODO Auto-generated method stub
		    }
		 });
		
//		listView.setOnRefreshListener(new OnRefreshListener() {
//
//			public void onRefresh() {
//				reload = true;
//				api_get_all_shop(0,Store.limit,Store.Shop_Search_param);
//			}
//		});
	}

	@Override
	protected void initAnimations() {
		
	}

	public void api_get_all_shop(int last_id, int limit, String search){
	 	
    	Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", CanvasFragment.mUser.getAccessToken());
		params.put("last_id", String.valueOf(last_id));
		params.put("limit", String.valueOf(limit));
		params.put("search", String.valueOf(search));
			RequestQueue requestQueue =  MonApplication.getInstance().getRequestQueue();
			HandleRequest jsObjRequest = new HandleRequest(Method.GET,
					HandleRequest.build_link(HandleRequest.GET_ALL_SHOP, params), params, 
					new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						if (reload){
							Store.ShopsList.removeAll(Store.ShopsList);
							reload = false;
						}
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
								Log.d("api_get_all_shop",response.toString());
								JSONArray data_aray = response.getJSONArray("data");
								for (int i = 0; i < data_aray.length(); i++) {								 
		                            Shop shop = new Shop((JSONObject) data_aray.get(i));
		                            	if (shop != null){
		                            		current_last_id = shop.getId();
		                            		Store.ShopsList.add(shop);
		                            	}
		                        }
								listView.removeFooterView(footer);
								storeAdapter.notifyDataSetChanged();
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
				}, 
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.d("api_get_all_shop",error.toString());
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
	
	
	public void api_get_shop_info(int shop_id) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", CanvasFragment.mUser.getAccessToken());
		params.put("shop_id", String.valueOf(shop_id));
		RequestQueue requestQueue =  MonApplication.getInstance().getRequestQueue();
		HandleRequest jsObjRequest = new HandleRequest(Method.GET,
				HandleRequest.build_link(HandleRequest.GET_SHOP_INFO, params), params,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.d("api_get_shop_info", response.toString());
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
								Store.current_shop = new Shop(response.getJSONObject("data"));
								Store.current_shop_name =  Store.current_shop.getName();
								Intent shopInfoActivity = new Intent(mActivity,ShopInfoActivity.class);			            
					            startActivity(shopInfoActivity);
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
						Store.isConnectNetwotk = true;
						listView.setEnabled(true);
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						listView.setEnabled(true);
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
