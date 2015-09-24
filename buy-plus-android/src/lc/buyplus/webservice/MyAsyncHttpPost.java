package lc.buyplus.webservice;

import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.content.Context;

public class MyAsyncHttpPost extends MyAsyncHttpBase {

	public MyAsyncHttpPost(Context context,
			MyAsyncHttpResponseListener listener, List<NameValuePair> parameters) {
		super(context, listener, parameters);

//		if (DeveloperConfig.DEVELOPER_MODE) {
//			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
//				StrictMode
//						.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
//								.detectDiskReads().detectDiskWrites()
//								.detectNetwork().penaltyLog().build());
//				StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
//						.detectLeakedSqlLiteObjects().penaltyLog()
//						.penaltyDeath().build());
//			}
//		}
	}

//	public MyAsyncHttpPost(Context context,
//			MyAsyncHttpResponsePostProcess process,
//			List<NameValuePair> parameters) {
//		super(context, process, parameters);
//	}

	@Override
	protected String request(String url) {
	//	Log.d("url request = " + url);

		try {
			HttpParams params = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(params, NETWORK_TIME_OUT);
			HttpConnectionParams.setSoTimeout(params, NETWORK_TIME_OUT);
			HttpClient httpclient = createHttpClient(url, params);

			HttpPost httppost = new HttpPost(url);
			if (parameters != null) {
				//Log.d("parameters: " + parameters.toString());
			}
			httppost.setEntity(new UrlEncodedFormEntity(parameters));
			// httppost.setHeader("Content-Type", "multipart/form-data");
			response = httpclient.execute(httppost);
			responseString = EntityUtils.toString(response.getEntity());
			statusCode = NETWORK_STATUS_OK;
		} catch (Exception e) {
			responseString = e.toString();
			statusCode = NETWORK_STATUS_ERROR;
		}
		return null;
	}
}
