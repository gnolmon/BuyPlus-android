package lc.buyplus.application;

import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;
import lc.buyplus.customizes.FontsOverride;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class MonApplication extends MultiDexApplication  {
	
	public static final String LOG_TAG = MonApplication.class.getSimpleName();
	
	private RequestQueue mRequestQueue;
	private ImageLoader mImageLoader;
	LruBitmapCache mLruBitmapCache;
	private static MonApplication mInstance;
	
//	@Override 
//	protected void attachBaseContext(Context base) {
//		  super.attachBaseContext(base);
//		  MultiDex.install(this);
//	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		FontsOverride.setDefaultFont(this, "DEFAULT", "fonts/helvetica.ttf");
		mInstance = this;
	}
	
	public static synchronized MonApplication getInstance(){
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
			getLruBitmapCache();
			mImageLoader = new ImageLoader(this.mRequestQueue, mLruBitmapCache);
		}
		return this.mImageLoader;
	}
	
	public LruBitmapCache getLruBitmapCache() {
		if (mLruBitmapCache == null)
			mLruBitmapCache = new LruBitmapCache();
		return this.mLruBitmapCache;
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
