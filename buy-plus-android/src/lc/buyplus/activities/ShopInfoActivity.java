package lc.buyplus.activities;

import lc.buyplus.R;
import lc.buyplus.cores.CoreActivity;
import lc.buyplus.fragments.CanvasFragment;
import lc.buyplus.fragments.ShopDetailCanvasFragment;
import lc.buyplus.fragments.SplashFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class ShopInfoActivity extends CoreActivity {
	
	private static final long serialVersionUID = 250212518535755420L;
//	private ImageView image;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_demo);
		initViews();
		initModels();
		initListeners();
		initAnimations();
		FragmentManager mFragmentManager = getSupportFragmentManager();
		mFragmentManager.beginTransaction().add(R.id.canvas, ShopDetailCanvasFragment.getInstance(ShopInfoActivity.this,CanvasFragment.mUser)).commit();
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
		// TODO Auto-generated method stub
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

}
