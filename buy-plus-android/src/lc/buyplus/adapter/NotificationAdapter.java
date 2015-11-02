package lc.buyplus.adapter;

import java.util.List;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import lc.buyplus.R;
import lc.buyplus.application.MonApplication;
import lc.buyplus.models.Notification;

public class NotificationAdapter extends BaseAdapter {

	private LayoutInflater inflaterActivity;
	private LayoutInflater inflater;
	List<Notification> notiList;
	ImageLoader imageLoader = MonApplication.getInstance().getImageLoader();

	public NotificationAdapter( List<Notification> notiList,LayoutInflater inflaterActivity) {
		this.inflaterActivity = inflaterActivity;
		this.notiList = notiList;
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
		timeStamp.setText(item.getCreated_time());

		TextView name = (TextView) convertView.findViewById(R.id.tvTitleNoti);

		NetworkImageView imNoti = (NetworkImageView) convertView.findViewById(R.id.imNoti);

		// name.setText(item.getName());

		// user profile pic
		imNoti.setImageUrl(item.getImage(), imageLoader);

		return convertView;
	}

}