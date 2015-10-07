package lc.buyplus.fragments;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import lc.buyplus.R;
import lc.buyplus.activities.MainActivity;
import lc.buyplus.application.DarkmoonApplication;
import lc.buyplus.cores.CoreActivity;
import lc.buyplus.cores.CoreFragment;
import lc.buyplus.cores.HandleRequest;
import lc.buyplus.customizes.ProgressDialog;
import lc.buyplus.interfaces.JSONObjectRequestListener;
import lc.buyplus.models.UserAccount;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class LoginFragment extends CoreFragment {
	
	private TextView info;
	private LoginButton loginButton;
	private CallbackManager callbackManager;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		FacebookSdk.sdkInitialize(mActivity.getApplicationContext());
		callbackManager = CallbackManager.Factory.create();
		View view = inflater.inflate(R.layout.fragment_login, container, false);
		initViews(view);
		initModels();
		initAnimations();
		info = (TextView)view.findViewById(R.id.info);
		loginButton = (LoginButton)view.findViewById(R.id.login_button);
		api_user_login( "long@gmail.com","f5bb0c8de146c67b44babbf4e6584cc0");
		loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
			
			@Override
			public void onSuccess(LoginResult result) {
				
				GraphRequest request = GraphRequest.newMeRequest(
                result.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        // Application code
                        response.getError();
                        Log.e("JSON:", object.toString());

                        try {
                             String user_id = object.getString("id");
                             String user_name = object.getString("name");
                             String user_profilepic_url = object.getString("link");
                             
                             
                             //api_user_login_facebook( "long@gmail.com","f5bb0c8de146c67b44babbf4e6584cc0");            		
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,link");
                request.setParameters(parameters);
                request.executeAsync();
			}
			
			@Override
			public void onError(FacebookException error) {
				info.setText("Login attempt canceled.");;
			}
			
			@Override
			public void onCancel() {
				info.setText("Login attempt failed.");
			}
		});	
		return view;
	}	
		
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    callbackManager.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	public void onClick(View view) {
		switch(view.getId()) {

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
	


	public void api_user_login(String name, String password){
	 	
    	Map<String, String> params = new HashMap<String, String>();
		params.put("login_name", name);
		params.put("password", password);
			RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
			HandleRequest jsObjRequest = new HandleRequest(
					HandleRequest.USER_LOGIN, params, 
					new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						try {
							JSONObject data = response.getJSONObject("data");
							String accessToken = data.getString("access_token");
							int id = Integer.parseInt(data.getString("id"));
							String phone = data.getString("phone");
							String email = data.getString("email");
							String login_name = data.getString("login_name");
							String imageUrl = data.getString("image");
							String imageThumbnail = data.getString("image_thumbnail");
							String username = data.getString("name");
							int active = Integer.parseInt(data.getString("active"));
							
							UserAccount user = new UserAccount(accessToken,id,phone,email,login_name,imageUrl,imageThumbnail,username,active);
							mFragmentManager.beginTransaction().replace(R.id.canvas, CanvasFragment.getInstance(mActivity, user)).commit();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	
					}
				}, 
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
					}
				});
			requestQueue.add(jsObjRequest);
	}
	
	public void api_user_login_facebook(String facebook_access_token, String email, String phone, String name, String facebook_id){
	 	
    	Map<String, String> params = new HashMap<String, String>();
		params.put("facebook_access_token", facebook_access_token);
		params.put("email", email);
		params.put("phone", phone);
		params.put("name", name);
		params.put("facebook_id", facebook_id);
			RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
			HandleRequest jsObjRequest = new HandleRequest(
					HandleRequest.LOGIN_FACEBOOK, params, 
					new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						try {
							JSONObject data = response.getJSONObject("data");
							String accessToken = data.getString("access_token");
							int id = Integer.parseInt(data.getString("id"));
							String phone = data.getString("phone");
							String email = data.getString("email");
							String login_name = data.getString("login_name");
							String imageUrl = data.getString("image");
							String imageThumbnail = data.getString("image_thumbnail");
							String username = data.getString("name");
							int active = Integer.parseInt(data.getString("active"));
							
							
							UserAccount user = new UserAccount(accessToken,id,phone,email,login_name,imageUrl,imageThumbnail,username,active);
							mFragmentManager.beginTransaction().replace(R.id.canvas, CanvasFragment.getInstance(mActivity, user)).commit();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	
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
	public static LoginFragment mInstance;
	public static LoginFragment getInstance(CoreActivity activity) {
		if (mInstance == null) {
			mInstance = new LoginFragment();
		}
		mActivity = activity;		
		return mInstance;
	}
	public static LoginFragment getInstance() {
		if (mInstance == null) {
			mInstance = new LoginFragment();
		}
		return mInstance;
	}
	@Override
	protected void initListener() {
		// TODO Auto-generated method stub
		
	}
}
