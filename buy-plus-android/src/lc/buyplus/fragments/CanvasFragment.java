package lc.buyplus.fragments;

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
import com.facebook.CallbackManager;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import lc.buyplus.R;
import lc.buyplus.cores.CoreActivity;
import lc.buyplus.cores.CoreFragment;
import lc.buyplus.cores.HandleRequest;
import lc.buyplus.customizes.DialogMessage;
import lc.buyplus.customizes.DialogNews;
import lc.buyplus.customizes.DialogSort;
import lc.buyplus.customizes.MyAnimations;
import lc.buyplus.customizes.MyEditText;
import lc.buyplus.customizes.MyTextView;
import lc.buyplus.models.Announcement;
import lc.buyplus.models.Shop;
import lc.buyplus.models.Store;
import lc.buyplus.models.UserAccount;

public class CanvasFragment extends CoreFragment {

	private static final long serialVersionUID = 1L;
	private LinearLayout mHomeTab, mPersonalTab, mLoyaltyCardTab, mSettingTab, rHidAnnouce, rHidNews,rHidSort;
	RelativeLayout mNotiTab;
	private LinearLayout rHomeTab;
	private LinearLayout mSearchTab, mSortTab;
	private LinearLayout mSearchBlock, mTitleBlock, idSort;
	private LinearLayout mSearchBlockCancel;
	private MyEditText mSearchEdittext;
	private CallbackManager callbackManager;
	private TextView tvStore, tvNews,tvNumNoti;
	private ImageView imNewsDialog, imSearch ,imJoinShop;
	private MyTextView mTitle;
	private boolean isSearching = false;
	private boolean isHomeRefresh = false;
	private boolean isAnnoRefresh = false;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		callbackManager = CallbackManager.Factory.create();
		View view = inflater.inflate(R.layout.fragment_canvas, container, false);

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
		InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(mContext.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(mSearchEdittext.getWindowToken(), 0);

	}

	public void showSearchBlock() {
		mTitleBlock.setVisibility(View.GONE);
		mSearchBlock.setVisibility(View.VISIBLE);
		mSearchBlock.startAnimation(MyAnimations.fromLeft(800, 200));
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		callbackManager.onActivityResult(requestCode, resultCode, data);
	}

