package com.tomoapp.tomowallet.base;

import android.os.StrictMode;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.support.v7.app.AppCompatDelegate;

import com.crashlytics.android.Crashlytics;
import com.tomoapp.tomowallet.BuildConfig;
import com.tomoapp.tomowallet.helper.LogUtil;
import com.tomoapp.tomowallet.helper.alert.AlertHelper;
import com.tomoapp.tomowallet.helper.socket.TOMOSocketListener;
import com.tomoapp.tomowallet.model.userInfo.UserInfoRepository;
import com.tomoapp.tomowallet.model.userInfo.pojo.UserInfo;
import com.tomoapp.tomowallet.model.walletActionResponse.CashActionResponse;
import com.tomoapp.tomowallet.model.walletActionResponse.RewardResponse;

import org.json.JSONObject;

import java.math.BigInteger;

import io.fabric.sdk.android.Fabric;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;


/**
 * Created by macbook on 12/21/17.
 */

public class MainApplication extends MultiDexApplication {


    private static MainApplication instance;
    private Socket socket;
    private Double tmcInSideChain;
    private Double tmcInMainChain;
    @Override
    public void onCreate() {
        super.onCreate();

        MultiDex.install(this);
        instance = this;
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Fabric fabric = new Fabric.Builder(this)
                .kits(new Crashlytics())
                .debuggable(true)
                .build();
        Fabric.with(fabric);
    }

    public Double getTmcInSideChain() {
        return tmcInSideChain;
    }

    public Double getTmcInMainChain() {
        return tmcInMainChain;
    }
    private void initSocket(final TOMOSocketListener callback){
        try {
            socket = IO.socket(BuildConfig.API_ENDPOINT);
            socket.on(io.socket.client.Socket.EVENT_CONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    callback.onSocketConnected();
                }

            }).on("user", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    for (Object object : args) {
                        LogUtil.d("onUser: " + object.toString());
                    }
                    UserInfo userInfo = new UserInfoRepository().createUserInfo(args[0].toString());
                    try {
                        tmcInMainChain = userInfo.getTmcMainchain();
                        tmcInSideChain = userInfo.getTmcSidechain();
                    } catch (Exception e) {
                        LogUtil.e(e);
                    }
                    callback.onRetrieveUserInfo(userInfo);
                }
            }).on("reward", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    for (Object object : args) {
                        LogUtil.d("onReward: " + object.toString());
                    }
                    RewardResponse response = RewardResponse.parseFromJson(args[0].toString());
                    try {
                        tmcInMainChain = response.getLog().getTmcMainchain();
                        tmcInSideChain = response.getLog().getTmcSidechain();
                    } catch (Exception e) {
                        LogUtil.e(e);
                    }
                    callback.onRetrieveReward(response);
                }
            }).on("cashIn", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    for (Object object : args) {
                        LogUtil.d("onCashIn: " + object.toString());
                    }
                    CashActionResponse cashActionResponse = CashActionResponse.parseFromJson(args[0].toString());
                    try {
                        tmcInMainChain = cashActionResponse.getMainchain();
                        tmcInSideChain = cashActionResponse.getSidechain();
                    } catch (Exception e) {
                        LogUtil.e(e);
                    }
                    callback.onCashedIn(cashActionResponse);
                }
            }).on("cashOut", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    for (Object object : args) {
                        LogUtil.d("onCashOut: " + object.toString());
                    }
                    CashActionResponse cashActionResponse = CashActionResponse.parseFromJson(args[0].toString());
                    try {
                        tmcInMainChain = cashActionResponse.getMainchain();
                        tmcInSideChain = cashActionResponse.getSidechain();
                    } catch (Exception e) {
                        LogUtil.e(e);
                    }
                    callback.onCashedOut(cashActionResponse);
                }
            }).on(io.socket.client.Socket.EVENT_DISCONNECT, new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                    for (Object object : args) {
                        LogUtil.d("onDisconnect: " + object);
                    }
                    callback.onSocketDisconnected(args);
                }

            }).on("transfer", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    for (Object object : args) {
                        LogUtil.d("onTransfer: " + object);
                    }
                    CashActionResponse cashActionResponse = CashActionResponse.parseFromJson(args[0].toString());
                    try {
                        tmcInMainChain = cashActionResponse.getMainchain();
                        tmcInSideChain = cashActionResponse.getSidechain();
                    } catch (Exception e) {
                        LogUtil.e(e);
                    }
                    callback.onTMCSent(cashActionResponse);
                }
            }).on("receive", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    for (Object object : args) {
                        LogUtil.d("onReceive: " + object);
                    }
                    CashActionResponse cashActionResponse = CashActionResponse.parseFromJson(args[0].toString());
                    try {
                        tmcInMainChain = cashActionResponse.getMainchain();
                        tmcInSideChain = cashActionResponse.getSidechain();
                    } catch (Exception e) {
                        LogUtil.e(e);
                    }
                    callback.onTMCReceived(cashActionResponse);
                }
            })
            ;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void connectSocket(TOMOSocketListener callback){
        try {
            if (socket == null)
                initSocket(callback);
            if (!socket.connected())
                socket.connect();
        } catch (Exception e) {
            LogUtil.e(e);
        }
    }

    public void disconnectSocket(TOMOSocketListener callback){
        try {
            if (socket == null)
                initSocket(callback);
            if (socket.connected())
                socket.disconnect();
        } catch (Exception e) {
            LogUtil.e(e);
        }
    }

    public void emitUser(String walletAddress){
        try {
            if (socket == null || !socket.connected())
                return;
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("address", walletAddress);
            socket.emit("user", jsonObject);
        } catch (Exception e) {
            LogUtil.e(e);
        }
    }


    public static BaseActivity currentActivity;

    public static BaseActivity getCurrentActivity() {
        return currentActivity;
    }
    public static void setCurrentActivity(BaseActivity activity){
        currentActivity = activity;
    }
    public static MainApplication get(){
        return instance;
    }
}
