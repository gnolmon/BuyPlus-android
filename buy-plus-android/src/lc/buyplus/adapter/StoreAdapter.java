package lc.buyplus.adapter;

import java.util.ArrayList;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import lc.buyplus.R;
import lc.buyplus.application.MonApplication;
import lc.buyplus.models.Shop;

public class StoreAdapter extends BaseAdapter{
	private Activity activity;
	private LayoutInflater inflater;
	private LayoutInflater inflaterActivity;
	ArrayList<Shop> storeList;
	ImageLoader imageLoader = MonApplication.getInstance().getImageLoader();
	
	public StoreAdapter(ArrayList<Shop> shopsList, LayoutInflater inflaterActivity) {
		this.inflaterActivity = inflaterActivity;
		this.storeList = shopsList;
	}
	
	@Override
	public int getCount() {
		return storeList.size();
	}

	@Override
	public Object getItem(int position) {
		return storeList.get(position);
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
			convertView = inflater.inflate(R.layout.item_store_home, null);

		if (imageLoader == null)
			imageLoader = MonApplication.getInstance().getImageLoader();

		TextView tvNameStore = (TextView) convertView.findViewById(R.id.tvNameStore);
		tvNameStore.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		TextView tvAddressStore = (TextView) convertView.findViewById(R.id.tvAddressStore);
		
		NetworkImageView avaStore = (NetworkImageView) convertView
				.findViewById(R.id.avaStore);

		Shop item = storeList.get(position);

		tvNameStore.setText(item.getName());
		tvAddressStore.setText(item.getAddress());
		
		avaStore.setImageUrl(item.getImage(), imageLoader);
		
		return convertView;
	}
}
