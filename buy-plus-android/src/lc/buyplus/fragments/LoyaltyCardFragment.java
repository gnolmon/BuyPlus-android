package lc.buyplus.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import lc.buyplus.R;
import lc.buyplus.cores.CoreActivity;
import lc.buyplus.cores.CoreFragment;

public class LoyaltyCardFragment extends CoreFragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_loyalty_card, container, false);
		initViews(view);
		initModels();
		initAnimations();
		
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(500);
					// load data
//					switchFragmentWithAnimation(CanvasFragment.getInstance(mActivity), 100, 200);
					mFragmentManager.beginTransaction().replace(R.id.canvas, LoginFragment.getInstance(mActivity)).commit();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
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
	public static LoyaltyCardFragment mInstance;
	public static LoyaltyCardFragment getInstance(CoreActivity activity) {
		if (mInstance == null) {
			mInstance = new LoyaltyCardFragment();
		}
		mActivity = activity;
		return mInstance;
	}
	public static LoyaltyCardFragment getInstance() {
		if (mInstance == null) {
			mInstance = new LoyaltyCardFragment();
		}
		return mInstance;
	}
	@Override
	protected void initListener() {
		// TODO Auto-generated method stub
		
	}
}
