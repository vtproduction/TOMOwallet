package com.tomoapp.tomowallet.ui.ethInfo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.tomoapp.tomowallet.R;
import com.tomoapp.tomowallet.base.BaseActivity;
import com.tomoapp.tomowallet.base.BaseSocketActivity;
import com.tomoapp.tomowallet.base.MainApplication;
import com.tomoapp.tomowallet.helper.LogUtil;
import com.tomoapp.tomowallet.model.userInfo.UserInfoDataSource;
import com.tomoapp.tomowallet.model.userInfo.UserInfoRepository;
import com.tomoapp.tomowallet.model.userInfo.pojo.UserInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by macbook on 12/27/17.
 */

public class ETHInfoActivity extends BaseSocketActivity {

    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.container_header)
    LinearLayout containerHeader;
    @BindView(R.id.txt_1)
    TextView txt1;
    @BindView(R.id.btn_1)
    LinearLayout btn1;
    @BindView(R.id.txt_2)
    TextView txt2;
    @BindView(R.id.btn_2)
    LinearLayout btn2;
    @BindView(R.id.txt_3)
    TextView txt3;
    @BindView(R.id.btn_3)
    LinearLayout btn3;
    @BindView(R.id.scroll_view)
    ScrollView scrollView;
    private UserInfoDataSource mUserInfo;
    public static final String ARGS_USER_INFO = "USER_INFO";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eth_info);
        ButterKnife.bind(this);
        this.mUserInfo = new UserInfoRepository();
        UserInfo userInfo = mUserInfo.createUserInfo(getIntent().getStringExtra(ARGS_USER_INFO));
        if (userInfo != null){
            txt1.setText(userInfo.getMainchainInformation().getTmcAddress());
            txt2.setText(userInfo.getSidechainInformation().getCashOutAddress());
            txt3.setText(userInfo.getSidechainInformation().getCashInAddress());
        }
    }



    @OnClick({R.id.btn_back, R.id.btn_1, R.id.btn_2, R.id.btn_3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                onBackPressed();
                break;
            case R.id.btn_1:
                onDetailClicked("token",txt1.getText().toString());
                break;
            case R.id.btn_2:
                onDetailClicked("address",txt2.getText().toString());
                break;
            case R.id.btn_3:
                onDetailClicked("address",txt3.getText().toString());
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        MainApplication.setCurrentActivity(this);
    }

    private void onDetailClicked(String path, String content){
        try {
            String link = "https://rinkeby.etherscan.io/" + path +"/" + content;
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
            startActivity(browserIntent);
        } catch (Exception e) {
            LogUtil.e(e);
        }
    }
}
