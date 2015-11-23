package lc.buyplus.customizes;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import lc.buyplus.R;

public class DialogMessage extends android.app.Dialog implements android.view.View.OnClickListener {

	public Activity c;
	public Dialog d;
	public TextView tvVN, tvEng;
	public int flag;
	public String msg;
	private TextView tvMsg;

	public DialogMessage(Activity a, String msg) {
		super(a);
		// TODO Auto-generated constructor stub
		this.c = a;
		flag = 0;
		this.msg  = msg;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_sort);
		tvMsg = (TextView) findViewById(R.id.tvMsg);
		tvMsg.setText(msg);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tvVN:
			flag = 1;
			break;
		case R.id.tvEng:
			dismiss();
			break;
		default:
			break;
		}
		dismiss();
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}
	
	
}