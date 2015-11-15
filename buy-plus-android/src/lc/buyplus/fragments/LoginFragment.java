package lc.buyplus.fragments;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import lc.buyplus.R;
import lc.buyplus.activities.ShopInfoActivity;
import lc.buyplus.adapter.StoreAdapter;
import lc.buyplus.cores.CoreActivity;
import lc.buyplus.cores.CoreFragment;
import lc.buyplus.cores.HandleRequest;
import lc.buyplus.customizes.MyTextView;
import lc.buyplus.models.FacebookFriend;
import lc.buyplus.models.Shop;
import lc.buyplus.models.Store;
import lc.buyplus.models.UserAccount;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class LoginFragment extends CoreFragment {
	
	private EditText editName;
	private EditText editPass;
	private Button loginbtn;
	private MyTextView registerbtn;
	private LoginButton loginButton;
	private CallbackManager callbackManager;
	private String faceBookAccessToken;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		FacebookSdk.sdkInitialize(mActivity.getApplicationContext());
		callbackManager = CallbackManager.Factory.create();
		View view = inflater.inflate(R.layout.fragment_login, container, false);
		initViews(view);
		initModels();
		initAnimations();
		
		loginButton = (LoginButton)view.findViewById(R.id.login_button);
		loginButton.setReadPermissions(Arrays.asList("public_profile","email", "user_friends"));
		loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
			
			@Override
			public void onSuccess(LoginResult result) {
				faceBookAccessToken = result.getAccessToken().toString();
				
				GraphRequest request = GraphRequest.newMeRequest(
                result.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        // Application code
                    	 
                        response.getError();
                        //Log.e("JSON:", object.toString());
                        Log.d("accessToken", faceBookAccessToken);
                        try {
                             String user_id = object.getString("id");
                             String user_name = object.getString("name");
                             String email = object.getString("email");
                             
                             api_user_login_facebook(faceBookAccessToken, email, "", user_name, user_id);
                             new GraphRequest(
                            		    AccessToken.getCurrentAccessToken(),
                            		    "/"+user_id+"/taggable_friends",
                            		    null,
                            		    HttpMethod.GET,
                            		    new GraphRequest.Callback() {
                            		        public void onCompleted(GraphResponse response) {
                            		        	try {
                            		        		Log.e("JSON:", response.toString());
                            		        		JSONObject jobj = new JSONObject(response.getRawResponse());
                        							JSONArray data_aray = jobj.getJSONArray("data");
                        							for (int i = 0; i < data_aray.length(); i++) {
                        								
                        								FacebookFriend facebookFriend = new FacebookFriend((JSONObject) data_aray.get(i));
                        	                            	if (facebookFriend != null){
                        	                            		Store.FacebookFriendsList.add(facebookFriend);
                        	                            	}
                        	                        }
      
                        							
                        						} catch (JSONException e) {
                        							e.printStackTrace();
                        						}	
                            		        }
                            		    }
                            		).executeAsync();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email");
                request.setParameters(parameters);
                request.executeAsync();
                
                
			}
			
			@Override
			public void onError(FacebookException error) {
			}
			
			@Override
			public void onCancel() {
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
		editName = (EditText)v.findViewById(R.id.edNameLogin);
		editPass = (EditText)v.findViewById(R.id.edPass);
		loginbtn = (Button)v.findViewById(R.id.btnLogin);
		registerbtn = (MyTextView)v.findViewById(R.id.btnRegister);
		
		SharedPreferences pre=getmContext().getSharedPreferences("buy_pus", 0);
		String login_name = pre.getString("login_name", "");
		String password = pre.getString("password", "");
		editName.setText(login_name);
		editPass.setText(password);
		loginbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String name = editName.getText().toString();
				String password = editPass.getText().toString();
				api_user_login( name,md5(password));
			}
		});

		registerbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				mFragmentManager.beginTransaction().replace(R.id.canvas, RegisterFragment.getInstance(mActivity)).commit();
			}
		});
	}

	@Override
	protected void initAnimations() {
		
	}
	
	public String md5(final String s) {
	    try {
	        // Create MD5 Hash
	        MessageDigest digest = java.security.MessageDigest
	                .getInstance("MD5");
	        digest.update(s.getBytes());
	        byte messageDigest[] = digest.digest();
	 
	        // Create Hex String
	        StringBuffer hexString = new StringBuffer();
	        for (int i = 0; i < messageDigest.length; i++) {
	            String h = Integer.toHexString(0xFF & messageDigest[i]);
	            while (h.length() < 2)
	                h = "0" + h;
	            hexString.append(h);
	        }
	        return hexString.toString();
	 
	    } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    }
	    return "";
	}
	
	public void api_user_login(String name, String password){
	 	
    	Map<String, String> params = new HashMap<String, String>();
		params.put("login_name", name);
		params.put("password", password);
			RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
			HandleRequest jsObjRequest = new HandleRequest(Method.POST,
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
							
							
							SharedPreferences pre=getmContext().getSharedPreferences("buy_pus", 0);
							SharedPreferences.Editor editor=pre.edit();
							editor.clear();
							editor.putString("login_name", editName.getText().toString());
							editor.putString("password", editPass.getText().toString());
							editor.commit();
							Store.user = new UserAccount(accessToken,id,phone,email,login_name,imageUrl,imageThumbnail,username,active);;
							mFragmentManager.beginTransaction().replace(R.id.canvas, CanvasFragment.getInstance(mActivity, Store.user)).commit();
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
		//params.put("phone", phone);
		params.put("name", name);
		params.put("facebook_id", facebook_id);
			RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
			HandleRequest jsObjRequest = new HandleRequest(Method.POST,
					HandleRequest.LOGIN_FACEBOOK, params, 
					new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						try {
							Log.d("api_user_login_facebook",response.toString());
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
							
							
							Store.user = new UserAccount(accessToken,id,phone,email,login_name,imageUrl,imageThumbnail,username,active);
							mFragmentManager.beginTransaction().replace(R.id.canvas, CanvasFragment.getInstance(mActivity, Store.user)).commit();
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
	
	public void api_logout(){
	 	
    	Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", CanvasFragment.mUser.getAccessToken());
			RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
			HandleRequest jsObjRequest = new HandleRequest(Method.POST,
					HandleRequest.LOGOUT, params, 
					new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.d("api_logout",response.toString());
						// code here
					}
					}, 
					new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
						}
					});
			requestQueue.add(jsObjRequest);
	}
	
	public void api_authenticate_forget_password(){
	 	
    	Map<String, String> params = new HashMap<String, String>();
			RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
			HandleRequest jsObjRequest = new HandleRequest(Method.POST,
					HandleRequest.AUTHENTICATE_FORGET_PASSWORD, params, 
					new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.d("api_authenticate_forget_password",response.toString());
						// code here
					}
					}, 
					new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
						}
					});
			requestQueue.add(jsObjRequest);
	}

	public void api_forget_password(String token, String email, String password){
	 	
    	Map<String, String> params = new HashMap<String, String>();
    	params.put("token", token);
    	params.put("email", email);
    	params.put("password", password);
			RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
			HandleRequest jsObjRequest = new HandleRequest(Method.POST,
					HandleRequest.FORGET_PASSWORD, params, 
					new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.d("api_authenticate_forget_password",response.toString());
						// code here
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
