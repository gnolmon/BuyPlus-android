package lc.buyplus.fragments;


import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.ImageLoader;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import lc.buyplus.R;
import lc.buyplus.activities.LoginActivity;
import lc.buyplus.application.MonApplication;
import lc.buyplus.cores.CoreActivity;
import lc.buyplus.cores.CoreFragment;
import lc.buyplus.cores.HandleRequest;
import lc.buyplus.customizes.BlurTransformation;
import lc.buyplus.customizes.DialogMessage;
import lc.buyplus.customizes.RoundedImageView;
import lc.buyplus.models.Store;

public class FeedbackFragment  extends CoreFragment {
	private LinearLayout mHomeTab, mPersonalTab, mLoyaltyCardTab, mNotiTab, mSettingTab, mBack;
	private Button btnSendFB;
	private EditText edFB;
	Display display;

	private ImageView imbackground;
	private RoundedImageView imAvaUser;
	public static TextView userName;
	ImageLoader imageLoader = MonApplication.getInstance().getImageLoader();
	ImageView myImage;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_feedback, container, false);

		
		initViews(view);
		initModels();
		initAnimations();
		return view;
	}
	
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.fragment_canvas_back:
			mActivity.finish();
			break;
		case R.id.fragment_canvas_home_tab:
			CanvasFragment.mPager.setCurrentItem(0);
			mActivity.finish();
			break;
		case R.id.fragment_canvas_personal_tab:
			CanvasFragment.mPager.setCurrentItem(2);
			mActivity.finish();
			break;
		case R.id.fragment_canvas_loyaltycard_tab:
			CanvasFragment.mPager.setCurrentItem(3);
			mActivity.finish();
			break;
		case R.id.fragment_canvas_notifications_tab:
			CanvasFragment.mPager.setCurrentItem(4);
			mActivity.finish();
			break;
		case R.id.fragment_canvas_setting_tab:
			CanvasFragment.mPager.setCurrentItem(5);
			mActivity.finish();
			break;
		case R.id.btnSendFB:
			api_send_feedback("", edFB.getText().toString());
			break;
		default:
			break;
		}
	}
	
	protected void initModels() {
		
	}

	@Override
	protected void initViews(View v) {
		WindowManager manager = (WindowManager) mActivity.getSystemService(Context.WINDOW_SERVICE);
		display = manager.getDefaultDisplay();

		imAvaUser = (RoundedImageView) v.findViewById(R.id.imAvaUser);
		
		imAvaUser.setImageUrl(Store.user.getImageUrl(), imageLoader);
		imAvaUser.buildDrawingCache();

		imbackground = (ImageView) v.findViewById(R.id.imbackground);
		Glide.with(mActivity).load(Store.user.getImageUrl())
		.placeholder(imbackground.getDrawable()).centerCrop().diskCacheStrategy(DiskCacheStrategy.SOURCE).transform(new BlurTransformation(mActivity))
		.into(imbackground);
		
		userName = (TextView) v.findViewById(R.id.userName);
		userName.setText(Store.user.getLogin_name());
		
		mHomeTab = (LinearLayout) v.findViewById(R.id.fragment_canvas_home_tab);
		mPersonalTab = (LinearLayout) v.findViewById(R.id.fragment_canvas_personal_tab);
		mLoyaltyCardTab = (LinearLayout) v.findViewById(R.id.fragment_canvas_loyaltycard_tab);
		mNotiTab = (LinearLayout) v.findViewById(R.id.fragment_canvas_notifications_tab);
		mSettingTab = (LinearLayout) v.findViewById(R.id.fragment_canvas_setting_tab);
		mBack = (LinearLayout) v.findViewById(R.id.fragment_canvas_back);
		mHomeTab.setOnClickListener(this);
		mPersonalTab.setOnClickListener(this);
		mLoyaltyCardTab.setOnClickListener(this);
		mNotiTab.setOnClickListener(this);
		mSettingTab.setOnClickListener(this);
		mBack.setOnClickListener(this);
		
		btnSendFB = (Button) v.findViewById(R.id.btnSendFB);
		edFB = (EditText) v.findViewById(R.id.edFB);
		btnSendFB.setOnClickListener(this);

	}	

	@Override
	protected void initAnimations() {
		
	}
	
	public static final long serialVersionUID = 6036846677812555352L;
	
	public static CoreActivity mActivity;
	public static FeedbackFragment mInstance;
	public static FeedbackFragment getInstance(CoreActivity activity) {
		if (mInstance == null) {
			mInstance = new FeedbackFragment();
		}
		mActivity = activity;
		return mInstance;
	}
	public static FeedbackFragment getInstance() {
		if (mInstance == null) {
			mInstance = new FeedbackFragment();
		}
		return mInstance;
	}
	@Override
	protected void initListener() {
		// TODO Auto-generated method stub
		
	}
	
public void api_send_feedback(String title, String content){
	 	
		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", Store.user.getAccessToken());
		params.put("name", Store.user.getUsername());
		params.put("email", Store.user.getEmail());
		params.put("title", title);
		params.put("content", content);
			RequestQueue requestQueue =  MonApplication.getInstance().getRequestQueue();
			HandleRequest jsObjRequest = new HandleRequest(Method.POST,
					HandleRequest.SEND_FEEDBACK, params, 
					new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						try {
							if (Integer.parseInt(response.getString("error"))==2){
								DialogMessage dialog = new DialogMessage(mActivity,mActivity.getResources().getString(R.string.end_session));
								dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
								dialog.show();
								SharedPreferences pre=getmContext().getSharedPreferences("buy_pus", 0);
								SharedPreferences.Editor editor=pre.edit();
								//editor.clear();
								editor.putBoolean("immediate_login", false);
								editor.commit();
								Intent loginActivity = new Intent(mActivity,LoginActivity.class);
							    startActivity(loginActivity);
							    mActivity.finish();

							}else
							if (Integer.parseInt(response.getString("error"))==1){
								DialogMessage dialog = new DialogMessage(mActivity,response.getString("message"));
								dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
								dialog.show();
							}else{
								DialogMessage dialog = new DialogMessage(mActivity,"Góp ý của bạn đã được gửi đến buy plus, xin cảm ơn!");
								dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
								dialog.show();
								Store.isConnectNetwotk = true;
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
								DialogMessage dialog = new DialogMessage(mActivity,mActivity.getResources().getString(R.string.connect_problem));
								dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
								dialog.show();
							}
						}
					});
			requestQueue.add(jsObjRequest);
	}
}