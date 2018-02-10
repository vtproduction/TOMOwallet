package com.tomoapp.tomowallet.customView;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.tomoapp.tomowallet.R;
import com.tomoapp.tomowallet.helper.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cityme on 1/10/18.
 */

public class CreateWalletLoadingDialog extends Dialog {

    @BindView(R.id.img_logo_1)
    ImageView imgLogo1;
    @BindView(R.id.img_logo_2)
    ImageView imgLogo2;
    @BindView(R.id.img_logo_3)
    ImageView imgLogo3;
    @BindView(R.id.txt_message)
    TextView txtMessage;
    @BindView(R.id.animation_view)
    LottieAnimationView animationView;
    @BindView(R.id.loading_view)
    View loadingView;

    private Context context;
    public interface OnDialogDismiss{
        void onDismiss();
    }
    OnDialogDismiss callback;
    public CreateWalletLoadingDialog(@NonNull Context context, OnDialogDismiss callback) {
        super(context);
        this.context = context;
        this.callback = callback;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_create_wallet_loading_dialog);
        ButterKnife.bind(this);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                imgLogo2.animate().rotationBy(360).withEndAction(this).setDuration(7000).setInterpolator(new LinearInterpolator()).start();
                imgLogo3.animate().rotationBy(-360).withEndAction(this).setDuration(7000).setInterpolator(new LinearInterpolator()).start();
            }
        };

        imgLogo2.animate().rotationBy(360).withEndAction(runnable).setDuration(7000).setInterpolator(new LinearInterpolator()).start();
        imgLogo3.animate().rotationBy(-360).withEndAction(runnable).setDuration(7000).setInterpolator(new LinearInterpolator()).start();
    }


    public void show(String title) {
        /*txtMessage.setText(title);
        if (title.isEmpty())
            txtMessage.setVisibility(View.GONE);
        else
            txtMessage.setVisibility(View.VISIBLE);*/
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        show();
    }

    @Override
    public void onBackPressed() {

    }


    public void onDone(){
        loadingView.setVisibility(View.GONE);
        animationView.setVisibility(View.VISIBLE);
        txtMessage.setVisibility(View.GONE);
        animationView.setAnimation("check_done.json");
        animationView.loop(false);
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f)
                .setDuration(3000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                animationView.setProgress(valueAnimator.getAnimatedFraction());
            }


        });

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                callback.onDismiss();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });


        animator.start();

        //animationView.playAnimation();
    }
}
