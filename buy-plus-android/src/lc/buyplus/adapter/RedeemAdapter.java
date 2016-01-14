package lc.buyplus.adapter;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.toolbox.ImageLoader;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import lc.buyplus.R;
import lc.buyplus.adapter.AnnounmentAdapter.ViewHolder;
import lc.buyplus.application.MonApplication;
import lc.buyplus.customizes.RoundedImageView;
import lc.buyplus.customizes.RoundedViewImage;
import lc.buyplus.fragments.CanvasFragment;
import lc.buyplus.models.Shop;

public class RedeemAdapter extends BaseAdapter {
	private Activity activity;
	private LayoutInflater inflater;
	private LayoutInflater inflaterActivity;
	List<Shop> giftItems;
	ImageLoader imageLoader = MonApplication.getInstance().getImageLoader();
	private OnLoadMoreListener onLoadMoreListener;

	public RedeemAdapter(ArrayList<Shop> giftItems, LayoutInflater inflaterActivity) {
		this.inflaterActivity = inflaterActivity;
		this.giftItems = giftItems;
	}

	static class ViewHolder {
		public TextView name;
		public TextView scoreTotal;
		public RoundedViewImage imShop;
		public LinearLayout canex;
	}

	@Override
	public int getCount() {
		return giftItems.size();
	}

	public OnLoadMoreListener getOnLoadMoreListener() {
		return onLoadMoreListener;
	}

	public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
		this.onLoadMoreListener = onLoadMoreListener;
	}

	@Override
	public Object getItem(int position) {
		return giftItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public int getItem_id(int position) {
		return giftItems.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (inflater == null)
			inflater = inflaterActivity;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_redeem, null);
			viewHolder = new ViewHolder();

			viewHolder.name = (TextView) convertView.findViewById(R.id.tvTitleShop);
			viewHolder.scoreTotal = (TextView) convertView.findViewById(R.id.tvScore);
			viewHolder.imShop = (RoundedViewImage) convertView.findViewById(R.id.imShop);
			viewHolder.canex = (LinearLayout) convertView.findViewById(R.id.tvChangeGift);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		if (imageLoader == null)
			imageLoader = MonApplication.getInstance().getImageLoader();

		Shop item = giftItems.get(position);

		viewHolder.scoreTotal.setText("Số điểm tích: " + item.getCurrent_customer_shop_point());

		if (!item.canEx) {
			viewHolder.canex.setVisibility(View.GONE);
		} else {
			viewHolder.canex.setVisibility(0);
		}
		viewHolder.name.setText(item.getName());
		
		//viewHolder.imShop.setImageUrl(item.getImage_thumbnail(), imageLoader);
		Glide.with(CanvasFragment.mActivity).load(item.getImage_thumbnail()).placeholder(viewHolder.imShop.getDrawable()).centerCrop().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(viewHolder.imShop);
		return convertView;

	}

}
