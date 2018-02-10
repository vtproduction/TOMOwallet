package com.tomoapp.tomowallet.model.walletActionResponse;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tomoapp.tomowallet.helper.LogUtil;
import com.tomoapp.tomowallet.model.userInfo.pojo.Log;

/**
 * Created by macbook on 12/26/17.
 */

public class RewardResponse {
    @SerializedName("value")
    @Expose
    public Double value;

    @SerializedName("log")
    @Expose
    public Log log;

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Log getLog() {
        return log;
    }

    public void setLog(Log log) {
        this.log = log;
    }

    @Override
    public String toString() {
        return "RewardResponse{" +
                "value=" + value +
                ", log=" + log.toString() +
                '}';
    }


    public static RewardResponse parseFromJson(String source){
        try {
            return new Gson().fromJson(source, RewardResponse.class);
        } catch (JsonSyntaxException e) {
            LogUtil.e(e);
            return null;
        }
    }
}
