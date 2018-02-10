package com.tomoapp.tomowallet.ui.checkPassword;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.andrognito.pinlockview.PinLockListener;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.tomoapp.tomowallet.R;
import com.tomoapp.tomowallet.base.BaseActivity;
import com.tomoapp.tomowallet.helper.KeyboardUtil;
import com.tomoapp.tomowallet.helper.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by cityme on 1/3/18.
 */

public class CheckPasswordActivity extends BaseActivity implements CheckPasswordContract.View {


    @BindView(R.id.txt_header_mess)
    TextView txtHeaderMess;
    @BindView(R.id.edt_password)
    EditText edtPassword;
    @BindView(R.id.btn_done)
    Button btnDone;
    private CheckPasswordContract.Presenter mPresenter;
    public static final String ARGS_PIN = "PIN";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_code_lock);
        ButterKnife.bind(this);

        mPresenter = new CheckPasswordPresenter(this);

        try {
            edtPassword.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (!txtHeaderMess.getText().toString().equals(getString(R.string.mes_15))){
                        txtHeaderMess.setText(getString(R.string.mes_15));
                        txtHeaderMess.setTextColor(getResources().getColor(R.color.color_8));
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        } catch (Exception e) {
            LogUtil.e(e);
        }
        edtPassword.post(new Runnable() {
            @Override
            public void run() {
                final InputMethodManager imm = (InputMethodManager) edtPassword.getContext().getSystemService(INPUT_METHOD_SERVICE);
                imm.showSoftInput(edtPassword, InputMethodManager.SHOW_IMPLICIT);
                edtPassword.requestFocus(); // needed if you have more then one input
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setContent();
    }

    @Override
    public void setContent() {
        try {
            txtHeaderMess.setText(getString(R.string.mes_15));
            txtHeaderMess.setTextColor(getResources().getColor(R.color.color_8));
            edtPassword.post(new Runnable() {
                @Override
                public void run() {
                    final InputMethodManager imm = (InputMethodManager) edtPassword.getContext().getSystemService(INPUT_METHOD_SERVICE);
                    imm.showSoftInput(edtPassword, InputMethodManager.SHOW_IMPLICIT);
                    edtPassword.requestFocus(); // needed if you have more then one input
                }
            });
        } catch (Resources.NotFoundException e) {
            LogUtil.e(e);
        }
    }

    @Override
    public BaseActivity getActivity() {
        return this;
    }

    @Override
    public void onPINIncorrect() {
        try {
            txtHeaderMess.setText(getString(R.string.mes_07));
            txtHeaderMess.setTextColor(getResources().getColor(R.color.color_50));
            YoYo.with(Techniques.Shake)
                    .duration(300)
                    .playOn(edtPassword);
        } catch (Resources.NotFoundException e) {
            LogUtil.e(e);
        }
    }

    @Override
    public void onPINSuccess(String pin) {
        Intent intent = new Intent();
        intent.putExtra(ARGS_PIN, pin);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
    }


    @OnClick(R.id.btn_done)
    public void onViewClicked() {
        mPresenter.checkPIN(edtPassword.getText().toString());
    }
}
