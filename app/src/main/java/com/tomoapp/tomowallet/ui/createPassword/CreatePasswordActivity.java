package com.tomoapp.tomowallet.ui.createPassword;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.gson.Gson;
import com.tomoapp.tomowallet.R;
import com.tomoapp.tomowallet.base.BaseActivity;
import com.tomoapp.tomowallet.helper.AppConstant;
import com.tomoapp.tomowallet.model.wallet.Wallet;
import com.tomoapp.tomowallet.ui.createWallet.CreateWalletActivity;
import com.tomoapp.tomowallet.ui.importWallet.ImportWalletSuccessActivity;
import com.tomoapp.tomowallet.ui.splash.SplashActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by cityme on 1/8/18.
 */

public class CreatePasswordActivity extends BaseActivity implements CreatePasswordContract.View{


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.txt_message)
    TextView txtMessage;
    @BindView(R.id.editText_password)
    EditText editTextPassword;
    @BindView(R.id.text_input_layout_password)
    TextInputLayout textInputLayoutPassword;
    @BindView(R.id.editText_password_again)
    EditText editTextPasswordAgain;
    @BindView(R.id.text_input_layout_password_again)
    TextInputLayout textInputLayoutPasswordAgain;
    @BindView(R.id.btn_recover_wallet)
    Button btnCreateWallet;
    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.txt_error_password)
    TextView txtErrorPassword;
    @BindView(R.id.txt_error_password_again)
    TextView txtErrorPasswordAgain;

    private CreatePasswordContract.Presenter mPresenter;
    public static final String ARGS_WALLET = "ARGS_WALLET";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_password);
        ButterKnife.bind(this);
        mPresenter = new CreatePasswordPresenter(this);
        mPresenter.init();

    }

    @Override
    public void setContent() {
        try {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            editTextPassword.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (txtErrorPassword.getVisibility() == View.VISIBLE)
                        txtErrorPassword.setVisibility(View.INVISIBLE);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            editTextPasswordAgain.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (txtErrorPasswordAgain.getVisibility() == View.VISIBLE)
                        txtErrorPasswordAgain.setVisibility(View.INVISIBLE);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void displayPasswordError(int messageId) {
        try {
            String message = "";
            if (messageId == R.string.mes_10)
                message = getString(R.string.mes_10).replace("[num]","" + AppConstant.PASSWORD_MIN_LENGTH);
            else if (messageId == R.string.mes_11)
                message = getString(R.string.mes_11).replace("[num]","" + AppConstant.PASSWORD_MAX_LENGTH);
            else
                message = getString(messageId);
            txtErrorPassword.setText(message);
            txtErrorPassword.setVisibility(View.VISIBLE);
            txtErrorPasswordAgain.setVisibility(View.INVISIBLE);
            YoYo.with(Techniques.Shake).duration(500).playOn(textInputLayoutPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void displayPasswordConfirmError(int messageId) {
        try {
            String message = getString(messageId);
            txtErrorPasswordAgain.setText(message);
            txtErrorPasswordAgain.setVisibility(View.VISIBLE);
            txtErrorPassword.setVisibility(View.INVISIBLE);
            YoYo.with(Techniques.Shake).duration(500).playOn(textInputLayoutPasswordAgain);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDone(String password) {
        String walletData = getIntent().getStringExtra(ARGS_WALLET);
        if (walletData == null || walletData.isEmpty()){
            startActivity(new Intent(this, CreateWalletActivity.class).putExtra("PSW", password));
            finish();
        }else {
            Wallet wallet  = new Gson().fromJson(walletData, Wallet.class);
            if (wallet == null){
                startActivity(new Intent(this, CreateWalletActivity.class).putExtra("PSW", password));
                finish();
            }else {
                mPresenter.saveCurrentWallet(password, wallet);
                onSaveWalletDone(password);

            }
        }


    }

    private void onSaveWalletDone(String password){
        startActivity(new Intent(this, ImportWalletSuccessActivity.class).putExtra(ImportWalletSuccessActivity.ARGS_PASSWORD, password));
        finishAffinity();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @OnClick({R.id.btn_back, R.id.btn_recover_wallet})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                onBackPressed();
                break;
            case R.id.btn_recover_wallet:
                mPresenter.checkPassword(editTextPassword.getText().toString(), editTextPasswordAgain.getText().toString());
                break;
        }
    }
}
