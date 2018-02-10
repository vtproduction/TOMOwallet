package com.tomoapp.tomowallet.ui.checkPassword;

import com.tomoapp.tomowallet.base.BaseActivity;

/**
 * Created by cityme on 1/3/18.
 */

public interface CheckPasswordContract {

    interface View{
        BaseActivity getActivity();
        void setContent();
        void onPINIncorrect();
        void onPINSuccess(String pin);
    }

    interface Presenter{
        void init();
        void checkPIN(String pin);
    }
}
