package lc.buyplus.fragments;

import lc.buyplus.R;
import lc.buyplus.cores.CoreActivity;
import lc.buyplus.cores.CoreFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class NotificationsFragment extends CoreFragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_notifications, container, false);
		initViews(view);
		initModels();
		initAnimations();
		return view;
	}
	@Override
	public void onClick(View view) {
		switch(view.getId()) {

		}
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
	public static NotificationsFragment mInstance;
	public static NotificationsFragment getInstance(CoreActivity activity) {
		if (mInstance == null) {
			mInstance = new NotificationsFragment();
		}
		mActivity = activity;
		return mInstance;
	}
	public static NotificationsFragment getInstance() {
		if (mInstance == null) {
			mInstance = new NotificationsFragment();
		}
		return mInstance;
	}
	@Override
	protected void initListener() {
		// TODO Auto-generated method stub
		
	}
}
