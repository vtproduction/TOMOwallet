package com.tomoapp.tomowallet.ui.tmcInfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.tomoapp.tomowallet.R;
import com.tomoapp.tomowallet.base.BaseActivity;
import com.tomoapp.tomowallet.base.BaseSocketActivity;
import com.tomoapp.tomowallet.base.MainApplication;
import com.tomoapp.tomowallet.model.userInfo.UserInfoDataSource;
import com.tomoapp.tomowallet.model.userInfo.UserInfoRepository;
import com.tomoapp.tomowallet.model.userInfo.pojo.UserInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by macbook on 12/27/17.
 */

public class TMCInfoActivity extends BaseSocketActivity {


    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.container_header)
    LinearLayout containerHeader;
    @BindView(R.id.txt_1)
    TextView txt1;
    @BindView(R.id.txt_2)
    TextView txt2;
    @BindView(R.id.txt_3)
    TextView txt3;
    @BindView(R.id.txt_4)
    TextView txt4;
    @BindView(R.id.scroll_view)
    ScrollView scrollView;
    private UserInfoDataSource mUserInfo;
    public static final String ARGS_USER_INFO = "USER_INFO";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tmc_info);
        ButterKnife.bind(this);
        this.mUserInfo = new UserInfoRepository();

        UserInfo userInfo = mUserInfo.createUserInfo(getIntent().getStringExtra(ARGS_USER_INFO));
        txt1.setText(userInfo.getSidechainInformation().getRewardEngineAddress());
        txt2.setText(userInfo.getSidechainInformation().getTmcAddress());
        txt3.setText(userInfo.getSidechainInformation().getCashOutAddress());
        txt4.setText(userInfo.getSidechainInformation().getCashInAddress());
    }







    @Override
    protected void onResume() {
        super.onResume();
        MainApplication.setCurrentActivity(this);
    }

    @OnClick(R.id.btn_back)
    public void onViewClicked() {
        onBackPressed();
    }
}
