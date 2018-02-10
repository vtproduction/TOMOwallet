package com.tomoapp.tomowallet.helper.api;

import org.json.JSONObject;

import java.math.BigInteger;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by macbook on 12/22/17.
 */

public interface APIEndpoint {



    @FormUrlEncoded
    @POST("api/wallets/reward")
    Call<JSONObject> reward(@Field("walletAddress") String walletAddress);

    @FormUrlEncoded
    @POST("api/wallets/cashIn")
    Call<JSONObject> cashIn(@Field("walletAddress") String walletAddress, @Field("cashInValue") double cashInValue);

    @FormUrlEncoded
    @POST("api/wallets/cashOut")
    Call<JSONObject> cashOut(@Field("walletAddress") String walletAddress, @Field("cashOutValue") double cashOutValue);

    @FormUrlEncoded
    @POST("api/wallets/transfer")
    Call<JSONObject> transfer(@Field("from") String from, @Field("to") String to, @Field("value") double value);
}
