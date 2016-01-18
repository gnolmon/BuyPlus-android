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
import com.bumptech.glide.Glide;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentManager;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import lc.buyplus.R;
import lc.buyplus.activities.LoginActivity;
import lc.buyplus.activities.ShopInfoActivity;
import lc.buyplus.adapter.AnnounmentAdapter.ViewHolder;
import lc.buyplus.application.MonApplication;
import lc.buyplus.cores.CoreActivity;
import lc.buyplus.cores.FeedImageView;
import lc.buyplus.cores.HandleRequest;
import lc.buyplus.customizes.DialogMessage;
import lc.buyplus.customizes.RoundedImageView;
import lc.buyplus.customizes.RoundedViewImage;
import lc.buyplus.fragments.CanvasFragment;
import lc.buyplus.models.Announcement;
import lc.buyplus.models.Photo;
import lc.buyplus.models.Shop;
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

	static class ViewHolder {
		public RoundedImageView avaStore;
		public TextView name;
		public TextView timestamp;
		public TextView tvStatus;
		public ImageView imSaleOff;
		public TextView tvTimeSale;
		public FeedImageView feedImageView;
		public TextView tvDescription;
		public RelativeLayout rlShopDescription;
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
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_announcement_detail, null);
			viewHolder = new ViewHolder();
			viewHolder.avaStore = (RoundedImageView) convertView.findViewById(R.id.avaStore);
			viewHolder.name = (TextView) convertView.findViewById(R.id.tvNameStore);
			viewHolder.timestamp = (TextView) convertView.findViewById(R.id.tvTimestamp);
			viewHolder.tvStatus = (TextView) convertView.findViewById(R.id.tvStatus);
			viewHolder.imSaleOff = (ImageView) convertView.findViewById(R.id.imSaleOff);
			viewHolder.tvTimeSale = (TextView) convertView.findViewById(R.id.tvTimeSale);
			viewHolder.feedImageView = (FeedImageView) convertView.findViewById(R.id.imShopImage);
			viewHolder.tvDescription = (TextView) convertView.findViewById(R.id.tvDescription);
			viewHolder.rlShopDescription = (RelativeLayout) convertView.findViewById(R.id.rlshop);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		if (imageLoader == null)
			imageLoader = MonApplication.getInstance().getImageLoader();

		if (position == 0) {
			CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
					1000*Long.parseLong(Store.current_announcement.getCreated_time()), System.currentTimeMillis(),
					DateUtils.SECOND_IN_MILLIS);
			viewHolder.timestamp.setText(timeAgo);
			viewHolder.avaStore.setImageUrl(Store.current_announcement.getShop().getImage_thumbnail(),
			imageLoader);
			//Glide.with(CanvasFragment.mActivity).load(Store.current_announcement.getShop().getImage_thumbnail())
			//		.centerCrop().placeholder(R.drawable.loading_icon).crossFade().into(viewHolder.avaStore);
			viewHolder.name.setText(Store.current_announcement.getShop().getName());
			viewHolder.tvStatus.setText(Store.current_announcement.getContent());

			long unixSeconds = Store.current_announcement.getStart_time();
			Date date_start = new Date(unixSeconds * 1000L);
			unixSeconds = Store.current_announcement.getEnd_time();
			Date date_end = new Date(unixSeconds * 1000L);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");
			sdf.setTimeZone(TimeZone.getTimeZone("GMT-7"));
			String formattedDate = sdf.format(date_start) + " - " + sdf.format(date_end);
			viewHolder.tvTimeSale.setText(formattedDate);

			if (Store.current_announcement.getType() == 2) {
				viewHolder.imSaleOff.setVisibility(View.VISIBLE);
				viewHolder.tvTimeSale.setVisibility(View.VISIBLE);
			} else {
				viewHolder.imSaleOff.setVisibility(View.GONE);
				viewHolder.tvTimeSale.setVisibility(View.GONE);
			}

			Photo item = announcementList.get(position);
			if (item.getCaption() == "null") {
				viewHolder.tvDescription.setText("");
			} else {
				viewHolder.tvDescription.setText(item.getCaption());
			}

			viewHolder.name.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Store.current_shop_id = (Store.current_announcement.getShop_id());
					api_get_shop_info(Store.current_shop_id);

				}
			});
			viewHolder.avaStore.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Store.current_shop_id = (Store.current_announcement.getShop_id());
					api_get_shop_info(Store.current_shop_id);

				}
			});

			// Feed image
			Log.d("image", item.getImage());
			viewHolder.feedImageView.setImageUrl(item.getImage(),
			imageLoader);
			//Glide.with(CanvasFragment.mActivity).load(item.getImage()).centerCrop().placeholder(R.drawable.loading_icon)
			//		.crossFade().into(viewHolder.feedImageView);
			viewHolder.feedImageView.setVisibility(View.VISIBLE);

		} else {
			viewHolder.rlShopDescription.setVisibility(View.GONE);
			Photo item = announcementList.get(position);
			TextView tvDescription = (TextView) convertView.findViewById(R.id.tvDescription);
			if (item.getCaption() == "null") {
				tvDescription.setText("");
			} else {
				tvDescription.setText(item.getCaption());
			}

			// Feed image
			Log.d("image", item.getImage());
			viewHolder.feedImageView.setImageUrl(item.getImage(),
			imageLoader);
			//Glide.with(CanvasFragment.mActivity).load(item.getImage()).centerCrop().placeholder(R.drawable.loading_icon)
			//		.crossFade().into(viewHolder.feedImageView);
			viewHolder.feedImageView.setVisibility(View.VISIBLE);

		}
		return convertView;
	}

	public void api_get_shop_info(int shop_id) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", CanvasFragment.mUser.getAccessToken());
		params.put("shop_id", String.valueOf(shop_id));
		RequestQueue requestQueue =  MonApplication.getInstance().getRequestQueue();
		HandleRequest jsObjRequest = new HandleRequest(Method.GET,
				HandleRequest.build_link(HandleRequest.GET_SHOP_INFO, params), params,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.d("api_get_shop_info", response.toString());
						try {
							if (Integer.parseInt(response.getString("error")) == 2) {
								DialogMessage dialog = new DialogMessage(activity,
										activity.getResources().getString(R.string.end_session));
								dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
								dialog.show();
								SharedPreferences pre = activity.getSharedPreferences("buy_pus", 0);
								SharedPreferences.Editor editor = pre.edit();
								// editor.clear();
								editor.putBoolean("immediate_login", false);
								editor.commit();
								Intent loginActivity = new Intent(activity, LoginActivity.class);
								activity.startActivity(loginActivity);
								activity.finish();

							}
							if (Integer.parseInt(response.getString("error")) == 1) {
								DialogMessage dialog = new DialogMessage(activity, response.getString("message"));
								dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
								dialog.show();
							} else {
								Store.current_shop = new Shop(response.getJSONObject("data"));
								Store.current_shop_name = Store.current_shop.getName();
								Intent shopInfoActivity = new Intent(activity, ShopInfoActivity.class);
								activity.startActivity(shopInfoActivity);
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						DialogMessage dialog = new DialogMessage(activity,
								activity.getResources().getString(R.string.connect_problem));
						dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
						dialog.show();
					}
				});
		requestQueue.add(jsObjRequest);
	}
}