package lc.buyplus.fragments;

import com.android.volley.toolbox.ImageLoader;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import lc.buyplus.R;
import lc.buyplus.application.MonApplication;
import lc.buyplus.cores.CoreActivity;
import lc.buyplus.cores.CoreFragment;
import lc.buyplus.customizes.MyTextView;
import lc.buyplus.models.Store;

public class MapShopFragment extends CoreFragment{
	private LinearLayout mHomeTab, mPersonalTab, mLoyaltyCardTab, mNotiTab, mSettingTab, mBack;
	Display display;
	private MyTextView mTitle;
	private LayoutInflater inflaterActivity;
	FragmentManager mFragmentManager;
	public static Fragment homeFrg;
	// Google Map
	private GoogleMap mMap;

	public static String type = "all";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_map_shop, null, false);
		Log.d("Map", "OK1");
		mMap = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map)).getMap();
		Log.d("Map", "OK2");
		
		inflaterActivity = inflater;
		initViews(view);
		initModels();
		initAnimations();
		mFragmentManager = getFragmentManager();
		
		double lat = Double.parseDouble( Store.current_shop.getLat().replace(",",".") );
		double lng = Double.parseDouble( Store.current_shop.getLng().replace(",",".") );
		LatLng location = new LatLng(lat, lng);
		mMap.addMarker(new MarkerOptions().position(location).title("Marker in Sydney"));
		mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location , 15));
		return view;
	}
	
	
	

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.fragment_canvas_back:
			HomeAnnounmentFragment.listView.setEnabled(true);
			mActivity.finish();
			break;
		case R.id.fragment_canvas_home_tab:
			HomeAnnounmentFragment.listView.setEnabled(true);
			CanvasFragment.mPager.setCurrentItem(0);
			mActivity.finish();
			break;
		case R.id.fragment_canvas_personal_tab:
			HomeAnnounmentFragment.listView.setEnabled(true);
			CanvasFragment.mPager.setCurrentItem(2);
			mActivity.finish();
			break;
		case R.id.fragment_canvas_loyaltycard_tab:
			HomeAnnounmentFragment.listView.setEnabled(true);
			CanvasFragment.mPager.setCurrentItem(3);
			mActivity.finish();
			break;
		case R.id.fragment_canvas_notifications_tab:
			HomeAnnounmentFragment.listView.setEnabled(true);
			CanvasFragment.mPager.setCurrentItem(4);
			mActivity.finish();
			break;
		case R.id.fragment_canvas_setting_tab:
			HomeAnnounmentFragment.listView.setEnabled(true);
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
		ImageLoader imageLoader = MonApplication.getInstance().getImageLoader();
		if (imageLoader == null)
			imageLoader = MonApplication.getInstance().getImageLoader();
		homeFrg = this.getTargetFragment();
		mTitle = (MyTextView) v.findViewById(R.id.fragment_canvas_title);
		mTitle.setText(Store.current_shop.getName());
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

	}

	@Override
	protected void initAnimations() {

	}

	public static final long serialVersionUID = 6036846677812555352L;

	public static CoreActivity mActivity;
	public static MapShopFragment mInstance;

	public static MapShopFragment getInstance(CoreActivity activity) {
		if (mInstance == null) {
			mInstance = new MapShopFragment();
		}
		mActivity = activity;
		return mInstance;
	}

	public static MapShopFragment getInstance() {
		if (mInstance == null) {
			mInstance = new MapShopFragment();
		}
		return mInstance;
	}

	@Override
	protected void initListener() {
		// TODO Auto-generated method stub

	}

	
}
