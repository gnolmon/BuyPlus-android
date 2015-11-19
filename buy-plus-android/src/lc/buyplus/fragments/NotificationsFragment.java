package lc.buyplus.fragments;

import lc.buyplus.R;
import lc.buyplus.adapter.NotificationAdapter;
import lc.buyplus.adapter.OnLoadMoreListener;

import lc.buyplus.cores.CoreActivity;
import lc.buyplus.cores.CoreFragment;
import lc.buyplus.cores.HandleRequest;
import lc.buyplus.models.Notification;
import lc.buyplus.models.Store;
import lc.buyplus.pulltorefresh.PullToRefreshListView;
import lc.buyplus.pulltorefresh.PullToRefreshListView.OnRefreshListener;
import android.support.v4.widget.SwipeRefreshLayout;

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
import android.widget.AbsListView.OnScrollListener;

public class NotificationsFragment extends CoreFragment{
	private PullToRefreshListView listView;
	private NotificationAdapter notiAdapter;
	private LayoutInflater inflaterActivity;
	
	private int limit = 6;
	private int current_last_id = 0;
	private boolean isLoadMore,isLoading,reload;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_notifications, container, false);
		initViews(view);
		initModels();
		initAnimations();
		
		inflaterActivity = inflater;
		isLoadMore = false;
		isLoading = false;
		listView = (PullToRefreshListView ) view.findViewById(R.id.listNoti);
		notiAdapter = new NotificationAdapter(Store.NotificationsList, inflaterActivity);
		listView.setAdapter(notiAdapter);
		api_get_notifications(0, limit);
		notiAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
            	api_get_notifications(current_last_id, limit);
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
		    		  notiAdapter.getOnLoadMoreListener().onLoadMore();
		    		  Log.d("isLoad",String.valueOf(Store.NotificationsList.size()));  	
		    	  }
		      }
		    }
		 });
		
		listView.setOnRefreshListener(new OnRefreshListener() {

			public void onRefresh() {
				reload = true;
				Log.d("sixxxxxze",String.valueOf(Store.NotificationsList.size()));
				api_get_notifications(0, limit);
				
			}
		});

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

	}

	@Override
	protected void initAnimations() {

	}

	public void api_get_notifications(int last_id, int limit) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", CanvasFragment.mUser.getAccessToken());
		params.put("last_id", String.valueOf(last_id));
		params.put("limit", String.valueOf(limit));
		RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
		HandleRequest jsObjRequest = new HandleRequest(Method.GET,
				HandleRequest.build_link(HandleRequest.GET_NOTIFICATIONS, params), params,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						if (reload){
							Store.NotificationsList.removeAll(Store.NotificationsList);
							Log.d("size",String.valueOf(Store.NotificationsList.size()));
							reload = false;
						}
						Log.d("api_get_notifications", response.toString());
						try {
							JSONArray data_aray = response.getJSONArray("data");
							for (int i = 0; i < data_aray.length(); i++) {
								Notification notification = new Notification((JSONObject) data_aray.get(i));
								current_last_id = notification.getId();
								Store.NotificationsList.add(notification);
							}
							isLoading = false;
							notiAdapter.notifyDataSetChanged();
						} catch (JSONException e) {

							e.printStackTrace();
						}
						// stopping swipe refresh
						
						listView.onRefreshComplete();
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						// stopping swipe refresh
						listView.onRefreshComplete();
					}
				});
		requestQueue.add(jsObjRequest);
	}

	public static final long serialVersionUID = 6036846677812555352L;

	public static CoreActivity mActivity;
	public static NotificationsFragment mInstance;

	public static NotificationsFragment getInstance(CoreActivity activity) {
		if (mInstance == null) {
			mInstance = new NotificationsFragment();
		}
		mActivity = activity;
		return mInstance;
	}

	public static NotificationsFragment getInstance() {
		if (mInstance == null) {
			mInstance = new NotificationsFragment();
		}
		return mInstance;
	}

	@Override
	protected void initListener() {
		// TODO Auto-generated method stub

	}
}
