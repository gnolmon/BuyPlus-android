package lc.buyplus.activities;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import lc.buyplus.R;
import lc.buyplus.cores.CoreActivity;
import lc.buyplus.fragments.SplashFragment;
import lc.buyplus.gcm.*;

public class MainActivity extends CoreActivity {
	
	private static final long serialVersionUID = 250212518535755420L;
//	private ImageView image;
	

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "MainActivity";

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private TextView mInformationTextView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_demo);
		initViews();
		
		initListeners();
		initAnimations();
		FragmentManager mFragmentManager = getSupportFragmentManager();
		mFragmentManager.beginTransaction().add(R.id.canvas, SplashFragment.getInstance(MainActivity.this)).commit();
		initModels();
	}
		
//		makeJsonArrayRequest(NetworkConfig.URL_JSON_ARRAY_REQUEST, new JSONArrayRequestListener() {
//			@Override
//			public void onBefore() {
//				showProgressDialog("Loading...");
//			}
//			@Override
//			public void onResponse(JSONArray response) {
//				showToastLong(response.toString());
//				removePreviousDialog();
//			}
//			@Override
//			public void onError(VolleyError error) {
//				showToastLong(error.getMessage());
//				removePreviousDialog();
//			}
//		});
//		
//		makeJsonObjectRequest(NetworkConfig.URL_JSON_OBJECT_REQUREST, new JSONObjectRequestListener() {
//			@Override
//			public void onBefore() {
//				showProgressDialog("Loading...");
//			}
//			@Override
//			public void onResponse(JSONObject response) {
//				showToastLong(response.toString());
//				removePreviousDialog();
//			}
//			@Override
//			public void onError(VolleyError error) {
//				showToastLong(error.getMessage());
//				removePreviousDialog();
//			}
//		});
//		
//		makeStringRequest(NetworkConfig.URL_STRING_REQUEST, new StringRequestListener() {
//			@Override
//			public void onBefore() {
//				showProgressDialog("Loading...");
//			}
//			@Override
//			public void onResponse(String response) {
//				showToastLong(response.toString());
//				removePreviousDialog();
//			}
//			@Override
//			public void onErrorResponse(VolleyError error) {
//				showToastLong(error.getMessage());
//				removePreviousDialog();
//			}
//		});
//		
//		makeImageRequest(NetworkConfig.URL_IMAGE_REQUEST, new ImageRequestListener() {
//			@Override
//			public void onBefore() {
//				showProgressDialog("Loading...");
//			}
//			@Override
//			public void onResponse(ImageContainer paramImageContainer,
//					boolean paramBoolean) {
//				image.setImageBitmap(paramImageContainer.getBitmap());
//				removePreviousDialog();
//			}
//			
//			@Override
//			public void onErrorResponse(VolleyError error) {
//				showToastLong(error.getMessage());
//				removePreviousDialog();
//			}
//		});
	@Override
	public void initViews() {
//		image = (ImageView) findViewById(R.id.image);
	}

	@Override
	public void initModels() {
		mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(context);
                boolean sentToken = sharedPreferences
                        .getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
                if (sentToken) {
                    //mInformationTextView.setText(getString(R.string.gcm_send_message));
                } else {
                    //mInformationTextView.setText(getString(R.string.token_error_message));
                }
            }
        };
        //mInformationTextView = (TextView) findViewById(R.id.informationTextView);

        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }
	}

	@Override
	public void initListeners() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initAnimations() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.canvas);
	    fragment.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	public void onBackPressed() {
		moveTaskToBack(true);
	}

	
	@Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }
}
