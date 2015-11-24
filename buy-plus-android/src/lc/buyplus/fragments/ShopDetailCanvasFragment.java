package lc.buyplus.fragments;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.Volley;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import lc.buyplus.R;
import lc.buyplus.activities.ShopFriendActivity;
import lc.buyplus.activities.ShopInfoActivity;
import lc.buyplus.cores.CoreActivity;
import lc.buyplus.cores.CoreFragment;
import lc.buyplus.cores.HandleRequest;
import lc.buyplus.customizes.CustomDialogClass;
import lc.buyplus.customizes.DialogMessage;
import lc.buyplus.customizes.MyAnimations;
import lc.buyplus.customizes.MyEditText;
import lc.buyplus.customizes.MyTextView;
import lc.buyplus.models.Shop;
import lc.buyplus.models.Store;
import lc.buyplus.models.UserAccount;

public class ShopDetailCanvasFragment extends CoreFragment {

	private static final long serialVersionUID = 1L;
	private LinearLayout mShopAnnouncements, mShopInfo, mShopImages, mShopNoti, mShopSetting;
	private RelativeLayout rHomeTab;
	private LinearLayout mBack, mSortTab;
	private LinearLayout mSearchBlock, mTitleBlock;
	private LinearLayout mSearchBlockCancel;
	private MyEditText mSearchEdittext;
	private TextView tvPoint;
	private LinearLayout rHomeBaiviet, rHomeThongtin, rHomeHinhanh;
	private MyTextView mTitle;
	private TextView tvPost, tvShopInfo, tvShopPhoto;
	private Shop shop;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_shop_canvas, container, false);
		initViews(view);
		initModels();
		initListener();
		initAnimations();

		return view;
	}

	public void hideSearchBlock() {
		mTitleBlock.setVisibility(View.VISIBLE);
		mSearchBlock.setVisibility(View.GONE);
		mSearchBlock.clearAnimation();
	}

	public void showSearchBlock() {
		mTitleBlock.setVisibility(View.GONE);
		mSearchBlock.setVisibility(View.VISIBLE);
		mSearchBlock.startAnimation(MyAnimations.fromLeft(800, 200));
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.fragment_canvas_back:
			Store.current_shop = null;
			mActivity.finish();
			break;
		case R.id.fragment_canvas_search:
			showSearchBlock();
			break;
		case R.id.fragment_canvas_sort:

			break;
		case R.id.fragment_canvas_home_tab:
			CanvasFragment.mPager.setCurrentItem(0);
			mActivity.finish();
			break;
		case R.id.fragment_canvas_personal_tab:
			CanvasFragment.mPager.setCurrentItem(2);
			mActivity.finish();
			break;
		case R.id.fragment_canvas_loyaltycard_tab:
			CanvasFragment.mPager.setCurrentItem(3);
			mActivity.finish();
			break;
		case R.id.fragment_canvas_notifications_tab:
			CanvasFragment.mPager.setCurrentItem(4);
			mActivity.finish();
			break;
		case R.id.fragment_canvas_setting_tab:
			CanvasFragment.mPager.setCurrentItem(5);
			mActivity.finish();
			break;
		case R.id.rHome_baiviet:
			mPager.setCurrentItem(0);
			break;
		case R.id.rHome_thongtin:
			mPager.setCurrentItem(1);
			break;
		case R.id.rHome_hinhanh:
			mPager.setCurrentItem(2);
			break;	
		default:
			break;
		}
	}
	

	
	public static final int NUM_PAGES = 3;
	public static ViewPager mPager;
	public PagerAdapter mPagerAdapter;

	public class ShopSlidePagerAdapter extends FragmentPagerAdapter  {
		public ShopSlidePagerAdapter(android.support.v4.app.FragmentManager fm) {
			super(fm);
		}

		@Override
		public CoreFragment getItem(int position) {
			switch (position) {
			case 0:
				return ShopAnnounmentFragment.getInstance(mActivity);
			case 1:
				return ShopInfoFragment.getInstance(mActivity);
			case 2:
				return ShopImageFragment.getInstance(mActivity);
			default:
				break;
			}
			return null;
		}

		@Override
		public int getCount() {
			return NUM_PAGES;
		}
	}

	public void changeTabState(boolean announcements, boolean info, boolean images) {
		if (announcements) {
			//mShopAnnouncements.setBackgroundColor(getResources().getColor(R.color.tab_selected));
			//rHomeBaiviet.setBackgroundColor(getResources().getColor(R.color.tab_selected));
			tvPost.setTextColor(getResources().getColor(R.color.tab_selected));
		} else {
			//mShopAnnouncements.setBackgroundColor(getResources().getColor(R.color.maincolor));
			//rHomeBaiviet.setBackgroundColor(Color.parseColor("#FFFFFF"));
			tvPost.setTextColor(getResources().getColor(R.color.title));
		}
		if (info) {
			//mShopInfo.setBackgroundColor(getResources().getColor(R.color.tab_selected));
			//rHomeThongtin.setBackgroundColor(getResources().getColor(R.color.tab_selected));
			tvShopInfo.setTextColor(getResources().getColor(R.color.tab_selected));
		} else {
			//mShopInfo.setBackgroundColor(getResources().getColor(R.color.maincolor));
			//rHomeThongtin.setBackgroundColor(Color.parseColor("#FFFFFF"));
			tvShopInfo.setTextColor(getResources().getColor(R.color.title));
		}
		if (images) {
			//mShopImages.setBackgroundColor(getResources().getColor(R.color.tab_selected));
			//rHomeHinhanh.setBackgroundColor(getResources().getColor(R.color.tab_selected));
			tvShopPhoto.setTextColor(getResources().getColor(R.color.tab_selected));
		} else {
			//mShopImages.setBackgroundColor(getResources().getColor(R.color.maincolor));
			//rHomeHinhanh.setBackgroundColor(Color.parseColor("#FFFFFF"));
			tvShopPhoto.setTextColor(getResources().getColor(R.color.title));
		}
	}

	protected void initModels() {
		mPagerAdapter = new ShopSlidePagerAdapter(getFragmentManager());
		mPager.setOffscreenPageLimit(NUM_PAGES);
		mPager.setAdapter(mPagerAdapter);
		mPagerAdapter.notifyDataSetChanged();
		mPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				switch (mPager.getCurrentItem()) {
				case 0:
					changeTabState(true, false, false);
					break;
				case 1:
					changeTabState(false, true, false);
					break;
				case 2:
					changeTabState(false, false, true);
					break;
				default:
					break;
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});

		mPager.setCurrentItem(firstTab);
		changeTabState(true, false, false);

	}

	@Override
	protected void initViews(View v) {
		mPager = (ViewPager) v.findViewById(R.id.viewpager);
		mShopAnnouncements = (LinearLayout) v.findViewById(R.id.fragment_canvas_home_tab);
		mShopInfo = (LinearLayout) v.findViewById(R.id.fragment_canvas_personal_tab);
		mShopImages = (LinearLayout) v.findViewById(R.id.fragment_canvas_loyaltycard_tab);
		mShopNoti = (LinearLayout) v.findViewById(R.id.fragment_canvas_notifications_tab);
		mShopSetting = (LinearLayout) v.findViewById(R.id.fragment_canvas_setting_tab);
		mTitle = (MyTextView) v.findViewById(R.id.fragment_canvas_title);
		mTitle.setText(Store.current_shop_name);
		mBack = (LinearLayout) v.findViewById(R.id.fragment_canvas_back);

		rHomeTab = (RelativeLayout) v.findViewById(R.id.rhomeTab);
		rHomeTab.setVisibility(View.VISIBLE);
		mTitleBlock = (LinearLayout) v.findViewById(R.id.fragment_canvas_title_block);
		rHomeBaiviet = (LinearLayout) v.findViewById(R.id.rHome_baiviet);
		rHomeThongtin = (LinearLayout) v.findViewById(R.id.rHome_thongtin);
		rHomeHinhanh = (LinearLayout) v.findViewById(R.id.rHome_hinhanh);
		
		tvPoint = (TextView) v.findViewById(R.id.tvPoint);
		tvPoint.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				api_get_shop_info(Store.current_shop_id);
				
			}
		});
		
		tvPost = (TextView) v.findViewById(R.id.tvPost);
		tvShopInfo = (TextView) v.findViewById(R.id.tvShopInfo);
		tvShopPhoto = (TextView) v.findViewById(R.id.tvShopPhoto);
	}
	
	 public void onActivityResult(int requestCode, int resultCode, Intent data) {
		    if (resultCode == 2) {
		    	mActivity.finish();
		    }
		}
	 
	@Override
	protected void initListener() {
		mShopAnnouncements.setOnClickListener(this);
		mShopInfo.setOnClickListener(this);
		mShopImages.setOnClickListener(this);
		mShopNoti.setOnClickListener(this);
		mShopSetting.setOnClickListener(this);
		mBack.setOnClickListener(this);
		rHomeBaiviet.setOnClickListener(this);
		rHomeThongtin.setOnClickListener(this);
		rHomeHinhanh.setOnClickListener(this);
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
							if ((shop.current_customer_shop_id == null) || (shop.current_customer_shop_id == "")) {
								CustomDialogClass dialog = new CustomDialogClass(mActivity,"Bạn chưa tham gia shop này", 999);
								dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
								dialog.show();
							} else {
								Intent shopFriendActivity = new Intent(mActivity, ShopFriendActivity.class);
								startActivityForResult(shopFriendActivity,2);
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

	public static int firstTab = 0;
	public static UserAccount mUser;
	public static CoreActivity mActivity;
	public static ShopDetailCanvasFragment mInstance;

	public static ShopDetailCanvasFragment getInstance(CoreActivity activity, UserAccount user) {
		if (mInstance == null) {
			mInstance = new ShopDetailCanvasFragment();
		}
		mActivity = activity;
		mUser = user;
		return mInstance;
	}

	public static ShopDetailCanvasFragment getInstance() {
		if (mInstance == null) {
			mInstance = new ShopDetailCanvasFragment();
		}
		return mInstance;
	}
}
