package com.tomoapp.tomowallet.ui.checkPassword;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.tomoapp.tomowallet.R;
import com.tomoapp.tomowallet.base.MainApplication;
import com.tomoapp.tomowallet.helper.LogUtil;
import com.tomoapp.tomowallet.model.wallet.WalletDataSource;
import com.tomoapp.tomowallet.model.wallet.WalletRepository;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by cityme on 1/3/18.
 */

public class CheckPasswordDialog {


    TextView txtHeaderMess;
    EditText edtPassword;
    TextView btnDone, btnCancel;
    private String title;
    private MaterialDialog dialog;
    private CheckPinDialogListener callback;
    private WalletDataSource mWallet;

    public static CheckPasswordDialog newInstance(CheckPinDialogListener callback) {
        CheckPasswordDialog checkPinDialog = new CheckPasswordDialog();
        checkPinDialog.title = MainApplication.getCurrentActivity().getString(R.string.mes_15);
        checkPinDialog.callback = callback;
        checkPinDialog.mWallet = new WalletRepository(MainApplication.getCurrentActivity());
        return checkPinDialog;
    }

    public void show() {

        title = MainApplication.getCurrentActivity().getString(R.string.mes_15);
        init();
        dialog.show();
        //clearState();
        edtPassword.requestFocus();
    }

    public void show(int titleId) {

        title = MainApplication.getCurrentActivity().getString(titleId);
        init();
        dialog.show();
        edtPassword.requestFocus();
    }

    public void show(String title) {

        this.title = title;
        init();
        dialog.show();
        edtPassword.requestFocus();
        //clearState();
    }





    private void init() {
        try {
            View mainView = LayoutInflater.from(MainApplication.getCurrentActivity())
                    .inflate(R.layout.part_pin_code_lock, null);
            txtHeaderMess = mainView.findViewById(R.id.txt_header_mess);
            txtHeaderMess.setText(title);
            edtPassword = mainView.findViewById(R.id.edt_password);
            btnDone = mainView.findViewById(R.id.btn_confirm);
            btnCancel = mainView.findViewById(R.id.btn_cancel);
            btnDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkPIN(edtPassword.getText().toString());
                }
            });
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    callback.onCancel();
                }
            });
            edtPassword.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (!txtHeaderMess.getText().toString().equals(MainApplication.getCurrentActivity().getString(R.string.mes_15))){
                        txtHeaderMess.setText(MainApplication.getCurrentActivity().getString(R.string.mes_15));
                        txtHeaderMess.setTextColor(MainApplication.getCurrentActivity().getResources().getColor(R.color.color_8));
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            dialog = new MaterialDialog.Builder(MainApplication.getCurrentActivity())
                    .customView(mainView, true)
                    .cancelable(false)
                    .build();
        } catch (Exception e) {
            LogUtil.e(e);
        }
    }

    private void checkPIN(String pin) {
        String address = mWallet.getAddress(pin);
        if (address == null || address.isEmpty()) {
            onPINIncorrect();
        } else {
            onPINSuccess(pin);
        }
    }


    private void onPINIncorrect() {
        try {
            txtHeaderMess.setText(MainApplication.get().getString(R.string.mes_07));
            txtHeaderMess.setTextColor(MainApplication.get().getResources().getColor(R.color.color_50));
            YoYo.with(Techniques.Shake)
                    .duration(300)
                    .playOn(edtPassword);
        } catch (Resources.NotFoundException e) {
            LogUtil.e(e);
        }
    }


    private void onPINSuccess(String pin) {
        callback.onPinChecked(pin);
        dialog.dismiss();
    }



    public interface CheckPinDialogListener {
        void onPinChecked(String Pin);

        void onCancel();
    }

}
