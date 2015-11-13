package lc.buyplus.adapter;

import java.util.ArrayList;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import lc.buyplus.R;
import lc.buyplus.application.MonApplication;
import lc.buyplus.cores.FeedImageView;
import lc.buyplus.models.Announcement;

public class ShopAnnounmentAdapter extends BaseAdapter {

	private Activity activity;
	private LayoutInflater inflater;
	private LayoutInflater inflaterActivity;
	ArrayList<Announcement> announcementList;
	ImageLoader imageLoader = MonApplication.getInstance().getImageLoader();

	public ShopAnnounmentAdapter(ArrayList<Announcement> announcementList, LayoutInflater inflaterActivity) {
		this.inflaterActivity = inflaterActivity;
		this.announcementList = announcementList;
	}

	@Override
	public int getCount() {
		return announcementList.size();
	}

	@Override
	public Object getItem(int location) {
		return announcementList.get(location);
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
			convertView = inflater.inflate(R.layout.item_shop_announment, null);

		if (imageLoader == null)
			imageLoader = MonApplication.getInstance().getImageLoader();

		TextView name = (TextView) convertView.findViewById(R.id.tvNameStore);

		TextView timestamp = (TextView) convertView.findViewById(R.id.tvTimestamp);

		TextView tvStatus = (TextView) convertView.findViewById(R.id.tvStatus);
		NetworkImageView avaStore = (NetworkImageView) convertView.findViewById(R.id.avaStore);
		FeedImageView feedImageView = (FeedImageView) convertView.findViewById(R.id.imFeed);

		Announcement item = announcementList.get(position);

		name.setText(item.getShop().getName());
		timestamp.setText(item.getStart_time());
		tvStatus.setText(item.getContent());

		avaStore.setImageUrl(item.getShop().getImage_thumbnail(), imageLoader);

		// Feed image
		if (item.getPhotos().size() > 0) {
			feedImageView.setImageUrl(item.getPhotos().get(0).getImage(), imageLoader);
			feedImageView.setVisibility(View.VISIBLE);
			feedImageView.setResponseObserver(new FeedImageView.ResponseObserver() {
				@Override
				public void onError() {
				}

				@Override
				public void onSuccess() {
				}
			});
		} else {
			feedImageView.setVisibility(View.GONE);
		}

		return convertView;
	}

}