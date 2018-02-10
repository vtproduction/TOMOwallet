package com.tomoapp.tomowallet.ui.splash;

import com.tomoapp.tomowallet.model.wallet.WalletDataSource;
import com.tomoapp.tomowallet.model.wallet.WalletRepository;

/**
 * Created by macbook on 12/21/17.
 */

public class SplashPresenter implements SplashContract.Presenter {
    private SplashContract.View mView;
    private WalletDataSource mWallet;

    public SplashPresenter(SplashContract.View mView) {
        this.mView = mView;
        this.mWallet = new WalletRepository(mView.getContext());
    }


    @Override
    public void init() {
        //mView.setContent();
        /*String walletAddress = mWallet.getAddress();
        if (walletAddress.isEmpty()){
            mView.setContent();
        }else {
            mView.goToMain();
        }*/
        boolean isWalletStored = mWallet.isWalletStored();
        if (isWalletStored)
            mView.goToMain();
        else
            mView.setContent();
    }

    @Override
    public void destroy() {

    }
}
