package lc.buyplus.fragments;

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
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import lc.buyplus.R;
import lc.buyplus.cores.CoreActivity;
import lc.buyplus.cores.CoreFragment;
import lc.buyplus.cores.HandleRequest;
import lc.buyplus.customizes.MyEditText;
import lc.buyplus.models.UserAccount;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterFragment extends CoreFragment implements OnClickListener {

	private Button btnRegister;
	private MyEditText edName, edPass, edCfPass, edEmail;

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

		btnRegister.setOnClickListener(this);

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
				Toast.makeText(this.getActivity(), "Please input username", 2000).show();
			} else if (email.isEmpty()) {
				Toast.makeText(this.getActivity(), "Please input email", 2000).show();
			} else if (password.isEmpty()) {
				Toast.makeText(this.getActivity(), "Please input password", 2000).show();
			} else if (cpassword.isEmpty()) {
				Toast.makeText(this.getActivity(), "Please confirm password", 2000).show();
			} else {
				api_user_register(email, cpassword);
				Toast.makeText(this.getActivity(), "Please check email to conplete your registation", 1000).show();
			}
			break;
		default:
			break;
		}
	}
	
	public void api_user_register(String login_name, String password){
	 	
    	Map<String, String> params = new HashMap<String, String>();
		params.put("login_name", login_name);
		params.put("password", password);
			RequestQueue requestQueue = Volley.newRequestQueue(this.getActivity());
			HandleRequest jsObjRequest = new HandleRequest(Method.POST,
					HandleRequest.USER_REGISTER, params, 
					new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.d("api_user_register",response.toString());
						mFragmentManager.beginTransaction().replace(R.id.canvas, LoginFragment.getInstance(mActivity)).commit();
					}
					}, 
					new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
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
