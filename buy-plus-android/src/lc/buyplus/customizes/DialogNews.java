package lc.buyplus.customizes;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import lc.buyplus.R;

public class DialogNews extends android.app.Dialog implements android.view.View.OnClickListener {

	public Activity c;
	public Dialog d;
	public TextView tvShop, tvPromo, tvAll;
	public ImageView imShop, imPromo, imAll;
	public int flag;

	public DialogNews(Activity a) {
		super(a);
		// TODO Auto-generated constructor stub
		this.c = a;
		flag = 0;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_news);

		tvShop = (TextView) findViewById(R.id.tvShop);
		tvShop.setOnClickListener(this);
		imShop = (ImageView) findViewById(R.id.imShop);

		tvPromo = (TextView) findViewById(R.id.tvPromo);
		imPromo = (ImageView) findViewById(R.id.imPromo);
		tvPromo.setOnClickListener(this);

		tvAll = (TextView) findViewById(R.id.tvAll);
		imAll = (ImageView) findViewById(R.id.imAll);
		tvAll.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tvShop:
			imShop.setVisibility(View.VISIBLE);
			imPromo.setVisibility(View.INVISIBLE);
			imAll.setVisibility(View.INVISIBLE);
			break;
		case R.id.tvPromo:
			imShop.setVisibility(View.INVISIBLE);
			imPromo.setVisibility(View.VISIBLE);
			imAll.setVisibility(View.INVISIBLE);
			break;
		case R.id.tvAll:
			imShop.setVisibility(View.INVISIBLE);
			imPromo.setVisibility(View.INVISIBLE);
			imAll.setVisibility(View.VISIBLE);
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