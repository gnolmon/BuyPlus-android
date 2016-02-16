package lc.buyplus.fragments;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import lc.buyplus.R;
import lc.buyplus.application.MonApplication;
import lc.buyplus.cores.CoreActivity;
import lc.buyplus.cores.CoreFragment;
import lc.buyplus.cores.HandleRequest;
import lc.buyplus.customizes.DialogMessage;
import lc.buyplus.models.Store;

public class TermFragment extends CoreFragment {
	private LinearLayout mHomeTab, mPersonalTab, mLoyaltyCardTab, mNotiTab, mSettingTab, mBack;
	private TextView tvTerm;
	private Button btnIgnore;
	private Button btnAgreeTerm;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.register_term_fragment, container, false);
		initViews(view);
		initModels();
		initAnimations();
		return view;
	}

	@Override
	public void onClick(View view) {
		Intent returnIntent;
		switch (view.getId()) {
		case R.id.fragment_canvas_back:
			mFragmentManager.beginTransaction().replace(R.id.canvas, RegisterFragment.getInstance(mActivity)).commit();
			break;
		case R.id.btnAgreeTerm:
			api_user_register( RegisterFragment.username, RegisterFragment.email, RegisterFragment.cpassword);
			
			break;
		case R.id.btnIgnore:
			mFragmentManager.beginTransaction().replace(R.id.canvas, RegisterFragment.getInstance(mActivity)).commit();
			break;
		default:
			break;
		}
	}

	protected void initModels() {

	}

	@Override
	protected void initViews(View v) {
		
		mBack = (LinearLayout) v.findViewById(R.id.fragment_canvas_back);
		mBack.setOnClickListener(this);
		
		btnIgnore = (Button) v.findViewById(R.id.btnIgnore);
		btnAgreeTerm = (Button) v.findViewById(R.id.btnAgreeTerm);
		btnIgnore.setOnClickListener(this);
		btnAgreeTerm.setOnClickListener(this);
		tvTerm = (TextView) v.findViewById(R.id.tvTerm);
		tvTerm.setMovementMethod(new ScrollingMovementMethod());
	}

	@Override
	protected void initAnimations() {

	}
	
	
	public void api_user_register(String name, String login_name, String password){
	 	
    	Map<String, String> params = new HashMap<String, String>();
		params.put("login_name", login_name);
		params.put("name", name);
		params.put("password", password);
		Log.d("api_user_register",params.toString());
			RequestQueue requestQueue = MonApplication.getInstance().getRequestQueue();
			HandleRequest jsObjRequest = new HandleRequest(Method.POST,
					HandleRequest.USER_REGISTER, params, 
					new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						
						try {
							if (Integer.parseInt(response.getString("error"))==1){
								DialogMessage dialog = new DialogMessage(mActivity,response.getString("message"));
								dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
								dialog.show();
							}else{
								DialogMessage dialog = new DialogMessage(mActivity,mActivity.getResources().getString(R.string.register_success));
								dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
								dialog.show();
								mFragmentManager.beginTransaction().replace(R.id.canvas, LoginFragment.getInstance(mActivity)).commit();
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
							Log.d("api_user_register",error.toString());
							if (Store.isConnectNetwotk == true) {
								Store.isConnectNetwotk = false;
								DialogMessage dialog = new DialogMessage(mActivity,mActivity.getResources().getString(R.string.connect_problem));
								dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
								dialog.show();
							}
						}
					});
			requestQueue.add(jsObjRequest);
	}

	public static final long serialVersionUID = 6036846677812555352L;

	public static CoreActivity mActivity;
	public static TermFragment mInstance;

	public static TermFragment getInstance(CoreActivity activity) {
		if (mInstance == null) {
			mInstance = new TermFragment();
		}
		mActivity = activity;
		return mInstance;
	}

	public static TermFragment getInstance() {
		if (mInstance == null) {
			mInstance = new TermFragment();
		}
		return mInstance;
	}

	@Override
	protected void initListener() {
		// TODO Auto-generated method stub

	}
}