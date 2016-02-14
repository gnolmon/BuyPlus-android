package lc.buyplus.customizes;

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
import lc.buyplus.models.Announcement;
import lc.buyplus.models.Store;

public class DialogNews extends android.app.Dialog implements android.view.View.OnClickListener {

	public Activity c;
	public Dialog d;
	public TextView tvShop, tvPromo, tvAll;
	public ImageView imShop, imPromo, imAll;
	public int flag;

	public DialogNews(Activity a) {
		super(a);
		// TODO Auto-generated constructor stub
		this.c = CanvasFragment.mActivity;
		flag = 0;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_news);

		tvShop = (TextView) findViewById(R.id.tvShop);
		tvShop.setOnClickListener(this);
		imShop = (ImageView) findViewById(R.id.imShop);

		tvPromo = (TextView) findViewById(R.id.tvPromo);
		imPromo = (ImageView) findViewById(R.id.imPromo);
		tvPromo.setOnClickListener(this);

		tvAll = (TextView) findViewById(R.id.tvAll);
		imAll = (ImageView) findViewById(R.id.imAll);
		tvAll.setOnClickListener(this);
		
		switch (HomeAnnounmentFragment.type) {
		case "normal":
			if (Store.AnnouncementsList.size()>0) HomeAnnounmentFragment.listView.setSelection(0);
			imShop.setVisibility(View.VISIBLE);
			imPromo.setVisibility(View.INVISIBLE);
			imAll.setVisibility(View.INVISIBLE);
			HomeAnnounmentFragment.type = "normal";
			break;
		case "promotion":
			if (Store.AnnouncementsList.size()>0) HomeAnnounmentFragment.listView.setSelection(0);
			imShop.setVisibility(View.INVISIBLE);
			imPromo.setVisibility(View.VISIBLE);
			imAll.setVisibility(View.INVISIBLE);
			HomeAnnounmentFragment.type = "promotion";
			break;
		case "all":
			if (Store.AnnouncementsList.size()>0) HomeAnnounmentFragment.listView.setSelection(0);
			imShop.setVisibility(View.INVISIBLE);
			imPromo.setVisibility(View.INVISIBLE);
			imAll.setVisibility(View.VISIBLE);
			HomeAnnounmentFragment.type = "all";
			break;
		default:
			break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tvShop:
			imShop.setVisibility(View.VISIBLE);
			imPromo.setVisibility(View.INVISIBLE);
			imAll.setVisibility(View.INVISIBLE);
			HomeAnnounmentFragment.type = "normal";
			api_get_all_announcements(0, Store.limit, HomeAnnounmentFragment.type, 0, 0);
			break;
		case R.id.tvPromo:
			imShop.setVisibility(View.INVISIBLE);
			imPromo.setVisibility(View.VISIBLE);
			imAll.setVisibility(View.INVISIBLE);
			HomeAnnounmentFragment.type = "promotion";
			api_get_all_announcements(0, Store.limit, HomeAnnounmentFragment.type, 0, 0);
			break;
		case R.id.tvAll:
			imShop.setVisibility(View.INVISIBLE);
			imPromo.setVisibility(View.INVISIBLE);
			imAll.setVisibility(View.VISIBLE);
			HomeAnnounmentFragment.type = "all";
			api_get_all_announcements(0, Store.limit, HomeAnnounmentFragment.type, 0, 0);
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
	
	
	public void api_get_all_announcements(int last_id, int limit, String type, int mode, int search) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", CanvasFragment.mUser.getAccessToken());
		params.put("type", String.valueOf(type));
		params.put("last_id", String.valueOf(last_id));
		params.put("limit", String.valueOf(limit));
		params.put("mode", String.valueOf(mode));
		params.put("search", String.valueOf(search));
		RequestQueue requestQueue =  MonApplication.getInstance().getRequestQueue();
		HandleRequest jsObjRequest = new HandleRequest(Method.GET,
				HandleRequest.build_link(HandleRequest.GET_ALL_ANNOUNCEMENTS, params), params,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {

						try {
							if (Integer.parseInt(response.getString("error"))==2){
								DialogMessage dialog = new DialogMessage(c,c.getResources().getString(R.string.end_session));
								dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
								dialog.show();
								SharedPreferences pre=c.getSharedPreferences("buy_pus", 0);
								SharedPreferences.Editor editor=pre.edit();
								//editor.clear();
								editor.putBoolean("immediate_login", false);
								editor.commit();
								Intent loginActivity = new Intent(c,LoginActivity.class);
								c.startActivity(loginActivity);
							    c.finish();

							}
							if (Integer.parseInt(response.getString("error"))==1){
								DialogMessage dialog = new DialogMessage(c,response.getString("message"));
								dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
								dialog.show();
							}else{
								Store.AnnouncementsList.removeAll(Store.AnnouncementsList);
								JSONArray data_aray = response.getJSONArray("data");
								for (int i = 0; i < data_aray.length(); i++) {
									Announcement announcement = new Announcement((JSONObject) data_aray.get(i));
									HomeAnnounmentFragment.current_last_id = announcement.getId();
									Store.AnnouncementsList.add(announcement);
								}
								HomeAnnounmentFragment.newsAdapter.notifyDataSetChanged();
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
						Store.isConnectNetwotk = true;
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						if (Store.isConnectNetwotk == true) {
							Store.isConnectNetwotk = false;
							DialogMessage dialog = new DialogMessage(c,c.getResources().getString(R.string.connect_problem));
							dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
							dialog.show();
						}
					}
				});
		requestQueue.add(jsObjRequest);
	}
}