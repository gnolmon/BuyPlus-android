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
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import lc.buyplus.R;
import lc.buyplus.activities.ShopInfoActivity;
import lc.buyplus.adapter.ShopAnnounmentAdapter;
import lc.buyplus.application.MonApplication;
import lc.buyplus.cores.CoreActivity;
import lc.buyplus.cores.CoreFragment;
import lc.buyplus.cores.HandleRequest;
import lc.buyplus.customizes.BlurBuilder;
import lc.buyplus.customizes.RoundedImageView;
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
	private RelativeLayout rlbanner;
	private RoundedImageView imbannerStore;
	ImageLoader imageLoader = MonApplication.getInstance().getImageLoader();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_store_info, container, false);
		initViews(view);
		initModels();
		initAnimations();
		api_get_shop_info(Store.current_shop_id);
		return view;
	}

	@SuppressLint("NewApi")
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btnAgreeTerm:
			if (isJoin){
				api_leave_shop(Store.current_shop_id);
				join_leave.setText("Đã tham gia");
				join_leave.setBackground(getResources().getDrawable(R.drawable.round_button_green));
				isJoin = false;
			}
			else{
				api_join_shop(Store.current_shop_id);
				join_leave.setText("Tham gia");
				join_leave.setBackground(getResources().getDrawable(R.drawable.round_button_gray));
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
		imbannerStore = (RoundedImageView) v.findViewById(R.id.imbannerStore);
		rlbanner = (RelativeLayout) v.findViewById(R.id.rlbanner);
		tvName = (TextView) v.findViewById(R.id.tvName);
		tvField = (TextView) v.findViewById(R.id.tvField);
		tvPhone = (TextView) v.findViewById(R.id.tvPhone);
		tvWeb = (TextView) v.findViewById(R.id.tvWeb);
		tvFb = (TextView) v.findViewById(R.id.tvFb);
	}

	@Override
	protected void initAnimations() {

	}

	@SuppressLint("NewApi")
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
								join_leave.setText("Đã tham gia");
								join_leave.setBackground(getResources().getDrawable(R.drawable.round_button_green));
								isJoin = false;
							}
							else{
								join_leave.setText("Tham gia");
								join_leave.setBackground(getResources().getDrawable(R.drawable.round_button_gray));
								isJoin = true;
							}
							
							tvName.setHint(shop.getAddress()); 
							tvField.setHint(shop.getName()); 
							tvPhone.setHint(shop.getPhone()); 
							tvWeb.setHint(shop.getWebsite()); 
							tvFb.setHint(String.valueOf(shop.getFacebook_id()));
							
							Log.d("sadsd", shop.getAddress());
							Log.d("sadsd", shop.getName());
							Log.d("sadsd", shop.getPhone());
							Log.d("sadsd", shop.getWebsite());
							Log.d("sadsd", String.valueOf(shop.getFacebook_id()));
							imbannerStore.setImageUrl(shop.getImage(), imageLoader);
							
							BitmapDrawable drawable = (BitmapDrawable) imbannerStore.getDrawable();
							final Bitmap bmap = drawable.getBitmap();
							
							/*if (imbannerStore.getWidth() > 0) {	
								int sdk = android.os.Build.VERSION.SDK_INT;
								if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
								    //if you want take image from resources - getResources().getDrawable(your_id)
									Bitmap image = Bitmap.createScaledBitmap(bmap,150, 150, true);
									rlbanner.setBackground(new BitmapDrawable(getResources(), image));
								} else {
									Bitmap image = BlurBuilder.blur(imbannerStore);
									rlbanner.setBackground(new BitmapDrawable(getResources(), image));
								}
								
							} else {
								imbannerStore.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
							        @Override
							        public void onGlobalLayout() {	
							            int sdk = android.os.Build.VERSION.SDK_INT;
										if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
										    //if you want take image from resources - getResources().getDrawable(your_id)
											Bitmap image = Bitmap.createScaledBitmap(bmap,150, 150, true);
											rlbanner.setBackground(new BitmapDrawable(getResources(), image));
										} else {
											Bitmap image = BlurBuilder.blur(imbannerStore);
											rlbanner.setBackground(new BitmapDrawable(getResources(), image));
										}
							        }
							    });
							}*/
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

