package com.tomoapp.tomowallet.ui.createWallet;

import android.content.Context;

import com.tomoapp.tomowallet.model.wallet.Wallet;

/**
 * Created by macbook on 12/21/17.
 */

public interface CreateWalletContract {

    interface View{
        Context getContext();
        void onCreating();
        void onCreated(Wallet wallet);
        void onDone();
        void onBack();
        void onFail(String why);
    }


    interface Presenter{
        void init();
        void destroy();
        void createAndSaveWallet();
    }
}
