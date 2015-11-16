package lc.buyplus.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import lc.buyplus.R;
import lc.buyplus.activities.ShopFriendActivity;
import lc.buyplus.cores.CoreActivity;
import lc.buyplus.cores.CoreFragment;
import lc.buyplus.customizes.MyAnimations;
import lc.buyplus.customizes.MyEditText;
import lc.buyplus.customizes.MyTextView;
import lc.buyplus.fragments.UserCanvasFragment.UserSlidePagerAdapter;
import lc.buyplus.models.UserAccount;

public class UserCanvasFragment extends CoreFragment {

	private static final long serialVersionUID = 1L;
	private LinearLayout mHomeTab, mPersonalTab, mLoyaltyCardTab,rHomeidInfo,rHomeidContact,rHomeidSecure;
	private RelativeLayout rHomeTab;
	private LinearLayout mBack, mSortTab;
	private LinearLayout mSearchBlock, mTitleBlock;
	private LinearLayout mSearchBlockCancel;
	private MyEditText mSearchEdittext;
	private TextView tvPoint;

	private MyTextView mTitle;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_user_canvas, container, false);
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
			mPager.setCurrentItem(1);
			break;
		case R.id.fragment_canvas_loyaltycard_tab:
			mPager.setCurrentItem(2);
			break;
		case R.id.idInfo:
			mPager.setCurrentItem(0);
			break;
		case R.id.idContact:
			mPager.setCurrentItem(1);
			break;
		case R.id.idSecure:
			mPager.setCurrentItem(2);
			break;	
		default:
			break;
		}
	}

	public static final int NUM_PAGES = 3;
	public static ViewPager mPager;
	public PagerAdapter mPagerAdapter;

	public class UserSlidePagerAdapter extends FragmentStatePagerAdapter {
		public UserSlidePagerAdapter(android.support.v4.app.FragmentManager fm) {
			super(fm);
		}

		@Override
		public CoreFragment getItem(int position) {
			switch (position) {
			case 0:
				return UserInfoFragment.getInstance(mActivity);
			case 1:
				return UserContactFragment.getInstance(mActivity);
			case 2:
				return UserSecureFragment.getInstance(mActivity);
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

	public void changeTabState(boolean home, boolean personal, boolean loyaltycard) {
		if (home) {
			rHomeidInfo.setBackgroundColor(getResources().getColor(R.color.tab_selected));
			mHomeTab.setBackgroundColor(getResources().getColor(R.color.tab_selected));
		} else {
			mHomeTab.setBackgroundColor(getResources().getColor(R.color.maincolor));
			rHomeidInfo.setBackgroundColor(Color.parseColor("#FFFFFF"));
		}
		if (personal) {
			mPersonalTab.setBackgroundColor(getResources().getColor(R.color.tab_selected));
			rHomeidContact.setBackgroundColor(getResources().getColor(R.color.tab_selected));
		} else {
			mPersonalTab.setBackgroundColor(getResources().getColor(R.color.maincolor));
			rHomeidContact.setBackgroundColor(Color.parseColor("#FFFFFF"));
		}
		if (loyaltycard) {
			mLoyaltyCardTab.setBackgroundColor(getResources().getColor(R.color.tab_selected));
			rHomeidSecure.setBackgroundColor(getResources().getColor(R.color.tab_selected));
			
		} else {
			mLoyaltyCardTab.setBackgroundColor(getResources().getColor(R.color.maincolor));
			rHomeidSecure.setBackgroundColor(Color.parseColor("#FFFFFF"));
		}
	}

	protected void initModels() {
		mPagerAdapter = new UserSlidePagerAdapter(getFragmentManager());
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
		mHomeTab = (LinearLayout) v.findViewById(R.id.fragment_canvas_home_tab);
		mPersonalTab = (LinearLayout) v.findViewById(R.id.fragment_canvas_personal_tab);
		mLoyaltyCardTab = (LinearLayout) v.findViewById(R.id.fragment_canvas_loyaltycard_tab);
		mTitle = (MyTextView) v.findViewById(R.id.fragment_canvas_title);
		mTitle.setText("User information");
		mBack = (LinearLayout) v.findViewById(R.id.fragment_canvas_back);

		rHomeTab = (RelativeLayout) v.findViewById(R.id.rhomeTab);
		rHomeidInfo = (LinearLayout) v.findViewById(R.id.idInfo);
		rHomeidInfo.setBackgroundColor(getResources().getColor(R.color.tab_selected));
		rHomeidContact = (LinearLayout) v.findViewById(R.id.idContact);
		rHomeidSecure = (LinearLayout) v.findViewById(R.id.idSecure);
		mTitleBlock = (LinearLayout) v.findViewById(R.id.fragment_canvas_title_block);
		rHomeTab.setVisibility(View.VISIBLE);
	}

	@Override
	protected void initListener() {
		mHomeTab.setOnClickListener(this);
		mPersonalTab.setOnClickListener(this);
		mLoyaltyCardTab.setOnClickListener(this);
		mBack.setOnClickListener(this);
		rHomeidInfo.setOnClickListener(this);
		rHomeidContact.setOnClickListener(this);
		rHomeidSecure.setOnClickListener(this);
	}

	@Override
	protected void initAnimations() {

	}

	public static int firstTab = 0;
	public static UserAccount mUser;
	public static CoreActivity mActivity;
	public static UserCanvasFragment mInstance;

	public static UserCanvasFragment getInstance(CoreActivity activity, UserAccount user) {
		if (mInstance == null) {
			mInstance = new UserCanvasFragment();
		}
		mActivity = activity;
		mUser = user;
		return mInstance;
	}

	public static UserCanvasFragment getInstance() {
		if (mInstance == null) {
			mInstance = new UserCanvasFragment();
		}
		return mInstance;
	}
}
