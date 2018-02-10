package com.tomoapp.tomowallet.ui.importWallet;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.gson.Gson;
import com.tomoapp.tomowallet.R;
import com.tomoapp.tomowallet.base.BaseActivity;
import com.tomoapp.tomowallet.customView.LoadingDialog;
import com.tomoapp.tomowallet.helper.KeyboardUtil;
import com.tomoapp.tomowallet.helper.LogUtil;
import com.tomoapp.tomowallet.helper.ToastUtil;
import com.tomoapp.tomowallet.model.wallet.Wallet;
import com.tomoapp.tomowallet.ui.createPassword.CreatePasswordActivity;
import com.tomoapp.tomowallet.ui.createPincode.CreatePinCodeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by cityme on 1/5/18.
 */

public class ImportWalletActivity extends BaseActivity implements ImportWalletContract.View {


    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn_restore_now)
    Button btnRestoreNow;
    @BindView(R.id.container_1)
    LinearLayout container1;
    @BindView(R.id.backup_message)
    TextView backupMessage;
    @BindView(R.id.edt_backup_phrase)
    EditText edtBackupPhrase;
    @BindView(R.id.btn_restore)
    Button btnRestore;
    @BindView(R.id.container_2)
    LinearLayout container2;
    private ImportWalletContract.Presenter mPresenter;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_wallet);
        ButterKnife.bind(this);
        loadingDialog = new LoadingDialog(this);
        mPresenter = new ImportWalletPresenter(this);
        mPresenter.init();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void setContent() {
        try {
            container2.setVisibility(View.GONE);
            edtBackupPhrase.setText("");
            backupMessage.setVisibility(View.INVISIBLE);
            edtBackupPhrase.setBackground(getResources().getDrawable(R.drawable.bg_edt_restore_wallet));
            edtBackupPhrase.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (backupMessage.getVisibility() == View.VISIBLE){
                        backupMessage.setVisibility(View.INVISIBLE);
                        edtBackupPhrase.setBackground(getResources().getDrawable(R.drawable.bg_edt_restore_wallet));
                    }
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
    public void onMnemonicInvalid(int messageId) {
        onMnemonicInvalid(getString(messageId));
    }

    @Override
    public void onMnemonicInvalid(String message) {
        try {
            loadingDialog.dismiss();
            backupMessage.setText(message);
            backupMessage.setVisibility(View.VISIBLE);
            edtBackupPhrase.setBackground(getResources().getDrawable(R.drawable.bg_edt_restore_wallet_error));
            YoYo.with(Techniques.Shake)
                    .duration(800)
                    .playOn(edtBackupPhrase);
            YoYo.with(Techniques.Shake)
                    .duration(800)
                    .playOn(backupMessage);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRecover() {
        try {
            loadingDialog.show(getString(R.string.recovering_your_wallet));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRecoverSuccess(final Wallet wallet) {
        try {
            loadingDialog.dismiss();
            new MaterialDialog.Builder(this)
                    .title(getString(R.string.wallet_found))
                    .content(getString(R.string.mes_27).replace("[add]", wallet.getAddress()))
                    .positiveText(getString(R.string.setup_new_password))
                    .cancelable(false)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            goToCreatePIN(wallet);
                        }
                    }).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRecoverFail(String why) {
        ToastUtil.show(why);
        setContent();
    }




    private void goToCreatePIN(Wallet wallet) {
        try {
            startActivity(new Intent(this, CreatePasswordActivity.class)
                    .putExtra(CreatePinCodeActivity.ARGS_WALLET, new Gson().toJson(wallet)));
        } catch (Exception e) {
            LogUtil.e(e);
        }
    }

    @OnClick({R.id.btn_back, R.id.btn_restore_now, R.id.btn_restore})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                onBackPressed();
                break;
            case R.id.btn_restore_now:
                container1.setVisibility(View.GONE);
                container2.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_restore:
                KeyboardUtil.hideSoftKeyboard(this);
                mPresenter.readMnemonic(edtBackupPhrase.getText().toString());
                break;
        }
    }
}
