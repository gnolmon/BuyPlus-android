package lc.buyplus.fragments;

import org.json.JSONException;
import org.json.JSONObject;

import lc.buyplus.R;
import lc.buyplus.activities.MainActivity;
import lc.buyplus.cores.CoreActivity;
import lc.buyplus.cores.CoreFragment;
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
	private String user_id;
	private String user_name;
	private String user_profilepic_url;
	
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
                             user_id = object.getString("id");
                             user_name = object.getString("name");
                             user_profilepic_url = object.getString("link");
                             
                             
             				info.setText(
             		    		    "User ID: "
             		    		    + user_id
             		    		    + "\n" 
             		    		    + "name: "
             		    		    + user_name
             		    		    + "\n" 
             		    		    + "Link: "
             		    		    + user_profilepic_url
             		    		);
             		    		
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,link");
                request.setParameters(parameters);
                //Log.e(" About to Graph Call", " ");
                request.executeAsync();
                //Log.e(" Finished Graph Call", " ");
                
                //String token = result.getAccessToken().getToken();
                //UserAccount user = new UserAccount(token,1,"","","","","",true,user_name);
				//mFragmentManager.beginTransaction().replace(R.id.canvas, CanvasFragment.getInstance(mActivity, user)).commit();
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
		
		/*
		Button x = (Button) view.findViewById(R.id.button);
		x.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Call it when login success
				UserAccount user = new UserAccount(token,1,"","","","","",true,"long");
				mFragmentManager.beginTransaction().replace(R.id.canvas, CanvasFragment.getInstance(mActivity, user)).commit();
			}
		});
		*/
		
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