	public static DialogNews sortDialog;

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.fragnemt_canvas_search_cancel:
			mSearchEdittext.setText("");
			Store.Shop_Search_param = "";
        	api_get_all_shop(0, Store.limit, Store.Shop_Search_param);
			hideSearchBlock();
			break;
		case R.id.fragment_canvas_search:
			showSearchBlock();
			mSearchEdittext.requestFocus();
			mSearchEdittext.postDelayed(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					InputMethodManager keyboard = (InputMethodManager) mActivity
							.getSystemService(mContext.INPUT_METHOD_SERVICE);
					keyboard.showSoftInput(mSearchEdittext, 0);
				}
			}, 200);
			break;
		case R.id.imSearch:

			break;
		case R.id.fragment_canvas_home_tab:
			if ((mPager.getCurrentItem()==0)){
				if ((isHomeRefresh==false)){
					isHomeRefresh = true;
					api_get_all_shop(0, Store.limit, Store.Shop_Search_param);
					if (Store.ShopsList.size()>0) HomeFragment.listView.setSelection(0);
				}
			}
			else if ((mPager.getCurrentItem()==1)){
				if ( (isAnnoRefresh == false)){
					isAnnoRefresh = true;
					api_get_all_announcements(0, Store.limit, HomeAnnounmentFragment.type, 0, 0);
					if (Store.AnnouncementsList.size()>0) HomeAnnounmentFragment.listView.setSelection(0);
				}
			}
			else	
			mPager.setCurrentItem(1);
			break;
		case R.id.fragment_canvas_personal_tab:
			mPager.setCurrentItem(2);
			break;
		case R.id.fragment_canvas_loyaltycard_tab:
			mPager.setCurrentItem(3);
			break;
		case R.id.fragment_canvas_notifications_tab:
			tvNumNoti.setVisibility(View.GONE);
			imJoinShop.setVisibility(View.GONE);
			mPager.setCurrentItem(4);
			break;
		case R.id.fragment_canvas_setting_tab:
			mPager.setCurrentItem(5);
			break;
		case R.id.idAnnouce:
			mPager.setCurrentItem(0);
			break;
		case R.id.idNews:
			mPager.setCurrentItem(1);
			break;
		case R.id.idSort:
			break;
		case R.id.imNewsDialog:
			if (sortDialog == null) {
				sortDialog = new DialogNews(mActivity);
			}
			sortDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			sortDialog.show();
			break;
		default:
			break;
		}
	}

	public static final int NUM_PAGES = 6;
	public static ViewPager mPager;
	public PagerAdapter mPagerAdapter;

	public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
		public ScreenSlidePagerAdapter(android.support.v4.app.FragmentManager fm) {
			super(fm);
		}

		@Override
		public CoreFragment getItem(int position) {
			switch (position) {
			case 0:
				return HomeFragment.getInstance(mActivity);
			case 1:
				return HomeAnnounmentFragment.getInstance(mActivity);
			case 2:
				return PersonalFragment.getInstance(mActivity);
			case 3:
				return LoyaltyCardFragment.getInstance(mActivity);
			case 4:
				return NotificationsFragment.getInstance(mActivity);
			case 5:
				return SettingFragment.getInstance(mActivity);
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

	public void changeTabState(boolean home, boolean homenews, boolean personal, boolean loyaltycard, boolean noti,
			boolean setting) {
		hideSearchBlock();
		if (home) {
			rHomeTab.setVisibility(View.VISIBLE);
			mHomeTab.setBackgroundColor(getResources().getColor(R.color.tab_selected));
			mTitle.setText(getResources().getString(R.string.canvas_title_home));

			tvStore.setTextColor(getResources().getColor(R.color.tab_selected));

			tvNews.setTextColor(getResources().getColor(R.color.title));
			imNewsDialog.setImageResource(R.drawable.down_gray);
			;
		} else if (homenews) {

			rHomeTab.setVisibility(View.VISIBLE);
			mHomeTab.setBackgroundColor(getResources().getColor(R.color.tab_selected));
			mTitle.setText(getResources().getString(R.string.canvas_title_home));

			tvNews.setTextColor(getResources().getColor(R.color.tab_selected));

			tvStore.setTextColor(getResources().getColor(R.color.title));
			imNewsDialog.setImageResource(R.drawable.down_green);
		} else {
			mHomeTab.setBackgroundColor(getResources().getColor(R.color.maincolor));
			rHomeTab.setVisibility(View.GONE);

		}
		if (personal) {
			mPersonalTab.setBackgroundColor(getResources().getColor(R.color.tab_selected));
			mTitle.setText(getResources().getString(R.string.canvas_title_personal));
		} else {
			mPersonalTab.setBackgroundColor(getResources().getColor(R.color.maincolor));
		}
		if (loyaltycard) {
			mLoyaltyCardTab.setBackgroundColor(getResources().getColor(R.color.tab_selected));
			mTitle.setText(getResources().getString(R.string.canvas_title_loyaltycard));
		} else {
			mLoyaltyCardTab.setBackgroundColor(getResources().getColor(R.color.maincolor));
		}
		if (noti) {
			mNotiTab.setBackgroundColor(getResources().getColor(R.color.tab_selected));
			mTitle.setText(getResources().getString(R.string.canvas_title_notifications));
		} else {
			mNotiTab.setBackgroundColor(getResources().getColor(R.color.maincolor));
		}
		if (setting) {
			mSettingTab.setBackgroundColor(getResources().getColor(R.color.tab_selected));
			mTitle.setText(getResources().getString(R.string.canvas_title_setting));
		} else {
			mSettingTab.setBackgroundColor(getResources().getColor(R.color.maincolor));
		}
	}

	public void initModels() {
		mPagerAdapter = new ScreenSlidePagerAdapter(getFragmentManager());
		mPager.setOffscreenPageLimit(NUM_PAGES);
		mPager.setAdapter(mPagerAdapter);
		mPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				switch (mPager.getCurrentItem()) {
				case 0:
					changeTabState(true, false, false, false, false, false);
					break;
				case 1:
					changeTabState(false, true, false, false, false, false);
					break;
				case 2:
					changeTabState(false, false, true, false, false, false);
					break;
				case 3:
					changeTabState(false, false, false, true, false, false);
					break;
				case 4:
					changeTabState(false, false, false, false, true, false);
					break;
				case 5:
					changeTabState(false, false, false, false, false, true);
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
		changeTabState(false, false, false, true, false, false);

		mSearchEdittext.setHintTextColor(getResources().getColor(R.color.white));
	}

	@Override
	protected void initViews(View v) {
		mPager = (ViewPager) v.findViewById(R.id.viewpager);
		mHomeTab = (LinearLayout) v.findViewById(R.id.fragment_canvas_home_tab);
		mPersonalTab = (LinearLayout) v.findViewById(R.id.fragment_canvas_personal_tab);
		mLoyaltyCardTab = (LinearLayout) v.findViewById(R.id.fragment_canvas_loyaltycard_tab);
		mNotiTab = (RelativeLayout) v.findViewById(R.id.fragment_canvas_notifications_tab);
		mSettingTab = (LinearLayout) v.findViewById(R.id.fragment_canvas_setting_tab);
		mTitle = (MyTextView) v.findViewById(R.id.fragment_canvas_title);
		mSettingTab = (LinearLayout) v.findViewById(R.id.fragment_canvas_setting_tab);
		mSearchTab = (LinearLayout) v.findViewById(R.id.fragment_canvas_search);
		mSortTab = (LinearLayout) v.findViewById(R.id.fragment_canvas_sort);
		mSearchBlock = (LinearLayout) v.findViewById(R.id.fragment_canvas_search_block);
		mTitleBlock = (LinearLayout) v.findViewById(R.id.fragment_canvas_title_block);
		mSearchBlockCancel = (LinearLayout) v.findViewById(R.id.fragnemt_canvas_search_cancel);
		mSearchEdittext = (MyEditText) v.findViewById(R.id.fragment_canvas_search_edittext);
		rHomeTab = (LinearLayout) v.findViewById(R.id.rhomeTab);
		rHomeTab.setVisibility(View.VISIBLE);
		rHidAnnouce = (LinearLayout) v.findViewById(R.id.idAnnouce);

		idSort = (LinearLayout) v.findViewById(R.id.idSort);

		rHidNews = (LinearLayout) v.findViewById(R.id.idNews);

		// TextView, ImageView
		tvNews = (TextView) v.findViewById(R.id.tvNews);
		tvStore = (TextView) v.findViewById(R.id.tvStore);
		tvNumNoti = (TextView) v.findViewById(R.id.tvNumNoti);
		imJoinShop = (ImageView) v.findViewById(R.id.imJoinShop);
		imNewsDialog = (ImageView) v.findViewById(R.id.imNewsDialog);
		imSearch = (ImageView) v.findViewById(R.id.imSearch);
		
		mSearchEdittext.setOnKeyListener(new OnKeyListener() {
		    public boolean onKey(View v, int keyCode, KeyEvent event) {
		        // If the event is a key-down event on the "enter" button
		        if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
		            (keyCode == KeyEvent.KEYCODE_ENTER) && (isSearching==false)) {
		        	isSearching = true;
		        	Store.Shop_Search_param = String.valueOf(mSearchEdittext.getText());
		        	api_get_all_shop(0, Store.limit, Store.Shop_Search_param);
		        	mPager.setCurrentItem(0);
		          return true;
		        }
		        return false;
		    }
		});
		tvNumNoti.setVisibility(View.GONE);
		imJoinShop.setVisibility(View.GONE);
		api_get_num_unread_notifications();
	}

	@Override
	protected void initListener() {
		mHomeTab.setOnClickListener(this);
		mPersonalTab.setOnClickListener(this);
		mLoyaltyCardTab.setOnClickListener(this);
		mNotiTab.setOnClickListener(this);
		mSettingTab.setOnClickListener(this);
		mSearchTab.setOnClickListener(this);
		mSortTab.setOnClickListener(this);
		mSearchBlockCancel.setOnClickListener(this);
		rHidAnnouce.setOnClickListener(this);
		rHidNews.setOnClickListener(this);
		imNewsDialog.setOnClickListener(this);
		idSort.setOnClickListener(this);
		imSearch.setOnClickListener(this);
	}

	@Override
	protected void initAnimations() {

	}

	public static int firstTab = 3;
	public static UserAccount mUser;
	public static CoreActivity mActivity;
	public static CanvasFragment mInstance;

	public static CanvasFragment getInstance(CoreActivity activity, UserAccount user) {
		if (mInstance == null) {
			mInstance = new CanvasFragment();
		}
		mActivity = activity;
		mUser = user;
		return mInstance;
	}

	public static CanvasFragment getInstance() {
		if (mInstance == null) {
			mInstance = new CanvasFragment();
		}
		return mInstance;
	}
	
public void api_get_all_shop(int last_id, int limit, String search){
	 	
    	Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", CanvasFragment.mUser.getAccessToken());
		params.put("last_id", String.valueOf(last_id));
		params.put("limit", String.valueOf(limit));
		params.put("search", String.valueOf(search));
			RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
			HandleRequest jsObjRequest = new HandleRequest(Method.GET,
					HandleRequest.build_link(HandleRequest.GET_ALL_SHOP, params), params, 
					new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Store.ShopsList.removeAll(Store.ShopsList);
						
						try {
							Log.d("api_get_all_shop",response.toString());
							JSONArray data_aray = response.getJSONArray("data");
							if (data_aray.length()==0){
								DialogMessage dialog = new DialogMessage(mActivity, "Cửa hàng này hiện không tồn tại trong hệ thống");
								dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
								dialog.show();
							}
							else{	
								for (int i = 0; i < data_aray.length(); i++) {								 
		                            Shop shop = new Shop((JSONObject) data_aray.get(i));
		                            	if (shop != null){
		                            		HomeFragment.current_last_id = shop.getId();
		                            		Store.ShopsList.add(shop);
		                            	}
		                        }
							}
							HomeFragment.storeAdapter.notifyDataSetChanged();
							isSearching = false;
							isHomeRefresh = false;
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}, 
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.d("api_get_all_shop",error.toString());
					}
				});
			requestQueue.add(jsObjRequest);
	}

