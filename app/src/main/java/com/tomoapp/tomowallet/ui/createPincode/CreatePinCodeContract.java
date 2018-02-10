package com.tomoapp.tomowallet.ui.createPincode;

import android.content.Context;

/**
 * Created by cityme on 1/2/18.
 */

public interface CreatePinCodeContract {
    interface View{
        CreatePinCodeActivity getActivity();
        Context getContext();
        void loadData();
        void switchToConfirm();
        void displayError(int error);
        void displayError(String error);
        void onSuccess();
    }


    interface Presenter{
        void init();
        void onSubmitPinCode(String code);

    }
}
