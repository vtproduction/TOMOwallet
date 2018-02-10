package com.tomoapp.tomowallet.model.userInfo.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MainchainInformation{
    @SerializedName("tmcAddress")
    @Expose
    public String tmcAddress;
    @SerializedName("cashOutAddress")
    @Expose
    public String cashOutAddress;
    @SerializedName("cashInAddress")
    @Expose
    public String cashInAddress;


    @Override
    public String toString() {
        return "MainchainInformation{" +
                "tmcAddress='" + tmcAddress + '\'' +
                ", cashOutAddress='" + cashOutAddress + '\'' +
                ", cashInAddress='" + cashInAddress + '\'' +
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
}
