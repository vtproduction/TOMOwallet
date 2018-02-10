package com.tomoapp.tomowallet.ui.splash;

import android.content.Context;

/**
 * Created by macbook on 12/21/17.
 */

public interface SplashContract {
    interface View{
        Context getContext();
        void setContent();
        void goToCreateWallet();
        void goToRecoverWallet();
        void goToMain();
    }

    interface Presenter{
        void init();
        void destroy();
    }
}
