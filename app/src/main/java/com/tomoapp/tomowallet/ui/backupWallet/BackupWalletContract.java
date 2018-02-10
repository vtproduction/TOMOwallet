package com.tomoapp.tomowallet.ui.backupWallet;

import android.content.Context;

/**
 * Created by cityme on 1/10/18.
 */

public interface BackupWalletContract {
    interface View{
        Context getContext();
        void setContent();
        void showMnemonic(String mnemonic, String password);
    }

    interface Presenter{
        void init();
        void onCheckBtnPressed();
    }
}
