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
import lc.buyplus.activities.AnnouncementDetailActivity;
import lc.buyplus.activities.LoginActivity;
import lc.buyplus.activities.RedeemDetailActivity;
import lc.buyplus.activities.ShopInfoActivity;
import lc.buyplus.adapter.OnLoadMoreListener;
import lc.buyplus.adapter.ShopFriendAdapter;
import lc.buyplus.adapter.ShopGiftAdapter;
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
public class ShopGiftFragment extends CoreFragment implements OnRefreshListener {
	private ListView listView;
	private ShopGiftAdapter giftAdapter;
	private LayoutInflater inflaterActivity;
	private int current_last_id = 0;
	private boolean isLoading,reload;
	private ArrayList<Gift> GiftsList = new ArrayList<Gift>();
	private int old_id = 0;
	private SwipeRefreshLayout swipeView;
	View footer;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_store_redeem, container, false);
		footer = inflater.inflate(R.layout.loadmore, null);
		swipeView = (SwipeRefreshLayout) view.findViewById(R.id.swipe_view);
		swipeView.setOnRefreshListener(this);
	    swipeView.setColorSchemeColors(Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN);
	    // swipeView.setDistanceToTriggerSync(20);// in dips
	    //swipeView.setSize(SwipeRefreshLayout.DEFAULT);// LARGE also can be used
		listView = (ListView) view.findViewById(R.id.listShopRedeem);
		inflaterActivity = inflater;
		initViews(view);
		initModels();
		initAnimations();
		
		
		return view;
	}
	
	@Override
	public void onRefresh() {
		Log.d("reload","true");
		reload = true;
		swipeView.setRefreshing(true);
		api_get_shop_gifts(Store.current_shop_id,0,Store.limit);
	}


	protected void initModels() {
		
	}

	@Override
	protected void initViews(View v) {
		current_last_id = 0;
		listView.addFooterView(footer);
		isLoading = false;
		GiftsList.removeAll(GiftsList);
		giftAdapter = new ShopGiftAdapter(GiftsList, inflaterActivity);
		listView.setAdapter(giftAdapter);
		//api_get_shop_gifts(Store.current_shop_id,0,Store.limit);
		giftAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
            	listView.addFooterView(footer);
            	api_get_shop_gifts(Store.current_shop_id,current_last_id,Store.limit);
            }
        });
		
		listView.setOnScrollListener(new OnScrollListener(){
		    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		    	
		    	if (!isLoading && firstVisibleItem + visibleItemCount == totalItemCount){
		    		  isLoading = true;
		    		  giftAdapter.getOnLoadMoreListener().onLoadMore();	
		    	}
		    	 
		    }
		    public void onScrollStateChanged(AbsListView view, int scrollState) {
		      // TODO Auto-generated method stub
		    }
		 });
		

		listView.setOnItemClickListener(new OnItemClickListener() {
		      public void onItemClick(AdapterView<?> parent, View view,
		          int position, long id) {
		    	  Store.current_gift = (Gift) giftAdapter.getItem(position);
					Intent mainActivity = new Intent(mActivity, RedeemDetailActivity.class);
					startActivity(mainActivity);
		      }
		    });
		
	}

	@Override
	protected void initAnimations() {

	}

	public void api_get_shop_gifts(int shop_id, int last_id, int limit) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", CanvasFragment.mUser.getAccessToken());
		params.put("shop_id", String.valueOf(shop_id));
		params.put("last_id", String.valueOf(last_id));
		params.put("limit", String.valueOf(limit));
		Log.d("api_get_shop_gifts_params", params.toString());
		RequestQueue requestQueue = MonApplication.getInstance().getRequestQueue();
		HandleRequest jsObjRequest = new HandleRequest(Method.GET,
				HandleRequest.build_link(HandleRequest.GET_SHOP_GIFTS, params), params,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.d("api_get_shop_gifts", response.toString());
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
									GiftsList.removeAll(GiftsList);
									reload = false;
								}
								JSONArray data_aray = response.getJSONArray("data");
								for (int i = 0; i < data_aray.length(); i++) {
									Gift gift = new Gift((JSONObject) data_aray.get(i));
									current_last_id = gift.getId();
									GiftsList.add(gift);
								}
								if (old_id != current_last_id) {
									isLoading = false;
									old_id = current_last_id;
								}
								listView.removeFooterView(footer);
								giftAdapter.notifyDataSetChanged();
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
	public static ShopGiftFragment mInstance;

	public static ShopGiftFragment getInstance(CoreActivity activity) {
		if (mInstance == null) {
			mInstance = new ShopGiftFragment();
		}
		mActivity = activity;
		return mInstance;
	}

	public static ShopGiftFragment getInstance() {
		if (mInstance == null) {
			mInstance = new ShopGiftFragment();
		}
		return mInstance;
	}

	@Override
	protected void initListener() {
		// TODO Auto-generated method stub

	}
}
