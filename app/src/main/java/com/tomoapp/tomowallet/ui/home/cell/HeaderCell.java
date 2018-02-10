package com.tomoapp.tomowallet.ui.home.cell;

import android.animation.Animator;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.airbnb.lottie.LottieAnimationView;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.gson.Gson;
import com.jaychang.srv.SimpleCell;
import com.jaychang.srv.SimpleViewHolder;
import com.jaychang.srv.Updatable;
import com.tomoapp.tomowallet.R;
import com.tomoapp.tomowallet.helper.LogUtil;
import com.tomoapp.tomowallet.helper.ToastUtil;
import com.tomoapp.tomowallet.model.userInfo.pojo.Log;
import com.tomoapp.tomowallet.model.userInfo.pojo.UserInfo;
import com.tomoapp.tomowallet.ui.checkPassword.CheckPasswordDialog;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by macbook on 12/26/17.
 */

public class HeaderCell extends SimpleCell<UserInfo, HeaderCell.HeaderCellViewHolder> implements Updatable<Log> {

    private Context context;
    private HeaderCellItemClickListener callback;
    private final String KEY_LOG = "LOG";

    public HeaderCell(@NonNull UserInfo item, Context context, HeaderCellItemClickListener callback) {
        super(item);
        this.context = context;
        this.callback = callback;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.cell_header;
    }

