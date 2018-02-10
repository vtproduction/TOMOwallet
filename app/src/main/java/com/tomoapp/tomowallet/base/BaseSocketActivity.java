package com.tomoapp.tomowallet.base;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;

import com.tomoapp.tomowallet.helper.LogUtil;
import com.tomoapp.tomowallet.helper.alert.AlertHelper;
import com.tomoapp.tomowallet.helper.socket.TOMOSocketListener;
import com.tomoapp.tomowallet.model.userInfo.pojo.UserInfo;
import com.tomoapp.tomowallet.model.wallet.WalletRepository;
import com.tomoapp.tomowallet.model.walletActionResponse.CashActionResponse;
import com.tomoapp.tomowallet.model.walletActionResponse.RewardResponse;

import io.socket.client.Socket;

/**
 * Created by macbook on 12/28/17.
 */

public class BaseSocketActivity extends BaseActivity implements TOMOSocketListener{


    AlertHelper alertHelper;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alertHelper = new AlertHelper();
        MainApplication.get().connectSocket(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        MainApplication.get().connectSocket(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //MainApplication.get().disconnectSocket(this);
    }

    @Override
    public void onSocketConnected() {
        LogUtil.d("BASE SOCKET: onSocketConnected " + MainApplication.getCurrentActivity().getLocalClassName());
        if (MainApplication.getCurrentActivity() != null)
            MainApplication.getCurrentActivity().doWhenSocketConnected();
    }

    @Override
    public void onSocketDisconnected(Object... args) {
        LogUtil.d("BASE SOCKET: onSocketDisconnected " + MainApplication.getCurrentActivity().getLocalClassName());
        /*if (MainApplication.getCurrentActivity() != null)
            MainApplication.getCurrentActivity().doWhenSocketDisconnected(args);*/
        MainApplication.get().connectSocket(this);
    }

    @Override
    public void onRetrieveUserInfo(UserInfo userInfo) {
        LogUtil.d("BASE SOCKET: onRetrieveUserInfo " + MainApplication.getCurrentActivity().getLocalClassName());
        if (MainApplication.getCurrentActivity() != null)
            MainApplication.getCurrentActivity().doWhenRetrieveUserInfo(userInfo);
    }

    @Override
    public void onRetrieveReward(RewardResponse reward) {
        LogUtil.d("BASE SOCKET: onRetrieveReward " + MainApplication.getCurrentActivity().getLocalClassName());
        if (reward == null || reward.getLog().getChange().equals("0")) return;
        alertHelper.showRewarded(reward);
        if (MainApplication.getCurrentActivity() != null)
            MainApplication.getCurrentActivity().doWhenRetrieveReward(reward);
    }

    @Override
    public void onCashedIn(CashActionResponse cashInDetail) {

        LogUtil.d("BASE SOCKET: onCashedIn " + MainApplication.getCurrentActivity().getLocalClassName());
        if (cashInDetail == null || cashInDetail.getLog().getChange().equals("0")) return;
        alertHelper.showCashedIn(cashInDetail);
        if (MainApplication.getCurrentActivity() != null)
            MainApplication.getCurrentActivity().doWhenCashedIn(cashInDetail);
    }

    @Override
    public void onCashedOut(CashActionResponse cashOutDetail) {
        LogUtil.d("BASE SOCKET: onCashedOut " + MainApplication.getCurrentActivity().getLocalClassName());
        if (cashOutDetail == null || cashOutDetail.getLog().getChange().equals("0")) return;
        alertHelper.showCashedOut(cashOutDetail);
        if (MainApplication.getCurrentActivity() != null)
            MainApplication.getCurrentActivity().doWhenCashedOut(cashOutDetail);
    }

    @Override
    public void onTMCSent(CashActionResponse transactionDetail) {
        LogUtil.d("BASE SOCKET: onTMCSent " + MainApplication.getCurrentActivity().getLocalClassName());
        alertHelper.showSentTMC(transactionDetail);
        if (MainApplication.getCurrentActivity() != null)
            MainApplication.getCurrentActivity().doWhenTMCSent(transactionDetail);

    }

    @Override
    public void onTMCReceived(CashActionResponse transactionDetail) {
        LogUtil.d("BASE SOCKET: onTMCReceived " + MainApplication.getCurrentActivity().getLocalClassName());
        alertHelper.showReceivedTMC(transactionDetail);
        if (MainApplication.getCurrentActivity() != null)
            MainApplication.getCurrentActivity().doWhenTMCReceived(transactionDetail);
    }


    protected void emitUser(final String PIN){
        LogUtil.d("BASE SOCKET: emitUser " + MainApplication.getCurrentActivity().getLocalClassName());
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                MainApplication.get().emitUser(new WalletRepository(MainApplication.getCurrentActivity()).getAddress(PIN));
            }
        });
    }



}
