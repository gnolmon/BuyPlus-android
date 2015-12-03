package lc.buyplus.fragments;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

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
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import lc.buyplus.R;
import lc.buyplus.activities.ShopInfoActivity;
import lc.buyplus.adapter.AnnouncementDetailAdapter;
import lc.buyplus.adapter.AnnounmentAdapter;
import lc.buyplus.adapter.OnLoadMoreListener;
import lc.buyplus.application.MonApplication;
import lc.buyplus.cores.CoreActivity;
import lc.buyplus.cores.CoreFragment;
import lc.buyplus.cores.HandleRequest;
import lc.buyplus.customizes.MyTextView;
import lc.buyplus.customizes.RoundedImageView;
import lc.buyplus.models.Announcement;
import lc.buyplus.models.Shop;
import lc.buyplus.models.Store;
import lc.buyplus.pulltorefresh.PullToRefreshListView;
import lc.buyplus.pulltorefresh.PullToRefreshListView.OnRefreshListener;

public class AnnouncementDetailFragment extends CoreFragment {
	private LinearLayout mHomeTab, mPersonalTab, mLoyaltyCardTab, mNotiTab, mSettingTab, mBack;
	Display display;
	private MyTextView mTitle;
	public static ListView listView;
	public static AnnouncementDetailAdapter newsAdapter;
	private LayoutInflater inflaterActivity;
	FragmentManager mFragmentManager;
	public static Fragment homeFrg;
	private int old_id = 0;
	public static int current_last_id = 0;
	private boolean isLoading,reload;
	public static String type = "all";
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_annoucement_detail, container, false);
		listView = (ListView) view.findViewById(R.id.listImage);
		inflaterActivity = inflater;
		initViews(view);
		initModels();
		initAnimations();
		mFragmentManager = getFragmentManager();
		
		
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
	protected void initViews(View v) {ImageLoader imageLoader = MonApplication.getInstance().getImageLoader();
		if (imageLoader == null)
			imageLoader = MonApplication.getInstance().getImageLoader();
		homeFrg = this.getTargetFragment();
		newsAdapter = new AnnouncementDetailAdapter(Store.current_announcement.photos, inflaterActivity, mActivity, mFragmentManager);
		listView.setAdapter(newsAdapter);
		mTitle = (MyTextView) v.findViewById(R.id.fragment_canvas_title);
		mTitle.setText(((Shop)Store.current_announcement.getShop()).getName());
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
		RoundedImageView avaStore = (RoundedImageView) v.findViewById(R.id.avaStore);
		avaStore.setImageUrl(Store.current_announcement.getShop().getImage_thumbnail(), imageLoader);
		
		TextView name = (TextView) v.findViewById(R.id.tvNameStore);
		name.setText(Store.current_announcement.getShop().getName());
		
		TextView timestamp = (TextView) v.findViewById(R.id.tvTimestamp);
		CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(Long.parseLong(Store.current_announcement.getCreated_time()),
				System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
		timestamp.setText(timeAgo);
		
		TextView tvStatus = (TextView) v.findViewById(R.id.tvStatus);
		tvStatus.setText(Store.current_announcement.getContent());
		
		ImageView imSaleOff = (ImageView) v.findViewById(R.id.imSaleOff);
		TextView tvTimeSale = (TextView) v.findViewById(R.id.tvTimeSale);
		
		long unixSeconds = Store.current_announcement.getStart_time();
		Date date_start = new Date(unixSeconds*1000L);
		unixSeconds = Store.current_announcement.getEnd_time();
		Date date_end = new Date(unixSeconds*1000L);
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm MM-dd");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT-7"));
		String formattedDate = sdf.format(date_start) +" đến "+ sdf.format(date_end);
		tvTimeSale.setText(formattedDate);
		
		if (Store.current_announcement.getType()==2){
			imSaleOff.setVisibility(View.VISIBLE);
			tvTimeSale.setVisibility(View.VISIBLE);
		}else {
			imSaleOff.setVisibility(View.GONE);
			tvTimeSale.setVisibility(View.GONE);
		}
	}

	@Override
	protected void initAnimations() {

	}

	public static final long serialVersionUID = 6036846677812555352L;

	public static CoreActivity mActivity;
	public static AnnouncementDetailFragment mInstance;

	public static AnnouncementDetailFragment getInstance(CoreActivity activity) {
		if (mInstance == null) {
			mInstance = new AnnouncementDetailFragment();
		}
		mActivity = activity;
		return mInstance;
	}

	public static AnnouncementDetailFragment getInstance() {
		if (mInstance == null) {
			mInstance = new AnnouncementDetailFragment();
		}
		return mInstance;
	}

	@Override
	protected void initListener() {
		// TODO Auto-generated method stub

	}
}
