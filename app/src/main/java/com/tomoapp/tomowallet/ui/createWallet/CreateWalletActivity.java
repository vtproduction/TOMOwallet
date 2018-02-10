package com.tomoapp.tomowallet.ui.createWallet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.tomoapp.tomowallet.R;
import com.tomoapp.tomowallet.base.BaseActivity;
import com.tomoapp.tomowallet.customView.CreateWalletLoadingDialog;
import com.tomoapp.tomowallet.customView.LoadingDialog;
import com.tomoapp.tomowallet.helper.LogUtil;
import com.tomoapp.tomowallet.model.wallet.Wallet;
import com.tomoapp.tomowallet.ui.backupWallet.BackupWalletActivity;
import com.tomoapp.tomowallet.ui.backupWallet.BackupWalletContract;
import com.tomoapp.tomowallet.ui.createPincode.CreatePinCodeActivity;
import com.tomoapp.tomowallet.ui.home.HomeActivity;
import com.tomoapp.tomowallet.ui.splash.SplashActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by macbook on 12/21/17.
 */

public class CreateWalletActivity extends BaseActivity implements CreateWalletContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn_backup_wallet)
    Button btnBackupWallet;
    @BindView(R.id.btn_later)
    TextView btnLater;
    @BindView(R.id.content_container)
    LinearLayout contentContainer;
    private CreateWalletPresenter mPresenter;
    private boolean isOnCreating;
    private String password;
    private CreateWalletLoadingDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_wallet);
        ButterKnife.bind(this);
        setStatusbarDefaultColor();
        this.password = getIntent().getStringExtra("PSW");
        mPresenter = new CreateWalletPresenter(this, password);
        mPresenter.init();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onCreating() {
        isOnCreating = true;
        if (dialog == null)
            dialog = new CreateWalletLoadingDialog(this, new CreateWalletLoadingDialog.OnDialogDismiss() {
                @Override
                public void onDismiss() {
                    isOnCreating = false;
                    if (dialog != null) dialog.hide();
                    contentContainer.setVisibility(View.VISIBLE);

                }
            });
        dialog.show(getString(R.string.creating_your_wallet));
        contentContainer.setVisibility(View.GONE);
    }

    @Override
    public void onCreated(Wallet wallet) {
        dialog.onDone();
    }

    @Override
    public void onDone() {
        try {
            startActivity(new Intent(this, SplashActivity.class));
            finishAffinity();

        } catch (Exception e) {
            LogUtil.e(e);
        }
    }

    public void onRecoverWallet() {
        try {
            startActivity(new Intent(this, BackupWalletActivity.class));
        } catch (Exception e) {
            LogUtil.e(e);
        }
    }



    @Override
    public void onBack() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        return;
    }

    @Override
    public void onFail(String why) {
        isOnCreating = false;
        new MaterialDialog.Builder(this)
                .title(getString(R.string.error))
                .content(why)
                .positiveText(getString(R.string.close))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        finish();
                    }
                })
                .cancelable(false)
                .show();
    }




    @OnClick({R.id.btn_backup_wallet, R.id.btn_later})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_backup_wallet:
                onRecoverWallet();
                break;
            case R.id.btn_later:
                onDone();
                break;
        }
    }
}
