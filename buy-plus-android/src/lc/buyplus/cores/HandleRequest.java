package lc.buyplus.cores;

import java.util.HashMap;
import java.util.Map;
import java.io.UnsupportedEncodingException;   

import org.json.JSONException;
import org.json.JSONObject;    

import android.app.Activity;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.Volley;

import lc.buyplus.application.DarkmoonApplication;

public class HandleRequest extends Request<JSONObject>{
	
	public static final String USER_REGISTER = "http://buyplus.vn/api/register/";
    public static final String LOGIN_FACEBOOK = "http://buyplus.vn/api/login_facebook/";
    public static final String USER_LOGIN = "http://buyplus.vn/api/login/";
    public static final String LOGOUT = "http://buyplus.vn/api/logout/";
    public static final String AUTHENTICATE_FORGET_PASSWORD = "http://buyplus.vn/api/authenticate_forget_pass/";
    public static final String FORGET_PASSWORD = "http://buyplus.vn/api/forget_password/";
    public static final String GET_ALL_SHOP = "http://buyplus.vn/api/get_all_shops/";
    public static final String GET_MY_SHOP = "http://buyplus.vn/api/get_my_shops/";
    public static final String GET_SHOP_INFO = "http://buyplus.vn/api/get_shop_info/";
    public static final String GET_ALL_ANNOUNCEMENTS = "http://buyplus.vn/api/get_all_announcements/";
    public static final String GET_SHOP_ANNOUNCEMENTS = "http://buyplus.vn/api/get_shop_announcements/";
    public static final String GET_SHOP_GIFTS = "http://buyplus.vn/api/get_shop_gifts/";
    public static final String GET_SHOP_FRIENDS = "http://buyplus.vn/api/get_shop_friends/";
    public static final String GET_NOTIFICATIONS = "http://buyplus.vn/api/get_notifications/";
    public static final String READ_NOTIFICATIONS = "http://buyplus.vn/api/read_notifications/";
    public static final String SEND_REQUEST_JOIN_SHOP_TO_FRIEND = "http://buyplus.vn/api/send_request_join_shop_to_friend/";
    public static final String RESPONSE_REQUEST__JOIN_SHOP = "http://buyplus.vn/api/response_request_join_shop/";
    public static final String JOIN_SHOP = "http://buyplus.vn/api/join_shop/";
    public static final String LEAVE_SHOP = "http://buyplus.vn/api/leave_shop/";
    public static final String GET_ADDED_POINT_HISTORY = "http://buyplus.vn/api/get_added_point_history/";
    public static final String GET_ADDED_POINT_IN_SHOP = "API URL: http://buyplus.vn/api/get_added_point_in_shop/";
   
    private Listener<JSONObject> listener;
    private Map<String, String> params;
    
    public HandleRequest(int method, String url, Map<String, String> params,
            Listener<JSONObject> reponseListener, ErrorListener errorListener) {
        super(method, url, errorListener);
        this.listener = reponseListener;
        this.params = params;
    }

    protected Map<String, String> getParams()
            throws com.android.volley.AuthFailureError {
        return params;
    };

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new JSONObject(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

    @Override
    protected void deliverResponse(JSONObject response) {
        // TODO Auto-generated method stub
        listener.onResponse(response);
    }
    
    public static String build_link(String url, Map<String, String> params){
    	String builder = "";
		for (String key : params.keySet())
	    {
	        Object value = params.get(key);
	        if (value != null)
	        {
	        	 builder += "&";
	        	 builder = builder + key.toString() + "="+ value.toString();
	        }
	    }
		char[] tmp = builder.toCharArray();
		tmp[0] = '?';
		builder = url+String.valueOf(tmp);
		return builder;
    }
}
