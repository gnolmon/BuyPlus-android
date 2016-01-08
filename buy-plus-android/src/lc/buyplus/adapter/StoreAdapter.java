package lc.buyplus.adapter;

import java.util.ArrayList;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.bumptech.glide.Glide;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import lc.buyplus.R;
import lc.buyplus.activities.ShopInfoActivity;
import lc.buyplus.adapter.AnnounmentAdapter.ViewHolder;
import lc.buyplus.application.MonApplication;
import lc.buyplus.customizes.RoundedImageView;
import lc.buyplus.customizes.RoundedViewImage;
import lc.buyplus.fragments.CanvasFragment;
import lc.buyplus.models.Shop;
import lc.buyplus.models.Store;

public class StoreAdapter extends BaseAdapter{
	private LayoutInflater inflater;
	private LayoutInflater inflaterActivity;
	ArrayList<Shop> storeList;
	ImageLoader imageLoader = MonApplication.getInstance().getImageLoader();
	private OnLoadMoreListener onLoadMoreListener;

	public StoreAdapter(ArrayList<Shop> shopsList, LayoutInflater inflaterActivity, Activity activity) {
		this.inflaterActivity = inflaterActivity;
		this.storeList = shopsList;
	}
	
	static class ViewHolder {
		public TextView tvNameStore;
		
		TextView tvAddressStore;
		ImageView imJoinShop;
		
		RoundedViewImage avaStore;
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
	
	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (inflater == null)
			inflater = inflaterActivity;
		if (convertView == null){
			convertView = inflater.inflate(R.layout.item_store_home, null);
			viewHolder = new ViewHolder();
			viewHolder.tvNameStore = (TextView) convertView.findViewById(R.id.tvNameStore);
			
			viewHolder.tvAddressStore = (TextView) convertView.findViewById(R.id.tvAddressStore);
			viewHolder.imJoinShop = (ImageView) convertView.findViewById(R.id.imJoinShop);
			
			viewHolder.avaStore = (RoundedViewImage) convertView
					.findViewById(R.id.avaStore);
			convertView.setTag(viewHolder);
		} else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
			

		if (imageLoader == null)
			imageLoader = MonApplication.getInstance().getImageLoader();

		

		final Shop item = storeList.get(position);
		if (item.isJoin == true){
			viewHolder.imJoinShop.setVisibility(View.VISIBLE);
		}else{
			viewHolder.imJoinShop.setVisibility(View.INVISIBLE);
		}
		viewHolder.tvNameStore.setText(item.getName());
		
//		tvNameStore.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Store.current_shop_id = item.getId();
//	             Intent shopInfoActivity = new Intent(activity,ShopInfoActivity.class);		             
//	             activity.startActivity(shopInfoActivity);
//				
//			}
//		});
//		
		viewHolder.tvAddressStore.setText(item.getAddress());
		
		if (item.getImage() != null || item.getImage() != ""){
			Glide.with(CanvasFragment.mActivity).load(item.getImage()).centerCrop()
			.placeholder(R.drawable.loading_icon).crossFade().into(viewHolder.avaStore);
			//avaStore.setImageUrl(item.getImage(), imageLoader);
		}
		
		
		return convertView;
	}
}
