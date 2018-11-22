package com.zaptiye.quiz.http;

import com.zaptiye.quiz.Constant;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Mitesh on 03/11/16.
 */

public class HttpUtil {

//    public void get(String wsName, String parameters[], HttpCallback callback) {
//        postCall(wsName, parameters, callback);
//    }

    public void post(String wsName, String parameters[], HttpCallback callback) {
        postCall(wsName, parameters, callback);
    }


//    private void getCall(String wsName,String parameters,final HttpCallback httpCallback){
//
//
//        RequestBody requestBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8"), parameters);
//
//
//        final Request request = new Request.Builder()
//                .header("Accept", "application/json")
//                .header("Content-Type","text/json;Charset=UTF-8")
//                .url(Constant.webserviceURL + wsName)
//                .post(requestBody)
//                .build();
//
//        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
//                .readTimeout(45, TimeUnit.SECONDS)
//                .connectTimeout(45, TimeUnit.SECONDS)
//                .retryOnConnectionFailure(true)
//                .build();
//
//
//        okHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//                httpCallback.onFailure(call, e);
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//
//                httpCallback.onSuccess(call, response);
//            }
//        });
//    }

    private void postCall(String wsName, String parameters[], final HttpCallback httpCallback) {

        RequestBody requestBody;

        if(parameters==null)
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new byte[0]);
        else{


            if(wsName.equals("reportQuestion")){

                requestBody = new FormBody.Builder()
                        .add("question_id",parameters[0])
                        .add("user_message",parameters[1])
                        .add("user_email",parameters[2])
                        .build();

            }else
                requestBody = new FormBody.Builder()
                    .add("exam_id",parameters[0])
                    .build();
        }

        final Request request = new Request.Builder()
                .header("Accept", "application/json")
                .url(Constant.webserviceURL + wsName)
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
