package lc.buyplus.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import lc.buyplus.R;
import lc.buyplus.activities.AddFriendActivity;
import lc.buyplus.activities.ShopInfoActivity;
import lc.buyplus.cores.CoreActivity;
import lc.buyplus.cores.CoreFragment;
import lc.buyplus.customizes.MyAnimations;
import lc.buyplus.customizes.MyEditText;
import lc.buyplus.customizes.MyTextView;
import lc.buyplus.models.Store;
import lc.buyplus.models.UserAccount;

public class ShopFriendRedeemFragment extends CoreFragment {

	private static final long serialVersionUID = 1L;
	private LinearLayout mHomeTab, mPersonalTab, mLoyaltyCardTab, mNotiTab, mSettingTab;
	private LinearLayout rHomeTab;
	private LinearLayout mBack, mSortTab;
	private LinearLayout mSearchBlock, mTitleBlock;
	private LinearLayout mSearchBlockCancel;
	private MyEditText mSearchEdittext;
	private ImageView imAdd;
	private TextView rHgiftlist, rHfriendlist;
	private MyTextView mTitle;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_shop_friend, container, false);
		initViews(view);
		initModels();
		initListener();
		initAnimations();

		//mActivity.showToastLong(mUser.toString());

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
		Intent returnIntent;
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
			returnIntent = new Intent();
			mActivity.setResult(2, returnIntent);
			mActivity.finish();
			break;
		case R.id.fragment_canvas_personal_tab:
			CanvasFragment.mPager.setCurrentItem(2);
			returnIntent = new Intent();
			mActivity.setResult(2, returnIntent);
			mActivity.finish();
			break;
		case R.id.fragment_canvas_loyaltycard_tab:
			CanvasFragment.mPager.setCurrentItem(3);
			returnIntent = new Intent();
			mActivity.setResult(2, returnIntent);
			mActivity.finish();
			break;
		case R.id.fragment_canvas_notifications_tab:
			CanvasFragment.mPager.setCurrentItem(4);
			returnIntent = new Intent();
			mActivity.setResult(2, returnIntent);
			mActivity.finish();
			break;
		case R.id.fragment_canvas_setting_tab:
			CanvasFragment.mPager.setCurrentItem(5);
			returnIntent = new Intent();
			mActivity.setResult(2, returnIntent);
			mActivity.finish();
			break;
		case R.id.idgiftlist:
			mPager.setCurrentItem(0);
			break;
		case R.id.idfriendlist:
			mPager.setCurrentItem(1);
			break;
		default:
			break;
		}
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Intent returnIntent;
		if (resultCode == 2) {
			returnIntent = new Intent();
			mActivity.setResult(2, returnIntent);
			mActivity.finish();
	    }
	}
	
	public static final int NUM_PAGES = 2;
	public static ViewPager mPager;
	public PagerAdapter mPagerAdapter;

	public class ShopFriendSlidePagerAdapter extends FragmentPagerAdapter {
		public ShopFriendSlidePagerAdapter(android.support.v4.app.FragmentManager fm) {
			super(fm);
		}

		@Override
		public CoreFragment getItem(int position) {
			switch (position) {
			case 0:
				return ShopGiftFragment.getInstance(mActivity);
			case 1:
				return ShopFriendFragment.getInstance(mActivity);
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

	public void changeTabState(boolean home, boolean personal) {
		if (home) {
			//mHomeTab.setBackgroundColor(getResources().getColor(R.color.tab_selected));
			rHgiftlist.setTextColor(getResources().getColor(R.color.tab_selected));
		} else {
			//mHomeTab.setBackgroundColor(getResources().getColor(R.color.maincolor));
			rHgiftlist.setTextColor(getResources().getColor(R.color.title));
		}
		if (personal) {
			//mPersonalTab.setBackgroundColor(getResources().getColor(R.color.tab_selected));
			rHfriendlist.setTextColor(getResources().getColor(R.color.tab_selected));
		} else {
			//mPersonalTab.setBackgroundColor(getResources().getColor(R.color.maincolor));
			rHfriendlist.setTextColor(getResources().getColor(R.color.title));
		}
	}

	protected void initModels() {
		mPagerAdapter = new ShopFriendSlidePagerAdapter(getFragmentManager());
		mPager.setOffscreenPageLimit(NUM_PAGES);
		mPager.setAdapter(mPagerAdapter);
		mPagerAdapter.notifyDataSetChanged();
		mPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				switch (mPager.getCurrentItem()) {
				case 0:
					changeTabState(true, false);
					break;
				case 1:
					changeTabState(false, true);
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
		changeTabState(true, false);

	}

	@Override
	protected void initViews(View v) {
		mPager = (ViewPager) v.findViewById(R.id.viewpager);
		mHomeTab = (LinearLayout) v.findViewById(R.id.fragment_canvas_home_tab);
		mPersonalTab = (LinearLayout) v.findViewById(R.id.fragment_canvas_personal_tab);
		mLoyaltyCardTab = (LinearLayout) v.findViewById(R.id.fragment_canvas_loyaltycard_tab);
		mNotiTab = (LinearLayout) v.findViewById(R.id.fragment_canvas_notifications_tab);
		mSettingTab = (LinearLayout) v.findViewById(R.id.fragment_canvas_setting_tab);
		mTitle = (MyTextView) v.findViewById(R.id.fragment_canvas_title);
		mBack = (LinearLayout) v.findViewById(R.id.fragment_canvas_back);
		mTitle.setText(Store.current_shop_name);
		rHomeTab = (LinearLayout) v.findViewById(R.id.rhomeTab);
		rHomeTab.setVisibility(View.VISIBLE);
		rHgiftlist = (TextView) v.findViewById(R.id.idgiftlist);
		rHgiftlist.setTextColor(getResources().getColor(R.color.tab_selected));
		rHfriendlist = (TextView) v.findViewById(R.id.idfriendlist);
		mTitleBlock = (LinearLayout) v.findViewById(R.id.fragment_canvas_title_block);
	}

	@Override
	protected void initListener() {
		mHomeTab.setOnClickListener(this);
		mPersonalTab.setOnClickListener(this);
		mLoyaltyCardTab.setOnClickListener(this);
		mNotiTab.setOnClickListener(this);
		mSettingTab.setOnClickListener(this);
		mBack.setOnClickListener(this);
		rHgiftlist.setOnClickListener(this);
		rHfriendlist.setOnClickListener(this);
	}

	@Override
	protected void initAnimations() {

	}

	public static int firstTab = 0;
	public static UserAccount mUser;
	public static CoreActivity mActivity;
	public static ShopFriendRedeemFragment mInstance;

	public static ShopFriendRedeemFragment getInstance(CoreActivity activity, UserAccount user) {
		if (mInstance == null) {
			mInstance = new ShopFriendRedeemFragment();
		}
		mActivity = activity;
		mUser = user;
		return mInstance;
	}

	public static ShopFriendRedeemFragment getInstance() {
		if (mInstance == null) {
			mInstance = new ShopFriendRedeemFragment();
		}
		return mInstance;
	}
}
