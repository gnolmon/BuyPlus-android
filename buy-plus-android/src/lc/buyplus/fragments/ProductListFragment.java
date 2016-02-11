package lc.buyplus.fragments;

import com.android.volley.toolbox.ImageLoader;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import lc.buyplus.R;
import lc.buyplus.adapter.ProductListAdapter;
import lc.buyplus.application.MonApplication;
import lc.buyplus.cores.CoreActivity;
import lc.buyplus.cores.CoreFragment;
import lc.buyplus.customizes.RoundedImageView;
import lc.buyplus.models.Store;

public class ProductListFragment extends CoreFragment {
	private ListView listProduct;
	private LayoutInflater inflaterActivity;
	FragmentManager mFragmentManager;
	private ProductListAdapter photoAdapter;
	private Button btnClose;
	public static Fragment homeFrg;
	public static String type = "all";
	private RoundedImageView imbannerStore;
	public TextView timestamp, tvNameStore;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_product_list, container, false);

		inflaterActivity = inflater;
		Log.d("PR", "step1");
		initViews(view);
		mFragmentManager = getFragmentManager();
		
		
		//Bundle b = CanvasFragment.mActivity.getIntent().getExtras();
		//int pos = b.getInt("location");
		//Log.d("PR", "step2 " + pos);
		photoAdapter = new ProductListAdapter(inflaterActivity,Store.PhotosList);
		listProduct.setAdapter(photoAdapter);
		Log.d("PR", "step3 " + Store.PhotosList.size());
		//mPager.setCurrentItem(pos);
		return view;
	}

	@Override
	protected void initViews(View view) {
		ImageLoader imageLoader = MonApplication.getInstance().getImageLoader();
		if (imageLoader == null)
			imageLoader = MonApplication.getInstance().getImageLoader();

		listProduct = (ListView) view.findViewById(R.id.listproduct);
		
		tvNameStore = (TextView) view.findViewById(R.id.tvNameStore);
		tvNameStore.setText(Store.current_shop_name);

		timestamp = (TextView) view.findViewById(R.id.tvTimestamp);
		timestamp.setVisibility(View.INVISIBLE);

		btnClose = (Button) view.findViewById(R.id.btnClose);
		btnClose.setOnClickListener(this);

		imbannerStore = (RoundedImageView) view.findViewById(R.id.avaStore);
		imbannerStore.setImageUrl(Store.current_shop.getImage_thumbnail(), imageLoader);

	}
	
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btnClose:
			mActivity.finish();
			break;
		}
	}

	public static final long serialVersionUID = 6036846677812555352L;

	public static CoreActivity mActivity;
	public static ProductListFragment mInstance;

	public static ProductListFragment getInstance(CoreActivity activity) {
		if (mInstance == null) {
			mInstance = new ProductListFragment();
		}
		mActivity = activity;
		return mInstance;
	}

	public static ProductListFragment getInstance() {
		if (mInstance == null) {
			mInstance = new ProductListFragment();
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
