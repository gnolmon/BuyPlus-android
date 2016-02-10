package lc.buyplus.customizes;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.Volley;
import com.facebook.login.LoginManager;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Button;
import lc.buyplus.R;
import lc.buyplus.activities.LoginActivity;
import lc.buyplus.application.MonApplication;
import lc.buyplus.cores.HandleRequest;
import lc.buyplus.fragments.CanvasFragment;
import lc.buyplus.fragments.ShopInfoFragment;
import lc.buyplus.models.Store;

public class CustomDialogClass extends android.app.Dialog implements android.view.View.OnClickListener {

	public Activity c;
	public Dialog d;
	public Button yes, no;
	public int flag;
	public String msg;
	private TextView tvMsg;

	public CustomDialogClass(Activity pa,String pmsg,int pflag) {
		super(pa);
		// TODO Auto-generated constructor stub
		this.c = CanvasFragment.mActivity;
		this.flag = pflag;
		this.msg = pmsg;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.custom_dialog);
		yes = (Button) findViewById(R.id.btnYes);
		no = (Button) findViewById(R.id.btnNo);
		yes.setOnClickListener(this);
		no.setOnClickListener(this);
		
		tvMsg = (TextView) findViewById(R.id.tvMsg);
		tvMsg.setTextColor(c.getResources().getColor(R.color.title));
		tvMsg.setText("" + msg);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnYes:
			if (flag == 1){
				SharedPreferences pre=getContext().getSharedPreferences("buy_pus", 0);
				SharedPreferences.Editor editor=pre.edit();
				//editor.clear();
				boolean immediate_login = pre.getBoolean("immediate_login", false);
				if (immediate_login==false){
					LoginManager.getInstance().logOut();
				} 
				
				editor.putBoolean("immediate_login", false);
				editor.commit();
				Intent loginActivity = new Intent(c,LoginActivity.class);
	            c.startActivity(loginActivity);
	            c.finish();
			}
			if (flag == 2){
				if (ShopInfoFragment.isJoin) {
					api_leave_shop(Store.current_shop_id);
					
				} else {
					api_join_shop(Store.current_shop_id);
					
				}
			}
			
			break;
		case R.id.btnNo:
			dismiss();
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
	
	public void api_join_shop(int shop_id) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", CanvasFragment.mUser.getAccessToken());
		params.put("shop_id", String.valueOf(shop_id));
		RequestQueue requestQueue =  MonApplication.getInstance().getRequestQueue();
		HandleRequest jsObjRequest = new HandleRequest(Method.POST, HandleRequest.JOIN_SHOP, params,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.d("api_join_shop", response.toString());
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
							else if (Integer.parseInt(response.getString("error"))==1){
								DialogMessage dialog = new DialogMessage(c,response.getString("message"));
								dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
								dialog.show();
							}else{
								Store.current_shop.setCurrent_customer_shop_id(String.valueOf(Store.user.getId()));
								ShopInfoFragment.join_leave.setText("Đã tham gia");
								ShopInfoFragment.join_leave.setBackground(c.getResources().getDrawable(R.drawable.round_button_green));
								ShopInfoFragment.isJoin = true;
							} 
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						DialogMessage dialog = new DialogMessage(c,c.getResources().getString(R.string.connect_problem));
						dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
						dialog.show();
					}
				});
		requestQueue.add(jsObjRequest);
	}

	public void api_leave_shop(int shop_id) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", CanvasFragment.mUser.getAccessToken());
		params.put("shop_id", String.valueOf(shop_id));
		RequestQueue requestQueue = MonApplication.getInstance().getRequestQueue();
		HandleRequest jsObjRequest = new HandleRequest(Method.POST, HandleRequest.LEAVE_SHOP, params,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.d("api_leave_shop", response.toString());
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
							else if (Integer.parseInt(response.getString("error"))==1){
								DialogMessage dialog = new DialogMessage(c,response.getString("message"));
								dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
								dialog.show();
							} else {
								Store.current_shop.setCurrent_customer_shop_id("");
								ShopInfoFragment.join_leave.setText("Tham gia");
								ShopInfoFragment.join_leave.setBackground(c.getResources().getDrawable(R.drawable.round_button_gray));
								ShopInfoFragment.isJoin = false;
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						DialogMessage dialog = new DialogMessage(c,c.getResources().getString(R.string.connect_problem));
						dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
						dialog.show();
					}
				});
		requestQueue.add(jsObjRequest);
	}
}