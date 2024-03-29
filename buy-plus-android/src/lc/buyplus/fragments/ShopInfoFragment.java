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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import lc.buyplus.R;
import lc.buyplus.activities.AnnouncementDetailActivity;
import lc.buyplus.activities.LoginActivity;
import lc.buyplus.activities.MapShopActivity;
import lc.buyplus.activities.ShopInfoActivity;
import lc.buyplus.adapter.ShopAnnounmentAdapter;
import lc.buyplus.application.MonApplication;
import lc.buyplus.cores.CoreActivity;
import lc.buyplus.cores.CoreFragment;
import lc.buyplus.cores.HandleRequest;
import lc.buyplus.customizes.BlurBuilder;
import lc.buyplus.customizes.BlurTransformation;
import lc.buyplus.customizes.CustomDialogClass;
import lc.buyplus.customizes.DialogMessage;
import lc.buyplus.customizes.FastBlur;
import lc.buyplus.customizes.RoundedImageView;
import lc.buyplus.models.Announcement;
import lc.buyplus.models.Friend;
import lc.buyplus.models.Gift;
import lc.buyplus.models.Notification;
import lc.buyplus.models.Shop;
import lc.buyplus.models.Store;

public class ShopInfoFragment extends CoreFragment {
	private ListView listView;
	private ShopAnnounmentAdapter newsAdapter;
	private LayoutInflater inflaterActivity;
	private ImageView imbackground;
	private RelativeLayout rlBackground;
	private TextView tvName, tvField, tvPhone, tvWeb, tvFb, storeName;
	public static Button join_leave;
	public static boolean isJoin;
	Shop shop;
	private LinearLayout rlbanner;
	private RoundedImageView imbannerStore;
	ImageLoader imageLoader = MonApplication.getInstance().getImageLoader();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_store_info, container, false);
		initViews(view);
		initModels();
		initAnimations();
		// api_get_shop_info(Store.current_shop_id);
		return view;
	}

	@SuppressLint("NewApi")
	@Override
	public void onClick(View view) {
		Intent returnIntent, fbIntent;
		Uri uri;
		switch (view.getId()) {
		case R.id.btnAgreeTerm:
			if (isJoin) {
				CustomDialogClass dialog = new CustomDialogClass(mActivity, "Bạn có muốn ra khỏi Shop ?", 2);
				dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
				dialog.show();
			} else {
				CustomDialogClass dialog = new CustomDialogClass(mActivity, "Bạn có muốn tham gia shop ?", 2);
				dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
				dialog.show();
			}
			break;
		case R.id.tvFb:
			try {
				if (Store.get_current_shop().getFacebook_id().contains("http")){
					uri = Uri.parse(Store.get_current_shop().getFacebook_id());
					fbIntent = new Intent(Intent.ACTION_VIEW, uri);
					startActivity(fbIntent);
				}else {
					String url = "https://www.facebook.com/" + Store.get_current_shop().getFacebook_id();
					startActivity(newFacebookIntent(mActivity.getPackageManager(), url));
				}
			} catch (Exception e) {
				DialogMessage dialog = new DialogMessage(mActivity,
						mActivity.getResources().getString(R.string.fb_problem));
				dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
				dialog.show();
			}

			break;
		case R.id.tvWeb:
			try {
				if (Store.get_current_shop().getWebsite().contains("http")) {
					uri = Uri.parse(Store.get_current_shop().getWebsite());
				} else {
					uri = Uri.parse("http://" + Store.get_current_shop().getWebsite());
				}
				returnIntent = new Intent(Intent.ACTION_VIEW, uri);

				startActivity(returnIntent);
			} catch (Exception e) {
				DialogMessage dialog = new DialogMessage(mActivity,
						mActivity.getResources().getString(R.string.web_problem));
				dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
				dialog.show();
			}
			break;

		case R.id.tvPhone:
			try {
				Intent callIntent = new Intent(Intent.ACTION_CALL);
				callIntent.setData(Uri.parse("tel:" + tvPhone.getText().toString()));
				startActivity(callIntent);
			} catch (ActivityNotFoundException e) {
			}
			break;

		case R.id.tvName:
			if (!Store.current_shop.getLat().trim().equals("") && !Store.current_shop.getLng().trim().equals("")) {
				Intent mainActivity = new Intent(mActivity, MapShopActivity.class);
				try {
					startActivity(mainActivity);
				} catch (Exception e) {
					DialogMessage dialog = new DialogMessage(mActivity,
							mActivity.getResources().getString(R.string.map_problem));
					dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
					dialog.show();
				}
			}
			break;
		}
	}

	public static Intent newFacebookIntent(PackageManager pm, String url) {
		Uri uri = Uri.parse(url);
		try {
			ApplicationInfo applicationInfo = pm.getApplicationInfo("com.facebook.katana", 0);
			if (applicationInfo.enabled) {
				uri = Uri.parse("fb://facewebmodal/f?href=" + url);
			}
		} catch (PackageManager.NameNotFoundException ignored) {
		}
		return new Intent(Intent.ACTION_VIEW, uri);
	}

	protected void initModels() {

	}

	@SuppressLint("NewApi")
	@Override
	protected void initViews(View v) {

		join_leave = (Button) v.findViewById(R.id.btnAgreeTerm);
		join_leave.setOnClickListener(this);
		imbannerStore = (RoundedImageView) v.findViewById(R.id.imbannerStore);
		rlBackground = (RelativeLayout) v.findViewById(R.id.rlBackground);
		imbannerStore.setImageUrl(Store.current_shop.getImage(), imageLoader);
		// imbannerStore.buildDrawingCache();

		imbackground = (ImageView) v.findViewById(R.id.imbackground);
		Glide.with(CanvasFragment.mActivity).load(Store.current_shop.getImage()).placeholder(imbackground.getDrawable())
				.centerCrop().diskCacheStrategy(DiskCacheStrategy.SOURCE).transform(new BlurTransformation(mActivity))
				.into(imbackground);

		rlbanner = (LinearLayout) v.findViewById(R.id.rlbanner);
		tvName = (TextView) v.findViewById(R.id.tvName);
		tvField = (TextView) v.findViewById(R.id.tvField);
		tvPhone = (TextView) v.findViewById(R.id.tvPhone);
		tvWeb = (TextView) v.findViewById(R.id.tvWeb);
		tvFb = (TextView) v.findViewById(R.id.tvFb);
		storeName = (TextView) v.findViewById(R.id.storeName);

		tvFb.setOnClickListener(this);
		tvName.setOnClickListener(this);
		tvPhone.setOnClickListener(this);

		if ((Store.current_shop.current_customer_shop_id == null)
				|| (Store.current_shop.current_customer_shop_id == "")) {
			join_leave.setText("Tham gia");
			join_leave.setBackground(getResources().getDrawable(R.drawable.round_button_gray));
			isJoin = false;
		} else {
			join_leave.setText("Đã tham gia");
			join_leave.setBackground(getResources().getDrawable(R.drawable.round_button_green));
			isJoin = true;
		}
		if (!Store.current_shop.getAddress().trim().equals("")) {
			tvName.setText(Store.current_shop.getAddress());
		} else {
			if (!Store.current_shop.getLat().trim().equals("") && !Store.current_shop.getLng().trim().equals("")) {
				tvName.setText("Đang cập nhật -> Bản đồ");
			} else {
				tvName.setText("Đang cập nhật...");
			}
		}

		Log.d("WEB", Store.current_shop.getAddress());
		tvField.setText(Store.current_shop.getDescription());
		tvPhone.setText(Store.current_shop.getPhone());
		if (!Store.current_shop.getWebsite().trim().equals("")) {
			tvWeb.setText(Store.current_shop.getWebsite());
			tvWeb.setOnClickListener(this);
		} else {
			tvWeb.setText("Facebook Page.");
		}
		storeName.setText(Store.current_shop.getName());
		tvFb.setText(String.valueOf(Store.current_shop.getFacebook_id()));

	}

	@Override
	protected void initAnimations() {

	}

	@SuppressLint("NewApi")
	public void api_get_shop_info(int shop_id) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", CanvasFragment.mUser.getAccessToken());
		params.put("shop_id", String.valueOf(shop_id));
		RequestQueue requestQueue = MonApplication.getInstance().getRequestQueue();
		HandleRequest jsObjRequest = new HandleRequest(Method.GET,
				HandleRequest.build_link(HandleRequest.GET_SHOP_INFO, params), params,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.d("api_get_shop_info", response.toString());
						try {
							if (Integer.parseInt(response.getString("error")) == 2) {
								DialogMessage dialog = new DialogMessage(mActivity,
										mActivity.getResources().getString(R.string.end_session));

								dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
								dialog.show();
								SharedPreferences pre = getmContext().getSharedPreferences("buy_pus", 0);
								SharedPreferences.Editor editor = pre.edit();
								// editor.clear();
								editor.putBoolean("immediate_login", false);
								editor.commit();
								Intent loginActivity = new Intent(mActivity, LoginActivity.class);
								startActivity(loginActivity);
								mActivity.finish();

							} else if (Integer.parseInt(response.getString("error")) == 1) {
								DialogMessage dialog = new DialogMessage(mActivity, response.getString("message"));
								dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
								dialog.show();
							} else {
								shop = new Shop(response.getJSONObject("data"));

							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
						Store.isConnectNetwotk = true;
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						if (Store.isConnectNetwotk == true) {
							Store.isConnectNetwotk = false;
							DialogMessage dialog = new DialogMessage(mActivity,
									mActivity.getResources().getString(R.string.connect_problem));
							dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
							dialog.show();
						}
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

	@SuppressLint("NewApi")
	private void blur(Bitmap bkg, View view) {
		long startMs = System.currentTimeMillis();
		int radius = 20;

		Bitmap overlay = Bitmap.createScaledBitmap(bkg, (int) (view.getWidth()), (int) (view.getHeight()), true);
		Canvas canvas = new Canvas(overlay);
		canvas.translate(-view.getLeft(), -view.getTop());
		canvas.drawBitmap(overlay, 0, 0, null);
		overlay = FastBlur.doBlur(overlay, (int) radius, true);
		view.setBackground(new BitmapDrawable(getResources(), overlay));
	}

}
