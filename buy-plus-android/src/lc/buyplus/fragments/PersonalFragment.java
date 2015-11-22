package lc.buyplus.fragments;

import com.android.volley.toolbox.ImageLoader;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import lc.buyplus.R;
import lc.buyplus.activities.UserActivity;
import lc.buyplus.application.MonApplication;
import lc.buyplus.cores.CoreActivity;
import lc.buyplus.cores.CoreFragment;
import lc.buyplus.customizes.FastBlur;
import lc.buyplus.customizes.RoundedImageView;

public class PersonalFragment extends CoreFragment {
	Display display;

	private RelativeLayout rlBackground;
	private ImageView imEdit;
	private RoundedImageView imAvaUser;
	ImageLoader imageLoader = MonApplication.getInstance().getImageLoader();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_personal, container, false);
		initViews(view);
		initModels();
		initAnimations();
		return view;
	}

	@Override
	public void onClick(View v) {

	}

	protected void initModels() {

	}

	@SuppressLint("NewApi")
	@Override
	protected void initViews(View v) {
		WindowManager manager = (WindowManager) mActivity.getSystemService(Context.WINDOW_SERVICE);
		display = manager.getDefaultDisplay();

		imEdit = (ImageView) v.findViewById(R.id.imEdit);
		imEdit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent userActivity = new Intent(mActivity, UserActivity.class);

				startActivity(userActivity);

			}
		});

		imAvaUser = (RoundedImageView) v.findViewById(R.id.imAvaUser);
		imAvaUser.setImageUrl(CanvasFragment.mUser.getImageUrl(), imageLoader);
		imAvaUser.buildDrawingCache();
		rlBackground = (RelativeLayout) v.findViewById(R.id.rlBackground);
		

		
		if (imAvaUser.getWidth() > 0) {
			BitmapDrawable drawable = (BitmapDrawable) imAvaUser.getDrawable();
			Bitmap bmap = drawable.getBitmap();
			blur(bmap, rlBackground);
		} else {
			imAvaUser.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
				@Override
				public void onGlobalLayout() {
					BitmapDrawable drawable = (BitmapDrawable) imAvaUser.getDrawable();
					Bitmap bmap = drawable.getBitmap();
					blur(bmap, rlBackground);
				}
			});
		}

		TextView user_id_txt = (TextView) v.findViewById(R.id.user_id);
		user_id_txt.setText("Mã số cá nhân: " + CanvasFragment.mUser.getId());
		String qrInputText = user_id_txt.toString();
		// Find screen size

		Point point = new Point();
		display.getSize(point);
		int width = point.x;
		int height = point.y;
		int smallerDimension = width < height ? width : height;
		smallerDimension = smallerDimension * 3 / 4;

		// Encode with a QR Code image
		QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(qrInputText, null, Contents.Type.TEXT,
				BarcodeFormat.QR_CODE.toString(), smallerDimension);
		try {
			Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();
			ImageView myImage = (ImageView) v.findViewById(R.id.QR_code);
			myImage.setImageBitmap(bitmap);
			Log.d("QRCODE", "asdsd");

		} catch (WriterException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void initAnimations() {

	}

	public static final long serialVersionUID = 6036846677812555352L;

	public static CoreActivity mActivity;
	public static PersonalFragment mInstance;

	public static PersonalFragment getInstance(CoreActivity activity) {
		if (mInstance == null) {
			mInstance = new PersonalFragment();
		}
		mActivity = activity;
		return mInstance;
	}

	public static PersonalFragment getInstance() {
		if (mInstance == null) {
			mInstance = new PersonalFragment();
		}
		return mInstance;
	}

	@Override
	protected void initListener() {
		// TODO Auto-generated method stub

	}
	@SuppressLint("NewApi")
	private void blur(Bitmap bkg, View view) {
	    long startMs = System.currentTimeMillis();
	    float radius = 20;

	    Bitmap overlay = Bitmap.createBitmap((int) (view.getMeasuredWidth()),
	            (int) (view.getMeasuredHeight()), Bitmap.Config.ARGB_8888);
	    Canvas canvas = new Canvas(overlay);
	    canvas.translate(-view.getLeft(), -view.getTop());
	    canvas.drawBitmap(bkg, 0, 0, null);
	    //overlay = FastBlur.doBlur(overlay, (int)radius, true);
	    view.setBackground(new BitmapDrawable(getResources(), overlay));
	}
}
