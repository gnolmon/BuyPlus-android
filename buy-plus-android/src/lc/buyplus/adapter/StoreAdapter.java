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
import lc.buyplus.customizes.RoundedImageView;
import lc.buyplus.models.Shop;

public class StoreAdapter extends BaseAdapter{
	private Activity activity;
	private LayoutInflater inflater;
	private LayoutInflater inflaterActivity;
	ArrayList<Shop> storeList;
	ImageLoader imageLoader = MonApplication.getInstance().getImageLoader();
	private OnLoadMoreListener onLoadMoreListener;

	public StoreAdapter(ArrayList<Shop> shopsList, LayoutInflater inflaterActivity) {
		this.inflaterActivity = inflaterActivity;
		this.storeList = shopsList;
	}
	
	public OnLoadMoreListener getOnLoadMoreListener() {
		return onLoadMoreListener;
	}

	public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
		this.onLoadMoreListener = onLoadMoreListener;
	}

	@Override
	public int getCount() {
		return storeList.size();
	}

	@Override
	public Object getItem(int position) {
		return storeList.get(position);
	}
	
	public int getItem_id(int position){
		return storeList.get(position).getId();
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
		
		TextView tvAddressStore = (TextView) convertView.findViewById(R.id.tvAddressStore);
		
		RoundedImageView avaStore = (RoundedImageView) convertView
				.findViewById(R.id.avaStore);

		Shop item = storeList.get(position);

		tvNameStore.setText(item.getName());
		tvAddressStore.setText(item.getAddress());
		
		if (item.getImage() != null || item.getImage() != ""){
			avaStore.setImageUrl(item.getImage(), imageLoader);
		}
		
		
		return convertView;
	}
}
