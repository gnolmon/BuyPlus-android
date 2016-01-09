package lc.buyplus.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import lc.buyplus.R;
import lc.buyplus.activities.LoginActivity;
import lc.buyplus.activities.ShopInfoActivity;
import lc.buyplus.adapter.AnnounmentAdapter.ViewHolder;
import lc.buyplus.application.MonApplication;
import lc.buyplus.cores.HandleRequest;
import lc.buyplus.customizes.DialogMessage;
import lc.buyplus.customizes.RoundedImageView;
import lc.buyplus.fragments.CanvasFragment;
import lc.buyplus.models.FacebookFriend;
import lc.buyplus.models.Friend;
import lc.buyplus.models.Store;

public class FriendAdapter extends BaseAdapter {

	private LayoutInflater inflaterActivity;
	private LayoutInflater inflater;
	List<FacebookFriend> friendList;
	ImageLoader imageLoader = MonApplication.getInstance().getImageLoader();

	public FriendAdapter( List<FacebookFriend> friendList,LayoutInflater inflaterActivity) {
		this.inflaterActivity = inflaterActivity;
		this.friendList = friendList;
	}
	static class ViewHolder {
		public TextView nameFriend;
		public TextView tvID;
		public Button addFriend;
		public RoundedImageView imNoti;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return friendList.size();
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		if (inflater == null)
			inflater = (LayoutInflater) inflaterActivity;
		if (convertView == null){
			convertView = inflater.inflate(R.layout.item_add_friend, null);
			viewHolder = new ViewHolder();
			
			viewHolder.nameFriend = (TextView) convertView.findViewById(R.id.tvFriendName);
			viewHolder.tvID = (TextView) convertView.findViewById(R.id.tvFriendId);
			viewHolder.addFriend = (Button) convertView.findViewById(R.id.btnAddFriend);
			viewHolder.imNoti = (RoundedImageView) convertView.findViewById(R.id.imFriend);
			
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
			

		if (imageLoader == null)
			imageLoader = MonApplication.getInstance().getImageLoader();
		
		
		
		final FacebookFriend item = friendList.get(position);
		final int pos = position;
		//TextView tvID = (TextView) convertView.findViewById(R.id.tvFriendId);
		//tvID.setText("ID:" +item.getId());

		
		
		viewHolder.nameFriend.setText(item.getName());
		viewHolder.tvID.setText("ID:" +String.valueOf(item.getId()));
		viewHolder.addFriend.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
				api_send_request_join_shop_to_friend(Store.current_shop_id,item.getId());
				viewHolder.addFriend.setText("Dang cho");
				viewHolder.addFriend.setEnabled(false);
			}
		});
		

		// name.setText(item.getName());

		// user profile pic
		//viewHolder.imNoti.setImageUrl(item.getPicture(), imageLoader);
		Glide.with(CanvasFragment.mActivity).load(item.getPicture()).centerCrop()
		.placeholder(R.drawable.loading_icon).crossFade().into(viewHolder.imNoti);

		return convertView;
	}
	
	public void api_send_request_join_shop_to_friend(int shop_id, String temp_id){
	 	
		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", Store.user.getAccessToken());
		params.put("shop_id", String.valueOf(shop_id));
		params.put("temp_id", String.valueOf(temp_id));
			RequestQueue requestQueue = Volley.newRequestQueue(CanvasFragment.mActivity);
			HandleRequest jsObjRequest = new HandleRequest(Method.POST,
					HandleRequest.SEND_REQUEST_JOIN_SHOP_TO_FRIEND, params, 
					new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.d("api_send_request_join_shop_to_friend",response.toString());
						try {
							if (Integer.parseInt(response.getString("error"))==2){
								DialogMessage dialog = new DialogMessage(CanvasFragment.mActivity,CanvasFragment.mActivity.getResources().getString(R.string.end_session));
								dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
								dialog.show();
								SharedPreferences pre=CanvasFragment.mActivity.getSharedPreferences("buy_pus", 0);
								SharedPreferences.Editor editor=pre.edit();
								//editor.clear();
								editor.putBoolean("immediate_login", false);
								editor.commit();
								Intent loginActivity = new Intent(CanvasFragment.mActivity,LoginActivity.class);
								CanvasFragment.mActivity.startActivity(loginActivity);
								CanvasFragment.mActivity.finish();

							}
							if (Integer.parseInt(response.getString("error"))==1){
								DialogMessage dialog = new DialogMessage(CanvasFragment.mActivity,response.getString("message"));
								dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
								dialog.show();
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
					}, 
					new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
							DialogMessage dialog = new DialogMessage(CanvasFragment.mActivity,CanvasFragment.mActivity.getResources().getString(R.string.connect_problem));
						dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
						dialog.show();
						}
					});
			requestQueue.add(jsObjRequest);
	}

}