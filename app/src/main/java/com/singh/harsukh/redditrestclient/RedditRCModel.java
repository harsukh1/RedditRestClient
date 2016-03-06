package com.singh.harsukh.redditrestclient;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by harsukh on 3/6/16.
 */
public class RedditRCModel {

    private static AsyncHttpClient client = new AsyncHttpClient();
    private static String access_token;

    private void setToken(String token)
    {
        access_token = token;
    }

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(url, params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {

        client.post(url, params, responseHandler);
    }

    public String getToken(String relativeUrl,String grant_type,String device_id, String CLIENT_ID,
                         String CLIENT_SECRET, String REDIRECT_URI,String code) throws JSONException {
        client.setBasicAuth(CLIENT_ID, CLIENT_SECRET);
        RequestParams requestParams = new RequestParams();
        requestParams.put("code",code);
        requestParams.put("grant_type",grant_type);
        requestParams.put("redirect_uri", REDIRECT_URI);

        post(relativeUrl, requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                Log.i("response", response.toString());
                try {
                    String token = response.getString("access_token").toString();
                    setToken(token);
                    Log.e("Access_token", token);
                } catch (JSONException j) {
                    j.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e("statusCode", "" + statusCode);
            }
        });
        return access_token;
    }
}
