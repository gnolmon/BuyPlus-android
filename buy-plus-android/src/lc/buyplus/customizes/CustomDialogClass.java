package lc.buyplus.customizes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Button;
import lc.buyplus.R;
import lc.buyplus.activities.LoginActivity;

public class CustomDialogClass extends android.app.Dialog implements android.view.View.OnClickListener {

	public Activity c;
	public Dialog d;
	public Button yes, no;
	public int flag;
	public String msg;
	private TextView tvMsg;

	public CustomDialogClass(Activity a,String msg,int flag) {
		super(a);
		// TODO Auto-generated constructor stub
		this.c = a;
		flag = flag;
		this.msg = msg;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.custom_dialog);
		yes = (Button) findViewById(R.id.btnYes);
		no = (Button) findViewById(R.id.btnNo);
		yes.setOnClickListener(this);
		no.setOnClickListener(this);
		
		tvMsg = (TextView) findViewById(R.id.tvMsg);
		tvMsg.setTextColor(c.getResources().getColor(R.color.title));
		tvMsg.setText("" + msg);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnYes:
			if (flag == 1){
				Intent loginActivity = new Intent(c,LoginActivity.class);
	            c.startActivity(loginActivity);
	            c.finish();
			}
			
			
			break;
		case R.id.btnNo:
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