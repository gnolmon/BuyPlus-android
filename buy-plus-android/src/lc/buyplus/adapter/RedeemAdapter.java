package lc.buyplus.adapter;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.toolbox.ImageLoader;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import lc.buyplus.R;
import lc.buyplus.application.MonApplication;
import lc.buyplus.customizes.RoundedImageView;
import lc.buyplus.models.Shop;

public class RedeemAdapter extends BaseAdapter {
	private Activity activity;
	private LayoutInflater inflater;
	private LayoutInflater inflaterActivity;
	List<Shop> giftItems;
	ImageLoader imageLoader = MonApplication.getInstance().getImageLoader();

	public RedeemAdapter(ArrayList<Shop> giftItems, LayoutInflater inflaterActivity) {
		this.inflaterActivity = inflaterActivity;
		this.giftItems = giftItems;
	}

	@Override
	public int getCount() {
		return giftItems.size();
	}

	@Override
	public Object getItem(int position) {
		return giftItems.get(position);
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
			convertView = inflater.inflate(R.layout.item_redeem, null);

		if (imageLoader == null)
			imageLoader = MonApplication.getInstance().getImageLoader();

		TextView name = (TextView) convertView.findViewById(R.id.tvTitleShop);

		RoundedImageView imShop = (RoundedImageView) convertView.findViewById(R.id.imShop);

		Shop item = giftItems.get(position);

		TextView scoreTotal = (TextView) convertView.findViewById(R.id.tvScore);

		if (item.getCurrent_customer_shop_point() != null) {
			scoreTotal.setText("Số điểm tích: " + item.getCurrent_customer_shop_point());
		} else {
			scoreTotal.setText("Số điểm tích: 0");
		}

		name.setText(item.getName());
		imShop.setImageUrl(item.getImage_thumbnail(), imageLoader);

		return convertView;

	}

}
