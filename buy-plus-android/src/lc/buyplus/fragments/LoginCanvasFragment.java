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

public class LoginCanvasFragment extends CoreFragment {

	private static final long serialVersionUID = 1L;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_user_canvas, container, false);
		initViews(view);
		initModels();
		initListener();
		initAnimations();

		return view;
	}


	@Override
	public void onClick(View view) {
		
	}

	public static final int NUM_PAGES = 2;
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
				return LoginFragment.getInstance(mActivity);
			case 1:
				return RegisterFragment.getInstance(mActivity);
			case 2:
				return TermFragment.getInstance(mActivity);
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

	protected void initModels() {
		mPagerAdapter = new UserSlidePagerAdapter(getFragmentManager());
		mPager.setOffscreenPageLimit(NUM_PAGES);
		mPager.setAdapter(mPagerAdapter);
		mPagerAdapter.notifyDataSetChanged();


		mPager.setCurrentItem(firstTab);
	}

	@Override
	protected void initViews(View v) {
		mPager = (ViewPager) v.findViewById(R.id.viewpager);
	}

	@Override
	protected void initListener() {
	}

	@Override
	protected void initAnimations() {

	}

	public static int firstTab = 0;
	public static UserAccount mUser;
	public static CoreActivity mActivity;
	public static LoginCanvasFragment mInstance;

	public static LoginCanvasFragment getInstance(CoreActivity activity, UserAccount user) {
		if (mInstance == null) {
			mInstance = new LoginCanvasFragment();
		}
		mActivity = activity;
		mUser = user;
		return mInstance;
	}

	public static LoginCanvasFragment getInstance() {
		if (mInstance == null) {
			mInstance = new LoginCanvasFragment();
		}
		return mInstance;
	}
}
