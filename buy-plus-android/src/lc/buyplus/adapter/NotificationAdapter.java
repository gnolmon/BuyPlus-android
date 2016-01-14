package lc.buyplus.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import lc.buyplus.R;
import lc.buyplus.activities.LoginActivity;
import lc.buyplus.adapter.AnnounmentAdapter.ViewHolder;
import lc.buyplus.application.MonApplication;
import lc.buyplus.cores.HandleRequest;
import lc.buyplus.customizes.DialogMessage;
import lc.buyplus.customizes.RoundedImageView;
import lc.buyplus.customizes.RoundedViewImage;
import lc.buyplus.fragments.CanvasFragment;
import lc.buyplus.models.Notification;
import lc.buyplus.models.Store;

public class NotificationAdapter extends BaseAdapter {

	private LayoutInflater inflaterActivity;
	private LayoutInflater inflater;
	List<Notification> notiList;
	ImageLoader imageLoader = MonApplication.getInstance().getImageLoader();
	private OnLoadMoreListener onLoadMoreListener;

	public NotificationAdapter(List<Notification> notiList, LayoutInflater inflaterActivity) {
		this.inflaterActivity = inflaterActivity;
		this.notiList = notiList;
	}

	static class ViewHolder {
		public Notification item;

		public TextView noti;

		public TextView timeStamp;

		public RoundedViewImage imNoti;

	}

	public OnLoadMoreListener getOnLoadMoreListener() {
		return onLoadMoreListener;
	}

	public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
		this.onLoadMoreListener = onLoadMoreListener;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return notiList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return notiList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (inflater == null)
			inflater = (LayoutInflater) inflaterActivity;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_notification, null);
			viewHolder = new ViewHolder();
			viewHolder.noti = (TextView) convertView.findViewById(R.id.tvNoti);

			viewHolder.timeStamp = (TextView) convertView.findViewById(R.id.tvTimeStamp);
			viewHolder.imNoti = (RoundedViewImage) convertView.findViewById(R.id.imNoti);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		if (imageLoader == null)
			imageLoader = MonApplication.getInstance().getImageLoader();
		Notification item = notiList.get(position);

		CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(Long.parseLong(item.getCreated_time()),
				System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
		viewHolder.noti.setText(item.getMessage());
		viewHolder.timeStamp.setText(timeAgo);

		// name.setText(item.getName());

		// user profile pic

		final Button accept = (Button) convertView.findViewById(R.id.btnAgreeTerm);
		final Button reject = (Button) convertView.findViewById(R.id.btnIgnore);

		final String string = notiList.get(position).getParams();
		final String[] parts = string.split(":");
		String param_request_id = "0";
		if (notiList.get(position).getType() == 2)
			param_request_id = parts[2];
		if (notiList.get(position).getType() == 4)
			param_request_id = parts[1];
		final String res_id = param_request_id;
		if (notiList.get(position).getResponse_result() == 0
				&& (notiList.get(position).getType() == 4 || notiList.get(position).getType() == 2)) {
			accept.setVisibility(0);
			reject.setVisibility(0);
		} else {
			accept.setVisibility(View.GONE);
			reject.setVisibility(View.GONE);
		}
		final int pos = position;
		accept.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				Log.d("params", string);
				Log.d("params", parts[0]);
				api_response_join_shop(Integer.valueOf(res_id), "accept");
				notiList.get(pos).setResponse_result(1);
				accept.setVisibility(View.GONE);
				reject.setVisibility(View.GONE);
			}
		});
		reject.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				api_response_join_shop(Integer.valueOf(res_id), "deny");
				notiList.get(pos).setResponse_result(2);
				accept.setVisibility(View.GONE);
				reject.setVisibility(View.GONE);
			}
		});
		// imNoti.setImageUrl(item.getImage(), imageLoader);
		Glide.with(CanvasFragment.mActivity).load(item.getImage()).placeholder(viewHolder.imNoti.getDrawable()).centerCrop()
		.diskCacheStrategy(DiskCacheStrategy.SOURCE).into(viewHolder.imNoti);

		return convertView;
	}

	// accept_type: accept, deny, deny_forever
	public void api_response_join_shop(int request_id, String accept_type) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", Store.user.getAccessToken());
		params.put("request_id", String.valueOf(request_id));
		params.put("accept_type", accept_type);
		Log.d("params", String.valueOf(request_id));
		Log.d("params", accept_type);
		RequestQueue requestQueue = MonApplication.getInstance().getRequestQueue();
		HandleRequest jsObjRequest = new HandleRequest(Method.POST, HandleRequest.RESPONSE_REQUEST__JOIN_SHOP, params,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.d("api_response_join_shop", response.toString());
						try {
							if (Integer.parseInt(response.getString("error")) == 2) {
								DialogMessage dialog = new DialogMessage(CanvasFragment.mActivity,
										CanvasFragment.mActivity.getResources().getString(R.string.end_session));
								dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
								dialog.show();
								SharedPreferences pre = CanvasFragment.mActivity.getSharedPreferences("buy_pus", 0);
								SharedPreferences.Editor editor = pre.edit();
								// editor.clear();
								editor.putBoolean("immediate_login", false);
								editor.commit();
								Intent loginActivity = new Intent(CanvasFragment.mActivity, LoginActivity.class);
								CanvasFragment.mActivity.startActivity(loginActivity);
								CanvasFragment.mActivity.finish();

							}
							if (Integer.parseInt(response.getString("error")) == 1) {
								DialogMessage dialog = new DialogMessage(CanvasFragment.mActivity,
										response.getString("message"));
								dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
								dialog.show();
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						DialogMessage dialog = new DialogMessage(CanvasFragment.mActivity,
								CanvasFragment.mActivity.getResources().getString(R.string.connect_problem));
						dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
						dialog.show();
					}
				});
		requestQueue.add(jsObjRequest);
	}
}