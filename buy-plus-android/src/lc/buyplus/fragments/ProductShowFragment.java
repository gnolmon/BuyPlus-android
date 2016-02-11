package lc.buyplus.fragments;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.android.volley.toolbox.ImageLoader;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import lc.buyplus.R;
import lc.buyplus.adapter.ProductShowAdapter;
import lc.buyplus.adapter.ShopPhotoAdapter;
import lc.buyplus.application.MonApplication;
import lc.buyplus.cores.CoreActivity;
import lc.buyplus.cores.CoreFragment;
import lc.buyplus.customizes.RoundedImageView;
import lc.buyplus.models.Store;

public class ProductShowFragment extends CoreFragment {
	private ViewPager mPager;
	private LayoutInflater inflaterActivity;
	FragmentManager mFragmentManager;
	private ProductShowAdapter photoAdapter;
	private Button btnClose;
	public static Fragment homeFrg;
	public static String type = "all";
	private RoundedImageView imbannerStore;
	public TextView timestamp, tvNameStore;
	private TextView tvpoint, tvprice, tvdiscount, tvprice_after;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_product_show, container, false);

		inflaterActivity = inflater;
		initViews(view);
		mFragmentManager = getFragmentManager();
		Log.d("PR", "step1");
		
		//Bundle b = CanvasFragment.mActivity.getIntent().getExtras();
		//int pos = b.getInt("location");
		//Log.d("PR", "step2 " + pos);
		photoAdapter = new ProductShowAdapter(inflaterActivity,Store.PhotosList);
		mPager.setAdapter(photoAdapter);
		Log.d("PR", "step3 " + Store.PhotosList.size());
		//mPager.setCurrentItem(pos);
		return view;
	}

	@Override
	protected void initViews(View view) {
		ImageLoader imageLoader = MonApplication.getInstance().getImageLoader();
		if (imageLoader == null)
			imageLoader = MonApplication.getInstance().getImageLoader();

		mPager = (ViewPager) view.findViewById(R.id.listproduct);
		tvpoint = (TextView) view.findViewById(R.id.tvpoint);
		tvprice = (TextView) view.findViewById(R.id.tvprice);
		tvdiscount = (TextView) view.findViewById(R.id.tvdiscount);
		tvprice_after = (TextView) view.findViewById(R.id.tvprice_after);
		tvNameStore = (TextView) view.findViewById(R.id.tvNameStore);
		tvNameStore.setText(Store.current_shop_name);

		timestamp = (TextView) view.findViewById(R.id.tvTimestamp);

		btnClose = (Button) view.findViewById(R.id.btnClose);
		btnClose.setOnClickListener(this);

		imbannerStore = (RoundedImageView) view.findViewById(R.id.avaStore);
		imbannerStore.setImageUrl(Store.current_shop.getImage_thumbnail(), imageLoader);

	}

	public static final long serialVersionUID = 6036846677812555352L;

	public static CoreActivity mActivity;
	public static ProductShowFragment mInstance;

	public static ProductShowFragment getInstance(CoreActivity activity) {
		if (mInstance == null) {
			mInstance = new ProductShowFragment();
		}
		mActivity = activity;
		return mInstance;
	}

	public static ProductShowFragment getInstance() {
		if (mInstance == null) {
			mInstance = new ProductShowFragment();
		}
		return mInstance;
	}

	@Override
	protected void initModels() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initListener() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initAnimations() {
		// TODO Auto-generated method stub

	}
}
