package com.tomoapp.tomowallet.ui.backupWallet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tomoapp.tomowallet.R;
import com.tomoapp.tomowallet.base.BaseActivity;
import com.tomoapp.tomowallet.ui.splash.SplashActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by cityme on 1/10/18.
 */

public class BackupWalletActivity extends BaseActivity implements BackupWalletContract.View {


    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.txt_mnemonic)
    TextView txtMnemonic;
    @BindView(R.id.backup_container)
    LinearLayout backupContainer;
    @BindView(R.id.btn_backup_wallet)
    Button btnBackupWallet;
    @BindView(R.id.content_container)
    LinearLayout contentContainer;
    @BindView(R.id.btn_done)
    Button btnDone;

    private BackupWalletContract.Presenter mPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backup_wallet);
        ButterKnife.bind(this);
        mPresenter = new BackupWalletPresenter(this);
        mPresenter.init();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void onResume() {
        super.onResume();
        setContent();
    }

    @Override
    public void setContent() {
        try {
            txtMnemonic.setText("");
            backupContainer.setVisibility(View.GONE);
            contentContainer.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showMnemonic(String mnemonic, String password) {
        try {
            txtMnemonic.setText(mnemonic);
            btnDone.setTag(password);
            backupContainer.setVisibility(View.VISIBLE);
            contentContainer.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void onBtnBack(){
        try {
            String password = (String) btnDone.getTag();
            if (password == null || password.isEmpty())
                startActivity(new Intent(this, SplashActivity.class));
            else
                startActivity(new Intent(this, SplashActivity.class).putExtra(SplashActivity.ARGS_PASSWORD, password));
            finishAffinity();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.btn_back, R.id.btn_backup_wallet, R.id.btn_done})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                onBackPressed();
                break;
            case R.id.btn_done:
                onBtnBack();
                break;
            case R.id.btn_backup_wallet:
                mPresenter.onCheckBtnPressed();
                break;
        }
    }
}
