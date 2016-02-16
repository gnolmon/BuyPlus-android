package lc.buyplus.fragments;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.facebook.CallbackManager;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import lc.buyplus.R;
import lc.buyplus.application.MonApplication;
import lc.buyplus.cores.CoreActivity;
import lc.buyplus.cores.CoreFragment;
import lc.buyplus.cores.HandleRequest;
import lc.buyplus.customizes.DialogMessage;
import lc.buyplus.customizes.MyEditText;
import lc.buyplus.customizes.MyTextView;

public class RegisterFragment extends CoreFragment implements OnClickListener {

	private Button btnRegister;
	private MyEditText edName, edPass, edCfPass, edEmail;
	private MyTextView backBtn;
	private CallbackManager callbackManager;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		callbackManager = CallbackManager.Factory.create();
		View view = inflater.inflate(R.layout.fragment_register, container, false);
		initViews(view);
		initModels();
		initAnimations();
		btnRegister = (Button) view.findViewById(R.id.btnConfirm);
		
		edName = (MyEditText) view.findViewById(R.id.edNameRegister);
		edPass = (MyEditText) view.findViewById(R.id.edPassRegister);
		edCfPass = (MyEditText) view.findViewById(R.id.edcfPass);
		edEmail = (MyEditText) view.findViewById(R.id.edEmail);
		backBtn = (MyTextView) view.findViewById(R.id.btbBack);

		btnRegister.setOnClickListener(this);
		backBtn.setOnClickListener(this);;
		return view;
	}

	@Override
	protected void initModels() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initViews(View view) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initAnimations() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnConfirm:
			String username = edName.getText().toString();
			String password = edPass.getText().toString();
			String cpassword = edCfPass.getText().toString();
			String email = edEmail.getText().toString();
			if (username.isEmpty()) {
				DialogMessage dialog = new DialogMessage(mActivity,mActivity.getResources().getString(R.string.fill_name));
				dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
				dialog.show();
			} else if (email.isEmpty()) {
				DialogMessage dialog = new DialogMessage(mActivity,mActivity.getResources().getString(R.string.fill_email));
				dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
				dialog.show();
			} else if (password.isEmpty()) {
				DialogMessage dialog = new DialogMessage(mActivity,mActivity.getResources().getString(R.string.fill_pass));
				dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
				dialog.show();
			} else if (cpassword.isEmpty()) {
				DialogMessage dialog = new DialogMessage(mActivity,mActivity.getResources().getString(R.string.fill_pass_confirm));
				dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
				dialog.show();
			} else {
				api_user_register( email,username, cpassword);
			}
			break;
		case R.id.btbBack:
			mFragmentManager.beginTransaction().replace(R.id.canvas, LoginFragment.getInstance(mActivity)).commit();
			break;
		default:
			break;
		}
	}
	
	public void api_user_register(String name, String login_name, String password){
	 	
    	Map<String, String> params = new HashMap<String, String>();
		params.put("login_name", login_name);
		params.put("name", name);
		params.put("password", password);
			RequestQueue requestQueue = MonApplication.getInstance().getRequestQueue();
			HandleRequest jsObjRequest = new HandleRequest(Method.POST,
					HandleRequest.USER_REGISTER, params, 
					new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.d("api_user_register",response.toString());
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

					}
					}, 
					new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
							DialogMessage dialog = new DialogMessage(mActivity,"Kiểm tra lại kết nối của bạn");
							dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
							dialog.show();
						}
					});
			requestQueue.add(jsObjRequest);
	}
	
	public static final long serialVersionUID = 6036846677812555352L;
	
	public static CoreActivity mActivity;
	public static RegisterFragment mInstance;
	public static RegisterFragment getInstance(CoreActivity activity) {
		if (mInstance == null) {
			mInstance = new RegisterFragment();
		}
		mActivity = activity;		
		return mInstance;
	}
	public static RegisterFragment getInstance() {
		if (mInstance == null) {
			mInstance = new RegisterFragment();
		}
		return mInstance;
	}
	@Override
	protected void initListener() {
		// TODO Auto-generated method stub
	}
}
