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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import lc.buyplus.R;
import lc.buyplus.activities.ShopInfoActivity;
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

public class ShopInfoFragment   extends CoreFragment {
	private ListView listView;
	private ShopAnnounmentAdapter newsAdapter;
	private LayoutInflater inflaterActivity;
	private TextView tvName, tvField, tvPhone, tvWeb, tvFb;
	private Button join_leave;
	private boolean isJoin;
	Shop shop;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_store_info, container, false);
		initViews(view);
		initModels();
		initAnimations();
		api_get_shop_info(ShopInfoActivity.current_shop_id);
		return view;
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btnAgreeTerm:
			if (isJoin){
				api_leave_shop(ShopInfoActivity.current_shop_id);
				join_leave.setText("Tham gia");
				isJoin = false;
			}
			else{
				api_join_shop(ShopInfoActivity.current_shop_id);
				join_leave.setText("Roi di");
				isJoin = true;
			}
		break;
		}
	}

	protected void initModels() {

	}

	@Override
	protected void initViews(View v) {
		join_leave= (Button) v.findViewById(R.id.btnAgreeTerm);
		join_leave.setOnClickListener(this);
	}

	@Override
	protected void initAnimations() {

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
							shop = new Shop(response.getJSONObject("data"));
							
							if ((shop.current_customer_shop_id==null) || (shop.current_customer_shop_id=="")){
								join_leave.setText("Tham gia");
								isJoin = false;
							}
							else{
								join_leave.setText("Roi di");
								isJoin = true;
							}
							
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
	public static ShopInfoFragment mInstance;

	public static ShopInfoFragment getInstance(CoreActivity activity) {
		if (mInstance == null) {
			mInstance = new ShopInfoFragment();
		}
		mActivity = activity;
		return mInstance;
	}

	public static ShopInfoFragment getInstance() {
		if (mInstance == null) {
			mInstance = new ShopInfoFragment();
		}
		return mInstance;
	}

	@Override
	protected void initListener() {
		// TODO Auto-generated method stub

	}
	
public void api_join_shop(int shop_id){
	 	
    	Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", CanvasFragment.mUser.getAccessToken());
		params.put("shop_id", String.valueOf(shop_id));
			RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
			HandleRequest jsObjRequest = new HandleRequest(Method.POST,
					HandleRequest.JOIN_SHOP, params, 
					new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.d("api_join_shop",response.toString());
						// code here
					}
					}, 
					new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
						}
					});
			requestQueue.add(jsObjRequest);
	}
	
	public void api_leave_shop(int shop_id){
	 	
    	Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", CanvasFragment.mUser.getAccessToken());
		params.put("shop_id", String.valueOf(shop_id));
			RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
			HandleRequest jsObjRequest = new HandleRequest(Method.POST,
					HandleRequest.LEAVE_SHOP, params, 
					new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.d("api_leave_shop",response.toString());
						// code here
					}
					}, 
					new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
						}
					});
			requestQueue.add(jsObjRequest);
	}
	
}

