package com.tomoapp.tomowallet.helper.socket;

import com.tomoapp.tomowallet.model.userInfo.pojo.UserInfo;
import com.tomoapp.tomowallet.model.walletActionResponse.CashActionResponse;
import com.tomoapp.tomowallet.model.walletActionResponse.RewardResponse;

import java.math.BigInteger;

/**
 * Created by macbook on 12/22/17.
 */

public interface TOMOSocketListener {

    void onSocketConnected();
    void onSocketDisconnected(Object... args);
    void onRetrieveUserInfo(UserInfo userInfo);
    void onRetrieveReward(RewardResponse reward);
    void onCashedIn(CashActionResponse cashInDetail);
    void onCashedOut(CashActionResponse cashOutDetail);
    void onTMCSent(CashActionResponse transactionDetail);
    void onTMCReceived(CashActionResponse transactionDetail);
}
