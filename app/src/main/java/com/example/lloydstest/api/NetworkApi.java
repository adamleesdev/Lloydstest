package com.example.lloydstest.api;

import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class NetworkApi {

    private static OkHttpClient client;
    private static NetworkApi instance;
    private static Gson gson;

    public static NetworkApi getInstance(){
       if (instance == null){
           instance = instantiate();
       }
       return instance;
    }

    private static NetworkApi instantiate(){
        if(client == null){
            client = new OkHttpClient().newBuilder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .build();
        }
        if (gson == null){
            gson = new Gson();
        }
        if(instance == null){
            instance = new NetworkApi();
        }
        return instance;
    }

    public Gson getGson(){
        return gson;
    }

    //Create new get request
    public void getRequest(String url, Callback callback) {
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(callback);
    }
}
