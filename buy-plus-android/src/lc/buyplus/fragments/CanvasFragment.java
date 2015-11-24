package lc.buyplus.fragments;

import com.facebook.CallbackManager;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import lc.buyplus.R;
import lc.buyplus.cores.CoreActivity;
import lc.buyplus.cores.CoreFragment;
import lc.buyplus.customizes.DialogNews;
import lc.buyplus.customizes.DialogSort;
import lc.buyplus.customizes.MyAnimations;
import lc.buyplus.customizes.MyEditText;
import lc.buyplus.customizes.MyTextView;
import lc.buyplus.models.UserAccount;

public class CanvasFragment extends CoreFragment {

	private static final long serialVersionUID = 1L;
	private LinearLayout mHomeTab, mPersonalTab, mLoyaltyCardTab, mNotiTab, mSettingTab, rHidAnnouce, rHidNews,
			rHidSort;
	private LinearLayout rHomeTab;
	private LinearLayout mSearchTab, mSortTab;
	private LinearLayout mSearchBlock, mTitleBlock, idSort;
	private LinearLayout mSearchBlockCancel;
	private MyEditText mSearchEdittext;
	private CallbackManager callbackManager;
	private TextView tvStore, tvNews;
	private ImageView imNewsDialog;

	private MyTextView mTitle;

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
			hideSearchBlock();
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
		case R.id.idAnnouce:
			mPager.setCurrentItem(0);
			break;
		case R.id.idNews:
			mPager.setCurrentItem(1);
			break;
		case R.id.idSort:
			break;
		case R.id.imNewsDialog:
			if (sortDialog == null ) {
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
		changeTabState(true, false, false, false, false, false);

		mSearchEdittext.setHintTextColor(getResources().getColor(R.color.white));
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

		imNewsDialog = (ImageView) v.findViewById(R.id.imNewsDialog);
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
	}

	@Override
	protected void initAnimations() {

	}

	public static int firstTab = 0;
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
}
