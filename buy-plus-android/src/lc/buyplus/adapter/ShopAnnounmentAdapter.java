package lc.buyplus.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import com.android.volley.toolbox.ImageLoader;
import com.bumptech.glide.Glide;

import android.app.Activity;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import lc.buyplus.R;
import lc.buyplus.adapter.AnnounmentAdapter.ViewHolder;
import lc.buyplus.application.MonApplication;
import lc.buyplus.cores.FeedImageView;
import lc.buyplus.customizes.RoundedImageView;
import lc.buyplus.customizes.RoundedViewImage;
import lc.buyplus.fragments.CanvasFragment;
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
	
	static class ViewHolder {
		public TextView name;
		public TextView timestamp;
		public TextView tvStatus;
		public RoundedImageView avaStore;
		public FeedImageView feedImageView;
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
		ViewHolder viewHolder;
		if (inflater == null)
			inflater = inflaterActivity;
		if (convertView == null){
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_shop_announment, null);
			
			viewHolder.name = (TextView) convertView.findViewById(R.id.tvNameStore);
			viewHolder.timestamp = (TextView) convertView.findViewById(R.id.tvTimestamp);
			viewHolder.tvStatus = (TextView) convertView.findViewById(R.id.tvStatus);
			viewHolder.avaStore = (RoundedImageView) convertView.findViewById(R.id.avaStore);
			viewHolder.feedImageView = (FeedImageView) convertView.findViewById(R.id.imFeed);
			
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
			

		if (imageLoader == null)
			imageLoader = MonApplication.getInstance().getImageLoader();


		Announcement item = announcementList.get(position);

		viewHolder.name.setText(item.getShop().getName());
		long unixSeconds = item.getStart_time();
		Date date = new Date(unixSeconds*1000L);
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT-7"));
		String formattedDate = sdf.format(date);
		
		CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(1000 * Long.parseLong(item.getCreated_time()),
				System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
		
		viewHolder.timestamp.setText(timeAgo);
		viewHolder.tvStatus.setText(item.getContent());

		viewHolder.avaStore.setImageUrl(item.getShop().getImage_thumbnail(), imageLoader);
		//Glide.with(CanvasFragment.mActivity).load(item.getShop().getImage_thumbnail()).centerCrop()
		//.placeholder(R.drawable.loading_icon).crossFade().into(viewHolder.avaStore);
		
		// Feed image
		if (item.getPhotos().size() > 0) {
			viewHolder.feedImageView.setImageUrl(item.getPhotos().get(0).getImage(), imageLoader);
			//Glide.with(CanvasFragment.mActivity).load(item.getPhotos().get(0).getImage()).centerCrop()
			//.placeholder(R.drawable.loading_icon).crossFade().into(viewHolder.feedImageView);
			viewHolder.feedImageView.setVisibility(View.VISIBLE);
			
		} else {
			viewHolder.feedImageView.setVisibility(View.GONE);
		}

		return convertView;
	}

}