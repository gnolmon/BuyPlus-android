package lc.buyplus.interfaces;

import com.android.volley.VolleyError;

import lc.buyplus.cores.CoreInterface;

public interface StringRequestListener  extends CoreInterface {
	public void onBefore();
	public void onResponse(String response);
	public void onErrorResponse(VolleyError error);
}
