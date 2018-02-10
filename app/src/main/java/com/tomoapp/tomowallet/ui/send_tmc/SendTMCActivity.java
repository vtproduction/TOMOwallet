package com.tomoapp.tomowallet.ui.send_tmc;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.airbnb.lottie.LottieAnimationView;
import com.tomoapp.tomowallet.R;
import com.tomoapp.tomowallet.base.BaseSocketActivity;
import com.tomoapp.tomowallet.base.MainApplication;
import com.tomoapp.tomowallet.helper.LogUtil;
import com.tomoapp.tomowallet.helper.ToastUtil;
import com.tomoapp.tomowallet.model.walletActionResponse.CashActionResponse;
import com.tomoapp.tomowallet.ui.home.HomeActivity;
import com.tomoapp.tomowallet.ui.home.cell.HeaderCell;
import com.tomoapp.tomowallet.ui.home.cell.LogCell;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by macbook on 12/27/17.
 */

public class SendTMCActivity extends BaseSocketActivity implements SendTMCContract.View {


    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.container_header)
    LinearLayout containerHeader;
    @BindView(R.id.txt_label_1)
    TextView txtLabel1;
    @BindView(R.id.btn_scan_qr)
    ImageView btnScanQr;
    @BindView(R.id.edt_receiver_address)
    EditText edtReceiverAddress;
    @BindView(R.id.txt_label_2)
    TextView txtLabel2;
    @BindView(R.id.txt_label_3)
    TextView txtLabel3;
    @BindView(R.id.edt_tmc_to_transfer)
    EditText edtTmcToTransfer;
    @BindView(R.id.btn_transfer)
    LinearLayout btnTransfer;
    @BindView(R.id.progress_mining)
    LottieAnimationView progressMining;
    @BindView(R.id.txt_send)
    TextView txtSend;

    private SendTMCContract.Presenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_tmc);
        ButterKnife.bind(this);
        MainApplication.setCurrentActivity(this);
        mPresenter = new SendTMCPresenter(this);
        mPresenter.init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.destroy();
    }

    @OnClick({R.id.btn_back, R.id.btn_scan_qr, R.id.btn_transfer})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                onBackPressed();
                break;
            case R.id.btn_scan_qr:
                startActivityForResult(new Intent(this, QRCodeScanActivity.class), 1101);
                break;
            case R.id.btn_transfer:
                mPresenter.onTransfer(edtReceiverAddress.getText().toString().trim(), edtTmcToTransfer.getText().toString().trim());
                break;
        }
    }


    @Override
    public void loadContent() {
        try {
            txtLabel3.setText(getString(R.string.amount_between).replace("[1]", mPresenter.getTMCInSideChain() + ""));
        } catch (Exception e) {
            LogUtil.e(e);
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void displayError(String error) {
        ToastUtil.show(error);
    }

    @Override
    public void confirmTransfer(final String address, final Double value) {
        LogUtil.i("confirmTransfer: " + address);
        new MaterialDialog.Builder(this)
                .title(getString(R.string.confirm_send))
                .content(getString(R.string.confirm_send_content).replace("[2]", address)
                        .replace("[1]", "" + value))
                .positiveText(getString(R.string.transfer))
                .negativeText(getString(R.string.cancel))
                .positiveColor(getResources().getColor(R.color.color_blue))
                .negativeColor(getResources().getColor(R.color.color_50))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                        mPresenter.performTransfer(address, value);
                    }
                })
                .cancelable(false)
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MainApplication.setCurrentActivity(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        MainApplication.setCurrentActivity(this);
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1101:
                if (resultCode == RESULT_OK && data != null) {
                    String address = data.getStringExtra("QR");
                    edtReceiverAddress.setText(address);
                }
                break;
        }
    }

    @Override
    public void onTransferring() {
        try {
            progressMining.setVisibility(View.VISIBLE);
            txtSend.setText(getString(R.string.transferring));
            btnTransfer.setEnabled(false);
        } catch (Exception e) {
            LogUtil.e(e);
        }
    }

    @Override
    public void onTransferred() {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        progressMining.setVisibility(View.GONE);
                        txtSend.setText(getString(R.string.transfer));
                        btnTransfer.setEnabled(true);
                    } catch (Exception e) {
                        LogUtil.e(e);
                    }
                    /**/
                }
            });

        } catch (Exception e) {
            LogUtil.e(e);
        }
    }


    @Override
    protected void doWhenTMCSent(CashActionResponse transactionDetail) {
        super.doWhenTMCSent(transactionDetail);
        LogUtil.d("onTMCSent from SEND TMC: " + transactionDetail.toString());
        onTransferred();
    }
}
