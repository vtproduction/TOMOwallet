package com.tomoapp.tomowallet.ui.createWallet;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tomoapp.tomowallet.R;
import com.tomoapp.tomowallet.base.BaseActivity;
import com.tomoapp.tomowallet.helper.LogUtil;
import com.tomoapp.tomowallet.ui.createPassword.CreatePasswordActivity;
import com.tomoapp.tomowallet.ui.createPincode.CreatePinCodeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by cityme on 1/8/18.
 */

public class CreateWalletInstructionActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.txt_label_0)
    TextView txtLabel0;
    @BindView(R.id.txt_label_1)
    TextView txtLabel1;
    @BindView(R.id.btn_next)
    TextView btnNext;
    @BindView(R.id.bottom_container)
    LinearLayout bottomContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_wallet_tos);
        ButterKnife.bind(this);
        setContent();
    }

    @OnClick(R.id.btn_next)
    public void onViewClicked() {
        startActivity(new Intent(this, CreatePasswordActivity.class));
        finish();
    }


    public void setContent() {
        try {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            SpannableStringBuilder builder = new SpannableStringBuilder();
            String content = getString(R.string.mes_01);
            builder.append(content);
            int start1 = content.indexOf("Tomochain");
            int end1 = "Tomochain".length() + start1;

            int start2 = content.indexOf("Ethereum Rinkeby");
            int end2 = "Ethereum Rinkeby".length() + start2;

            builder.setSpan(new StyleSpan(Typeface.BOLD), start1, end1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            builder.setSpan(new StyleSpan(Typeface.BOLD), start2, end2, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

            ClickableSpan span1 = new ClickableSpan() {
                @Override
                public void onClick(View view) {
                    String link = "https://stats.tomocoin.io/";
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                    startActivity(browserIntent);
                }

                public void updateDrawState(TextPaint ds) {// override updateDrawState
                    ds.setUnderlineText(false); // set to false to remove underline
                    ds.setColor(getResources().getColor(R.color.color_blue));
                }
            };
            ClickableSpan span2 = new ClickableSpan() {
                @Override
                public void onClick(View view) {
                    String link = "https://www.rinkeby.io/";
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                    startActivity(browserIntent);
                }

                public void updateDrawState(TextPaint ds) {// override updateDrawState
                    ds.setUnderlineText(false); // set to false to remove underline
                    ds.setColor(getResources().getColor(R.color.color_blue));
                }
            };
            builder.setSpan(span1, start1, end1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            builder.setSpan(span2, start2, end2, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            txtLabel1.setText(builder);
        } catch (Exception e) {
            LogUtil.e(e);
        }
    }
}
