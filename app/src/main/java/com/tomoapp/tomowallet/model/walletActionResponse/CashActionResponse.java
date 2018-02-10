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

public class CashActionResponse {
    @SerializedName("sidechain")
    @Expose
    Double sidechain;

    @SerializedName("mainchain")
    @Expose
    Double mainchain;

    @SerializedName("txMainchain")
    @Expose
    String txMainchain;

    @SerializedName("txSidechain")
    @Expose
    String txSidechain;

    @SerializedName("log")
    @Expose
    Log log;

    public Double getSidechain() {
        return sidechain;
    }

    public void setSidechain(Double sidechain) {
        this.sidechain = sidechain;
    }

    public Double getMainchain() {
        return mainchain;
    }

    public void setMainchain(Double mainchain) {
        this.mainchain = mainchain;
    }

    public String getTxMainchain() {
        return txMainchain;
    }

    public void setTxMainchain(String txMainchain) {
        this.txMainchain = txMainchain;
    }

    public String getTxSidechain() {
        return txSidechain;
    }

    public void setTxSidechain(String txSidechain) {
        this.txSidechain = txSidechain;
    }

    public Log getLog() {
        return log;
    }

    public void setLog(Log log) {
        this.log = log;
    }

    @Override
    public String toString() {
        return "CashActionResponse{" +
                "sidechain=" + sidechain +
                ", mainchain=" + mainchain +
                ", txMainchain='" + txMainchain + '\'' +
                ", txSidechain='" + txSidechain + '\'' +
                ", log=" + log.toString() +
                '}';
    }


    public static CashActionResponse parseFromJson(String source){
        try {
            return new Gson().fromJson(source, CashActionResponse.class);
        } catch (JsonSyntaxException e) {
            LogUtil.e(e);
            return null;
        }
    }
}
