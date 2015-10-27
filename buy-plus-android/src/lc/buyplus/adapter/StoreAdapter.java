package lc.buyplus.adapter;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import lc.buyplus.R;
import lc.buyplus.cores.AppController;
import lc.buyplus.models.Shop;
import lc.buyplus.models.Store;

public class StoreAdapter extends BaseAdapter{
	private Activity activity;
	private LayoutInflater inflater;
	ArrayList<Shop> storeList;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	
	public StoreAdapter(ArrayList<Shop> shopsList) {
		this.activity = activity;
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
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null)
			convertView = inflater.inflate(R.layout.item_store_home, null);

		if (imageLoader == null)
			imageLoader = AppController.getInstance().getImageLoader();

		TextView tvNameStore = (TextView) convertView.findViewById(R.id.tvNameStore);
		TextView tvAddressStore = (TextView) convertView.findViewById(R.id.tvAddressStore);
		
		NetworkImageView avaStore = (NetworkImageView) convertView
				.findViewById(R.id.avaStore);

		Shop item = storeList.get(position);

		tvNameStore.setText(item.getName());
		tvAddressStore.setText(item.getAddress());
		avaStore.setImageUrl(item.getImage_thumbnail(), imageLoader);
		
		return convertView;
	}
}
