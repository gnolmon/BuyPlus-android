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
import android.widget.ListView;
import lc.buyplus.R;
import lc.buyplus.activities.ShopInfoActivity;
import lc.buyplus.adapter.ShopFriendAdapter;
import lc.buyplus.adapter.ShopGiftAdapter;
import lc.buyplus.cores.CoreActivity;
import lc.buyplus.cores.CoreFragment;
import lc.buyplus.cores.HandleRequest;
import lc.buyplus.models.Announcement;
import lc.buyplus.models.Friend;
import lc.buyplus.models.Gift;
import lc.buyplus.models.Notification;
import lc.buyplus.models.Shop;
import lc.buyplus.models.Store;

public class ShopGiftFragment extends CoreFragment {
	private ListView listView;
	private ShopGiftAdapter giftAdapter;
	private LayoutInflater inflaterActivity;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_store_redeem, container, false);
		initViews(view);
		initModels();
		initAnimations();
		listView = (ListView) view.findViewById(R.id.listShopRedeem);
		inflaterActivity = inflater;
		api_get_shop_gifts(Store.current_shop_id);
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

	public void api_get_shop_gifts(int shop_id) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", CanvasFragment.mUser.getAccessToken());
		params.put("shop_id", String.valueOf(shop_id));
		RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
		HandleRequest jsObjRequest = new HandleRequest(Method.GET,
				HandleRequest.build_link(HandleRequest.GET_SHOP_GIFTS, params), params,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.d("api_get_shop_gifts", response.toString());
						try {
							ArrayList<Gift> GiftsList = new ArrayList<Gift>();
							JSONArray data_aray = response.getJSONArray("data");
							for (int i = 0; i < data_aray.length(); i++) {
								Gift gift = new Gift((JSONObject) data_aray.get(i));
								GiftsList.add(gift);
							}
							giftAdapter = new ShopGiftAdapter(GiftsList, inflaterActivity);
							listView.setAdapter(giftAdapter);
							giftAdapter.notifyDataSetChanged();
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
