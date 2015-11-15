package lc.buyplus.fragments;

import android.content.Intent;
import android.os.Bundle;
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
import lc.buyplus.R;
import lc.buyplus.activities.AddFriendActivity;
import lc.buyplus.activities.ShopInfoActivity;
import lc.buyplus.cores.CoreActivity;
import lc.buyplus.cores.CoreFragment;
import lc.buyplus.customizes.MyAnimations;
import lc.buyplus.customizes.MyEditText;
import lc.buyplus.customizes.MyTextView;
import lc.buyplus.models.UserAccount;

public class ShopFriendRedeemFragment extends CoreFragment {

	private static final long serialVersionUID = 1L;
	private LinearLayout mHomeTab, mPersonalTab, mLoyaltyCardTab, mNotiTab, mSettingTab;
	private RelativeLayout rHomeTab;
	private LinearLayout mBack, mSortTab;
	private LinearLayout mSearchBlock, mTitleBlock;
	private LinearLayout mSearchBlockCancel;
	private MyEditText mSearchEdittext;
	private ImageView imAdd;

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
		switch (view.getId()) {
		case R.id.fragment_canvas_back:
			mActivity.finish();
			break;
		case R.id.fragment_canvas_search:
			showSearchBlock();
			break;
		case R.id.fragment_canvas_sort:

			break;
		case R.id.fragment_canvas_home_tab:
			mPager.setCurrentItem(0);
			break;
		case R.id.fragment_canvas_personal_tab:
			mPager.setCurrentItem(2);
			break;
		case R.id.fragment_canvas_loyaltycard_tab:
			mPager.setCurrentItem(3);
			break;
		case R.id.fragment_canvas_notifications_tab:
			mPager.setCurrentItem(4);
			break;
		case R.id.fragment_canvas_setting_tab:
			mPager.setCurrentItem(5);
			break;
		default:
			break;
		}
	}

	public static final int NUM_PAGES = 2;
	public static ViewPager mPager;
	public PagerAdapter mPagerAdapter;

	public class ShopFriendSlidePagerAdapter extends FragmentStatePagerAdapter {
		public ShopFriendSlidePagerAdapter(android.support.v4.app.FragmentManager fm) {
			super(fm);
		}

		@Override
		public CoreFragment getItem(int position) {
			switch (position) {
			case 0:
				return ShopFriendFragment.getInstance(mActivity);
			case 1:
				return ShopGiftFragment.getInstance(mActivity);
			/*
			 * case 3: return LoyaltyCardFragment.getInstance(mActivity); case
			 * 4: return NotificationsFragment.getInstance(mActivity); case 5:
			 * return SettingFragment.getInstance(mActivity);
			 */
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
		mTitle.startAnimation(MyAnimations.fadeId(1000));
		if (home) {
			rHomeTab.setVisibility(View.VISIBLE);
			mHomeTab.setBackgroundColor(getResources().getColor(R.color.tab_selected));
			mTitle.setText(getResources().getString(R.string.canvas_title_home));
		} else {
			mHomeTab.setBackgroundColor(getResources().getColor(R.color.maincolor));
			rHomeTab.setVisibility(View.GONE);
		}
		if (homenews) {
			rHomeTab.setVisibility(View.VISIBLE);
			mHomeTab.setBackgroundColor(getResources().getColor(R.color.tab_selected));
			mTitle.setText(getResources().getString(R.string.canvas_title_home));
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
					changeTabState(true, true, false, false, false, false);
					break;
				case 1:
					changeTabState(true, true, false, false, false, false);
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
		changeTabState(true, true, false, false, false, false);

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
		
		rHomeTab = (RelativeLayout) v.findViewById(R.id.rhomeTab);
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
