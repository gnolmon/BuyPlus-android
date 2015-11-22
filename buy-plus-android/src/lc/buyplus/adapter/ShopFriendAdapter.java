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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import lc.buyplus.R;
import lc.buyplus.activities.ShopInfoActivity;
import lc.buyplus.application.MonApplication;
import lc.buyplus.cores.HandleRequest;
import lc.buyplus.customizes.RoundedImageView;
import lc.buyplus.fragments.CanvasFragment;
import lc.buyplus.models.Friend;
import lc.buyplus.models.Store;

public class ShopFriendAdapter extends BaseAdapter {

	private LayoutInflater inflaterActivity;
	private LayoutInflater inflater;
	List<Friend> friendList;
	ImageLoader imageLoader = MonApplication.getInstance().getImageLoader();
	private OnLoadMoreListener onLoadMoreListener;
	
	public ShopFriendAdapter( List<Friend> friendList,LayoutInflater inflaterActivity) {
		this.inflaterActivity = inflaterActivity;
		this.friendList = friendList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return friendList.size();
	}
	
	public OnLoadMoreListener getOnLoadMoreListener() {
		return onLoadMoreListener;
	}

	public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
		this.onLoadMoreListener = onLoadMoreListener;
	}
	
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return friendList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	public long getFriendId(int position) {
		// TODO Auto-generated method stub
		return friendList.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (inflater == null)
			inflater = (LayoutInflater) inflaterActivity;
		if (convertView == null)
			convertView = inflater.inflate(R.layout.item_store_friend, null);

		if (imageLoader == null)
			imageLoader = MonApplication.getInstance().getImageLoader();
		final Friend item = friendList.get(position);
		final int pos = position;
		TextView nameFriend = (TextView) convertView.findViewById(R.id.tvFriendName);
		nameFriend.setText(item.getName());

		
		TextView tvID = (TextView) convertView.findViewById(R.id.tvFriendId);
		tvID.setText("ID:" +item.getId());
		
		final Button delBtn = (Button) convertView.findViewById(R.id.btnDelFriend);
		delBtn.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
				api_remove_friend_from_shop(Store.current_shop_id,item.getId());
				friendList.remove(pos);
				notifyDataSetChanged();
			}
		});

		RoundedImageView imNoti = (RoundedImageView) convertView.findViewById(R.id.imFriend);

		// name.setText(item.getName());

		// user profile pic
		imNoti.setImageUrl(item.getImage(), imageLoader);

		return convertView;
	}
	
	public void api_remove_friend_from_shop(int shop_id,int friend_id){
	 	
    	Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", CanvasFragment.mUser.getAccessToken());
		params.put("shop_id", String.valueOf(shop_id));
		params.put("friend_id", String.valueOf(friend_id));
		Log.d("sadd",params.toString());
			RequestQueue requestQueue = Volley.newRequestQueue(CanvasFragment.mActivity);
			HandleRequest jsObjRequest = new HandleRequest(Method.POST,
					HandleRequest.REMOVE_FRIEND_FROM_CIRCLE, params, 
					new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.d("api_remove_friend_from_shop",response.toString());
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