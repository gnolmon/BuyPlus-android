package lc.buyplus.adapter;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import lc.buyplus.R;
import lc.buyplus.activities.ProductShowActivity;
import lc.buyplus.adapter.AnnounmentAdapter.ViewHolder;
import lc.buyplus.application.MonApplication;
import lc.buyplus.customizes.RoundedViewImage;
import lc.buyplus.fragments.CanvasFragment;
import lc.buyplus.models.Photo;

public class ProductListAdapter extends BaseAdapter {

	private LayoutInflater inflaterActivity;
	private LayoutInflater inflater;
	private List<Photo> PhotosList = new ArrayList<Photo>();
	ImageLoader imageLoader = MonApplication.getInstance().getImageLoader();

	public ProductListAdapter(LayoutInflater inflaterActivity, List<Photo> PhotosList) {
		this.inflaterActivity = inflaterActivity;
		this.PhotosList = PhotosList;
	}

	static class ViewHolder {
		public NetworkImageView imProduct;
		public TextView tvpoint, tvprice, tvdiscount, tvprice_after;
	}

	@Override
	public int getCount() {
		return this.PhotosList.size();
	}

	@Override
	public Object getItem(int position) {
		return this.PhotosList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (inflater == null)
			inflater = inflaterActivity;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_product_list, null);
			viewHolder = new ViewHolder();
			
			viewHolder.imProduct = (NetworkImageView) convertView.findViewById(R.id.imProduct);
			viewHolder.tvpoint = (TextView) convertView.findViewById(R.id.tvpoint);
			viewHolder.tvprice = (TextView) convertView.findViewById(R.id.tvprice);
			viewHolder.tvprice_after = (TextView) convertView.findViewById(R.id.tvprice_after);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		if (imageLoader == null)
			imageLoader = MonApplication.getInstance().getImageLoader();
		Photo p = PhotosList.get(position);
		
		viewHolder.imProduct.setImageUrl(p.getImage(),
				imageLoader);
		viewHolder.tvpoint.setText("Điểm số: " + p.getPoint());
		viewHolder.tvprice.setText("Giá gốc: " + p.getPrice());
		viewHolder.tvprice_after.setText("Giá khuyến mãi: " + p.getPrice_after_discount());

//		Glide.with(CanvasFragment.mActivity).load(p.getImage())
//		.placeholder(viewHolder.imProduct.getDrawable()).centerCrop()
//		.diskCacheStrategy(DiskCacheStrategy.SOURCE).into(viewHolder.imProduct);
		
		
		return convertView;
	}

}