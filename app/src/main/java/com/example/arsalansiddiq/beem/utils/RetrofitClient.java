package com.example.arsalansiddiq.beem.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
//import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by arsalansiddiq on 1/16/18.
 * Singleton class of Retrofit Instance
 */

public class RetrofitClient {

    private static Retrofit retrofit = null;

    //Call`s response waiting
    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build();


    static Gson gson = new GsonBuilder()
            .setLenient()
            .create();


    public static Retrofit getRetrofitClient(String baseURL) {
        if (retrofit == null) {

            retrofit = new Retrofit.Builder()
                    .baseUrl(baseURL)
//                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit;
    }
}
