package lc.buyplus.fragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import lc.buyplus.R;
import lc.buyplus.cores.CoreActivity;
import lc.buyplus.cores.CoreFragment;

public class BuyPlusInfoFragment  extends CoreFragment {
	private LinearLayout mHomeTab, mPersonalTab, mLoyaltyCardTab, mNotiTab, mSettingTab, mBack;
	private TextView tvFb,tvWeb,setting_term,tvPhone;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_buyplus_info, container, false);
		initViews(view);
		initModels();
		initAnimations();

		Log.d("bbbuy","fragment_share");
		return view;
	}
	
	@Override
	public void onClick(View view) {
		Intent returnIntent;
		Uri uri;
		switch (view.getId()) {
		case R.id.fragment_canvas_back:
			mActivity.finish();
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
		case R.id.tvTerm:
			mFragmentManager.beginTransaction()
			.add(R.id.canvas, SettingTermFragment.getInstance(mActivity))
			.commit();
			break;
		case R.id.tvFb:
			uri = Uri.parse("https://www.facebook.com/groups/867716866646587/");
			returnIntent = new Intent(Intent.ACTION_VIEW, uri);
			try {
				startActivity(returnIntent);
			} catch (ActivityNotFoundException e) {
			}
			break;
		case R.id.tvWeb:
			uri = Uri.parse("https://buyplus.vn");
			returnIntent = new Intent(Intent.ACTION_VIEW, uri);
			try {
				startActivity(returnIntent);
			} catch (ActivityNotFoundException e) {
			}

			break;
		case R.id.tvPhone:
			try {
				Intent callIntent = new Intent(Intent.ACTION_CALL);
				callIntent.setData(Uri.parse("tel:" + "0971854899"));
				startActivity(callIntent);
			} catch (ActivityNotFoundException e) {
			}
			break;
		default:
			break;
		}
	}
	
	protected void initModels() {
		
	}

	@Override
	protected void initViews(View v) {
		setting_term = (TextView) v.findViewById(R.id.tvTerm);
		setting_term.setOnClickListener(this);
		mHomeTab = (LinearLayout) v.findViewById(R.id.fragment_canvas_home_tab);
		mPersonalTab = (LinearLayout) v.findViewById(R.id.fragment_canvas_personal_tab);
		mLoyaltyCardTab = (LinearLayout) v.findViewById(R.id.fragment_canvas_loyaltycard_tab);
		mNotiTab = (LinearLayout) v.findViewById(R.id.fragment_canvas_notifications_tab);
		mSettingTab = (LinearLayout) v.findViewById(R.id.fragment_canvas_setting_tab);
		mBack = (LinearLayout) v.findViewById(R.id.fragment_canvas_back);
		mHomeTab.setOnClickListener(this);
		mPersonalTab.setOnClickListener(this);
		mLoyaltyCardTab.setOnClickListener(this);
		mNotiTab.setOnClickListener(this);
		mSettingTab.setOnClickListener(this);
		mBack.setOnClickListener(this);
		tvWeb = (TextView) v.findViewById(R.id.tvWeb);
		tvFb = (TextView) v.findViewById(R.id.tvFb);
		tvPhone = (TextView) v.findViewById(R.id.tvPhone);
		tvWeb.setOnClickListener(this);
		tvFb.setOnClickListener(this);
		tvPhone.setOnClickListener(this);
	}	

	@Override
	protected void initAnimations() {
		
	}
	
	public static final long serialVersionUID = 6036846677812555352L;
	
	public static CoreActivity mActivity;
	public static BuyPlusInfoFragment mInstance;
	public static BuyPlusInfoFragment getInstance(CoreActivity activity) {
		if (mInstance == null) {
			mInstance = new BuyPlusInfoFragment();
		}
		mActivity = activity;
		return mInstance;
	}
	public static BuyPlusInfoFragment getInstance() {
		if (mInstance == null) {
			mInstance = new BuyPlusInfoFragment();
		}
		return mInstance;
	}
	@Override
	protected void initListener() {
		// TODO Auto-generated method stub
		
	}
}