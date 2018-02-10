package com.tomoapp.tomowallet.model.userInfo.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SidechainInformation{
    @SerializedName("tmcAddress")
    @Expose
    public String tmcAddress;
    @SerializedName("cashOutAddress")
    @Expose
    public String cashOutAddress;
    @SerializedName("cashInAddress")
    @Expose
    public String cashInAddress;
    @SerializedName("rewardEngineAddress")
    @Expose
    public String rewardEngineAddress;


    @Override
    public String toString() {
        return "SidechainInformation{" +
                "tmcAddress='" + tmcAddress + '\'' +
                ", cashOutAddress='" + cashOutAddress + '\'' +
                ", cashInAddress='" + cashInAddress + '\'' +
                ", rewardEngineAddress='" + rewardEngineAddress + '\'' +
                '}';
    }

    public String getTmcAddress() {
        return tmcAddress;
    }

    public void setTmcAddress(String tmcAddress) {
        this.tmcAddress = tmcAddress;
    }

    public String getCashOutAddress() {
        return cashOutAddress;
    }

    public void setCashOutAddress(String cashOutAddress) {
        this.cashOutAddress = cashOutAddress;
    }

    public String getCashInAddress() {
        return cashInAddress;
    }

    public void setCashInAddress(String cashInAddress) {
        this.cashInAddress = cashInAddress;
    }

    public String getRewardEngineAddress() {
        return rewardEngineAddress;
    }

    public void setRewardEngineAddress(String rewardEngineAddress) {
        this.rewardEngineAddress = rewardEngineAddress;
    }
}
