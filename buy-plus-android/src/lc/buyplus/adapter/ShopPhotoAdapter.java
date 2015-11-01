package lc.buyplus.adapter;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import lc.buyplus.R;
import lc.buyplus.application.MonApplication;
import lc.buyplus.models.Photo;

public class ShopPhotoAdapter extends BaseAdapter {

	private LayoutInflater inflaterActivity;
	private LayoutInflater inflater;
	private List<Photo> PhotosList = new ArrayList<Photo>();
	private int imageWidth;
	ImageLoader imageLoader = MonApplication.getInstance().getImageLoader();

	public ShopPhotoAdapter(LayoutInflater inflaterActivity, List<Photo> PhotosList, int imageWidth) {
		this.inflaterActivity = inflaterActivity;
		this.PhotosList = PhotosList;
		this.imageWidth = imageWidth;
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
		if (inflater == null)
			inflater = inflaterActivity;
		if (convertView == null)
			convertView = inflater.inflate(R.layout.item_store_photo, null);

		if (imageLoader == null)
			imageLoader = MonApplication.getInstance().getImageLoader();

		// Grid thumbnail image view
		NetworkImageView thumbNail = (NetworkImageView) convertView.findViewById(R.id.thumbnail);

		Photo p = PhotosList.get(position);

		thumbNail.setScaleType(ImageView.ScaleType.CENTER_CROP);
		thumbNail.setLayoutParams(new RelativeLayout.LayoutParams(imageWidth, imageWidth));
		thumbNail.setImageUrl(p.getImage(), imageLoader);

		return convertView;
	}

}