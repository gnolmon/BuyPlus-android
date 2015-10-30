package lc.buyplus.fragments;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

import lc.buyplus.R;
import lc.buyplus.cores.CoreActivity;
import lc.buyplus.cores.CoreFragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class PersonalFragment extends CoreFragment {
	Display display;
	
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

	@Override
	protected void initViews(View v) {
		WindowManager manager = (WindowManager) mActivity.getSystemService(Context.WINDOW_SERVICE);
		display = manager.getDefaultDisplay();
		   
		
		TextView user_id_txt = (TextView) v.findViewById(R.id.user_id);
		user_id_txt.setText("123213");
		String qrInputText = user_id_txt.toString();
	    //Find screen size
	   
	    Point point = new Point();
	    display.getSize(point);
	    int width = point.x;
	    int height = point.y;
	    int smallerDimension = width < height ? width : height;
	    smallerDimension = smallerDimension * 3/4;
	    
	    //Encode with a QR Code image
	    QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(qrInputText, 
	             null, 
	             Contents.Type.TEXT,  
	             BarcodeFormat.QR_CODE.toString(), 
	             smallerDimension);
	    try {
	    Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();
	    ImageView myImage = (ImageView) v.findViewById(R.id.QR_code);
	    myImage.setImageBitmap(bitmap);
	    Log.d("QRCODE","asdsd");
	 
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
}