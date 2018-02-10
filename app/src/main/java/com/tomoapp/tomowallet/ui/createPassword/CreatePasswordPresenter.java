package com.tomoapp.tomowallet.ui.createPassword;

import com.tomoapp.tomowallet.R;
import com.tomoapp.tomowallet.helper.AppConstant;
import com.tomoapp.tomowallet.model.wallet.Wallet;
import com.tomoapp.tomowallet.model.wallet.WalletDataSource;
import com.tomoapp.tomowallet.model.wallet.WalletRepository;

/**
 * Created by cityme on 1/8/18.
 */

public class CreatePasswordPresenter implements CreatePasswordContract.Presenter{
    private CreatePasswordContract.View mView;

    public CreatePasswordPresenter(CreatePasswordContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void init() {
        mView.setContent();
    }

    @Override
    public void checkPassword(String password, String passwordConfirm) {
        try {
            if (password.length() < AppConstant.PASSWORD_MIN_LENGTH){
                mView.displayPasswordError(R.string.mes_10);
                return;
            }
            if (password.length() > AppConstant.PASSWORD_MAX_LENGTH){
                mView.displayPasswordError(R.string.mes_11);
                return;
            }
            if (!password.equals(passwordConfirm)){
                mView.displayPasswordConfirmError(R.string.mes_12);
                return;
            }
            mView.onDone(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveCurrentWallet(String password, Wallet wallet) {
        WalletDataSource mWallet
                = new WalletRepository(mView.getContext());
        mWallet.saveWallet(wallet, password);
    }
}
