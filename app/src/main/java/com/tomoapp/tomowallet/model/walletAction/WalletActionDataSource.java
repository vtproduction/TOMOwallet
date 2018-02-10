package com.tomoapp.tomowallet.model.walletAction;

/**
 * Created by macbook on 12/25/17.
 */

public interface WalletActionDataSource {
    interface ActionExecuteListener{
        void onFail(int code, String why);
    }


    void performReward(ActionExecuteListener callback);
    void cashIn(double value, ActionExecuteListener callback);
    void cashOut(double value, ActionExecuteListener callback);
}
