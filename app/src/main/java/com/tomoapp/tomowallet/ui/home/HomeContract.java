package com.tomoapp.tomowallet.ui.home;

import android.content.Context;
import android.content.Intent;

import com.tomoapp.tomowallet.base.BaseSocketActivity;
import com.tomoapp.tomowallet.model.userInfo.pojo.Log;
import com.tomoapp.tomowallet.model.userInfo.pojo.UserInfo;
import com.tomoapp.tomowallet.model.wallet.Wallet;
import com.tomoapp.tomowallet.model.walletActionResponse.CashActionResponse;
import com.tomoapp.tomowallet.model.walletActionResponse.RewardResponse;


/**
 * Created by macbook on 12/22/17.
 */

public interface HomeContract {

    interface View{
        HomeActivity getContext();
        void setUserInfo(UserInfo userInfo);
        void onRewarded(RewardResponse rewardResponse);
        void onCashing();
        void onCashed(CashActionResponse cashActionResponse);
        void onCashFail(String reason);
        void onRewarding(boolean firstReward);
        void onShowAddress();
        void onShowMenu();
        void onWalletDeleted();
        void goToCheckPIN();

    }


    interface Presenter{
        void init();
        void destroy();
        void onMine();
        void deleteWallet();
        void refreshUserInfo();
        String getWalletAddress();
        String getMnemonic();
        String getPIN();
        void setPIN(String currentPass);
        void onCashIn(double value);
        void onCashOut(double value);
        UserInfo getUserInfo();
        void setUserInfo(UserInfo userInfo);
        void updateUserInfo(Log log);

        void onActivityResult(int requestCode, int resultCode, Intent data);
    }
}
