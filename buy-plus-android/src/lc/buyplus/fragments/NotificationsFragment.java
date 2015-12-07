package lc.buyplus.fragments;

import lc.buyplus.R;
import lc.buyplus.activities.AnnouncementDetailActivity;
import lc.buyplus.activities.HomeNotiActivity;
import lc.buyplus.activities.LoginActivity;
import lc.buyplus.activities.ShopFriendActivity;
import lc.buyplus.activities.ShopInfoActivity;
import lc.buyplus.adapter.NotificationAdapter;
import lc.buyplus.adapter.OnLoadMoreListener;
import lc.buyplus.cores.CoreActivity;
import lc.buyplus.cores.CoreFragment;
import lc.buyplus.cores.HandleRequest;
import lc.buyplus.customizes.DialogMessage;
import lc.buyplus.models.Announcement;
import lc.buyplus.models.Notification;
import lc.buyplus.models.Shop;
import lc.buyplus.models.Store;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class NotificationsFragment extends CoreFragment implements OnRefreshListener{
	private ListView listView;
	private NotificationAdapter notiAdapter;
	private LayoutInflater inflaterActivity;
	private int old_id = 0;
	private int current_last_id = 0;
	private boolean isLoadMore,isLoading,reload;
	private SwipeRefreshLayout swipeView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_notifications, container, false);
		initViews(view);
		initModels();
		initAnimations();
		swipeView = (SwipeRefreshLayout) view.findViewById(R.id.swipe_view);
		swipeView.setOnRefreshListener(this);
	    swipeView.setColorSchemeColors(Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN);
	    // swipeView.setDistanceToTriggerSync(20);// in dips
	    //swipeView.setSize(SwipeRefreshLayout.DEFAULT);// LARGE also can be used
		inflaterActivity = inflater;
		isLoadMore = false;
		isLoading = false;
		listView = (ListView ) view.findViewById(R.id.listNoti);
		notiAdapter = new NotificationAdapter(Store.NotificationsList, inflaterActivity);
		listView.setAdapter(notiAdapter);
		//Store.NotificationsList.removeAll(Store.NotificationsList);
		//api_get_notifications(0, Store.limit);
		api_read_notifications();
		notiAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
            	api_get_notifications(current_last_id, Store.limit);
            }
        });
		
		listView.setOnItemClickListener(new OnItemClickListener() {
		      public void onItemClick(AdapterView<?> parent, View view,
		          int position, long id) {
		    	  Store.current_notification = (Notification)notiAdapter.getItem(position);
		    	  String string = Store.current_notification.getParams();
		    	  Store.noti_params = string.split(":");
		    	  Intent notiActivity;
		    	  switch (Store.current_notification.getType()) {

					case 1:
						notiActivity = new Intent(mActivity,ShopFriendActivity.class);
						Store.current_shop_id =  Integer.valueOf(Store.noti_params[1]);
			            Store.current_shop_name =  Store.noti_params[4];
			            startActivity(notiActivity);
						break;
					case 2:
						Store.current_shop_id = Integer.valueOf(Store.noti_params[1]);
			    	  	 listView.setEnabled(false);
			    	  	 api_get_shop_info(Store.current_shop_id);
						break;
					case 3:
						notiActivity = new Intent(mActivity,ShopFriendActivity.class);
						 Store.current_shop_id =  Integer.valueOf(Store.noti_params[1]);
			            Store.current_shop_name =  Store.noti_params[5];
			            startActivity(notiActivity);
						break;
					case 4:
						Store.current_shop_id = Integer.valueOf(Store.noti_params[0]);
			    	  	 listView.setEnabled(false);
			    	  	 api_get_shop_info(Store.current_shop_id);
						break;
					case 5:
						notiActivity = new Intent(mActivity,HomeNotiActivity.class);
				        startActivity(notiActivity);
						break;
					case 6:
						notiActivity = new Intent(mActivity,ShopFriendActivity.class);
						 Store.current_shop_id =  Integer.valueOf(Store.noti_params[0]);
			            Store.current_shop_name =  Store.noti_params[4];
			            startActivity(notiActivity);
						break;
					case 7:
						notiActivity = new Intent(mActivity,HomeNotiActivity.class);
				        startActivity(notiActivity);
						break;
					case 8:
						notiActivity = new Intent(mActivity,ShopFriendActivity.class);
						 Store.current_shop_id =  Integer.valueOf(Store.noti_params[0]);
			            Store.current_shop_name =  Store.noti_params[4];
			            startActivity(notiActivity);
						break;
					default:
						break;
					}
		    	 
		      }
	    });
		
		listView.setOnScrollListener(new OnScrollListener(){
		    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		    	
		    	if (!isLoading && firstVisibleItem + visibleItemCount == totalItemCount){
		    		isLoading = true;
		    		 notiAdapter.getOnLoadMoreListener().onLoadMore();
		    	}
		    	 
		    }
		    public void onScrollStateChanged(AbsListView view, int scrollState) {
		      // TODO Auto-generated method stub
		      if(scrollState == 0) {
		    	  if (isLoadMore){	
		    	  }
		      }
		    }
		 });
		

		return view;
	}
		
	@Override
	public void onRefresh() {
		reload = true;
		swipeView.setRefreshing(true);
		api_get_notifications(0, Store.limit);
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
						Log.d("api_get_notifications", response.toString());
						if (reload){
							Store.NotificationsList.removeAll(Store.NotificationsList);
							Log.d("size",String.valueOf(Store.NotificationsList.size()));
							reload = false;
						}
						try {
							if (Integer.parseInt(response.getString("error"))==2){
								DialogMessage dialog = new DialogMessage(mActivity,"Phiên truy nhập của bạn đã hết, hãy đăng nhập lại");
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
								JSONArray data_aray = response.getJSONArray("data");
								for (int i = 0; i < data_aray.length(); i++) {
									Notification notification = new Notification((JSONObject) data_aray.get(i));
									current_last_id = notification.getId();
									Store.NotificationsList.add(notification);
								}
								if (old_id != current_last_id) {
									isLoading = false;
									old_id = current_last_id;
								}
								notiAdapter.notifyDataSetChanged();
							}
							
						} catch (JSONException e) {

							e.printStackTrace();
						}
						swipeView.setRefreshing(false);
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						swipeView.setRefreshing(false);
						DialogMessage dialog = new DialogMessage(mActivity,"Kiểm tra mạng của bạn!");
						dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
						dialog.show();
					}
				});
		requestQueue.add(jsObjRequest);
	}
	
	public void api_read_notifications() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", CanvasFragment.mUser.getAccessToken());
		RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
		HandleRequest jsObjRequest = new HandleRequest(Method.POST,
				HandleRequest.READ_NOTIFICATIONS, params,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {

						try {
							if (Integer.parseInt(response.getString("error"))==2){
								DialogMessage dialog = new DialogMessage(mActivity,"Phiên truy nhập của bạn đã hết, hãy đăng nhập lại");
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
							}
						} catch (NumberFormatException | JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						DialogMessage dialog = new DialogMessage(mActivity,"Kiểm tra mạng của bạn!");
						dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
						dialog.show();
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
							if (Integer.parseInt(response.getString("error"))==2){
								DialogMessage dialog = new DialogMessage(mActivity,"Phiên truy nhập của bạn đã hết, hãy đăng nhập lại");
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
						listView.setEnabled(true);
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						listView.setEnabled(true);
						DialogMessage dialog = new DialogMessage(mActivity,"Kiểm tra mạng của bạn!");
						dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
						dialog.show();
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
