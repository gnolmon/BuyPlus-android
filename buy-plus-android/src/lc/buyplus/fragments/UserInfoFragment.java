package lc.buyplus.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.Volley;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import lc.buyplus.R;
import lc.buyplus.activities.AddFriendActivity;
import lc.buyplus.activities.LoginActivity;
import lc.buyplus.activities.ShopInfoActivity;
import lc.buyplus.adapter.ShopFriendAdapter;
import lc.buyplus.cores.CoreActivity;
import lc.buyplus.cores.CoreFragment;
import lc.buyplus.cores.HandleRequest;
import lc.buyplus.customizes.CustomDialogClass;
import lc.buyplus.customizes.DialogMessage;
import lc.buyplus.customizes.DialogUser;
import lc.buyplus.models.Friend;
import lc.buyplus.models.Store;

public class UserInfoFragment extends CoreFragment {
	private ListView listView;
	private ShopFriendAdapter friendAdapter;
	private LayoutInflater inflaterActivity;
	private ImageView imAdd;
	private EditText username;
	private Button accept, reject;
	String tmp_name;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_user_info, container, false);
		initViews(view);
		initModels();
		initAnimations();
		inflaterActivity = inflater;
		return view;
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btnAgreeTerm:
			DialogUser dialog = new DialogUser(mActivity,"Bạn có muốn lưu thay đổi không?", 1,username.getText().toString());
			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			dialog.show();
			
		break;	
		case R.id.btnIgnore:
//			DialogUser dialog1 = new DialogUser(mActivity,"Bạn có muốn thoát và không lưu thay đổi ?", 1);
//			dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//			dialog1.show();
			username.setText(Store.user.getLogin_name());
		break;	
		}
		
	}

	protected void initModels() {

	}

	@Override
	protected void initViews(View v) {
		username = (EditText) v.findViewById(R.id.edUser);
		accept = (Button) v.findViewById(R.id.btnAgreeTerm);
		reject = (Button) v.findViewById(R.id.btnIgnore);
		accept.setOnClickListener(this);
		reject.setOnClickListener(this);
		username.setText(Store.user.getLogin_name());
	}

	@Override
	protected void initAnimations() {

	}


	public static final long serialVersionUID = 6036846677812555352L;

	public static CoreActivity mActivity;
	public static UserInfoFragment mInstance;

	public static UserInfoFragment getInstance(CoreActivity activity) {
		if (mInstance == null) {
			mInstance = new UserInfoFragment();
		}
		mActivity = activity;
		return mInstance;
	}

	public static UserInfoFragment getInstance() {
		if (mInstance == null) {
			mInstance = new UserInfoFragment();
		}
		return mInstance;
	}

	@Override
	protected void initListener() {
		// TODO Auto-generated method stub

	}
}
