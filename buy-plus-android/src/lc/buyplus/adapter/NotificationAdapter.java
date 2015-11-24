package lc.buyplus.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import lc.buyplus.R;
import lc.buyplus.application.MonApplication;
import lc.buyplus.cores.HandleRequest;
import lc.buyplus.customizes.RoundedImageView;
import lc.buyplus.fragments.CanvasFragment;
import lc.buyplus.models.Notification;
import lc.buyplus.models.Store;

public class NotificationAdapter extends BaseAdapter {

	private LayoutInflater inflaterActivity;
	private LayoutInflater inflater;
	List<Notification> notiList;
	ImageLoader imageLoader = MonApplication.getInstance().getImageLoader();
	private OnLoadMoreListener onLoadMoreListener;
	
	public NotificationAdapter( List<Notification> notiList,LayoutInflater inflaterActivity) {
		this.inflaterActivity = inflaterActivity;
		this.notiList = notiList;
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
		if (inflater == null)
			inflater = (LayoutInflater) inflaterActivity;
		if (convertView == null)
			convertView = inflater.inflate(R.layout.item_notification, null);

		if (imageLoader == null)
			imageLoader = MonApplication.getInstance().getImageLoader();
		Notification item = notiList.get(position);

		TextView noti = (TextView) convertView.findViewById(R.id.tvNoti);
		noti.setText(item.getMessage());

		TextView timeStamp = (TextView) convertView.findViewById(R.id.tvTimeStamp);
		CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(Long.parseLong(item.getCreated_time()),
		System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
		timeStamp.setText(timeAgo);


		RoundedImageView imNoti = (RoundedImageView) convertView.findViewById(R.id.imNoti);

	
		// name.setText(item.getName());

		// user profile pic
		
		
		
		final Button accept= (Button) convertView.findViewById(R.id.btnAgreeTerm);
		final Button reject= (Button) convertView.findViewById(R.id.btnIgnore);
		
		String string = notiList.get(position).getParams();
		final String[] parts = string.split(":");
		if (notiList.get(position).getResponse_result()==0 && (notiList.get(position).getType()==4 || notiList.get(position).getType()==2)){
			accept.setVisibility(0);
			reject.setVisibility(0);
		}
		else{
			accept.setVisibility(View.GONE);
			reject.setVisibility(View.GONE);
		} 
		final int pos = position;
		accept.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				api_response_join_shop(Integer.valueOf(parts[1]),"accept");
				notiList.get(pos).setResponse_result(1);
				accept.setVisibility(View.GONE);
				reject.setVisibility(View.GONE);
			}
		});
		reject.setOnClickListener(new OnClickListener() {
	
			public void onClick(View v) {
				api_response_join_shop(Integer.valueOf(parts[1]),"deny");
				notiList.get(pos).setResponse_result(2);
				accept.setVisibility(View.GONE);
				reject.setVisibility(View.GONE);
			}
		});
		imNoti.setImageUrl(item.getImage(), imageLoader);

		return convertView;
	}
	
	//accept_type:  accept, deny, deny_forever	
		public void api_response_join_shop(int request_id, String accept_type){
		 	
			Map<String, String> params = new HashMap<String, String>();
			params.put("access_token", Store.user.getAccessToken());
			params.put("request_id", String.valueOf(request_id));
			params.put("accept_type", accept_type);
				RequestQueue requestQueue = Volley.newRequestQueue(CanvasFragment.mActivity);
				HandleRequest jsObjRequest = new HandleRequest(Method.POST,
						HandleRequest.RESPONSE_REQUEST__JOIN_SHOP, params, 
						new Response.Listener<JSONObject>() {
						@Override
						public void onResponse(JSONObject response) {
							Log.d("api_response_join_shop",response.toString());
							// code here
						}
						}, 
						new Response.ErrorListener() {
							@Override
							public void onErrorResponse(VolleyError error) {
							}
						});
				requestQueue.add(jsObjRequest);
		}
}