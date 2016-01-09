package lc.buyplus.adapter;

import java.util.List;

import com.android.volley.toolbox.ImageLoader;
import com.bumptech.glide.Glide;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import lc.buyplus.R;
import lc.buyplus.adapter.AnnounmentAdapter.ViewHolder;
import lc.buyplus.application.MonApplication;
import lc.buyplus.customizes.RoundedImageView;
import lc.buyplus.customizes.RoundedViewImage;
import lc.buyplus.fragments.CanvasFragment;
import lc.buyplus.models.Gift;
import lc.buyplus.models.Store;

public class ShopGiftAdapter extends BaseAdapter {

	private LayoutInflater inflaterActivity;
	private LayoutInflater inflater;
	List<Gift> giftList;
	ImageLoader imageLoader = MonApplication.getInstance().getImageLoader();
	private OnLoadMoreListener onLoadMoreListener;
	private ProgressBar pbPoint;

	public ShopGiftAdapter(List<Gift> giftList, LayoutInflater inflaterActivity) {
		this.inflaterActivity = inflaterActivity;
		this.giftList = giftList;
	}

	static class ViewHolder {
		public TextView storeName;
		public TextView tvContentRedeem;
		public TextView tvPoint;
		public RoundedViewImage imRedeem;
		public ProgressBar pbPoint;
		public TextView tvScore;
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

		ViewHolder viewHolder;
		if (inflater == null)
			inflater = (LayoutInflater) inflaterActivity;

		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_store_redeem, null);

			viewHolder.storeName = (TextView) convertView.findViewById(R.id.tvstoreTitle);
			viewHolder.tvContentRedeem = (TextView) convertView.findViewById(R.id.tvContentRedeem);
			viewHolder.tvPoint = (TextView) convertView.findViewById(R.id.tvPoint);
			viewHolder.imRedeem = (RoundedViewImage) convertView.findViewById(R.id.imRedeem);
			viewHolder.pbPoint = (ProgressBar) convertView.findViewById(R.id.pbScore);
			viewHolder.tvScore = (TextView) convertView.findViewById(R.id.tvScore);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		if (imageLoader == null)
			imageLoader = MonApplication.getInstance().getImageLoader();
		Gift item = giftList.get(position);

		viewHolder.storeName.setText("" + item.getName());
		viewHolder.tvContentRedeem.setText("" + item.getDescription());
		viewHolder.tvPoint.setText("Diem: " + item.getPoint());

		// name.setText(item.getName());

		// user profile pic
		// viewHolder.imRedeem.setImageUrl(item.getImage(), imageLoader);
		Glide.with(CanvasFragment.mActivity).load(item.getImage()).centerCrop().placeholder(R.drawable.loading_icon)
				.crossFade().into(viewHolder.imRedeem);

		int myPoint = Store.current_shop.getCurrent_customer_shop_point();
		int giftPoint = item.getPoint();
		int percent = (int) (myPoint * 100 / giftPoint);
		pbPoint.setProgress(percent);

		viewHolder.tvScore.setText(percent + "%");

		return convertView;
	}

}