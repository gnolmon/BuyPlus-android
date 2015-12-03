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
import android.widget.TextView;
import lc.buyplus.R;
import lc.buyplus.application.MonApplication;
import lc.buyplus.cores.CoreActivity;
import lc.buyplus.cores.FeedImageView;
import lc.buyplus.cores.HandleRequest;
import lc.buyplus.customizes.RoundedImageView;
import lc.buyplus.fragments.CanvasFragment;
import lc.buyplus.models.Announcement;

public class AnnouncementDetailAdapter extends BaseAdapter {

	public CoreActivity activity;
	private LayoutInflater inflater;
	private LayoutInflater inflaterActivity;
	ArrayList<Announcement> announcementList;
	ImageLoader imageLoader = MonApplication.getInstance().getImageLoader();
	FragmentManager mFragmentManager;
	private OnLoadMoreListener onLoadMoreListener;
	
	public AnnouncementDetailAdapter(ArrayList<Announcement> announcementList, LayoutInflater inflaterActivity, CoreActivity activity, FragmentManager mFragmentManager) {
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

		TextView tvDescription = (TextView) convertView.findViewById(R.id.tvDescription);
		
		FeedImageView feedImageView = (FeedImageView) convertView.findViewById(R.id.imShopImage);


		return convertView;
	}

	
	public void api_get_shop_announcements(int shop_id, int type, int latest_id, int oldest_id) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", CanvasFragment.mUser.getAccessToken());
		params.put("shop_id", String.valueOf(shop_id));
		params.put("type", String.valueOf(type));
		params.put("latest_id", String.valueOf(latest_id));
		params.put("oldest_id", String.valueOf(oldest_id));
		RequestQueue requestQueue = Volley.newRequestQueue(this.activity);
		HandleRequest jsObjRequest = new HandleRequest(Method.GET,
				HandleRequest.build_link(HandleRequest.GET_SHOP_ANNOUNCEMENTS, params), params,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						Log.d("api_get_shop_announcements", response.toString());
						try {
							ArrayList<Announcement> AnnouncementsList = new ArrayList<Announcement>();
							JSONArray data_aray = response.getJSONArray("data");
							for (int i = 0; i < data_aray.length(); i++) {
								Announcement announcement = new Announcement((JSONObject) data_aray.get(i));
								AnnouncementsList.add(announcement);
							}
						} catch (JSONException e) {

							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
					}
				});
		requestQueue.add(jsObjRequest);
	}
}