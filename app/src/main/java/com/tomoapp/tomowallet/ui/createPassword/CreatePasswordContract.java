package com.tomoapp.tomowallet.ui.createPassword;

import android.content.Context;

import com.tomoapp.tomowallet.model.wallet.Wallet;

/**
 * Created by cityme on 1/8/18.
 */

public interface CreatePasswordContract {
    interface View{
        void setContent();
        void displayPasswordError(int messageId);
        void displayPasswordConfirmError(int messageId);
        void onDone(String password);
        Context getContext();
    }

    interface Presenter{
        void init();
        void checkPassword(String password, String passwordConfirm);
        void saveCurrentWallet(String password, Wallet wallet);
    }
}
