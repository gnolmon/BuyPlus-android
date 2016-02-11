package lc.buyplus.adapter;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import lc.buyplus.R;
import lc.buyplus.application.MonApplication;
import lc.buyplus.fragments.CanvasFragment;
import lc.buyplus.models.Photo;
import lc.buyplus.models.Store;

public class ProductShowAdapter extends PagerAdapter {
	private LayoutInflater inflaterActivity;
	private LayoutInflater inflater;
	private List<Photo> PhotosList = new ArrayList<Photo>();
	ImageLoader imageLoader = MonApplication.getInstance().getImageLoader();
	private NetworkImageView imProduct;

	public ProductShowAdapter(LayoutInflater inflaterActivity, List<Photo> PhotosList) {
		this.inflaterActivity = inflaterActivity;
		this.PhotosList = PhotosList;
	}

	@Override
	public int getCount() {
		return PhotosList.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == ((View) arg1);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		if (inflater == null)
			inflater = inflaterActivity;
		if (imageLoader == null)
			imageLoader = MonApplication.getInstance().getImageLoader();
		View view = inflater.inflate(R.layout.item_product_show, null);

		imProduct = (NetworkImageView) view.findViewById(R.id.imProduct);
//		Glide.with(CanvasFragment.mActivity).load(PhotosList.get(position).getImage())
//				.placeholder(imProduct.getDrawable()).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imProduct);
		imProduct.setImageUrl(PhotosList.get(position).getImage(), imageLoader);
		Log.d("PR", "step4 " + PhotosList.get(position).getImage());
		((ViewPager) container).addView(view, 0);
		return view;
	}
	
	@Override
	public void destroyItem(View arg0, int arg1, Object arg2) {
		((ViewPager) arg0).removeView((View) arg2);
	}

	@Override
	public Parcelable saveState() {
		return null;
	}

}
