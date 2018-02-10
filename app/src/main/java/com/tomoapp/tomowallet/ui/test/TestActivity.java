package com.tomoapp.tomowallet.ui.test;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.evgenii.jsevaluator.interfaces.JsCallback;
import com.tomoapp.tomowallet.R;
import com.tomoapp.tomowallet.base.BaseActivity;
import com.tomoapp.tomowallet.helper.LogUtil;
import com.tomoapp.tomowallet.helper.wallet.WalletHelper;
import com.tomoapp.tomowallet.model.wallet.Wallet;

/**
 * Created by macbook on 12/21/17.
 */

public class TestActivity extends BaseActivity {
    private WalletHelper walletHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        walletHelper = new WalletHelper(this);

        walletHelper.createWallet(new WalletHelper.CreateWalletCallback() {
            @Override
            public void onStartCreate() {
                LogUtil.d("walletHelper: start!");
            }

            @Override
            public void onCreated(Wallet wallet) {
                LogUtil.d("walletHelper: onCreated: " + wallet.toString());
            }

            @Override
            public void onFail(String why) {
                LogUtil.d("walletHelper: onFail: " + why);
            }
        });
    }
}
