package com.tomoapp.tomowallet.ui.createPincode;

import android.content.Context;

import com.google.gson.Gson;
import com.tomoapp.tomowallet.R;
import com.tomoapp.tomowallet.helper.LogUtil;
import com.tomoapp.tomowallet.helper.crypto.AESCrypt;
import com.tomoapp.tomowallet.model.wallet.Wallet;
import com.tomoapp.tomowallet.model.wallet.WalletDataSource;
import com.tomoapp.tomowallet.model.wallet.WalletRepository;

/**
 * Created by cityme on 1/2/18.
 */

public class CreatePinCodePresenter implements CreatePinCodeContract.Presenter {
    private CreatePinCodeContract.View mView;
    private String currentPinCode;
    private WalletDataSource mWallet;

    public CreatePinCodePresenter(CreatePinCodeContract.View mView) {
        this.mView = mView;
        this.mWallet = new WalletRepository(mView.getContext());
    }

    @Override
    public void init() {
        mView.loadData();
        this.currentPinCode = null;
    }

    @Override
    public void onSubmitPinCode(String code) {
        try {
            if (this.currentPinCode == null){
                this.currentPinCode = code;
                mView.switchToConfirm();
            }else {
                if (!code.equals(currentPinCode)){
                    mView.displayError(R.string.pin_un_match);
                    return;
                }
                encryptData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*@Override
    public void onConfirmPinCode(String code) {
        try {

        }catch (Exception e){
            LogUtil.e(e);
        }
    }*/


    private void encryptData(){
        try {
            String walletString
                    = mView.getActivity().getIntent().getStringExtra(CreatePinCodeActivity.ARGS_WALLET);
            Wallet wallet = new Gson().fromJson(walletString, Wallet.class);
            this.mWallet.saveWallet(wallet, currentPinCode);
            mView.onSuccess();
        } catch (Exception e) {
            LogUtil.e(e);
        }
    }
}
