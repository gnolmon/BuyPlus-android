package lc.buyplus.adapter;

import java.util.List;

import com.android.volley.toolbox.ImageLoader;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import lc.buyplus.R;
import lc.buyplus.application.MonApplication;
import lc.buyplus.customizes.RoundedImageView;
import lc.buyplus.models.Gift;

public class ShopGiftAdapter extends BaseAdapter {

	private LayoutInflater inflaterActivity;
	private LayoutInflater inflater;
	List<Gift> giftList;
	ImageLoader imageLoader = MonApplication.getInstance().getImageLoader();
	private OnLoadMoreListener onLoadMoreListener;
	private ProgressBar pbPoint;
	
	public ShopGiftAdapter( List<Gift> giftList,LayoutInflater inflaterActivity) {
		this.inflaterActivity = inflaterActivity;
		this.giftList = giftList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return giftList.size();
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
		return giftList.get(position);
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
			convertView = inflater.inflate(R.layout.item_store_redeem, null);

		if (imageLoader == null)
			imageLoader = MonApplication.getInstance().getImageLoader();
		Gift item = giftList.get(position);

		TextView storeName = (TextView) convertView.findViewById(R.id.tvstoreTitle);
		storeName.setText("" + item.getName());

		TextView tvContentRedeem = (TextView) convertView.findViewById(R.id.tvContentRedeem);
		tvContentRedeem.setText("" + item.getDescription());

		TextView tvPoint = (TextView) convertView.findViewById(R.id.tvPoint);
		tvPoint.setText("Diem: " + item.getPoint());

		RoundedImageView imRedeem = (RoundedImageView) convertView.findViewById(R.id.imRedeem);

		// name.setText(item.getName());

		// user profile pic
		imRedeem.setImageUrl(item.getImage(), imageLoader);
		
		pbPoint = (ProgressBar) convertView.findViewById(R.id.pbScore);
		pbPoint.setProgress(progress);

		return convertView;
	}

}