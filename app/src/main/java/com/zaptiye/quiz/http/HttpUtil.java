package com.praxistechinc.pts.http;

import com.praxistechinc.pts.utils.Utility;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Mitesh on 03/11/16.
 */

public class HttpUtil {

    public void get(String wsName, String parameters, HttpCallback callback) {
        call(wsName, parameters, callback);
    }

    public void post(String wsName, String parameters, HttpCallback callback) {
        call(wsName, parameters, callback);
    }


    private void call(String wsName, String parameters, final HttpCallback httpCallback) {


        RequestBody requestBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8"), parameters);


        final Request request = new Request.Builder()
                .header("Accept", "application/json")
                .url(Utility.webservice_url + wsName)
                .post(requestBody)
                .build();

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .readTimeout(45, TimeUnit.SECONDS)
                .connectTimeout(45, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();


        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                httpCallback.onFailure(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                httpCallback.onSuccess(call, response);
            }
        });
    }

}
