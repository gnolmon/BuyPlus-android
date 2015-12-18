package lc.buyplus.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import android.support.v4.app.FragmentManager;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import lc.buyplus.R;
import lc.buyplus.application.MonApplication;
import lc.buyplus.cores.CoreActivity;
import lc.buyplus.cores.FeedImageView;
import lc.buyplus.cores.HandleRequest;
import lc.buyplus.customizes.RoundedImageView;
import lc.buyplus.fragments.CanvasFragment;
import lc.buyplus.models.Announcement;
import lc.buyplus.models.Photo;
import lc.buyplus.models.Store;

public class AnnouncementDetailAdapter extends BaseAdapter {

	public CoreActivity activity;
	private LayoutInflater inflater;
	private LayoutInflater inflaterActivity;
	ArrayList<Photo> announcementList;
	ImageLoader imageLoader = MonApplication.getInstance().getImageLoader();
	FragmentManager mFragmentManager;
	private OnLoadMoreListener onLoadMoreListener;

	public AnnouncementDetailAdapter(ArrayList<Photo> announcementList, LayoutInflater inflaterActivity,
			CoreActivity activity, FragmentManager mFragmentManager) {
		this.inflaterActivity = inflaterActivity;
		this.announcementList = announcementList;
		this.activity = activity;
		this.mFragmentManager = mFragmentManager;
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
			convertView = inflater.inflate(R.layout.item_announcement_detail, null);

		if (imageLoader == null)
			imageLoader = MonApplication.getInstance().getImageLoader();

		if (position == 0) {
			RoundedImageView avaStore = (RoundedImageView) convertView.findViewById(R.id.avaStore);
			avaStore.setImageUrl(Store.current_announcement.getShop().getImage_thumbnail(), imageLoader);

			TextView name = (TextView) convertView.findViewById(R.id.tvNameStore);
			name.setText(Store.current_announcement.getShop().getName());

			TextView timestamp = (TextView) convertView.findViewById(R.id.tvTimestamp);
			CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
					Long.parseLong(Store.current_announcement.getCreated_time()), System.currentTimeMillis(),
					DateUtils.SECOND_IN_MILLIS);
			timestamp.setText(timeAgo);

			TextView tvStatus = (TextView) convertView.findViewById(R.id.tvStatus);
			tvStatus.setText(Store.current_announcement.getContent());

			ImageView imSaleOff = (ImageView) convertView.findViewById(R.id.imSaleOff);
			TextView tvTimeSale = (TextView) convertView.findViewById(R.id.tvTimeSale);

			long unixSeconds = Store.current_announcement.getStart_time();
			Date date_start = new Date(unixSeconds * 1000L);
			unixSeconds = Store.current_announcement.getEnd_time();
			Date date_end = new Date(unixSeconds * 1000L);
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm MM-dd");
			sdf.setTimeZone(TimeZone.getTimeZone("GMT-7"));
			String formattedDate = sdf.format(date_start) + " đến " + sdf.format(date_end);
			tvTimeSale.setText(formattedDate);

			if (Store.current_announcement.getType() == 2) {
				imSaleOff.setVisibility(View.VISIBLE);
				tvTimeSale.setVisibility(View.VISIBLE);
			} else {
				imSaleOff.setVisibility(View.GONE);
				tvTimeSale.setVisibility(View.GONE);
			}

			Photo item = announcementList.get(position);
			TextView tvDescription = (TextView) convertView.findViewById(R.id.tvDescription);
			if (item.getCaption() == "null") {
				tvDescription.setText("");
			} else {
				tvDescription.setText(item.getCaption());
			}

			FeedImageView feedImageView = (FeedImageView) convertView.findViewById(R.id.imShopImage);
			// Feed image
			Log.d("image", item.getImage());
			feedImageView.setImageUrl(item.getImage(), imageLoader);
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
			RelativeLayout rlShopDescription = (RelativeLayout) convertView.findViewById(R.id.rlshop);
			rlShopDescription.setVisibility(View.GONE);
			Photo item = announcementList.get(position);
			TextView tvDescription = (TextView) convertView.findViewById(R.id.tvDescription);
			if (item.getCaption() == "null") {
				tvDescription.setText("");
			} else {
				tvDescription.setText(item.getCaption());
			}

			FeedImageView feedImageView = (FeedImageView) convertView.findViewById(R.id.imShopImage);
			// Feed image
			Log.d("image", item.getImage());
			feedImageView.setImageUrl(item.getImage(), imageLoader);
			feedImageView.setVisibility(View.VISIBLE);
			feedImageView.setResponseObserver(new FeedImageView.ResponseObserver() {
				@Override
				public void onError() {
				}

				@Override
				public void onSuccess() {
				}
			});
		}
		return convertView;
	}

}