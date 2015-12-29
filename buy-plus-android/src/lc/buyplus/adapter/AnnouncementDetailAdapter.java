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
import lc.buyplus.application.MonApplication;
import lc.buyplus.cores.CoreActivity;
import lc.buyplus.cores.FeedImageView;
import lc.buyplus.cores.HandleRequest;
import lc.buyplus.customizes.DialogMessage;
import lc.buyplus.customizes.RoundedImageView;
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
			
			name.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Store.current_shop_id = ( Store.current_announcement.getShop_id());
		    	  	 api_get_shop_info(Store.current_shop_id);
					
				}
			});
			avaStore.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Store.current_shop_id = ( Store.current_announcement.getShop_id());
		    	  	 api_get_shop_info(Store.current_shop_id);
					
				}
			});
			
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
	
	public void api_get_shop_info(int shop_id) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", CanvasFragment.mUser.getAccessToken());
		params.put("shop_id", String.valueOf(shop_id));
		RequestQueue requestQueue = Volley.newRequestQueue(activity);
		HandleRequest jsObjRequest = new HandleRequest(Method.GET,
				HandleRequest.build_link(HandleRequest.GET_SHOP_INFO, params), params,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.d("api_get_shop_info", response.toString());
						try {
							if (Integer.parseInt(response.getString("error"))==2){
								DialogMessage dialog = new DialogMessage(activity,activity.getResources().getString(R.string.end_session));
								dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
								dialog.show();
								SharedPreferences pre=activity.getSharedPreferences("buy_pus", 0);
								SharedPreferences.Editor editor=pre.edit();
								//editor.clear();
								editor.putBoolean("immediate_login", false);
								editor.commit();
								Intent loginActivity = new Intent(activity,LoginActivity.class);
								activity.startActivity(loginActivity);
							    activity.finish();

							}
							if (Integer.parseInt(response.getString("error"))==1){
								DialogMessage dialog = new DialogMessage(activity,response.getString("message"));
								dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
								dialog.show();
							}else{
								Store.current_shop = new Shop(response.getJSONObject("data"));
								Store.current_shop_name =  Store.current_shop.getName();
								Intent shopInfoActivity = new Intent(activity,ShopInfoActivity.class);			            
								activity.startActivity(shopInfoActivity);
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						DialogMessage dialog = new DialogMessage(activity,activity.getResources().getString(R.string.connect_problem));
						dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
						dialog.show();
					}
				});
		requestQueue.add(jsObjRequest);
	}
}