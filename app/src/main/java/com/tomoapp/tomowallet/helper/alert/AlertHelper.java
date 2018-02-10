package com.tomoapp.tomowallet.helper.alert;

import android.app.Activity;
import android.view.View;

import com.tapadoo.alerter.Alert;
import com.tapadoo.alerter.Alerter;
import com.tomoapp.tomowallet.R;
import com.tomoapp.tomowallet.base.BaseActivity;
import com.tomoapp.tomowallet.base.BaseSocketActivity;
import com.tomoapp.tomowallet.base.MainApplication;
import com.tomoapp.tomowallet.helper.LogUtil;
import com.tomoapp.tomowallet.model.walletActionResponse.CashActionResponse;
import com.tomoapp.tomowallet.model.walletActionResponse.RewardResponse;
import com.tomoapp.tomowallet.ui.home.LogDetailDialog;
import com.tomoapp.tomowallet.ui.send_tmc.SendTMCActivity;
import com.tomoapp.tomowallet.ui.send_tmc.SendTMCContract;

import java.util.Locale;

/**
 * Created by macbook on 12/28/17.
 */

public class AlertHelper {

    private Alert alert;
    private LogDetailDialog dialog;
    public AlertHelper() {

    }


    public Alert getAlert() {
        return alert;
    }


    public boolean isAlertShowing(){
        return alert != null && alert.isShown();
    }


    public void showRewarded(final RewardResponse rewardResponse){
        try {
            Activity activity = MainApplication.getCurrentActivity();
            if (activity == null) return;
            LogUtil.d("ALERTER: showRewarded from " + activity.getLocalClassName());
            alert = Alerter.create(activity)
                    .setTitle("REWARD")
                    .setText(rewardResponse.getLog().getMessage())
                    .setDuration(3000)
                    .setIcon(R.drawable.ic_crown)
                    .enableSwipeToDismiss()
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alert.hide();
                            onItemClicked(rewardResponse);
                        }
                    })
                    .show();
        } catch (Exception e) {
            LogUtil.e(e);
        }
    }

    public void showCashedIn(final CashActionResponse cashActionResponse){
        try {
            Activity activity = MainApplication.getCurrentActivity();
            if (activity == null) return;
            LogUtil.d("ALERTER: showCashedIn from " + activity.getLocalClassName());
            alert = Alerter.create(activity)
                    .setTitle("CASHED IN")
                    .setText(cashActionResponse.getLog().getMessage())
                    .setDuration(3000)
                    .setIcon(R.drawable.ic_cash_in)
                    .enableSwipeToDismiss()
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alert.hide();
                            onItemClicked(cashActionResponse);
                        }
                    })
                    .show();
        } catch (Exception e) {
            LogUtil.e(e);
        }
    }

    public void showCashedOut(final CashActionResponse cashActionResponse){
        try {
            Activity activity = MainApplication.getCurrentActivity();
            if (activity == null) return;
            LogUtil.d("ALERTER: showCashedOut from " + activity.getLocalClassName());
            alert = Alerter.create(activity)
                    .setTitle("CASHED OUT")
                    .setText(cashActionResponse.getLog().getMessage())
                    .setDuration(3000)
                    .setIcon(R.drawable.ic_cash_out)
                    .enableSwipeToDismiss()
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alert.hide();
                            onItemClicked(cashActionResponse);
                        }
                    })
                    .show();
        } catch (Exception e) {
            LogUtil.e(e);
        }
    }

    public void showSentTMC(final CashActionResponse cashActionResponse){
        try {
            Activity activity = MainApplication.getCurrentActivity();
            if (activity == null) return;
            LogUtil.d("ALERTER: showSentTMC from " + activity.getLocalClassName());
            /*if (activity instanceof SendTMCActivity)
                ((SendTMCActivity)activity).onTMCSent(cashActionResponse);*/
            alert = Alerter.create(activity)
                    .setTitle("TMC SENT!")
                    .setText(cashActionResponse.getLog().getMessage())
                    .setDuration(3000)
                    .setIcon(R.drawable.ic_send)
                    .enableSwipeToDismiss()
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alert.hide();
                            onItemClicked(cashActionResponse);
                        }
                    })
                    .show();
        } catch (Exception e) {
            LogUtil.e(e);
        }
    }

    public void showReceivedTMC(final CashActionResponse cashActionResponse){
        try {
            Activity activity = MainApplication.getCurrentActivity();
            if (activity == null) return;
            LogUtil.d("ALERTER: showReceivedTMC from " + activity.getLocalClassName());
            alert = Alerter.create(activity)
                    .setTitle("TMC RECEIVED!")
                    .setText(cashActionResponse.getLog().getMessage())
                    .setDuration(3000)
                    .setIcon(R.drawable.ic_receive)
                    .enableSwipeToDismiss()
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alert.hide();
                            onItemClicked(cashActionResponse);
                        }
                    })
                    .show();
        } catch (Exception e) {
            LogUtil.e(e);
        }
    }


    void onItemClicked(CashActionResponse cashActionResponse){
        LogUtil.d("onItemClicked in Alert: " + cashActionResponse.toString());
        dialog = LogDetailDialog.newInstance(MainApplication.getCurrentActivity());
        dialog.show(cashActionResponse.getLog());
    }

    void onItemClicked(RewardResponse rewardResponse){
        LogUtil.d("onItemClicked in Alert: " + rewardResponse.toString());
        dialog = LogDetailDialog.newInstance(MainApplication.getCurrentActivity());
        dialog.show(rewardResponse.getLog());
    }
}
