package com.tomoapp.tomowallet.helper.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ihsanbal.logging.LoggingInterceptor;

import com.ihsanbal.logging.Level;
import com.tomoapp.tomowallet.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.internal.platform.Platform;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by macbook on 12/22/17.
 */

public class APIService {
    private static APIService instance;
    public static APIService getInstance(){
        if (instance == null) instance = new APIService();
        return instance;
    }

    private OkHttpClient okHttpClient;
    public OkHttpClient getOkHttpClient(){
        return okHttpClient;
    }

    public APIEndpoint build(){
        String api = BuildConfig.API_ENDPOINT;


        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(new LoggingInterceptor.Builder()

                .loggable(true)
                .setLevel(Level.BODY)
                .log(Platform.INFO)
                .request("TOMO-REQUEST")
                .response("TOMO-RESPONSE")
                .loggable(true)
                .addHeader("android-version-code", BuildConfig.VERSION_CODE + "")
                .addHeader("android-version-name", BuildConfig.VERSION_NAME)
                .build());
        okHttpClient = client.build();
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(api)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit.create(APIEndpoint.class);
    }
}
