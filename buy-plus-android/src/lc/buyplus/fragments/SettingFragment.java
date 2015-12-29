package lc.buyplus.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import lc.buyplus.R;
import lc.buyplus.R;
import lc.buyplus.activities.BuyPlusInfoActivity;
import lc.buyplus.activities.LoginActivity;
import lc.buyplus.activities.SettingNotificationActivity;
import lc.buyplus.activities.UserActivity;
import lc.buyplus.cores.CoreActivity;
import lc.buyplus.cores.CoreActivity;
import lc.buyplus.cores.CoreFragment;
import lc.buyplus.cores.CoreFragment;
import lc.buyplus.customizes.CustomDialogClass;
import lc.buyplus.customizes.LanguageDialog;

public class SettingFragment extends CoreFragment {
	TextView logout;
	private RelativeLayout rlSettingNoti, rlLanguage, rlBuyplusInfo, rlSettingUser, rlLogout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_setting, container, false);
		initViews(view);
		initModels();
		initAnimations();
		return view;
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.tvLogout:
			CustomDialogClass dialog = new CustomDialogClass(mActivity,"Bạn có thực sự muốn đăng xuất?", 1);
			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			dialog.show();
			break;
//		case R.id.rlSettingNoti:
//			Intent settingNotiActivity = new Intent(mActivity, SettingNotificationActivity.class);
//			startActivity(settingNotiActivity);
//			break;
		case R.id.rlBuyplusInfo:
			Intent buyplusInfoActivity = new Intent(mActivity, BuyPlusInfoActivity.class);
			startActivity(buyplusInfoActivity);
			break;
//		case R.id.rlLanguage:
//			LanguageDialog lanDialog = new LanguageDialog(mActivity);
//			lanDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//			lanDialog.show();
//			break;
		case R.id.rlSettingUser:
			Intent userActivity = new Intent(mActivity, UserActivity.class);
			startActivity(userActivity);
			break;
		}
	}

	protected void initModels() {

	}

	@Override
	protected void initViews(View v) {
		logout = (TextView) v.findViewById(R.id.tvLogout);
		logout.setOnClickListener(this);
		//rlSettingNoti = (RelativeLayout) v.findViewById(R.id.rlSettingNoti);
		//rlSettingNoti.setOnClickListener(this);

		rlBuyplusInfo = (RelativeLayout) v.findViewById(R.id.rlBuyplusInfo);
		rlBuyplusInfo.setOnClickListener(this);

		rlSettingUser = (RelativeLayout) v.findViewById(R.id.rlSettingUser);
		rlSettingUser.setOnClickListener(this);

		//rlLanguage = (RelativeLayout) v.findViewById(R.id.rlLanguage);
		//rlLanguage.setOnClickListener(this);
	}

	@Override
	protected void initAnimations() {

	}

	public static final long serialVersionUID = 6036846677812555352L;

	public static CoreActivity mActivity;
	public static SettingFragment mInstance;

	public static SettingFragment getInstance(CoreActivity activity) {
		if (mInstance == null) {
			mInstance = new SettingFragment();
		}
		mActivity = activity;
		return mInstance;
	}

	public static SettingFragment getInstance() {
		if (mInstance == null) {
			mInstance = new SettingFragment();
		}
		return mInstance;
	}

	@Override
	protected void initListener() {
		// TODO Auto-generated method stub

	}
}
