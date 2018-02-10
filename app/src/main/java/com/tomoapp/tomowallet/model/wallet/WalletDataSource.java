package com.tomoapp.tomowallet.model.wallet;

import android.content.Context;

import com.evgenii.jsevaluator.interfaces.JsCallback;
import com.tomoapp.tomowallet.helper.wallet.WalletHelper;

/**
 * Created by macbook on 12/21/17.
 */

public interface WalletDataSource {


    void createMnemonic(JsCallback callback);
    void createWallet(String mnemonic, WalletHelper.CreateWalletCallback callback);
    void createWallet(WalletHelper.CreateWalletCallback callback);
    void validateMnemonic(String mnemonic, JsCallback callback);
    void saveWallet(Wallet wallet);
    void saveWallet(Wallet wallet, String password);
    /*String getPrivateKey();
    String getAddress();
    String getMnemonic();*/

    String getPrivateKey(String password);
    String getAddress(String password);
    String getMnemonic(String password);
    void deleteWallet();
    boolean isWalletStored();
}
