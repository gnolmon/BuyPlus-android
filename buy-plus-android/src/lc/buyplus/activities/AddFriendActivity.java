package lc.buyplus.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import lc.buyplus.R;
import lc.buyplus.cores.CoreActivity;
import lc.buyplus.fragments.AddFriendFragment;

public class AddFriendActivity extends CoreActivity {
	
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
		mFragmentManager.beginTransaction().add(R.id.canvas, AddFriendFragment.getInstance(AddFriendActivity.this)).commit();
	}
	
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
	
	@Override
	public void onBackPressed() {
		moveTaskToBack(true);
	}
}