public void api_get_all_announcements(int last_id, int limit, String type, int mode, int search) {

	Map<String, String> params = new HashMap<String, String>();
	params.put("access_token", CanvasFragment.mUser.getAccessToken());
	params.put("type", String.valueOf(type));
	params.put("last_id", String.valueOf(last_id));
	params.put("limit", String.valueOf(limit));
	params.put("mode", String.valueOf(mode));
	params.put("search", String.valueOf(search));
	RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
	HandleRequest jsObjRequest = new HandleRequest(Method.GET,
			HandleRequest.build_link(HandleRequest.GET_ALL_ANNOUNCEMENTS, params), params,
			new Response.Listener<JSONObject>() {
				@Override
				public void onResponse(JSONObject response) {
					try {
						
						Store.AnnouncementsList.removeAll(Store.AnnouncementsList);

						JSONArray data_aray = response.getJSONArray("data");
						for (int i = 0; i < data_aray.length(); i++) {
							Announcement announcement = new Announcement((JSONObject) data_aray.get(i));
							HomeAnnounmentFragment.current_last_id = announcement.getId();
							Store.AnnouncementsList.add(announcement);
						}
						HomeAnnounmentFragment.newsAdapter.notifyDataSetChanged();
						isAnnoRefresh = false;
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

public void api_get_num_unread_notifications(){
 	
	Map<String, String> params = new HashMap<String, String>();
	params.put("access_token", CanvasFragment.mUser.getAccessToken());
		RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
		HandleRequest jsObjRequest = new HandleRequest(Method.GET,
				HandleRequest.build_link(HandleRequest.NUM_UNREAD_NOTIFICATIONS, params), params, 
				new Response.Listener<JSONObject>() {
				@Override
				public void onResponse(JSONObject response) {
					
					try {
						Log.d("api_get_all_shop",response.toString());
						JSONObject data = response.getJSONObject("data");
						tvNumNoti.setText(data.getString("total"));
						if (Integer.parseInt(data.getString("total")) > 0) {
							tvNumNoti.setVisibility(View.VISIBLE);
							imJoinShop.setVisibility(View.VISIBLE);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
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
