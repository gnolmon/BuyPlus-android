package lc.buyplus.fragments;

import lc.buyplus.R;
import lc.buyplus.activities.LoginActivity;
import lc.buyplus.activities.MainActivity;
import lc.buyplus.cores.CoreActivity;
import lc.buyplus.cores.CoreFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SettingFragment extends CoreFragment {
	TextView logout;
	
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
		switch(view.getId()){ 
		case R.id.tvLogout:
			Intent loginActivity = new Intent(mActivity,LoginActivity.class);
            startActivity(loginActivity);
            mActivity.finish();
		break;
		}
	}
	
	protected void initModels() {

	}

	@Override
	protected void initViews(View v) {
		logout = (TextView) v.findViewById(R.id.tvLogout);
		logout.setOnClickListener(this);
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
