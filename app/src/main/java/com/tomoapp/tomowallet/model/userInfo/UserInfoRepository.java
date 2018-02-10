package com.tomoapp.tomowallet.model.userInfo;

import com.google.gson.Gson;
import com.tomoapp.tomowallet.helper.LogUtil;
import com.tomoapp.tomowallet.model.userInfo.pojo.UserInfo;

/**
 * Created by macbook on 12/22/17.
 */

public class UserInfoRepository implements UserInfoDataSource {


    @Override
    public UserInfo createUserInfo(String jsonString) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(jsonString, UserInfo.class);
        } catch (Exception e) {
            LogUtil.e(e);
            return null;
        }
    }



}
