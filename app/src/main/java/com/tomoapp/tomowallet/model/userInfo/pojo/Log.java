package com.tomoapp.tomowallet.model.userInfo.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tomoapp.tomowallet.helper.DateTimeUtil;

public class Log {

    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("change")
    @Expose
    public String change;
    @SerializedName("time")
    @Expose
    public String time;
    @SerializedName("tmcSidechain")
    @Expose
    public Double tmcSidechain;
    @SerializedName("tmcMainchain")
    @Expose
    public Double tmcMainchain;
    @SerializedName("txSidechain")
    @Expose
    public String txSidechain;
    @SerializedName("txMainchain")
    @Expose
    public String txMainchain;
    @SerializedName("total")
    @Expose
    public Double total;

    public static final String TYPE_CASHOUT = "cashOut";
    public static final String TYPE_CASHIN = "cashIn";
    public static final String TYPE_TRANSFER = "transfer";
    public static final String TYPE_RECEIVE = "receive";
    public static final String TYPE_REWARD = "reward";
    public static final String TYPE_MESSAGE = "message";
    public static final String[] ACCEPT_TYPES = new String[]{TYPE_CASHIN, TYPE_CASHOUT, TYPE_MESSAGE, TYPE_REWARD, TYPE_TRANSFER, TYPE_RECEIVE};



    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getChange() {
        return change == null ? "NAN" : change;
    }


    public void setChange(String change) {
        this.change = change;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public String getTxSidechain() {
        return txSidechain;
    }

    public void setTxSidechain(String txSidechain) {
        this.txSidechain = txSidechain;
    }

    public String getTxMainchain() {
        return txMainchain;
    }

    public void setTxMainchain(String txMainchain) {
        this.txMainchain = txMainchain;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }


    @Override
    public String toString() {
        return "Log{" +
                "msg='" + message + '\'' +
                ", type='" + type + '\'' +
                ", change='" + change + '\'' +
                ", time='" + time + '\'' +
                ", tmcSidechain=" + tmcSidechain +
                ", tmcMainchain=" + tmcMainchain +
                ", total=" + total +
                '}';
    }

    public String getDisplayDate(){
        return DateTimeUtil.displayShortDate(getTime());
    }


}
