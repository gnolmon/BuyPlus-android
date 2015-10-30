package lc.buyplus.application;

import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class DarkmoonApplication extends MultiDexApplication  {
	
	public static final String LOG_TAG = DarkmoonApplication.class.getSimpleName();
	
	private RequestQueue mRequestQueue;
	private ImageLoader mImageLoader;
	
	private static DarkmoonApplication mInstance;
	
//	@Override 
//	protected void attachBaseContext(Context base) {
//		  super.attachBaseContext(base);
//		  MultiDex.install(this);
//	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
	}
	
	public static synchronized DarkmoonApplication getInstance(){
		return mInstance;
	}
	
	public RequestQueue getRequestQueue(){
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		}
		return mRequestQueue;
	}
	
	public ImageLoader getImageLoader(){
		getRequestQueue();
		if (mImageLoader == null) {
			mImageLoader = new ImageLoader(this.mRequestQueue, new LruBitmapCache());
		}
		return this.mImageLoader;
	}
	
	public <T> void addToRequestQueue(Request<T> request, String tag){
		//Set the default tag if tag is empty
		request.setTag(TextUtils.isEmpty(tag) ? LOG_TAG : tag);
		getRequestQueue().add(request);
	}
	
	public <T> void addToRequestQueue(Request<T> request){
		request.setTag(LOG_TAG);
		getRequestQueue().add(request);
	}
	
	public void cancelPendingRequests(Object tag){
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(tag);
		}
	}
}