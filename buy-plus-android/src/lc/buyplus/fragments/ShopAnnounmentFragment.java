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
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;
import lc.buyplus.R;
import lc.buyplus.activities.ShopInfoActivity;
import lc.buyplus.adapter.AnnounmentAdapter;
import lc.buyplus.adapter.OnLoadMoreListener;
import lc.buyplus.adapter.ShopAnnounmentAdapter;
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

public class ShopAnnounmentFragment extends CoreFragment {
	private PullToRefreshListView listView;
	private ShopAnnounmentAdapter newsAdapter;
	private LayoutInflater inflaterActivity;
	private ArrayList<Announcement> ShopAnnouncementsList = new ArrayList<Announcement>();
	private int current_last_id = 0;
	private boolean isLoadMore,isLoading,reload;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_shop_announment, container, false);
		listView = (PullToRefreshListView) view.findViewById(R.id.listShopAnnounment);
		inflaterActivity = inflater;
		initViews(view);
		initModels();
		initAnimations();
		return view;
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
		isLoadMore = false;
		isLoading = false;
		newsAdapter = new ShopAnnounmentAdapter(ShopAnnouncementsList, inflaterActivity);
		listView.setAdapter(newsAdapter);
		api_get_shop_announcements(Store.current_shop_id, 0, Store.limit, 0);
		
		newsAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
            	api_get_shop_announcements(Store.current_shop_id, current_last_id, Store.limit, 0);
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
		    		  newsAdapter.getOnLoadMoreListener().onLoadMore();	
		    	  }
		      }
		    }
		 });
		
		listView.setOnRefreshListener(new OnRefreshListener() {

			public void onRefresh() {
				reload = true;
				api_get_shop_announcements(Store.current_shop_id, 0, Store.limit, 0);
				
			}
		});
	}

	@Override
	protected void initAnimations() {

	}

	public void api_get_shop_announcements(int shop_id, int last_id, int limit, int type) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", CanvasFragment.mUser.getAccessToken());
		params.put("shop_id", String.valueOf(shop_id));
		params.put("type", String.valueOf(type));
		params.put("last_id", String.valueOf(last_id));
		params.put("limit", String.valueOf(limit));
		RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
		HandleRequest jsObjRequest = new HandleRequest(Method.GET,
				HandleRequest.build_link(HandleRequest.GET_SHOP_ANNOUNCEMENTS, params), params,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.d("api_get_shop_announcements", response.toString());
						try {
							if (reload){
								ShopAnnouncementsList.removeAll(ShopAnnouncementsList);
								reload = false;
							}
							JSONArray data_aray = response.getJSONArray("data");
							for (int i = 0; i < data_aray.length(); i++) {
								Announcement announcement = new Announcement((JSONObject) data_aray.get(i));
								if (announcement != null){
									current_last_id = announcement.getId();
									ShopAnnouncementsList.add(announcement);
								}
							}
							
							newsAdapter.notifyDataSetChanged();
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

	public static final long serialVersionUID = 6036846677812555352L;

	public static CoreActivity mActivity;
	public static ShopAnnounmentFragment mInstance;

	public static ShopAnnounmentFragment getInstance(CoreActivity activity) {
		if (mInstance == null) {
			mInstance = new ShopAnnounmentFragment();
		}
		mActivity = activity;
		return mInstance;
	}

	public static ShopAnnounmentFragment getInstance() {
		if (mInstance == null) {
			mInstance = new ShopAnnounmentFragment();
		}
		return mInstance;
	}

	@Override
	protected void initListener() {
		// TODO Auto-generated method stub

	}
}
