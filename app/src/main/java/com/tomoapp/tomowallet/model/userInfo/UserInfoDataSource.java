package com.tomoapp.tomowallet.model.userInfo;

import com.tomoapp.tomowallet.model.userInfo.pojo.UserInfo;

/**
 * Created by macbook on 12/22/17.
 */

public interface UserInfoDataSource {


    UserInfo createUserInfo(String jsonString);

}
