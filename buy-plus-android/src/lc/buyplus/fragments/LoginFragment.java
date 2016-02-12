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
import lc.buyplus.activities.MainActivity;
import lc.buyplus.activities.ShopInfoActivity;
import lc.buyplus.adapter.StoreAdapter;
import lc.buyplus.application.MonApplication;
import lc.buyplus.cores.CoreActivity;
import lc.buyplus.cores.CoreFragment;
import lc.buyplus.cores.HandleRequest;
import lc.buyplus.customizes.DialogMessage;
import lc.buyplus.customizes.DialogMessage;
import lc.buyplus.customizes.MyTextView;
import lc.buyplus.models.FacebookFriend;
import lc.buyplus.models.Shop;
import lc.buyplus.models.Store;
import lc.buyplus.models.UserAccount;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
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
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class LoginFragment extends CoreFragment {

	private EditText editName;
	private EditText editPass;
	private Button loginbtn;
	private MyTextView registerbtn;
	private MyTextView ForgetPassbtn;
	private LoginButton loginButton;
	private CallbackManager callbackManager;
	String user_id;
	String user_name;
	String email;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		FacebookSdk.sdkInitialize(mActivity.getApplicationContext());
		callbackManager = CallbackManager.Factory.create();
		View view = inflater.inflate(R.layout.fragment_login, container, false);
		initViews(view);
		initModels();
		initAnimations();

		loginButton = (LoginButton) view.findViewById(R.id.login_button);
		loginButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_friends"));
		loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

			@Override
			public void onSuccess(LoginResult result) {

				GraphRequest request = GraphRequest.newMeRequest(result.getAccessToken(),
						new GraphRequest.GraphJSONObjectCallback() {
					@Override
					public void onCompleted(JSONObject object, GraphResponse response) {
						// Application code

						try {
							user_id = object.getString("id");
							user_name = object.getString("name");
							email = object.getString("email");
							Log.d("FB", "Value: " + AccessToken.getCurrentAccessToken().getToken() + "-" + user_id
									+ user_name + email);
							api_user_login_facebook(AccessToken.getCurrentAccessToken().getToken(), email, "",
									user_name, user_id);

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
		switch (view.getId()) {

		}
	}

	protected void initModels() {

	}

	@Override
	protected void initViews(View v) {
		editName = (EditText) v.findViewById(R.id.edNameLogin);
		editPass = (EditText) v.findViewById(R.id.edPass);
		loginbtn = (Button) v.findViewById(R.id.btnLogin);
		registerbtn = (MyTextView) v.findViewById(R.id.btnRegister);
		ForgetPassbtn = (MyTextView) v.findViewById(R.id.btnForgetPass);
		SharedPreferences pre = getmContext().getSharedPreferences("buy_pus", 0);
		String login_name_edit = pre.getString("login_name", "");
		String password = pre.getString("password", "");
		editName.requestFocus();
		editName.setText(login_name_edit);
		editPass.setText(password);
		boolean immediate_login = pre.getBoolean("immediate_login", false);
		if (immediate_login == true) {
			String accessToken = pre.getString("accessToken", "");
			String phone = pre.getString("phone", "");
			String email = pre.getString("email", "");
			String login_name = pre.getString("login_name", "");
			String imageUrl = pre.getString("imageUrl", "");
			String imageThumbnail = pre.getString("imageThumbnail", "");
			String username = pre.getString("username", "");
			int id = pre.getInt("id", 0);
			int active = pre.getInt("active", 0);
			Store.user = new UserAccount(accessToken, id, phone, email, login_name, imageUrl, imageThumbnail, username,
					active);
			Intent mainActivity = new Intent(mActivity, MainActivity.class);
			startActivity(mainActivity);
			mActivity.finish();
		}

		if (AccessToken.getCurrentAccessToken() != null) {

			SharedPreferences pre_fb = getmContext().getSharedPreferences("buy_pus_fb", 0);
			String accessToken = pre_fb.getString("accessToken", "");
			String phone = pre_fb.getString("phone", "");
			String email = pre_fb.getString("email", "");
			String login_name = pre_fb.getString("login_name", "");
			String imageUrl = pre_fb.getString("imageUrl", "");
			String imageThumbnail = pre_fb.getString("imageThumbnail", "");
			String username = pre_fb.getString("username", "");

			int id = pre_fb.getInt("id", 0);
			int active = pre_fb.getInt("active", 0);
			Log.d("OUT", accessToken + "--" + phone + "--" + email + "--" + login_name + "--" + imageUrl + "--"
					+ imageThumbnail + "--" + user_name + "--" + id + "--" + active);
			Store.user = new UserAccount(accessToken, id, phone, email, login_name, imageUrl, imageThumbnail, username,
					active);
			Intent mainActivity = new Intent(mActivity, MainActivity.class);
			startActivity(mainActivity);
			mActivity.finish();
		}

		loginbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				loginbtn.setEnabled(false);
				String name = editName.getText().toString();
				String password = editPass.getText().toString();
				api_user_login(name, md5(password));
			}
		});

		registerbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				mFragmentManager.beginTransaction().replace(R.id.canvas, RegisterFragment.getInstance(mActivity))
						.commit();
			}
		});
		ForgetPassbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent returnIntent;
				Uri uri;
				uri = Uri.parse("http://buyplus.vn/site/forgot_password");
				returnIntent = new Intent(Intent.ACTION_VIEW, uri);
				try {
					startActivity(returnIntent);
				} catch (ActivityNotFoundException e) {
					DialogMessage dialog = new DialogMessage(mActivity,
							mActivity.getResources().getString(R.string.connect_problem));
					dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
					dialog.show();
				}
			}
		});
	}

	@Override
	protected void initAnimations() {

	}

	public String md5(final String s) {
		try {
			// Create MD5 Hash
			MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
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

	public void api_user_login(String name, String password) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("login_name", name);
		params.put("password", password);
		RequestQueue requestQueue = MonApplication.getInstance().getRequestQueue();
		HandleRequest jsObjRequest = new HandleRequest(Method.POST, HandleRequest.USER_LOGIN, params,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						try {

							if (Integer.parseInt(response.getString("error")) == 1) {
								loginbtn.setEnabled(true);
								DialogMessage dialog = new DialogMessage(mActivity, response.getString("message"));
								dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
								dialog.show();
							} else {
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

								SharedPreferences pre = getmContext().getSharedPreferences("buy_pus", 0);
								SharedPreferences.Editor editor = pre.edit();
								editor.remove("buy_pus_fb");
								editor.clear();
								editor.putString("login_name_edit", editName.getText().toString());
								editor.putString("password", editPass.getText().toString());
								editor.putString("accessToken", accessToken);
								editor.putString("phone", phone);
								editor.putString("email", email);
								editor.putString("login_name", login_name);
								editor.putString("imageUrl", imageUrl);
								editor.putString("imageThumbnail", imageThumbnail);
								editor.putString("username", username);
								editor.putInt("id", id);
								editor.putInt("active", active);
								editor.putBoolean("immediate_login", true);
								editor.commit();
								Store.user = new UserAccount(accessToken, id, phone, email, login_name, imageUrl,
										imageThumbnail, username, active);
								Intent mainActivity = new Intent(mActivity, MainActivity.class);
								startActivity(mainActivity);
								mActivity.finish();
							}
							loginbtn.setEnabled(true);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						loginbtn.setEnabled(true);
						DialogMessage dialog = new DialogMessage(mActivity,
								mActivity.getResources().getString(R.string.connect_problem));
						dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
						dialog.show();
					}
				});
		requestQueue.add(jsObjRequest);
	}

	public void api_user_login_facebook(String facebook_access_token, String email, String phone, String name,
			String facebook_id) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("facebook_access_token", facebook_access_token);
		params.put("email", email);
		params.put("name", name);
		params.put("facebook_id", facebook_id);
		Log.d("FB", "GO here:1");
		RequestQueue requestQueue = MonApplication.getInstance().getRequestQueue();
		HandleRequest jsObjRequest = new HandleRequest(Method.POST, HandleRequest.LOGIN_FACEBOOK, params,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						try {
							Log.d("api_user_login_facebook", response.toString());
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
							Log.d("FB", "GO here:");

							SharedPreferences pre = getmContext().getSharedPreferences("buy_pus_fb", 0);
							SharedPreferences.Editor editor = pre.edit();
							editor.remove("buy_pus");
							editor.clear();
							editor.putString("accessToken", accessToken);
							editor.putString("phone", phone);
							editor.putString("email", email);
							editor.putString("login_name", login_name);
							editor.putString("imageUrl", imageUrl);
							editor.putString("imageThumbnail", imageThumbnail);
							editor.putString("username", username);
							editor.putInt("id", id);
							editor.putInt("active", active);
							editor.putBoolean("fb_login", true);
							Log.d("OUT", accessToken + "--" + phone + "--" + email + "--" + login_name + "--" + imageUrl + "--"
									+ imageThumbnail + "--" + user_name + "--" + id + "--" + active);
							editor.commit();

							Store.user = new UserAccount(accessToken, id, phone, email, login_name, imageUrl,
									imageThumbnail, username, active);
							Intent mainActivity = new Intent(mActivity, MainActivity.class);
							startActivity(mainActivity);
							mActivity.finish();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.d("api_user_login_facebook", error.toString());
						DialogMessage dialog = new DialogMessage(mActivity, error.toString());
						dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
						dialog.show();
						LoginManager.getInstance().logOut();
					}
				});
		requestQueue.add(jsObjRequest);
	}

	public void api_logout() {

		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", CanvasFragment.mUser.getAccessToken());
		RequestQueue requestQueue = MonApplication.getInstance().getRequestQueue();
		HandleRequest jsObjRequest = new HandleRequest(Method.POST, HandleRequest.LOGOUT, params,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.d("api_logout", response.toString());
						// code here
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
					}
				});
		requestQueue.add(jsObjRequest);
	}

	public void api_authenticate_forget_password() {

		Map<String, String> params = new HashMap<String, String>();
		RequestQueue requestQueue = MonApplication.getInstance().getRequestQueue();
		HandleRequest jsObjRequest = new HandleRequest(Method.POST, HandleRequest.AUTHENTICATE_FORGET_PASSWORD, params,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.d("api_authenticate_forget_password", response.toString());
						// code here
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
					}
				});
		requestQueue.add(jsObjRequest);
	}

	public void api_forget_password(String token, String email, String password) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("token", token);
		params.put("email", email);
		params.put("password", password);
		RequestQueue requestQueue = MonApplication.getInstance().getRequestQueue();
		HandleRequest jsObjRequest = new HandleRequest(Method.POST, HandleRequest.FORGET_PASSWORD, params,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.d("api_authenticate_forget_password", response.toString());
						// code here
					}
				}, new Response.ErrorListener() {
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
