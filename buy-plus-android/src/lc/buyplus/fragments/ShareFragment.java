package lc.buyplus.fragments;

import java.util.List;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import lc.buyplus.R;
import lc.buyplus.cores.CoreActivity;
import lc.buyplus.cores.CoreFragment;
import lc.buyplus.models.Store;

public class ShareFragment  extends CoreFragment {
	private LinearLayout mHomeTab, mPersonalTab, mLoyaltyCardTab, mNotiTab, mSettingTab, mBack;
	private TextView share_SMS,share_FB,share_GM,share_Link;
	ShareDialog shareDialog;
	private CallbackManager callbackManager;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_share, container, false);
		FacebookSdk.sdkInitialize(mActivity.getApplicationContext());
		callbackManager = CallbackManager.Factory.create();
		shareDialog = new ShareDialog(this);
	    shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
	        @Override
	        public void onSuccess(Sharer.Result result) {
	            Toast.makeText(getActivity(), "You shared this post", Toast.LENGTH_SHORT).show();
	        }

	        @Override
	        public void onCancel() {
	        }

	        @Override
	        public void onError(FacebookException e) {
	            e.printStackTrace();
	        }
	    });
		
		initViews(view);
		initModels();
		initAnimations();
		return view;
	}
	
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.fragment_canvas_back:
			mActivity.finish();
			break;
		case R.id.fragment_canvas_home_tab:
			CanvasFragment.mPager.setCurrentItem(0);
			mActivity.finish();
			break;
		case R.id.fragment_canvas_personal_tab:
			CanvasFragment.mPager.setCurrentItem(2);
			mActivity.finish();
			break;
		case R.id.fragment_canvas_loyaltycard_tab:
			CanvasFragment.mPager.setCurrentItem(3);
			mActivity.finish();
			break;
		case R.id.fragment_canvas_notifications_tab:
			CanvasFragment.mPager.setCurrentItem(4);
			mActivity.finish();
			break;
		case R.id.fragment_canvas_setting_tab:
			CanvasFragment.mPager.setCurrentItem(5);
			mActivity.finish();
			break;
		case R.id.shareSMS:
			openInbox();
			break;
		case R.id.shareGM:
			String[] TO = {""};
		      String[] CC = {""};
		      Intent emailIntent = new Intent(Intent.ACTION_SEND);
		      
		      emailIntent.setData(Uri.parse("mailto:"));
		      emailIntent.setType("text/plain");
		      emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
		      emailIntent.putExtra(Intent.EXTRA_CC, CC);
		      emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
		      emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");
		      
		      try {
		         startActivity(Intent.createChooser(emailIntent, "Send mail..."));
		         Log.i("Finished sending email...", "");
		      }
		      catch (android.content.ActivityNotFoundException ex) {
		         Toast.makeText(mActivity.getApplicationContext(), "There is no email client installed.", Toast.LENGTH_SHORT).show();
		      }
			break;
		case R.id.shareLink:
			ClipboardManager clipboardManager =(ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
			Uri copyUri = Uri.parse("https://play.google.com/store/apps/details?id=lc.buyplus&hl=en" + "/copy" + "/" + "Link download");
			ClipData clip = ClipData.newUri(mActivity.getContentResolver(),"URI",copyUri);
			clipboardManager.setPrimaryClip(clip);
			
			
		break;
		case R.id.shareFB:
			ShareLinkContent content = new ShareLinkContent.Builder()
	        .setContentUrl(Uri.parse("https://play.google.com/store/apps/details?id=lc.buyplus&hl=en"))
	        .build();
			if (ShareDialog.canShow(ShareLinkContent.class)) {

			    shareDialog.show(content);
			}
			break;
		default:
			break;
		}
	}
	
	protected void initModels() {
		
	}

	@Override
	protected void initViews(View v) {
		mHomeTab = (LinearLayout) v.findViewById(R.id.fragment_canvas_home_tab);
		mPersonalTab = (LinearLayout) v.findViewById(R.id.fragment_canvas_personal_tab);
		mLoyaltyCardTab = (LinearLayout) v.findViewById(R.id.fragment_canvas_loyaltycard_tab);
		mNotiTab = (LinearLayout) v.findViewById(R.id.fragment_canvas_notifications_tab);
		mSettingTab = (LinearLayout) v.findViewById(R.id.fragment_canvas_setting_tab);
		mBack = (LinearLayout) v.findViewById(R.id.fragment_canvas_back);
		mHomeTab.setOnClickListener(this);
		mPersonalTab.setOnClickListener(this);
		mLoyaltyCardTab.setOnClickListener(this);
		mNotiTab.setOnClickListener(this);
		mSettingTab.setOnClickListener(this);
		mBack.setOnClickListener(this);
		share_GM = (TextView) v.findViewById(R.id.shareGM);
		share_FB = (TextView) v.findViewById(R.id.shareFB);
		share_SMS = (TextView) v.findViewById(R.id.shareSMS);
		share_Link = (TextView) v.findViewById(R.id.shareLink);
		share_GM.setOnClickListener(this);
		share_FB.setOnClickListener(this);
		share_SMS.setOnClickListener(this);
		share_Link.setOnClickListener(this);
	}	

	@Override
	protected void initAnimations() {
		
	}
	
	public void openInbox() {
		String application_name = "com.android.mms";
		try {
		Intent intent = new Intent("android.intent.action.MAIN");
		intent.addCategory("android.intent.category.LAUNCHER");

		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		List<ResolveInfo> resolveinfo_list = mActivity.getPackageManager()
		.queryIntentActivities(intent, 0);

		for (ResolveInfo info : resolveinfo_list) {
		if (info.activityInfo.packageName
		.equalsIgnoreCase(application_name)) {
		launchComponent(info.activityInfo.packageName,
		info.activityInfo.name);
		break;
		}
		}
		} catch (ActivityNotFoundException e) {
		Toast.makeText(
				mActivity.getApplicationContext(),
		"There was a problem loading the application: "
		+ application_name, Toast.LENGTH_SHORT).show();
		}
		}

		private void launchComponent(String packageName, String name) {
			Intent launch_intent = new Intent("android.intent.action.MAIN");
			launch_intent.addCategory("android.intent.category.LAUNCHER");
			launch_intent.setComponent(new ComponentName(packageName, name));
			launch_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			this.startActivity(launch_intent);
		}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	  super.onActivityResult(requestCode, resultCode, data);
	  callbackManager.onActivityResult(requestCode, resultCode, data);
	}
	
	public static final long serialVersionUID = 6036846677812555352L;
	
	public static CoreActivity mActivity;
	public static ShareFragment mInstance;
	public static ShareFragment getInstance(CoreActivity activity) {
		if (mInstance == null) {
			mInstance = new ShareFragment();
		}
		mActivity = activity;
		return mInstance;
	}
	public static ShareFragment getInstance() {
		if (mInstance == null) {
			mInstance = new ShareFragment();
		}
		return mInstance;
	}
	@Override
	protected void initListener() {
		// TODO Auto-generated method stub
		
	}
}