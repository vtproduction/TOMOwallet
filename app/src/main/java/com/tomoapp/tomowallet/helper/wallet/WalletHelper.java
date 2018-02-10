package com.tomoapp.tomowallet.helper.wallet;

import android.content.Context;
import android.content.res.AssetManager;

import com.evgenii.jsevaluator.JsEvaluator;
import com.evgenii.jsevaluator.interfaces.JsCallback;
import com.tomoapp.tomowallet.helper.LogUtil;
import com.tomoapp.tomowallet.model.wallet.Wallet;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * Created by macbook on 12/21/17.
 */

public class WalletHelper {
    JsEvaluator mJsEvaluator;
    private Scanner scanner;
    String jsSource;
    private Context context;

    public interface CreateWalletCallback{
        void onStartCreate();
        void onCreated(Wallet wallet);
        void onFail(String why);
    }

    public WalletHelper(Context context) {
        this.context = context;
        mJsEvaluator = new JsEvaluator(context);

    }

    private String ReadFile(String fileName) throws IOException {
        final AssetManager am = context.getAssets();
        final InputStream inputStream = am.open(fileName);

        scanner = new Scanner(inputStream, "UTF-8");
        return scanner.useDelimiter("\\A").next();
    }


    private String loadJs() {
        try {
            String fileName = "ethgen/bundle.js";
            if (jsSource == null || jsSource.isEmpty())
                return ReadFile(fileName);
            return jsSource;
        } catch (final IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }


    public void createWallet(final CreateWalletCallback callback){
        try {
            callback.onStartCreate();
            String jsString = "var jsEvaluatorResult = ''; ";
            jsString += loadJs();
            jsString += "var mnemonic = bip39.generateMnemonic();";
            jsString += "var key = hdkey.fromMasterSeed(bip39.mnemonicToSeed(mnemonic));";
            jsString += "var wallet = key.derivePath(\"m/44'/60'/0'/0/0\").getWallet();";
            jsString += "jsEvaluatorResult += mnemonic + '<split-here>';";
            jsString += "jsEvaluatorResult += '0x'+ wallet.getAddress().toString('hex') + '<split-here>';";
            jsString += "jsEvaluatorResult += wallet.getPrivateKey().toString('hex');";
            jsString += "jsEvaluatorResult;";

            mJsEvaluator.evaluate(jsString, new JsCallback() {
                @Override
                public void onResult(String s) {
                    String[] splits = s.split("<split-here>");
                    Wallet wallet = new Wallet(splits[0],splits[1],splits[2]);
                    callback.onCreated(wallet);
                }

                @Override
                public void onError(String s) {
                    callback.onFail(s);
                }
            });
        }catch (Exception e){
            LogUtil.e(e);
        }
    }

    public void createMnemonic(final JsCallback callback){
        try {
            String jsString = "var jsEvaluatorResult = ''; ";
            jsString += loadJs();
            jsString += "var mnemonic = bip39.generateMnemonic();";
            jsString += "jsEvaluatorResult += mnemonic";
            jsString += "jsEvaluatorResult;";
            mJsEvaluator.evaluate(jsString, callback);
        }catch (Exception e){
            LogUtil.e(e);
        }
    }

    public void validateMnemonic(String mnemonic, final JsCallback callback){
        try {
            String jsString = "var jsEvaluatorResult = ''; ";
            jsString += loadJs();
            jsString += "var validate = bip39.validateMnemonic('"+mnemonic+"');";
            jsString += "jsEvaluatorResult += validate;";

            mJsEvaluator.evaluate(jsString, callback);
        }catch (Exception e){
            LogUtil.e(e);
        }
    }

    public void createWallet(final String mnemonic, final CreateWalletCallback callback){
        try {
            String jsString = "var jsEvaluatorResult = ''; ";
            jsString += loadJs();
            jsString += "var mnemonic = '"+mnemonic+"';";
            jsString += "var key = hdkey.fromMasterSeed(bip39.mnemonicToSeed(mnemonic));";
            jsString += "var wallet = key.derivePath(\"m/44'/60'/0'/0/0\").getWallet();";
            jsString += "jsEvaluatorResult += mnemonic + '<split-here>';";
            jsString += "jsEvaluatorResult += '0x'+ wallet.getAddress().toString('hex') + '<split-here>';";
            jsString += "jsEvaluatorResult += wallet.getPrivateKey().toString('hex');";
            jsString += "jsEvaluatorResult;";
            mJsEvaluator.evaluate(jsString,  new JsCallback() {
                @Override
                public void onResult(String s) {
                    LogUtil.d("FROM CREATE WALLET - ON RESULT: " + s);
                    String[] splits = s.split("<split-here>");
                    Wallet wallet = new Wallet(mnemonic,splits[1],splits[2]);
                    callback.onCreated(wallet);
                }

                @Override
                public void onError(String s) {
                    LogUtil.d("FROM CREATE WALLET - ON ERROR: " + s);
                    callback.onFail(s);
                }
            });
        }catch (Exception e){
            LogUtil.e(e);
        }
    }


}
