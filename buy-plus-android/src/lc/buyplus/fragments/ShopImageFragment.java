package lc.buyplus.fragments;

import java.util.ArrayList;
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

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import lc.buyplus.R;
import lc.buyplus.activities.ShopInfoActivity;
import lc.buyplus.adapter.ShopPhotoAdapter;
import lc.buyplus.configs.AppConfigs;
import lc.buyplus.adapter.ShopPhotoAdapter;
import lc.buyplus.cores.CoreActivity;
import lc.buyplus.cores.CoreFragment;
import lc.buyplus.cores.HandleRequest;
import lc.buyplus.customizes.Utils;
import lc.buyplus.models.Announcement;
import lc.buyplus.models.Friend;
import lc.buyplus.models.Gift;
import lc.buyplus.models.Notification;
import lc.buyplus.models.Shop;
import lc.buyplus.models.Store;
import lc.buyplus.ultils.UtilFunctions;

public class ShopImageFragment extends CoreFragment {
	private ListView listView;
	private ShopPhotoAdapter photoAdapter;
	private LayoutInflater inflaterActivity;
	private GridView gridView;
	private int columnWidth;
	private UtilFunctions utils;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_store_photo, container, false);
		initViews(view);
		initModels();
		initAnimations();
		listView = (ListView) view.findViewById(R.id.grid_view);
		inflaterActivity = inflater;

		return view;
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {

		}
	}

	protected void initModels() {

	}

	@Override
	protected void initViews(View v) {

	}

	@Override
	protected void initAnimations() {

	}

	public static final long serialVersionUID = 6036846677812555352L;

	public static CoreActivity mActivity;
	public static ShopImageFragment mInstance;

	public static ShopImageFragment getInstance(CoreActivity activity) {
		if (mInstance == null) {
			mInstance = new ShopImageFragment();
		}
		mActivity = activity;
		return mInstance;
	}

	public static ShopImageFragment getInstance() {
		if (mInstance == null) {
			mInstance = new ShopImageFragment();
		}
		return mInstance;
	}

	@Override
	protected void initListener() {
		// TODO Auto-generated method stub

	}

	/**
	 * Method to calculate the grid dimensions Calculates number columns and
	 * columns width in grid
	 */
	private void InitilizeGridLayout() {
		Resources r = getResources();
		float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, AppConfigs.GRID_PADDING,
				r.getDisplayMetrics());

		// Column width
		columnWidth = (int) ((utils.getScreenWidth() - ((5) * padding)) / 4);

		// Setting number of grid columns
		gridView.setNumColumns(4);
		gridView.setColumnWidth(columnWidth);
		gridView.setStretchMode(GridView.NO_STRETCH);
		gridView.setPadding((int) padding, (int) padding, (int) padding, (int) padding);

		// Setting horizontal and vertical padding
		gridView.setHorizontalSpacing((int) padding);
		gridView.setVerticalSpacing((int) padding);
	}

}
