package lc.buyplus.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import com.android.volley.toolbox.ImageLoader;

import android.app.Activity;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import lc.buyplus.R;
import lc.buyplus.application.MonApplication;
import lc.buyplus.cores.FeedImageView;
import lc.buyplus.customizes.RoundedImageView;
import lc.buyplus.models.Announcement;

public class ShopAnnounmentAdapter extends BaseAdapter {

	private Activity activity;
	private LayoutInflater inflater;
	private LayoutInflater inflaterActivity;
	ArrayList<Announcement> announcementList;
	ImageLoader imageLoader = MonApplication.getInstance().getImageLoader();
	private OnLoadMoreListener onLoadMoreListener;
	
	public ShopAnnounmentAdapter(ArrayList<Announcement> announcementList, LayoutInflater inflaterActivity) {
		this.inflaterActivity = inflaterActivity;
		this.announcementList = announcementList;
	}
	
	public OnLoadMoreListener getOnLoadMoreListener() {
		return onLoadMoreListener;
	}

	public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
		this.onLoadMoreListener = onLoadMoreListener;
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
		RoundedImageView avaStore = (RoundedImageView) convertView.findViewById(R.id.avaStore);
		FeedImageView feedImageView = (FeedImageView) convertView.findViewById(R.id.imFeed);

		Announcement item = announcementList.get(position);

		name.setText(item.getShop().getName());
		long unixSeconds = item.getStart_time();
		Date date = new Date(unixSeconds*1000L);
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT-7"));
		String formattedDate = sdf.format(date);
		
		CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(Long.parseLong(item.getCreated_time()),
				System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
		
		timestamp.setText(timeAgo);
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