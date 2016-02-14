package lc.buyplus.adapter;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentManager;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import lc.buyplus.R;
import lc.buyplus.activities.LoginActivity;
import lc.buyplus.activities.ShopInfoActivity;
import lc.buyplus.application.MonApplication;
import lc.buyplus.cores.CoreActivity;
import lc.buyplus.cores.HandleRequest;
import lc.buyplus.customizes.DialogMessage;
import lc.buyplus.customizes.RoundedViewImage;
import lc.buyplus.fragments.CanvasFragment;
import lc.buyplus.models.Announcement;
import lc.buyplus.models.Shop;
import lc.buyplus.models.Store;

public class AnnounmentAdapter extends BaseAdapter {

	public CoreActivity activity;
	private LayoutInflater inflater;
	private LayoutInflater inflaterActivity;
	ArrayList<Announcement> announcementList;
	ImageLoader imageLoader = MonApplication.getInstance().getImageLoader();
	FragmentManager mFragmentManager;
	private OnLoadMoreListener onLoadMoreListener;

	public AnnounmentAdapter(ArrayList<Announcement> announcementList, LayoutInflater inflaterActivity,
			CoreActivity activity, FragmentManager mFragmentManager) {
		this.inflaterActivity = inflaterActivity;
		this.announcementList = announcementList;
		this.activity = activity;
		this.mFragmentManager = mFragmentManager;
	}

	static class ViewHolder {
		public TextView name;
		public TextView timestamp;
		public ImageView imSaleOff;
		public TextView tvTimeSale;

		public TextView tvStatus;
		public TextView tvSeemore;
		public RoundedViewImage avaStore;
		public ImageView feedImageView, feedImageView2, feedImageView3, feedImageView4;
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
		final ViewHolder viewHolder;
		final Announcement item = announcementList.get(position);
		if (inflater == null)
			inflater = inflaterActivity;
		viewHolder = new ViewHolder();
		if (item.getPhotos().size() == 1) {
			Log.d("IM", "Vao 1");
			convertView = inflater.inflate(R.layout.item_announment, null);
			viewHolder.feedImageView = (ImageView) convertView.findViewById(R.id.imFeed);
		}
		if (item.getPhotos().size() == 2) {
			Log.d("IM", "Vao 2");
			convertView = inflater.inflate(R.layout.item_announment_2, null);
			viewHolder.feedImageView = (ImageView) convertView.findViewById(R.id.imFeed);
			viewHolder.feedImageView2 = (ImageView) convertView.findViewById(R.id.imFeed2);
		}
		if (item.getPhotos().size() == 3) {
			Log.d("IM", "Vao 3");
			convertView = inflater.inflate(R.layout.item_announment_3, null);
			viewHolder.feedImageView = (ImageView) convertView.findViewById(R.id.imFeed);
			viewHolder.feedImageView2 = (ImageView) convertView.findViewById(R.id.imFeed2);
			viewHolder.feedImageView3 = (ImageView) convertView.findViewById(R.id.imFeed3);
		}
		if (item.getPhotos().size() == 4) {
			Log.d("IM", "Vao 4");
			convertView = inflater.inflate(R.layout.item_announment_4, null);
			viewHolder.feedImageView = (ImageView) convertView.findViewById(R.id.imFeed);
			viewHolder.feedImageView2 = (ImageView) convertView.findViewById(R.id.imFeed2);
			viewHolder.feedImageView3 = (ImageView) convertView.findViewById(R.id.imFeed3);
			viewHolder.feedImageView4 = (ImageView) convertView.findViewById(R.id.imFeed4);
		}

		viewHolder.name = (TextView) convertView.findViewById(R.id.tvNameStore);
		viewHolder.timestamp = (TextView) convertView.findViewById(R.id.tvTimestamp);
		viewHolder.imSaleOff = (ImageView) convertView.findViewById(R.id.imSaleOff);
		viewHolder.tvTimeSale = (TextView) convertView.findViewById(R.id.tvTimeSale);

		viewHolder.tvStatus = (TextView) convertView.findViewById(R.id.tvStatus);
		viewHolder.tvSeemore = (TextView) convertView.findViewById(R.id.tvSeemore);

		viewHolder.avaStore = (RoundedViewImage) convertView.findViewById(R.id.avaStore);

		convertView.setTag(viewHolder);

		if (imageLoader == null)
			imageLoader = MonApplication.getInstance().getImageLoader();

		viewHolder.name.setText(item.getShop().getName());

		long unixSeconds = item.getStart_time();
		Date date_start = new Date(unixSeconds * 1000L);
		unixSeconds = item.getEnd_time();
		Date date_end = new Date(unixSeconds * 1000L);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT-7"));
		String formattedDate = sdf.format(date_start) + " - " + sdf.format(date_end);
		viewHolder.tvTimeSale.setText(formattedDate);

		CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(1000 * Long.parseLong(item.getCreated_time()),
				System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
		viewHolder.timestamp.setText(timeAgo);

		if (item.getContent().length() > 200) {
			viewHolder.tvStatus.setText(item.getContent().substring(0, 200) + "...");

			viewHolder.tvSeemore.setVisibility(View.VISIBLE);
			viewHolder.tvSeemore.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					viewHolder.tvStatus.setText(item.getContent());
					viewHolder.tvSeemore.setVisibility(View.GONE);
				}
			});
		} else {
			viewHolder.tvStatus.setText(item.getContent());
			viewHolder.tvSeemore.setVisibility(View.GONE);
		}

		// viewHolder.avaStore.setImageUrl(item.getShop().getImage_thumbnail(),
		// imageLoader);

		Glide.with(CanvasFragment.mActivity).load(item.getShop().getImage_thumbnail())
				.placeholder(viewHolder.avaStore.getDrawable()).centerCrop().diskCacheStrategy(DiskCacheStrategy.SOURCE)
				.into(viewHolder.avaStore);
		viewHolder.name.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Store.current_shop_id = (item.getShop_id());
				api_get_shop_info(Store.current_shop_id);

			}
		});
		viewHolder.avaStore.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Store.current_shop_id = (item.getShop_id());
				api_get_shop_info(Store.current_shop_id);

			}
		});

		if (item.getType() == 2) {
			viewHolder.imSaleOff.setVisibility(View.VISIBLE);
			viewHolder.tvTimeSale.setVisibility(View.VISIBLE);
		} else {
			viewHolder.imSaleOff.setVisibility(View.GONE);
			viewHolder.tvTimeSale.setVisibility(View.GONE);
		}

		switch (item.getPhotos().size()) {
		case 0:
			viewHolder.feedImageView.setVisibility(View.GONE);
			break;
		case 1:
			Glide.with(CanvasFragment.mActivity).load(item.getPhotos().get(0).getImage())
					.placeholder(viewHolder.feedImageView.getDrawable()).centerCrop()
					.diskCacheStrategy(DiskCacheStrategy.SOURCE).into(viewHolder.feedImageView);
			viewHolder.feedImageView.setVisibility(View.VISIBLE);
			break;
		case 2:
			// image 1
			Glide.with(CanvasFragment.mActivity).load(item.getPhotos().get(0).getImage())
					.placeholder(viewHolder.feedImageView.getDrawable()).centerCrop()
					.diskCacheStrategy(DiskCacheStrategy.SOURCE).into(viewHolder.feedImageView);
			viewHolder.feedImageView.setVisibility(View.VISIBLE);

			// image 2
			Glide.with(CanvasFragment.mActivity).load(item.getPhotos().get(1).getImage())
					.placeholder(viewHolder.feedImageView2.getDrawable()).centerCrop()
					.diskCacheStrategy(DiskCacheStrategy.SOURCE).into(viewHolder.feedImageView2);
			viewHolder.feedImageView2.setVisibility(View.VISIBLE);
			break;
		case 3:
			// image 1
			Glide.with(CanvasFragment.mActivity).load(item.getPhotos().get(0).getImage())
					.placeholder(viewHolder.feedImageView.getDrawable()).centerCrop()
					.diskCacheStrategy(DiskCacheStrategy.SOURCE).into(viewHolder.feedImageView);
			viewHolder.feedImageView.setVisibility(View.VISIBLE);

			Log.d("IM", item.getPhotos().get(1).getImage() + "");

			// image 2
			Glide.with(CanvasFragment.mActivity).load(item.getPhotos().get(1).getImage())
					.placeholder(viewHolder.feedImageView2.getDrawable()).centerCrop()
					.diskCacheStrategy(DiskCacheStrategy.SOURCE).into(viewHolder.feedImageView2);
			viewHolder.feedImageView2.setVisibility(View.VISIBLE);

			// image 3
			Glide.with(CanvasFragment.mActivity).load(item.getPhotos().get(2).getImage())
					.placeholder(viewHolder.feedImageView3.getDrawable()).centerCrop()
					.diskCacheStrategy(DiskCacheStrategy.SOURCE).into(viewHolder.feedImageView3);
			viewHolder.feedImageView3.setVisibility(View.VISIBLE);
			break;
		case 4:
			// image 1
			Glide.with(CanvasFragment.mActivity).load(item.getPhotos().get(0).getImage())
					.placeholder(viewHolder.feedImageView.getDrawable()).centerCrop()
					.diskCacheStrategy(DiskCacheStrategy.SOURCE).into(viewHolder.feedImageView);
			viewHolder.feedImageView.setVisibility(View.VISIBLE);

			// image 2
			Glide.with(CanvasFragment.mActivity).load(item.getPhotos().get(1).getImage())
					.placeholder(viewHolder.feedImageView2.getDrawable()).centerCrop()
					.diskCacheStrategy(DiskCacheStrategy.SOURCE).into(viewHolder.feedImageView2);
			viewHolder.feedImageView2.setVisibility(View.VISIBLE);

			// image 3
			Glide.with(CanvasFragment.mActivity).load(item.getPhotos().get(2).getImage())
					.placeholder(viewHolder.feedImageView3.getDrawable()).centerCrop()
					.diskCacheStrategy(DiskCacheStrategy.SOURCE).into(viewHolder.feedImageView3);
			viewHolder.feedImageView3.setVisibility(View.VISIBLE);

			// image 4
			Glide.with(CanvasFragment.mActivity).load(item.getPhotos().get(3).getImage())
					.placeholder(viewHolder.feedImageView4.getDrawable()).centerCrop()
					.diskCacheStrategy(DiskCacheStrategy.SOURCE).into(viewHolder.feedImageView4);
			viewHolder.feedImageView4.setVisibility(View.VISIBLE);
			break;
		}

		return convertView;
	}

	public void api_get_shop_info(int shop_id) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", CanvasFragment.mUser.getAccessToken());
		params.put("shop_id", String.valueOf(shop_id));
		RequestQueue requestQueue = MonApplication.getInstance().getRequestQueue();
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
						Store.isConnectNetwotk = true;
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						if (Store.isConnectNetwotk == true) {
							Store.isConnectNetwotk = false;
							DialogMessage dialog = new DialogMessage(activity, activity.getResources().getString(R.string.connect_problem));
							dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
							dialog.show();
						}
					}
				});
		requestQueue.add(jsObjRequest);
	}
}