package com.tomoapp.tomowallet.model.walletAction;

import android.content.Context;

import com.tomoapp.tomowallet.helper.LogUtil;
import com.tomoapp.tomowallet.helper.api.APIEndpoint;
import com.tomoapp.tomowallet.helper.api.APIService;
import com.tomoapp.tomowallet.model.wallet.WalletDataSource;
import com.tomoapp.tomowallet.model.wallet.WalletRepository;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by macbook on 12/25/17.
 */

public class WalletActionRepository implements WalletActionDataSource {
    private WalletDataSource mWallet;
    private APIEndpoint mAPI;
    private String PIN;

    public WalletActionRepository(Context context, String PIN) {
        this.mWallet = new WalletRepository(context);
        this.mAPI = APIService.getInstance().build();
        this.PIN = PIN;
    }

    @Override
    public void performReward(final ActionExecuteListener callback) {
        mAPI.reward(mWallet.getAddress(PIN))
                .enqueue(new Callback<JSONObject>() {
                    @Override
                    public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                        LogUtil.d("WalletActionRepository: reward request successful !");
                    }

                    @Override
                    public void onFailure(Call<JSONObject> call, Throwable t) {
                        LogUtil.d("WalletActionRepository: reward request fail: " + t.getLocalizedMessage());
                        callback.onFail(1, t.getLocalizedMessage());
                    }
                });
    }

    @Override
    public void cashIn(double value, final ActionExecuteListener callback) {
        mAPI.cashIn(mWallet.getAddress(PIN), value)
                .enqueue(new Callback<JSONObject>() {
                    @Override
                    public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                        LogUtil.d("WalletActionRepository: cashIn request successful !");
                    }

                    @Override
                    public void onFailure(Call<JSONObject> call, Throwable t) {
                        LogUtil.d("WalletActionRepository: cashIn request fail: " + t.getLocalizedMessage());
                        callback.onFail(1, t.getLocalizedMessage());
                    }
                });
    }

    @Override
    public void cashOut(double value, final ActionExecuteListener callback) {
        mAPI.cashOut(mWallet.getAddress(PIN), value)
                .enqueue(new Callback<JSONObject>() {
                    @Override
                    public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                        LogUtil.d("WalletActionRepository: cashOut request successful !");
                    }

                    @Override
                    public void onFailure(Call<JSONObject> call, Throwable t) {
                        LogUtil.d("WalletActionRepository: cashOut request fail: " + t.getLocalizedMessage());
                        callback.onFail(1, t.getLocalizedMessage());
                    }
                });
    }
}
