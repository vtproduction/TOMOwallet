package com.tomoapp.tomowallet.ui.splash;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.crashlytics.android.Crashlytics;
import com.tomoapp.tomowallet.R;
import com.tomoapp.tomowallet.base.BaseActivity;
import com.tomoapp.tomowallet.helper.AppRequest;
import com.tomoapp.tomowallet.helper.LogUtil;
import com.tomoapp.tomowallet.ui.createWallet.CreateWalletActivity;
import com.tomoapp.tomowallet.ui.createWallet.CreateWalletInstructionActivity;
import com.tomoapp.tomowallet.ui.home.HomeActivity;
import com.tomoapp.tomowallet.ui.importWallet.ImportWalletActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by macbook on 12/21/17.
 */

public class SplashActivity extends BaseActivity implements SplashContract.View {

    @BindView(R.id.img_logo_1)
    ImageView imgLogo1;
    @BindView(R.id.img_logo_2)
    ImageView imgLogo2;
    @BindView(R.id.img_logo_3)
    ImageView imgLogo3;
    @BindView(R.id.container_detail)
    LinearLayout containerDetail;
    @BindView(R.id.btn_create_wallet)
    Button btnCreateWallet;
    @BindView(R.id.anchor_2)
    TextView anchor2;
    @BindView(R.id.btn_recover_wallet)
    Button btnRecoverWallet;
    private SplashContract.Presenter mPresenter;
    public static final String ARGS_PASSWORD = "ARGS_PASSWORD";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        ButterKnife.bind(this);
        this.mPresenter = new SplashPresenter(this);

        mPresenter.init();
        //Crashlytics.getInstance().crash();
    }


    @Override
    protected void onDestroy() {
        if (mPresenter != null)
            mPresenter.destroy();
        super.onDestroy();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void goToCreateWallet() {
        startActivity(new Intent(this, CreateWalletInstructionActivity.class));
        //finish();
    }

    @Override
    public void goToRecoverWallet() {
        startActivity(new Intent(this, ImportWalletActivity.class));
        //finish();
    }


    @Override
    public void goToMain() {
        String password = getIntent().getStringExtra(ARGS_PASSWORD);
        if (password == null) password = "";
        startActivityForResult(new Intent(this, HomeActivity.class).putExtra(ARGS_PASSWORD, password), AppRequest.REQ_CHECK_PIN);
        finish();
    }

    @OnClick({R.id.btn_create_wallet, R.id.btn_recover_wallet})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_create_wallet:
                goToCreateWallet();
                break;
            case R.id.btn_recover_wallet:
                goToRecoverWallet();
                break;
        }
    }


    @Override
    public void setContent() {
        try {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    imgLogo2.animate().rotationBy(360).withEndAction(this).setDuration(7000).setInterpolator(new LinearInterpolator()).start();
                    imgLogo3.animate().rotationBy(-360).withEndAction(this).setDuration(7000).setInterpolator(new LinearInterpolator()).start();
                }
            };

            imgLogo2.animate().rotationBy(360).withEndAction(runnable).setDuration(7000).setInterpolator(new LinearInterpolator()).start();
            imgLogo3.animate().rotationBy(-360).withEndAction(runnable).setDuration(7000).setInterpolator(new LinearInterpolator()).start();
            //txtLabel1.setText(builder);
        } catch (Exception e) {
            LogUtil.e(e);
        }
    }
}
