/**
 * Copyright 2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package lc.buyplus.gcm;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.provider.Settings.Secure;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import lc.buyplus.R;
import lc.buyplus.activities.LoginActivity;
import lc.buyplus.application.MonApplication;
import lc.buyplus.cores.HandleRequest;
import lc.buyplus.customizes.DialogMessage;
import lc.buyplus.fragments.CanvasFragment;
import lc.buyplus.fragments.LoginFragment;
import lc.buyplus.models.Store;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class RegistrationIntentService extends IntentService {

    private static final String TAG = "RegIntentService";
    private static final String[] TOPICS = {"global"};

    public RegistrationIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        try {
            // [START register_for_gcm]
            // Initially this call goes out to the network to retrieve the token, subsequent calls
            // are local.
            // R.string.gcm_defaultSenderId (the Sender ID) is typically derived from google-services.json.
            // See https://developers.google.com/cloud-messaging/android/start for details on this file.
            // [START get_token]
            InstanceID instanceID = InstanceID.getInstance(this);
            String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            // [END get_token]
            Log.i(TAG, "GCM Registration Token: " + token);
            
            // TODO: Implement this method to send any registration to your app's servers.
            sendRegistrationToServer(token);

            // Subscribe to topic channels
            subscribeTopics(token);

            // You should store a boolean that indicates whether the generated token has been
            // sent to your server. If the boolean is false, send the token to your server,
            // otherwise your server should have already received the token.
            sharedPreferences.edit().putBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, true).apply();
            // [END register_for_gcm]
        } catch (Exception e) {
            Log.d(TAG, "Failed to complete token refresh", e);
            // If an exception happens while fetching the new token or updating our registration data
            // on a third-party server, this ensures that we'll attempt the update at a later time.
            sharedPreferences.edit().putBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false).apply();
        }
        // Notify UI that registration has completed, so the progress indicator can be hidden.
        Intent registrationComplete = new Intent(QuickstartPreferences.REGISTRATION_COMPLETE);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    /**
     * Persist registration to third-party servers.
     *
     * Modify this method to associate the user's GCM registration token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
    	//TelephonyManager tManager = (TelephonyManager)CanvasFragment.mActivity.getSystemService(Context.TELEPHONY_SERVICE );
    	String uuid = Secure.getString(this.getContentResolver(),Secure.ANDROID_ID);
        api_register_device_token(token,token,"android",uuid);
    }

    /**
     * Subscribe to any GCM topics of interest, as defined by the TOPICS constant.
     *
     * @param token GCM token
     * @throws IOException if unable to reach the GCM PubSub service
     */
    // [START subscribe_topics]
    private void subscribeTopics(String token) throws IOException {
        GcmPubSub pubSub = GcmPubSub.getInstance(this);
        for (String topic : TOPICS) {
            pubSub.subscribe(token, "/topics/" + topic, null);
        }
    }
    // [END subscribe_topics]
    
public void api_register_device_token(String device_token, String gcm_device_token, String type, String uuid){
	 	
    	Map<String, String> params = new HashMap<String, String>();
    	params.put("access_token", CanvasFragment.mUser.getAccessToken());
		
		params.put("device_token", device_token);
		params.put("gcm_device_token", gcm_device_token);
		params.put("type", type);
		params.put("uuid", uuid);
		Log.i(TAG, "OK!: " + device_token);
		Log.i(TAG, "OK2: " + CanvasFragment.mUser.getAccessToken());
		Log.i(TAG, "OK3: " + type);
		Log.i(TAG, "OK4: " + uuid);
			RequestQueue requestQueue = MonApplication.getInstance().getRequestQueue();
			HandleRequest jsObjRequest = new HandleRequest(Method.POST,
					HandleRequest.REGISTER_DIVICE_TOKEN, params, 
					new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.d(TAG, "RE" + response.toString());
						try {
							if (Integer.parseInt(response.getString("error"))==2){
                                DialogMessage dialog = new DialogMessage(CanvasFragment.mActivity,CanvasFragment.mActivity.getResources().getString(R.string.end_session));

                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                dialog.show();
                                SharedPreferences pre=getSharedPreferences("buy_pus", 0);
                                SharedPreferences.Editor editor=pre.edit();
                                //editor.clear();
                                editor.putBoolean("immediate_login", false);
                                editor.commit();
                                Intent loginActivity = new Intent(CanvasFragment.mActivity,LoginActivity.class);
                                CanvasFragment.mActivity.startActivity(loginActivity);
                                CanvasFragment.mActivity.finish();

                            }else
                            if (Integer.parseInt(response.getString("error"))==1){
                                DialogMessage dialog = new DialogMessage(CanvasFragment.mActivity,response.getString("message"));
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                dialog.show();
                            }else{           
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
							if (!CanvasFragment.mActivity.isFinishing()) {
								if (Store.isConnectNetwotk == true) {
	                                Store.isConnectNetwotk = false;
	                                DialogMessage dialog = new DialogMessage(CanvasFragment.mActivity,CanvasFragment.mActivity.getResources().getString(R.string.connect_problem));
	                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
	                                dialog.show();
	                            }
							}
						}
					});
			Log.d(TAG, "RE" + "G1");
			requestQueue.add(jsObjRequest);
			
	}
}
