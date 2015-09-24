package lc.buyplus.webservice;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import lc.buyplus.ultils.NetworkUtil;


@SuppressLint("TrulyRandom")
public class MyAsyncHttpBase extends AsyncTask<String, Integer, String> {
	// Network status
	public static final int NETWORK_STATUS_OK = 0;
	public static final int NETWORK_STATUS_OFF = 1;
	public static final int NETWORK_STATUS_ERROR = 2;

	// Configuration time out
	public static final int NETWORK_TIME_OUT = 30000;

	protected Context context;
	protected MyAsyncHttpResponseListener listener;
	protected List<NameValuePair> parameters;
	protected HttpResponse response = null;
	protected String responseString = null;
	protected int statusCode;
	protected boolean flagStop = false;

	protected File dir;
	protected String fileName;

	protected boolean saveResponse;
	protected String filePath;

	/**
	 * Constructor
	 * 
	 * @param context
	 * @param listener
	 * @param parameters
	 */
	public MyAsyncHttpBase(Context context,
			MyAsyncHttpResponseListener listener, List<NameValuePair> parameters) {
		this.context = context;
		this.listener = listener;
		this.parameters = parameters;
		this.flagStop = false;
		this.saveResponse = false;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		listener.before();
	}

	@Override
	protected String doInBackground(String... args) {
		if (NetworkUtil.getInstance(context).isConnect()) {
			// Request to server if network is available
			return request(args[0]);
		} else {
//			 Return status if network is not available
			statusCode = NETWORK_STATUS_OFF;
			return null;
		}
	}

	@Override
	protected void onPostExecute(String result) {
		// Call method to process http status code and response
		if (!flagStop)
			listener.after(statusCode, responseString);
		return;
	}

	/**
	 * Send request to server
	 * 
	 * @param url
	 * @return
	 */
	protected String request(String url) {
		// This function will be implemented in AsyncHttpGet and AsyncHttpPost
		// class
		return null;
	}

	/**
	 * Get HTTP Context of current URL
	 * 
	 * @param url
	 * @return
	 * @throws MalformedURLException
	 */
	protected HttpContext httpContext(String url) throws MalformedURLException {
		String host = new URL(url).getHost();
		return HttpContextFactory.newInstance().getHttpContext(host);
	}

	// ============================================================================

	/**
	 * Create HttpClient based on HTTP or HTTPS protocol that is parsed from url
	 * parameter. With HTTPS protocol, we accept all SSL certificates.
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	@SuppressLint("DefaultLocale")
	protected HttpClient createHttpClient(String url, HttpParams params) {
		if ((url.toLowerCase().startsWith("https"))) {
			// HTTPS process
			try {
				KeyStore trustStore = KeyStore.getInstance(KeyStore
						.getDefaultType());
				trustStore.load(null, null);

				SSLSocketFactory sf = new MySSLSocketFactory(trustStore);
				sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

				HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
				HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

				SchemeRegistry registry = new SchemeRegistry();
				registry.register(new Scheme("http", PlainSocketFactory
						.getSocketFactory(), 80));
				registry.register(new Scheme("https", sf, 443));

				ClientConnectionManager ccm = new ThreadSafeClientConnManager(
						params, registry);

				return new DefaultHttpClient(ccm, params);
			} catch (Exception e) {
				return new DefaultHttpClient(params);
			}
		} else {
			// HTTP process
			return new DefaultHttpClient(params);
		}
	}

	// ============================ Https functions ============================

	/**
	 * Trust every server - dont check for any certificate
	 */
	@SuppressLint("TrulyRandom")
	private static void trustAllHosts() {
		// Create a trust manager that does not validate certificate chains
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return new java.security.cert.X509Certificate[] {};
			}

			public void checkClientTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
			}

			public void checkServerTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
			}
		} };

		// Install the all-trusting trust manager
		try {
			SSLContext sc = SSLContext.getInstance("TLS");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection
					.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open HTTPS connection. Use this method to setup and accept all SSL
	 * certificates from HTTPS protocol.
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static HttpsURLConnection openSConnection(String url)
			throws IOException {
		URL theURL = new URL(url);
		trustAllHosts();
		HttpsURLConnection https = (HttpsURLConnection) theURL.openConnection();
		https.setHostnameVerifier(new HostnameVerifier() {
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		});
		return https;
	}

	/**
	 * Open HTTP connection
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static HttpURLConnection openConnection(String url)
			throws IOException {
		URL theURL = new URL(url);
		return (HttpURLConnection) theURL.openConnection();
	}

	/**
	 * @return the flagStop
	 */
	public boolean isFlagStop() {
		return flagStop;
	}

	/**
	 * @param flagStop
	 *            the flagStop to set
	 */
	public void setFlagStop(boolean flagStop) {
		this.flagStop = flagStop;
	}

	/**
	 * @return the saveResponse
	 */
	public boolean isSaveResponse() {
		return saveResponse;
	}

	/**
	 * @param saveResponse
	 *            the saveResponse to set
	 */
	public void setSaveResponse(boolean saveResponse) {
		this.saveResponse = saveResponse;
	}

	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @param filePath
	 *            the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
