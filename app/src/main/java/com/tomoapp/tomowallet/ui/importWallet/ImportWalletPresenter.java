package com.tomoapp.tomowallet.ui.importWallet;

import com.evgenii.jsevaluator.interfaces.JsCallback;
import com.tomoapp.tomowallet.R;
import com.tomoapp.tomowallet.helper.LogUtil;
import com.tomoapp.tomowallet.helper.wallet.WalletHelper;
import com.tomoapp.tomowallet.model.wallet.Wallet;
import com.tomoapp.tomowallet.model.wallet.WalletDataSource;
import com.tomoapp.tomowallet.model.wallet.WalletRepository;

/**
 * Created by cityme on 1/5/18.
 */

public class ImportWalletPresenter implements ImportWalletContract.Presenter {
    private ImportWalletContract.View mView;
    private WalletDataSource mModel;
    private Wallet wallet;

    public ImportWalletPresenter(ImportWalletContract.View mView) {
        this.mView = mView;
        this.mModel = new WalletRepository(mView.getContext());
    }


    @Override
    public void init() {
        mView.setContent();
    }

    @Override
    public Wallet getWallet() {
        return wallet;
    }

    @Override
    public void readMnemonic(final String mnemonic1) {
        final String mnemonic = mnemonic1.trim();
        String[] mnemonics = mnemonic.split(" ");
        if (mnemonics.length != 12){
            mView.onMnemonicInvalid(R.string.mnemonic_error_1);
            return;
        }
        mView.onRecover();
        mModel.validateMnemonic(mnemonic, new JsCallback() {
            @Override
            public void onResult(String s) {
                LogUtil.d("onRESULT: " + s);
                if (s.equals("true")){
                    mModel.createWallet(mnemonic, new WalletHelper.CreateWalletCallback() {
                        @Override
                        public void onStartCreate() {

                        }

                        @Override
                        public void onCreated(Wallet wallet) {
                            ImportWalletPresenter.this.wallet = wallet;
                            mView.onRecoverSuccess(wallet);
                        }

                        @Override
                        public void onFail(String why) {
                            mView.onRecoverFail(why);
                        }
                    });
                }else {
                    mView.onMnemonicInvalid(R.string.invalid_mnemonic);
                }
            }

            @Override
            public void onError(String s) {
                LogUtil.d("onError: " + s);
                mView.onMnemonicInvalid(R.string.invalid_mnemonic);
            }
        });
        /**/
    }
}
