package lc.buyplus.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import lc.buyplus.R;
import lc.buyplus.activities.ShopInfoActivity;
import lc.buyplus.adapter.AnnounmentAdapter;
import lc.buyplus.cores.CoreActivity;
import lc.buyplus.cores.CoreFragment;
import lc.buyplus.cores.HandleRequest;
import lc.buyplus.models.Announcement;
import lc.buyplus.models.Friend;
import lc.buyplus.models.Gift;
import lc.buyplus.models.Notification;
import lc.buyplus.models.Shop;
import lc.buyplus.models.Store;

public class HomeAnnounmentFragment extends CoreFragment {
	private ListView listView;
	private AnnounmentAdapter newsAdapter;
	private LayoutInflater inflaterActivity;
	FragmentManager mFragmentManager;
	public static Fragment homeFrg;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_announment, container, false);
		initViews(view);
		initModels();
		initAnimations();
		mFragmentManager = getFragmentManager();
		listView = (ListView) view.findViewById(R.id.listAnnounment);
		inflaterActivity = inflater;
		api_get_all_announcements(2, 0, 0, 0, 0);
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
		homeFrg = this.getTargetFragment();

	}

	@Override
	protected void initAnimations() {

	}

	public void api_get_all_announcements(int type, int latest_id, int oldest_id, int mode, int search) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", CanvasFragment.mUser.getAccessToken());
		params.put("type", String.valueOf(type));
		params.put("latest_id", String.valueOf(latest_id));
		params.put("oldest_id", String.valueOf(oldest_id));
		params.put("mode", String.valueOf(mode));
		params.put("search", String.valueOf(search));
		RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
		HandleRequest jsObjRequest = new HandleRequest(Method.GET,
				HandleRequest.build_link(HandleRequest.GET_ALL_ANNOUNCEMENTS, params), params,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						try {
							Log.d("api_get_all_announcements", response.toString());
							JSONArray data_aray = response.getJSONArray("data");
							for (int i = 0; i < data_aray.length(); i++) {
								Announcement announcement = new Announcement((JSONObject) data_aray.get(i));
								Store.AnnouncementsList.add(announcement);

							}
							newsAdapter = new AnnounmentAdapter(Store.AnnouncementsList, inflaterActivity, mActivity, mFragmentManager);
							listView.setAdapter(newsAdapter);
							listView.setOnItemClickListener(new OnItemClickListener() {
							      public void onItemClick(AdapterView<?> parent, View view,
							          int position, long id) {
							    	  Intent shopInfoActivity = new Intent(mActivity,ShopInfoActivity.class);
						              Bundle b = new Bundle();
						              b.putInt("shop_id", newsAdapter.getItem_id(position)); //Your id
						              shopInfoActivity.putExtras(b); //Put your id to your next Intent
						              startActivity(shopInfoActivity);
							      }
							    });
							newsAdapter.notifyDataSetChanged();
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
	public static HomeAnnounmentFragment mInstance;

	public static HomeAnnounmentFragment getInstance(CoreActivity activity) {
		if (mInstance == null) {
			mInstance = new HomeAnnounmentFragment();
		}
		mActivity = activity;
		return mInstance;
	}

	public static HomeAnnounmentFragment getInstance() {
		if (mInstance == null) {
			mInstance = new HomeAnnounmentFragment();
		}
		return mInstance;
	}

	@Override
	protected void initListener() {
		// TODO Auto-generated method stub

	}
}
