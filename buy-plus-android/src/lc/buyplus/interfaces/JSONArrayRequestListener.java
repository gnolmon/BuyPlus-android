package lc.buyplus.interfaces;

import org.json.JSONArray;

import com.android.volley.VolleyError;

import lc.buyplus.cores.CoreInterface;

public interface JSONArrayRequestListener extends CoreInterface {
	public void onBefore();
	public void onResponse(JSONArray response);
	public void onError(VolleyError error);
}
