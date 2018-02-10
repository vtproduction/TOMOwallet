package com.tomoapp.tomowallet.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.zxing.common.StringUtils;
import com.tomoapp.tomowallet.R;
import com.tomoapp.tomowallet.helper.DateTimeUtil;
import com.tomoapp.tomowallet.helper.LogUtil;
import com.tomoapp.tomowallet.model.userInfo.pojo.Log;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by cityme on 1/4/18.
 */

public class LogDetailDialog {

    @BindView(R.id.txt_label)
    TextView txtLabel;
    @BindView(R.id.txt_message)
    TextView txtMessage;
    @BindView(R.id.txt_transaction_time)
    TextView txtTransactionTime;
    @BindView(R.id.txt_transaction_in_tomochain)
    TextView txtTransactionInTomochain;
    @BindView(R.id.txt_transaction_in_ethereum)
    TextView txtTransactionInEthereum;
    @BindView(R.id.container_transaction_in_eth)
    LinearLayout containerTransactionInEth;
    @BindView(R.id.txt_tmc_in_tomo)
    TextView txtTmcInTomo;
    @BindView(R.id.txt_total_tmc)
    TextView txtTotalTmc;
    @BindView(R.id.txt_tmc_in_eth)
    TextView txtTmcInEth;

    private Context context;
    private MaterialDialog dialog;

    public static LogDetailDialog newInstance(Context context) {
        LogDetailDialog dialog = new LogDetailDialog();
        dialog.context = context;
        dialog.init();
        return dialog;
    }


    private void init() {
        try {
            View mainView
                    = LayoutInflater.from(context)
                    .inflate(R.layout.dialog_log_detail, null);
            ButterKnife.bind(this, mainView);
            dialog = new MaterialDialog.Builder(context)
                    .customView(mainView, false)
                    .positiveText(R.string.close)
                    .positiveColor(context.getResources().getColor(R.color.color_blue))
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dialog.dismiss();
                        }
                    }).build();
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }

    }

    private Log log;
    public void show(Log log){
        try {
            if (log == null) return;
            this.log = log;
            txtLabel.setText(log.getType());
            txtMessage.setText(log.getMessage());
            txtTransactionTime.setText(DateTimeUtil.displayLongDate(log.getTime()));
            txtTmcInTomo.setText(String.format(Locale.US,"%.2f TMC", log.getTmcSidechain()));
            txtTmcInEth.setText(String.format(Locale.US,"%.2f TMC", log.getTmcMainchain()));
            txtTransactionInTomochain.setText(log.getTxSidechain());
            txtTotalTmc.setText(String.format(Locale.US, "%.4f TMC", (log.getTmcSidechain() + log.getTmcMainchain())));
            if (log.getType().equals("cashOut")){
                containerTransactionInEth.setVisibility(View.VISIBLE);
                txtTransactionInEthereum.setText(log.getTxMainchain());
            }else {
                containerTransactionInEth.setVisibility(View.GONE);
            }
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.txt_transaction_in_tomochain, R.id.txt_transaction_in_ethereum, R.id.txt_tmc_in_tomo, R.id.txt_tmc_in_eth})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_transaction_in_tomochain:
            case R.id.txt_tmc_in_tomo:
                String str1 = "https://explorer.tomocoin.io/#/tx/" + log.getTxSidechain();
                onViewClicked(str1);
                break;
            case R.id.txt_transaction_in_ethereum:
            case R.id.txt_tmc_in_eth:
                if (log.getType().equals("cashOut")){
                    String str2 = "https://rinkeby.etherscan.io/tx/" + log.getTxMainchain();
                    onViewClicked(str2);
                }
                break;
        }
    }


    private void onViewClicked(String content){
        try {

            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(content));
            context.startActivity(browserIntent);
        } catch (Exception e) {
            LogUtil.e(e);
        }
    }
}
