package lc.buyplus.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import lc.buyplus.R;
import lc.buyplus.adapter.AddFriendAdapter;
import lc.buyplus.adapter.FriendAdapter;
import lc.buyplus.application.MonApplication;
import lc.buyplus.cores.CoreActivity;
import lc.buyplus.cores.CoreFragment;
import lc.buyplus.cores.HandleRequest;
import lc.buyplus.customizes.DialogMessage;
import lc.buyplus.customizes.MyTextView;
import lc.buyplus.customizes.RoundedImageView;
import lc.buyplus.models.Friend;
import lc.buyplus.models.Store;

public class HomeNotiFragment extends CoreFragment {
	private LinearLayout mHomeTab, mPersonalTab, mLoyaltyCardTab, mNotiTab, mSettingTab,mBack;
	Display display;
	private MyTextView mTitle;
	ImageLoader imageLoader = MonApplication.getInstance().getImageLoader();
	RoundedImageView avaStore;
	private LayoutInflater inflaterActivity;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_noti_home, container, false);
		inflaterActivity = inflater;
		initViews(view);
		initModels();
		initAnimations();
		return view;
	}

	@Override
	public void onClick(View view) {
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
		default:
			break;
		}
	}

	protected void initModels() {

	}

	@Override
	protected void initViews(View v) {
		mHomeTab = (LinearLayout) v.findViewById(R.id.fragment_canvas_home_tab);
		mPersonalTab = (LinearLayout) v.findViewById(R.id.fragment_canvas_personal_tab);
		mLoyaltyCardTab = (LinearLayout) v.findViewById(R.id.fragment_canvas_loyaltycard_tab);
		mNotiTab = (LinearLayout) v.findViewById(R.id.fragment_canvas_notifications_tab);
		mSettingTab = (LinearLayout) v.findViewById(R.id.fragment_canvas_setting_tab);
		mBack = (LinearLayout) v.findViewById(R.id.fragment_canvas_back);
		mTitle = (MyTextView) v.findViewById(R.id.fragment_canvas_title);
		mTitle.setText("Thông báo");
		
		mHomeTab.setOnClickListener(this);
		mPersonalTab.setOnClickListener(this);
		mLoyaltyCardTab.setOnClickListener(this);
		mNotiTab.setOnClickListener(this);
		mSettingTab.setOnClickListener(this);
		mBack.setOnClickListener(this);
		TextView name = (TextView) v.findViewById(R.id.tvNameStore);
		name.setText(Store.current_notification.getParams());
		if (imageLoader == null)
			imageLoader = MonApplication.getInstance().getImageLoader();
		avaStore = (RoundedImageView) v.findViewById(R.id.avaStore);
		avaStore.setImageUrl(Store.current_notification.getImage(), imageLoader);
		TextView timestamp = (TextView) v.findViewById(R.id.tvTimestamp);
		CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(Long.parseLong(Store.current_notification.getCreated_time()),
				System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
		timestamp.setText(timeAgo);
//		
//		TextView tvStatus = (TextView) v.findViewById(R.id.tvStatus);
//		tvStatus.setText(Store.current_announcement.getContent());
	}

	@Override
	protected void initAnimations() {

	}

	public static final long serialVersionUID = 6036846677812555352L;

	public static CoreActivity mActivity;
	public static HomeNotiFragment mInstance;

	public static HomeNotiFragment getInstance(CoreActivity activity) {
		if (mInstance == null) {
			mInstance = new HomeNotiFragment();
		}
		mActivity = activity;
		return mInstance;
	}

	public static HomeNotiFragment getInstance() {
		if (mInstance == null) {
			mInstance = new HomeNotiFragment();
		}
		return mInstance;
	}

	@Override
	protected void initListener() {
		// TODO Auto-generated method stub

	}
	
}
