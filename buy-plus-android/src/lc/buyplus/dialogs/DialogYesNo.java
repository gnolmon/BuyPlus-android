package lc.buyplus.dialogs;

import lc.buyplus.R;
import lc.buyplus.customizes.MyAnimations;
import lc.buyplus.customizes.MyTextView;
import lc.buyplus.interfaces.OnDialogYesNoListener;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

public class DialogYesNo extends DialogFragment implements OnClickListener {
	public Context mContext;
	public LayoutInflater mInflater;
	protected Dialog mDialog;
	public OnDialogYesNoListener mListener;
	protected MyTextView mContent;
	public MyTextView mBtYes;
	public MyTextView mBtNo;
	protected String mContentText;
	public DialogYesNo() {
		
	}
	public DialogYesNo(Context context, String content, OnDialogYesNoListener listener) {
		this.mContext = context;
		this.mInflater = LayoutInflater.from(context);
		this.mContentText = content;
		this.mListener = listener;
	}

	public static DialogYesNo newInstance(Context context, String content, OnDialogYesNoListener listener ) {
		DialogYesNo alertDialog = new DialogYesNo(context, content, listener);
		Bundle bundle = new Bundle();
		alertDialog.setArguments(bundle);
		return alertDialog;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		if (bundle != null) {
			
		}
	}
	
	@SuppressLint("InflateParams")
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		mDialog = new Dialog(mContext);
		mDialog.setCanceledOnTouchOutside(false);
		mDialog.setCancelable(true);
		mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		View view = mInflater.inflate(R.layout.dialog_yes_no, null);
	
		initViews(view);
		setViews();
		initOnClick();
		initAnimation();
		
		mDialog.setContentView(view);
		return mDialog;
	}
	
	public void initViews(View view) {
		mContent = (MyTextView) view.findViewById(R.id.dialog_yes_no_text);
		mBtYes = (MyTextView) view.findViewById(R.id.dialog_yes_no_yes);
		mBtNo = (MyTextView) view.findViewById(R.id.dialog_yes_no_no);
	}
	public void setViews() {
		mContent.setText(mContentText);
	}
	
	public void initOnClick() {
		mBtYes.setOnClickListener(this);
		mBtNo.setOnClickListener(this);
	}
	
	public void initAnimation() {
		mBtYes.startAnimation(MyAnimations.fromRight(100, 300));
		mBtNo.startAnimation(MyAnimations.fromLeft(100, 300));
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.dialog_yes_no_yes:
			mListener.onYes();
			dismiss();
			break;
		case R.id.dialog_yes_no_no:
			mListener.onNo();
			dismiss();
			break;
		default:
			break;
		}
		
	}
}
