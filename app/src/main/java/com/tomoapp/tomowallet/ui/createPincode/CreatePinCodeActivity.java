package com.tomoapp.tomowallet.ui.createPincode;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andrognito.pinlockview.IndicatorDots;
import com.andrognito.pinlockview.PinLockListener;
import com.andrognito.pinlockview.PinLockView;
import com.tomoapp.tomowallet.R;
import com.tomoapp.tomowallet.base.BaseActivity;
import com.tomoapp.tomowallet.helper.LogUtil;
import com.tomoapp.tomowallet.helper.ToastUtil;
import com.tomoapp.tomowallet.ui.splash.SplashActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by cityme on 1/2/18.
 */

public class CreatePinCodeActivity extends BaseActivity implements CreatePinCodeContract.View{


    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.toolbar)
    LinearLayout toolbar;
    @BindView(R.id.txt_header_mess)
    TextView txtHeaderMess;
    @BindView(R.id.pin_lock_view)
    PinLockView pinLockView;
    @BindView(R.id.indicator_dots)
    IndicatorDots indicatorDots;

    public static final String ARGS_WALLET = "ARGS_WALLET";
    private CreatePinCodeContract.Presenter mPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pin_lock);
        ButterKnife.bind(this);

        mPresenter = new CreatePinCodePresenter(this);
        mPresenter.init();
    }

    @OnClick(R.id.btn_back)
    public void onViewClicked() {
        onBackPressed();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public CreatePinCodeActivity getActivity() {
        return this;
    }

    @Override
    public void loadData() {
        try {
            pinLockView.setPinLength(4);
            pinLockView.setPinLockListener(new PinLockListener() {
                @Override
                public void onComplete(String pin) {
                    mPresenter.onSubmitPinCode(pin);
                }

                @Override
                public void onEmpty() {

                }

                @Override
                public void onPinChange(int pinLength, String intermediatePin) {

                }
            });
            indicatorDots.setPinLength(4);
            pinLockView.attachIndicatorDots(indicatorDots);
        } catch (Exception e) {
            LogUtil.e(e);
        }
    }

    @Override
    public void switchToConfirm() {
        try {
            pinLockView.resetPinLockView();
            txtHeaderMess.setText(getString(R.string.mes_05));
        } catch (Exception e) {
            LogUtil.e(e);
        }
    }

    @Override
    public void displayError(int error) {
        try {
            ToastUtil.show(error);
            pinLockView.resetPinLockView();
        } catch (Exception e) {
            LogUtil.e(e);
        }
    }

    @Override
    public void displayError(String error) {
        try {
            ToastUtil.show(error);
            pinLockView.resetPinLockView();
        } catch (Exception e) {
            LogUtil.e(e);
        }
    }

    @Override
    public void onSuccess() {
        LogUtil.dumpSharedPreferences(this, "data");
        finishAffinity();
        startActivity(new Intent(this, SplashActivity.class));
        finish();
    }
}
