package com.tomoapp.tomowallet.model.wallet;

import android.content.Context;
import android.content.SharedPreferences;

import com.evgenii.jsevaluator.interfaces.JsCallback;
import com.tomoapp.tomowallet.helper.crypto.AESCrypt;
import com.tomoapp.tomowallet.base.MainApplication;
import com.tomoapp.tomowallet.helper.LogUtil;
import com.tomoapp.tomowallet.helper.wallet.WalletHelper;


/**
 * Created by macbook on 12/21/17.
 */

public class WalletRepository implements WalletDataSource {
    WalletHelper walletHelper;
    private Context context;
    public WalletRepository(Context context) {
        this.context = context;
        this.walletHelper = new WalletHelper(context);
    }

    @Override
    public void createMnemonic(JsCallback callback) {
        try {
            walletHelper.createMnemonic(callback);
        }catch (Exception e){
            LogUtil.e(e);
        }
    }

    @Override
    public void createWallet(String mnemonic, WalletHelper.CreateWalletCallback callback) {
        try {
            walletHelper.createWallet(mnemonic, callback);
        }catch (Exception e){
            LogUtil.e(e);
        }
    }

    @Override
    public void createWallet(WalletHelper.CreateWalletCallback callback) {
        try {
            walletHelper.createWallet(callback);
        }catch (Exception e){
            LogUtil.e(e);
        }
    }

    @Override
    public void validateMnemonic(String mnemonic, JsCallback callback) {
        try {
            walletHelper.validateMnemonic(mnemonic, callback);
        }catch (Exception e){
            LogUtil.e(e);
        }
    }

    @Override
    public void saveWallet(Wallet wallet) {
        saveWallet(wallet, "ABC-DEF");
    }

    @Override
    public void saveWallet(Wallet wallet, String cipher) {
        try{
            String privateKey = wallet.getPrivateKey();
            String address = wallet.getAddress();
            String mnemonic = wallet.getMnemonic();
            String encryptedMnemonic = AESCrypt.encrypt(cipher, mnemonic);
            String encryptedPrivateKey = AESCrypt.encrypt(cipher, privateKey);
            String encryptedAddress = AESCrypt.encrypt(cipher, address);
            String finalEncrypted = AESCrypt.encrypt(cipher, encryptedMnemonic + cipher + encryptedAddress + cipher + encryptedPrivateKey);
            SharedPreferences preferences
                    = context.getSharedPreferences("data", Context.MODE_PRIVATE);
            preferences.edit().putString("storage-data",finalEncrypted).apply();
        }catch (Exception e){
            LogUtil.e(e);
        }
    }

    /*@Override
    public String getPrivateKey() {
        return getPrivateKey("ABC-DEF");
    }*/

    @Override
    public String getPrivateKey(String cipher) {
        try {
            String encryptedString = context.getSharedPreferences("data", Context.MODE_PRIVATE)
                    .getString("storage-data","");
            if (encryptedString.isEmpty())
                return "";
            String tmp1 = AESCrypt.decrypt(cipher, encryptedString);
            String tmp2 = tmp1.split(cipher)[2];
            return AESCrypt.decrypt(cipher, tmp2);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /*@Override
    public String getMnemonic() {
        return getMnemonic("ABC-DEF");
    }*/

    @Override
    public String getMnemonic(String cipher) {
        try {
            String encryptedString = context.getSharedPreferences("data", Context.MODE_PRIVATE)
                    .getString("storage-data","");
            if (encryptedString.isEmpty())
                return "";

            String tmp1 = AESCrypt.decrypt(cipher, encryptedString);
            String tmp2 = tmp1.split(cipher)[0];
            return AESCrypt.decrypt(cipher, tmp2);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /*@Override
    public String getAddress() {
        return getAddress("ABC-DEF");
    }*/

    @Override
    public String getAddress(String cipher) {
        try {
            String encryptedString = context.getSharedPreferences("data", Context.MODE_PRIVATE)
                    .getString("storage-data","");
            if (encryptedString.isEmpty())
                return "";
            String tmp1 = AESCrypt.decrypt(cipher, encryptedString);
            String tmp2 = tmp1.split(cipher)[1];
            return AESCrypt.decrypt(cipher, tmp2);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public void deleteWallet() {
        try {
            SharedPreferences preferences
                    = context.getSharedPreferences("data", Context.MODE_PRIVATE);
            preferences.edit().clear().apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isWalletStored() {
        try {
            String encryptedString = context.getSharedPreferences("data", Context.MODE_PRIVATE)
                    .getString("storage-data","");
            return !encryptedString.isEmpty();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
