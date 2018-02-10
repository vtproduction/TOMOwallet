package com.tomoapp.tomowallet.ui.checkPassword;


import com.tomoapp.tomowallet.model.wallet.WalletDataSource;
import com.tomoapp.tomowallet.model.wallet.WalletRepository;

/**
 * Created by cityme on 1/3/18.
 */

public class CheckPasswordPresenter implements CheckPasswordContract.Presenter {

    private CheckPasswordContract.View mView;
    private WalletDataSource mWallet;


    public CheckPasswordPresenter(CheckPasswordContract.View mView) {
        this.mView = mView;
        this.mWallet = new WalletRepository(mView.getActivity());
    }

    @Override
    public void init() {
        mView.setContent();
    }

    @Override
    public void checkPIN(String pin) {
        String address = mWallet.getAddress(pin);
        if (address.isEmpty()){
            mView.onPINIncorrect();
        }else {
            mView.onPINSuccess(pin);
        }
    }
}
