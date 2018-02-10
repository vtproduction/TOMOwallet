package com.tomoapp.tomowallet.ui.send_tmc;

import android.content.Context;

/**
 * Created by macbook on 12/27/17.
 */

public interface SendTMCContract {
    interface View{
        Context getContext();
        void loadContent();
        void displayError(String error);
        void confirmTransfer(String address, Double value);
        void onTransferring();
        void onTransferred();

    }


    interface Presenter{
        void init();
        void destroy();
        void onTransfer(String address, String amount);
        void performTransfer(String address, Double amount);
        Double getTMCInSideChain();
    }
}
