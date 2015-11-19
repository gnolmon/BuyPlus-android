package lc.buyplus.fragments;

import lc.buyplus.R;
import lc.buyplus.adapter.NotificationAdapter;
import lc.buyplus.adapter.StoreAdapter;
import lc.buyplus.cores.CoreActivity;
import lc.buyplus.cores.CoreFragment;
import lc.buyplus.cores.HandleRequest;
import lc.buyplus.models.Announcement;
import lc.buyplus.models.Friend;
import lc.buyplus.models.Gift;
import lc.buyplus.models.Notification;
import lc.buyplus.models.Shop;
import lc.buyplus.models.Store;
import android.support.v4.widget.SwipeRefreshLayout;

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
import android.widget.ListView;

public class NotificationsFragment extends CoreFragment implements SwipeRefreshLayout.OnRefreshListener {
	private ListView listView;
	private NotificationAdapter notiAdapter;
	private LayoutInflater inflaterActivity;
	private SwipeRefreshLayout swipeRefreshLayout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_notifications, container, false);
		initViews(view);
		initModels();
		initAnimations();
		inflaterActivity = inflater;
		listView = (ListView) view.findViewById(R.id.listNoti);

		swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);

		swipeRefreshLayout.setOnRefreshListener(this);

		/**
		 * Showing Swipe Refresh animation on activity create As animation won't
		 * start on onCreate, post runnable is used
		 */
		swipeRefreshLayout.post(new Runnable() {
			@Override
			public void run() {
				swipeRefreshLayout.setRefreshing(true);
				Log.d("xxxxx", "xxxx");
				api_get_notifications(0, 0);
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

	public void api_get_notifications(int latest_id, int oldest_id) {
		swipeRefreshLayout.setRefreshing(true);
		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", CanvasFragment.mUser.getAccessToken());
		params.put("latest_id", String.valueOf(latest_id));
		params.put("oldest_id", String.valueOf(oldest_id));
		RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
		HandleRequest jsObjRequest = new HandleRequest(Method.GET,
				HandleRequest.build_link(HandleRequest.GET_NOTIFICATIONS, params), params,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.d("api_get_notifications", response.toString());
						try {
							JSONArray data_aray = response.getJSONArray("data");
							for (int i = 0; i < data_aray.length(); i++) {
								Notification notification = new Notification((JSONObject) data_aray.get(i));
								Store.NotificationsList.add(notification);
							}
							notiAdapter = new NotificationAdapter(Store.NotificationsList, inflaterActivity);

							listView.setAdapter(notiAdapter);
							notiAdapter.notifyDataSetChanged();
						} catch (JSONException e) {

							e.printStackTrace();
						}
						// stopping swipe refresh
						swipeRefreshLayout.setRefreshing(false);
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						// stopping swipe refresh
						swipeRefreshLayout.setRefreshing(false);
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

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		api_get_notifications(0, 0);
	}
}
