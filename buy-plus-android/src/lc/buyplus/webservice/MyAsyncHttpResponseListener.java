package lc.buyplus.webservice;

public interface MyAsyncHttpResponseListener {
	public void before();
	public void after(int statusCode, String response);
}
