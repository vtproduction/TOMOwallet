package com.tomoapp.tomowallet.model.userInfo.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class UserInfo{

    @SerializedName("mainchainInformation")
    @Expose
    public MainchainInformation mainchainInformation;
    @SerializedName("sidechainInformation")
    @Expose
    public SidechainInformation sidechainInformation;
    @SerializedName("tmcSidechain")
    @Expose
    public Double tmcSidechain;
    @SerializedName("tmcMainchain")
    @Expose
    public Double tmcMainchain;
    @SerializedName("logs")
    @Expose
    public List<Log> logs = new ArrayList<>();


    @Override
    public String toString() {
        return "UserInfo{" +
                "mainchainInformation=" + mainchainInformation +
                ", sidechainInformation=" + sidechainInformation +
                ", tmcSidechain=" + tmcSidechain +
                ", tmcMainchain=" + tmcMainchain +
                '}';
    }

    public MainchainInformation getMainchainInformation() {
        return mainchainInformation;
    }

    public void setMainchainInformation(MainchainInformation mainchainInformation) {
        this.mainchainInformation = mainchainInformation;
    }

    public SidechainInformation getSidechainInformation() {
        return sidechainInformation;
    }

    public void setSidechainInformation(SidechainInformation sidechainInformation) {
        this.sidechainInformation = sidechainInformation;
    }

    public Double getTmcSidechain() {
        return tmcSidechain == null ? 0 : tmcSidechain;
    }

    public void setTmcSidechain(Double tmcSidechain) {
        this.tmcSidechain = tmcSidechain;
    }

    public Double getTmcMainchain() {
        return tmcMainchain == null ? 0 : tmcMainchain;
    }

    public void setTmcMainchain(Double tmcMainchain) {
        this.tmcMainchain = tmcMainchain;
    }

    public List<Log> getLogs() {
        return logs == null ? new ArrayList<Log>() : logs;
    }

    public void setLogs(List<Log> logs) {
        this.logs = logs;
    }



}