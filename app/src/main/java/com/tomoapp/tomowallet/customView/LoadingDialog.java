package com.tomoapp.tomowallet.customView;

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

import com.tomoapp.tomowallet.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cityme on 1/8/18.
 */

public class LoadingDialog extends Dialog {

    @BindView(R.id.img_logo_1)
    ImageView imgLogo1;
    @BindView(R.id.img_logo_2)
    ImageView imgLogo2;
    @BindView(R.id.img_logo_3)
    ImageView imgLogo3;
    @BindView(R.id.txt_message)
    TextView txtMessage;
    private Context context;

    public LoadingDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_loading_dialog);
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


    public void show(String title){
        /*txtMessage.setText(title);
        if (title.isEmpty())
            txtMessage.setVisibility(View.GONE);
        else
            txtMessage.setVisibility(View.VISIBLE);*/
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        show();
        txtMessage.setText(title);
    }

    @Override
    public void onBackPressed() {

    }
}
