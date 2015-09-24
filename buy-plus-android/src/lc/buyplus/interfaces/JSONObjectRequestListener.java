package lc.buyplus.interfaces;

import org.json.JSONObject;

import com.android.volley.VolleyError;

import lc.buyplus.cores.CoreInterface;

public interface JSONObjectRequestListener extends CoreInterface {
	public void onBefore();
	public void onResponse(JSONObject response);
	public void onError(VolleyError error);
}
