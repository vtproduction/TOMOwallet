package com.tomoapp.tomowallet.ui.importWallet;

import android.content.Context;

import com.tomoapp.tomowallet.model.wallet.Wallet;

/**
 * Created by cityme on 1/5/18.
 */

public interface ImportWalletContract {
    interface View{
        Context getContext();
        void setContent();
        void onMnemonicInvalid(int messageId);
        void onMnemonicInvalid(String message);
        void onRecover();
        void onRecoverSuccess(Wallet wallet);
        void onRecoverFail(String why);
    }


    interface Presenter{
        void init();
        Wallet getWallet();
        void readMnemonic(String mnemonic);
    }
}
