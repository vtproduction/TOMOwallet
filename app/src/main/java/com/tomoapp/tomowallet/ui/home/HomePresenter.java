package com.tomoapp.tomowallet.ui.home;

import android.app.Activity;
import android.content.Intent;

import com.tomoapp.tomowallet.base.MainApplication;
import com.tomoapp.tomowallet.helper.AppRequest;
import com.tomoapp.tomowallet.helper.LogUtil;
import com.tomoapp.tomowallet.helper.ToastUtil;
import com.tomoapp.tomowallet.model.userInfo.pojo.Log;
import com.tomoapp.tomowallet.model.userInfo.pojo.UserInfo;
import com.tomoapp.tomowallet.model.wallet.WalletDataSource;
import com.tomoapp.tomowallet.model.wallet.WalletRepository;
import com.tomoapp.tomowallet.model.walletAction.WalletActionDataSource;
import com.tomoapp.tomowallet.model.walletAction.WalletActionRepository;
import com.tomoapp.tomowallet.ui.checkPassword.CheckPasswordActivity;

/**
 * Created by macbook on 12/25/17.
 */

public class HomePresenter implements HomeContract.Presenter{
    private HomeContract.View mView;
    private WalletDataSource mWallet;
    private UserInfo userInfo;
    private String currentPIN;
    private WalletActionDataSource mWalletAction;
    public HomePresenter(HomeContract.View mView) {
        this.mView = mView;
        this.mWallet = new WalletRepository(mView.getContext());
        this.currentPIN = "";
    }

    public HomePresenter(HomeContract.View mView, String currentPIN) {
        this.mView = mView;
        this.mWallet = new WalletRepository(mView.getContext());
        this.currentPIN = currentPIN;
    }

    @Override
    public void init() {
        //MainApplication.get().connectSocket(mView.getContext());
        /*if (currentPIN.isEmpty()){
            mView.goToCheckPIN();
        }*/
    }

    @Override
    public void destroy() {
        //MainApplication.get().disconnectSocket(mView.getContext());
    }

    @Override
    public void onMine() {
        try {
            mView.onRewarding(userInfo == null || (userInfo.getTmcMainchain() + userInfo.getTmcSidechain() == 0));
            if (mWalletAction == null)
                this.mWalletAction = new WalletActionRepository(mView.getContext(), currentPIN);
            mWalletAction.performReward(new WalletActionDataSource.ActionExecuteListener() {
                @Override
                public void onFail(int code, String why) {
                    ToastUtil.show(why);
                    mView.onRewarded(null);
                }
            });
        }catch (Exception e){
            LogUtil.e(e);
        }
    }


    @Override
    public void onCashIn(double value) {
        if (mWalletAction == null)
            mWalletAction = new WalletActionRepository(mView.getContext(), currentPIN);
        mWalletAction.cashIn(value, new WalletActionDataSource.ActionExecuteListener() {
            @Override
            public void onFail(int code, String why) {
                mView.onCashFail(why);
            }
        });
    }

    @Override
    public void onCashOut(double value) {
        if (mWalletAction == null)
            mWalletAction = new WalletActionRepository(mView.getContext(), currentPIN);
        mWalletAction.cashOut(value, new WalletActionDataSource.ActionExecuteListener() {
            @Override
            public void onFail(int code, String why) {
                mView.onCashFail(why);
            }
        });
    }

    @Override
    public void deleteWallet() {
        mWallet.deleteWallet();
        mView.onWalletDeleted();
    }

    @Override
    public void refreshUserInfo() {
        LogUtil.d("onRefreshUserInfo");
        if (currentPIN.isEmpty()){
            mView.goToCheckPIN();
        }else
            MainApplication.get().emitUser(mWallet.getAddress(currentPIN));
    }

    @Override
    public String getWalletAddress() {
        if (currentPIN.isEmpty()){
            //mView.goToCheckPIN();
            return "";
        }
        return mWallet == null ? "" : mWallet.getAddress(currentPIN);
    }

    @Override
    public String getMnemonic() {
        if (currentPIN.isEmpty()){
            //mView.goToCheckPIN();
            return "";
        }
        return mWallet == null ? "" : mWallet.getMnemonic(currentPIN);
    }


    @Override
    public UserInfo getUserInfo() {
        return userInfo;
    }

    @Override
    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public void updateUserInfo(Log log) {
        try {
            userInfo.setTmcMainchain(log.getTmcMainchain());
            userInfo.setTmcSidechain(log.getTmcSidechain());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case AppRequest.REQ_CHECK_PIN:
                if (resultCode == Activity.RESULT_OK && data != null && data.getStringExtra(CheckPasswordActivity.ARGS_PIN) != null){
                    this.currentPIN = data.getStringExtra(CheckPasswordActivity.ARGS_PIN);
                    refreshUserInfo();
                }else {
                    mView.getContext().finishAffinity();
                }
                break;

        }
    }

    @Override
    public String getPIN() {
        return currentPIN;
    }

    @Override
    public void setPIN(String currentPass) {
        this.currentPIN = currentPass;
    }
}
