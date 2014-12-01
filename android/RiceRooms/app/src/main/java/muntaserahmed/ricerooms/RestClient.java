package muntaserahmed.ricerooms;

import com.loopj.android.http.*;

public class RestClient {
    private static final String BASE_URL = "http://54.148.64.121:5000/";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void getAll(RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(BASE_URL + "all", params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}