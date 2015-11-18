package lc.buyplus.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import lc.buyplus.R;
import lc.buyplus.activities.SettingNotificationActivity;
import lc.buyplus.activities.ShopInfoActivity;
import lc.buyplus.cores.CoreActivity;
import lc.buyplus.cores.CoreFragment;

public class SettingNotificationFragment extends CoreFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_setting_notification, container, false);
		initViews(view);
		initModels();
		initAnimations();
		return view;
	}

	protected void initModels() {

	}

	@Override
	protected void initViews(View v) {
	}

	@Override
	protected void initAnimations() {

	}

	public static final long serialVersionUID = 6036846677812555352L;

	public static CoreActivity mActivity;
	public static SettingNotificationFragment mInstance;

	public static SettingNotificationFragment getInstance(CoreActivity activity) {
		if (mInstance == null) {
			mInstance = new SettingNotificationFragment();
		}
		mActivity = activity;
		return mInstance;
	}

	public static SettingNotificationFragment getInstance() {
		if (mInstance == null) {
			mInstance = new SettingNotificationFragment();
		}
		return mInstance;
	}

	@Override
	protected void initListener() {
		// TODO Auto-generated method stub

	}
}
