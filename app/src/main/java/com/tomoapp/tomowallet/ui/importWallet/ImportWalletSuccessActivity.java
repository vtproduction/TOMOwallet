package com.tomoapp.tomowallet.ui.importWallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;

import com.airbnb.lottie.LottieAnimationView;
import com.tomoapp.tomowallet.R;
import com.tomoapp.tomowallet.base.BaseActivity;
import com.tomoapp.tomowallet.ui.splash.SplashActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by cityme on 1/11/18.
 */

public class ImportWalletSuccessActivity extends BaseActivity {


    @BindView(R.id.animation_view)
    LottieAnimationView animationView;
    @BindView(R.id.btn_done)
    Button btnDone;
    public static final String ARGS_PASSWORD = "ARGS_PASSWORD";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_success);
        ButterKnife.bind(this);
    }

    @Override
    public void onBackPressed() {
        onDone();
    }

    @OnClick(R.id.btn_done)
    public void onViewClicked() {
        onDone();
    }


    private void onDone(){
        String password = getIntent().getStringExtra(ARGS_PASSWORD);
        startActivity(new Intent(this, SplashActivity.class).putExtra(SplashActivity.ARGS_PASSWORD, password));
        finishAffinity();
    }
}
