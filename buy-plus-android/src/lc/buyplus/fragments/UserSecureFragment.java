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
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import lc.buyplus.R;
import lc.buyplus.activities.AddFriendActivity;
import lc.buyplus.adapter.ShopFriendAdapter;
import lc.buyplus.cores.CoreActivity;
import lc.buyplus.cores.CoreFragment;
import lc.buyplus.cores.HandleRequest;
import lc.buyplus.customizes.DialogUser;
import lc.buyplus.models.Friend;
import lc.buyplus.models.Store;

public class UserSecureFragment extends CoreFragment {
	private ListView listView;
	private ShopFriendAdapter friendAdapter;
	private LayoutInflater inflaterActivity;
	private ImageView imAdd;
	private Button accept, reject;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_user_secure, container, false);
		initViews(view);
		initModels();
		initAnimations();
		inflaterActivity = inflater;
		return view;
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btnAgreeTerm:
			DialogUser dialog = new DialogUser(mActivity,"Bạn có muốn lưu thay đổi không?", 1);
			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			dialog.show();
		break;	
		case R.id.btnIgnore:
			DialogUser dialog1 = new DialogUser(mActivity,"Bạn có muốn thoát và không lưu thay đổi ?", 1);
			dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			dialog1.show();
		break;	
		}
	}

	protected void initModels() {

	}

	@Override
	protected void initViews(View v) {
		accept = (Button) v.findViewById(R.id.btnAgreeTerm);
		reject = (Button) v.findViewById(R.id.btnIgnore);
		accept.setOnClickListener(this);
		reject.setOnClickListener(this);
	}

	@Override
	protected void initAnimations() {

	}

	public void api_get_shop_friends(int shop_id) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", CanvasFragment.mUser.getAccessToken());
		params.put("shop_id", String.valueOf(shop_id));
		RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
		HandleRequest jsObjRequest = new HandleRequest(Method.GET,
				HandleRequest.build_link(HandleRequest.GET_SHOP_FRIENDS, params), params,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.d("api_get_shop_gifts", response.toString());
						try {
							ArrayList<Friend> FriendsList = new ArrayList<Friend>();
							JSONArray data_aray = response.getJSONArray("data");
							for (int i = 0; i < data_aray.length(); i++) {
								Friend friend = new Friend((JSONObject) data_aray.get(i));
								FriendsList.add(friend);
							}
							friendAdapter = new ShopFriendAdapter(FriendsList, inflaterActivity);
							listView.setAdapter(friendAdapter);
							friendAdapter.notifyDataSetChanged();
						} catch (JSONException e) {

							e.printStackTrace();
						}
						// code here
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
	public static UserSecureFragment mInstance;

	public static UserSecureFragment getInstance(CoreActivity activity) {
		if (mInstance == null) {
			mInstance = new UserSecureFragment();
		}
		mActivity = activity;
		return mInstance;
	}

	public static UserSecureFragment getInstance() {
		if (mInstance == null) {
			mInstance = new UserSecureFragment();
		}
		return mInstance;
	}

	@Override
	protected void initListener() {
		// TODO Auto-generated method stub

	}
}
