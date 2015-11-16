package lc.buyplus.adapter;

import java.util.List;

import com.android.volley.toolbox.ImageLoader;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import lc.buyplus.R;
import lc.buyplus.application.MonApplication;
import lc.buyplus.customizes.RoundedImageView;
import lc.buyplus.models.FacebookFriend;

public class FriendAdapter extends BaseAdapter {

	private LayoutInflater inflaterActivity;
	private LayoutInflater inflater;
	List<FacebookFriend> friendList;
	ImageLoader imageLoader = MonApplication.getInstance().getImageLoader();

	public FriendAdapter( List<FacebookFriend> friendList,LayoutInflater inflaterActivity) {
		this.inflaterActivity = inflaterActivity;
		this.friendList = friendList;
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
		if (inflater == null)
			inflater = (LayoutInflater) inflaterActivity;
		if (convertView == null)
			convertView = inflater.inflate(R.layout.item_add_friend, null);

		if (imageLoader == null)
			imageLoader = MonApplication.getInstance().getImageLoader();
		FacebookFriend item = friendList.get(position);

		TextView nameFriend = (TextView) convertView.findViewById(R.id.tvFriendName);
		nameFriend.setText(item.getName());

		
		//TextView tvID = (TextView) convertView.findViewById(R.id.tvFriendId);
		//tvID.setText("ID:" +item.getId());


		RoundedImageView imNoti = (RoundedImageView) convertView.findViewById(R.id.imFriend);

		// name.setText(item.getName());

		// user profile pic
		imNoti.setImageUrl(item.getPicture(), imageLoader);

		return convertView;
	}

}