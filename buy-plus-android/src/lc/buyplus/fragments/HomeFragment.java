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
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import lc.buyplus.R;
import lc.buyplus.activities.ShopInfoActivity;
import lc.buyplus.adapter.OnLoadMoreListener;
import lc.buyplus.adapter.StoreAdapter;
import lc.buyplus.cores.CoreActivity;
import lc.buyplus.cores.CoreFragment;
import lc.buyplus.cores.HandleRequest;
import lc.buyplus.models.Shop;
import lc.buyplus.models.Store;
import lc.buyplus.pulltorefresh.PullToRefreshListView;
import lc.buyplus.pulltorefresh.PullToRefreshListView.OnRefreshListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class HomeFragment extends CoreFragment {
	private PullToRefreshListView listView;
	private StoreAdapter storeAdapter;
	private LayoutInflater inflaterActivity;
	private int current_last_id = 0;
	private boolean isLoadMore,isLoading,reload;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home, container, false);
		listView = (PullToRefreshListView) view.findViewById(R.id.listStore);
		inflaterActivity = inflater;
		initViews(view);
		initModels();
		initAnimations();
		
		
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
		isLoadMore = false;
		isLoading = false; 
		storeAdapter = new StoreAdapter(Store.ShopsList, inflaterActivity, mActivity);
		listView.setAdapter(storeAdapter);
		api_get_all_shop(0,Store.limit,"");
		listView.setOnItemClickListener(new OnItemClickListener() {
		      public void onItemClick(AdapterView<?> parent, View view,
		          int position, long id) {
		    	  	 Store.current_shop_id = storeAdapter.getItem_id(position);
		             Intent shopInfoActivity = new Intent(mActivity,ShopInfoActivity.class);		             
		             startActivity(shopInfoActivity);
		      }
		    });
		
		storeAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
            	api_get_all_shop(current_last_id, Store.limit, "");
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
		    		  storeAdapter.getOnLoadMoreListener().onLoadMore();	
		    	  }
		      }
		    }
		 });
		
		listView.setOnRefreshListener(new OnRefreshListener() {

			public void onRefresh() {
				reload = true;
				api_get_all_shop(0,Store.limit,"");
				
			}
		});
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
			RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
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
							Log.d("api_get_all_shop",response.toString());
							JSONArray data_aray = response.getJSONArray("data");
							for (int i = 0; i < data_aray.length(); i++) {								 
	                            Shop shop = new Shop((JSONObject) data_aray.get(i));
	                            	if (shop != null){
	                            		current_last_id = shop.getId();
	                            		Store.ShopsList.add(shop);
	                            	}
	                        }
							storeAdapter.notifyDataSetChanged();
							isLoading = false;
						} catch (JSONException e) {
							e.printStackTrace();
						}
												
						listView.onRefreshComplete();
					}
				}, 
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.d("api_get_all_shop",error.toString());
						listView.onRefreshComplete();
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