    @NonNull
    @Override
    protected HeaderCellViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, @NonNull View view) {
        return new HeaderCellViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull HeaderCellViewHolder headerCellViewHolder, int i, @NonNull Context context, Object payload) {
        if (payload != null) {
            if (payload instanceof Bundle) {
                Bundle bundle = (Bundle) payload;
                String logString = bundle.getString(KEY_LOG);
                Log log = new Gson().fromJson(logString, Log.class);
                headerCellViewHolder.updateContent(log);
            } else if (payload instanceof Log) {
                Log model = (Log) payload;
                headerCellViewHolder.updateContent(model);
            }
            return;
        }
        headerCellViewHolder.setContent(getItem());
    }

    @Override
    public boolean areContentsTheSame(@NonNull Log log) {
        return false;
    }

    @Override
    public Object getChangePayload(@NonNull Log log) {
        LogUtil.d("getChangePayload: " + log.toString());
        Bundle bundle = new Bundle();
        String userString = new Gson().toJson(log);
        bundle.putString(KEY_LOG, userString);
        return bundle;
    }


    public interface HeaderCellItemClickListener {
        void onMainChainInfoClicked();

        void onSideChainInfoClicked();

        void onCashInBtnClicked(Double cashInValue);

        void onCashOutBtnClicked(Double cashOutValue);

        void onRewardButtonClicked();
    }

    public class HeaderCellViewHolder extends SimpleViewHolder {
        @BindView(R.id.txt_side_chain_coin)
        TextView txtSideChainCoin;
        @BindView(R.id.txt_main_chain_coin)
        TextView txtMainChainCoin;
        @BindView(R.id.img_cash_in)
        ImageView imgCashIn;
        @BindView(R.id.img_cash_out)
        ImageView imgCashOut;
        @BindView(R.id.btn_side_chain_info)
        TextView btnSideChainInfo;
        @BindView(R.id.container_side_chain)
        CardView containerSideChain;
        @BindView(R.id.btn_cash_out)
        LinearLayout btnCashOut;
        @BindView(R.id.container_cash_out)
        LinearLayout containerCashOut;
        @BindView(R.id.btn_cash_in)
        LinearLayout btnCashIn;
        @BindView(R.id.container_cash_in)
        LinearLayout containerCashIn;
        @BindView(R.id.btn_main_chain_info)
        TextView btnMainChainInfo;
        @BindView(R.id.container_main_chain)
        CardView containerMainChain;
        @BindView(R.id.container_with_coin)
        LinearLayout containerWithCoin;
        @BindView(R.id.animation_view)
        LottieAnimationView animationView;
        @BindView(R.id.txt_mine)
        TextView txtMine;
        @BindView(R.id.progress_mining)
        LottieAnimationView progressMining;
        @BindView(R.id.btn_transfer)
        LinearLayout btnMine;
        @BindView(R.id.container_without_coin)
        LinearLayout containerWithoutCoin;

        private YoYo.YoYoString cashInAnimator;
        private YoYo.YoYoString cashOutAnimator;


        public HeaderCellViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        private UserInfo userInfo;

        public void setContent(UserInfo userInfo) {
            try {
                this.userInfo = userInfo;
                if (userInfo.getTmcMainchain() + userInfo.getTmcSidechain() == 0){
                    containerWithoutCoin.setVisibility(View.VISIBLE);
                    containerWithCoin.setVisibility(View.GONE);
                }else {
                    containerWithoutCoin.setVisibility(View.GONE);
                    containerWithCoin.setVisibility(View.VISIBLE);
                    txtSideChainCoin.setText(String.format(Locale.US, "%.4f", userInfo.getTmcSidechain()));
                    txtMainChainCoin.setText(String.format(Locale.US, "%.4f", userInfo.getTmcMainchain()));
                }

            } catch (Exception e) {
                LogUtil.e(e);
            }

        }

        public void updateContent(Log log) {
            try {
                LogUtil.d("updateContent: " + log.toString());
                stopAllAnimation();
                if (log.getTmcMainchain() + log.getTmcSidechain() == 0){
                    containerWithoutCoin.setVisibility(View.VISIBLE);
                    containerWithCoin.setVisibility(View.GONE);
                }else {
                    containerWithoutCoin.setVisibility(View.GONE);
                    containerWithCoin.setVisibility(View.VISIBLE);
                    this.userInfo.setTmcMainchain(log.getTmcMainchain());
                    this.userInfo.setTmcSidechain(log.getTmcSidechain());
                    txtSideChainCoin.setText(String.format(Locale.US, "%.4f", log.getTmcSidechain()));
                    txtMainChainCoin.setText(String.format(Locale.US, "%.4f", log.getTmcMainchain()));
                }

            } catch (Exception e) {
                LogUtil.e(e);
            }
        }

        @OnClick({R.id.btn_side_chain_info, R.id.btn_cash_out, R.id.btn_cash_in, R.id.btn_main_chain_info, R.id.btn_transfer})
        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.btn_side_chain_info:
                    callback.onSideChainInfoClicked();
                    break;
                case R.id.btn_cash_out:
                    onCashOutButtonClicked();
                    //animateCashOut();
                    break;
                case R.id.btn_cash_in:
                    onCashInButtonClicked();
                    //animateCashIn();
                    break;
                case R.id.btn_main_chain_info:
                    callback.onMainChainInfoClicked();
                    //stopAllAnimation();
                    break;
                case R.id.btn_transfer:
                    onRewardBtnClicked();
                    break;
            }
        }


        private void onRewardBtnClicked(){
            try {
                progressMining.setVisibility(View.VISIBLE);
                txtMine.setText(context.getText(R.string.mining));
                btnMine.setEnabled(false);
                callback.onRewardButtonClicked();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void onCashOutButtonClicked() {
            try {
                new MaterialDialog.Builder(context)
                        .title(context.getString(R.string.cash_out))
                        .content(context.getString(R.string.cash_out_amount).replace("[0]", "0").replace("[1]", userInfo.getTmcSidechain() + ""))
                        .positiveText(context.getString(R.string.cash_out))
                        .cancelable(true)
                        .inputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED)
                        .input(context.getString(R.string.cash_out_value), "", new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                dialog.dismiss();
                                checkCashOutValue(input.toString());
                            }
                        }).show();
            } catch (Exception e) {
                LogUtil.e(e);
            }
        }


        private void checkCashOutValue(String cashOutValue) {
            try {
                final Double cashOutVal = Double.valueOf(cashOutValue);
                if (cashOutVal <= 0 || cashOutVal > userInfo.getTmcSidechain()) {
                    ToastUtil.show(context.getString(R.string.invalid_value));

                } else {
                    CheckPasswordDialog.newInstance(new CheckPasswordDialog.CheckPinDialogListener() {
                        @Override
                        public void onPinChecked(String Pin) {
                            animateCashOut();
                            callback.onCashOutBtnClicked(cashOutVal);
                        }

                        @Override
                        public void onCancel() {

                        }
                    }).show();

                }
            } catch (NumberFormatException e) {
                ToastUtil.show(context.getString(R.string.invalid_value));
            }
        }


        private void onCashInButtonClicked() {
            try {

                new MaterialDialog.Builder(context)
                        .title(context.getString(R.string.cash_in))
                        .content(context.getString(R.string.cash_in_amount).replace("[0]", "0").replace("[1]", userInfo.getTmcMainchain() + ""))
                        .positiveText(context.getString(R.string.cash_in))
                        .cancelable(true)
                        .inputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED)
                        .input(context.getString(R.string.cash_in_value), "", new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                dialog.dismiss();
                                checkCashInValue(input.toString());
                            }
                        }).show();
            } catch (Exception e) {
                LogUtil.e(e);
            }
        }


        private void checkCashInValue(String cashOutValue) {
            try {
                final Double cashOutVal = Double.valueOf(cashOutValue);
                if (cashOutVal <= 0 || cashOutVal > userInfo.getTmcMainchain()) {
                    ToastUtil.show(context.getString(R.string.invalid_value));

                } else {
                    CheckPasswordDialog.newInstance(new CheckPasswordDialog.CheckPinDialogListener() {
                        @Override
                        public void onPinChecked(String Pin) {
                            animateCashIn();
                            callback.onCashInBtnClicked(cashOutVal);
                        }

                        @Override
                        public void onCancel() {

                        }
                    }).show();

                }
            } catch (NumberFormatException e) {
                ToastUtil.show(context.getString(R.string.invalid_value));
            }
        }


        private void stopAllAnimation() {
            try {
                if (cashInAnimator != null)
                    cashInAnimator.stop();
                if (cashOutAnimator != null)
                    cashOutAnimator.stop();
                containerCashIn.setVisibility(View.VISIBLE);
                containerCashOut.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                LogUtil.e(e);
            }
        }


        private void animateCashIn() {
            try {
                containerCashOut.setVisibility(View.INVISIBLE);
                containerCashIn.setVisibility(View.INVISIBLE);
                cashInAnimator = YoYo.with(Techniques.SlideInUp)
                        .duration(1500)
                        .repeat(YoYo.INFINITE)
                        .withListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animator) {
                                imgCashIn.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onAnimationEnd(Animator animator) {
                                imgCashIn.setVisibility(View.GONE);
                            }

                            @Override
                            public void onAnimationCancel(Animator animator) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animator) {

                            }
                        }).playOn(imgCashIn);


            } catch (Exception e) {
                LogUtil.e(e);
            }
        }

        private void animateCashOut() {
            try {
                containerCashIn.setVisibility(View.INVISIBLE);
                containerCashOut.setVisibility(View.INVISIBLE);
                cashOutAnimator = YoYo.with(Techniques.SlideOutDown)
                        .duration(1500)
                        .repeat(YoYo.INFINITE)
                        .withListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animator) {
                                imgCashOut.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onAnimationEnd(Animator animator) {
                                imgCashOut.setVisibility(View.GONE);
                            }

                            @Override
                            public void onAnimationCancel(Animator animator) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animator) {

                            }
                        }).playOn(imgCashOut);


            } catch (Exception e) {
                LogUtil.e(e);
            }
        }



    }
}
