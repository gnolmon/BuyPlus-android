package lc.buyplus.customizes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.Volley;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import lc.buyplus.R;
import lc.buyplus.activities.LoginActivity;
import lc.buyplus.application.MonApplication;
import lc.buyplus.cores.HandleRequest;
import lc.buyplus.fragments.CanvasFragment;
import lc.buyplus.fragments.HomeAnnounmentFragment;
import lc.buyplus.fragments.HomeFragment;
import lc.buyplus.models.Announcement;
import lc.buyplus.models.Shop;
import lc.buyplus.models.Store;

public class DialogShop extends android.app.Dialog implements android.view.View.OnClickListener {

	public Activity c;
	public Dialog d;
	public TextView tvShop, tvAll;
	public ImageView imShop, imAll;
	public int flag;

	public DialogShop(Activity a) {
		super(a);
		// TODO Auto-generated constructor stub
		this.c = CanvasFragment.mActivity;
		flag = 0;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_shop);

		tvShop = (TextView) findViewById(R.id.tvShop);
		tvShop.setOnClickListener(this);
		imShop = (ImageView) findViewById(R.id.imShop);

		//tvJoin = (TextView) findViewById(R.id.tvJoin);
		//imJoin = (ImageView) findViewById(R.id.imJoin);
		//tvJoin.setOnClickListener(this);

		tvAll = (TextView) findViewById(R.id.tvAll);
		imAll = (ImageView) findViewById(R.id.imAll);
		tvAll.setOnClickListener(this);
		
//		switch (HomeAnnounmentFragment.type) {
//		case "normal":
//			if (Store.AnnouncementsList.size()>0) HomeAnnounmentFragment.listView.setSelection(0);
//			imShop.setVisibility(View.VISIBLE);
//			imJoin.setVisibility(View.INVISIBLE);
//			imAll.setVisibility(View.INVISIBLE);
//			HomeAnnounmentFragment.type = "normal";
//			break;
//		case "promotion":
//			if (Store.AnnouncementsList.size()>0) HomeAnnounmentFragment.listView.setSelection(0);
//			imShop.setVisibility(View.INVISIBLE);
//			imJoin.setVisibility(View.VISIBLE);
//			imAll.setVisibility(View.INVISIBLE);
//			HomeAnnounmentFragment.type = "promotion";
//			break;
//		case "all":
//			if (Store.AnnouncementsList.size()>0) HomeAnnounmentFragment.listView.setSelection(0);
//			imShop.setVisibility(View.INVISIBLE);
//			imJoin.setVisibility(View.INVISIBLE);
//			imAll.setVisibility(View.VISIBLE);
//			HomeAnnounmentFragment.type = "all";
//			break;
//		default:
//			break;
//		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tvShop:
			imShop.setVisibility(View.VISIBLE);
			//imJoin.setVisibility(View.INVISIBLE);
			imAll.setVisibility(View.INVISIBLE);
			Store.ShopsListTmp = Store.ShopsList;
			Collections.sort(Store.ShopsList);
			HomeFragment.storeAdapter.notifyDataSetChanged();
			break;
//		case R.id.tvJoin:
//			imShop.setVisibility(View.INVISIBLE);
//			imJoin.setVisibility(View.VISIBLE);
//			imAll.setVisibility(View.INVISIBLE);
//			Log.d("DA", Store.ShopsListTmp.size() + "");
//			if (Store.ShopsListTmp.size() > 0){
//				Store.ShopsList.removeAll(Store.ShopsList);
//				Store.ShopsList = Store.ShopsListTmp;
//			};
//			HomeFragment.storeAdapter.notifyDataSetChanged();
//			break;
		case R.id.tvAll:
			imShop.setVisibility(View.INVISIBLE);
			//imJoin.setVisibility(View.INVISIBLE);
			imAll.setVisibility(View.VISIBLE);
			Log.d("DA", Store.ShopsListTmp.size() + "");
			if (Store.ShopsListTmp.size() > 0){
				Store.ShopsList.removeAll(Store.ShopsList);
				Store.ShopsList = Store.ShopsListTmp;
			};
			HomeFragment.storeAdapter.notifyDataSetChanged();
			break;
		default:
			break;
		}
		dismiss();
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}
	
	

}