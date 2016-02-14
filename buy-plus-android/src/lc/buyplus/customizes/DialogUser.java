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

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import lc.buyplus.R;
import lc.buyplus.activities.LoginActivity;
import lc.buyplus.application.MonApplication;
import lc.buyplus.cores.HandleRequest;
import lc.buyplus.fragments.CanvasFragment;
import lc.buyplus.fragments.PersonalFragment;
import lc.buyplus.models.Store;

public class DialogUser extends android.app.Dialog implements android.view.View.OnClickListener {

	public Activity c;
	public Dialog d;
	public Button yes, no;
	public int flag;
	public String msg;
	private TextView tvMsg;
	private String param_name;
	public DialogUser(Activity a,String msg,int pflag,String pParam_name) {
		super(a);
		// TODO Auto-generated constructor stub
		this.c = CanvasFragment.mActivity;
		flag = pflag;
		this.msg = msg;
		this.param_name = pParam_name;
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
				api_update_user_information(param_name);
			}
			dismiss();
			
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
	
public void api_update_user_information(String name){
	 	
    	Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", CanvasFragment.mUser.getAccessToken());
		params.put("name", String.valueOf(name));
			RequestQueue requestQueue =  MonApplication.getInstance().getRequestQueue();
			HandleRequest jsObjRequest = new HandleRequest(Method.POST,
					HandleRequest.UPDATE_USER_INFORMATION, params, 
					new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.d("api_join_shop",response.toString());
						try {
							if (Integer.parseInt(response.getString("error"))==2){
								DialogMessage dialog = new DialogMessage(c,c.getResources().getString(R.string.end_session));
								dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
								dialog.show();
								SharedPreferences pre=getContext().getSharedPreferences("buy_pus", 0);
								SharedPreferences.Editor editor=pre.edit();
								//editor.clear();
								editor.putBoolean("immediate_login", false);
								editor.commit();
								Intent loginActivity = new Intent(c,LoginActivity.class);
							    c.startActivity(loginActivity);
							    c.finish();

							}else
							if (Integer.parseInt(response.getString("error"))==1){
								DialogMessage dialog = new DialogMessage(c,response.getString("message"));
								dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
								dialog.show();
							}else{
								Store.user.setLogin_name(param_name);
								PersonalFragment.userName.setText(Store.user.getLogin_name());
							}
						} catch (NumberFormatException | JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Store.isConnectNetwotk = true;
					}
					}, 
					new Response.ErrorListener() {
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