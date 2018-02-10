package com.tomoapp.tomowallet.ui.createWallet;

import com.tomoapp.tomowallet.helper.LogUtil;
import com.tomoapp.tomowallet.helper.wallet.WalletHelper;
import com.tomoapp.tomowallet.model.wallet.Wallet;
import com.tomoapp.tomowallet.model.wallet.WalletDataSource;
import com.tomoapp.tomowallet.model.wallet.WalletRepository;

/**
 * Created by macbook on 12/21/17.
 */

public class CreateWalletPresenter implements CreateWalletContract.Presenter {
    private CreateWalletContract.View mView;
    private WalletDataSource mModel;
    private String password;

    public CreateWalletPresenter(CreateWalletContract.View mView, String password) {
        this.mView = mView;
        this.password = password;
        this.mModel = new WalletRepository(mView.getContext());
    }

    @Override
    public void init() {
        try {
            if (!mModel.isWalletStored())
                createAndSaveWallet();
            else
                mView.onDone();
        }catch (Exception e){
            LogUtil.e(e);
        }
    }


    @Override
    public void destroy() {

    }

    @Override
    public void createAndSaveWallet() {
        try {
            mModel.createWallet(new WalletHelper.CreateWalletCallback() {
                @Override
                public void onStartCreate() {
                    mView.onCreating();
                }

                @Override
                public void onCreated(Wallet wallet) {
                    mModel.saveWallet(wallet, password);
                    mView.onCreated(wallet);
                }

                @Override
                public void onFail(String why) {
                    mView.onFail(why);
                }
            });
        } catch (Exception e) {
            LogUtil.e(e);
        }
    }
}
