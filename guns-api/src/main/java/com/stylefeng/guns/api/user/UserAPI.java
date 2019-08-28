package com.stylefeng.guns.api.user;

import com.stylefeng.guns.api.user.model.UserInfoModel;
import com.stylefeng.guns.api.user.model.UserRegistryModel;

/**
 * @author ztkj-hzb
 * @Date 2019/8/26 13:50
 * @Description 用户接口
 */
public interface UserAPI {

    /**
     * 用户登陆
     *
     * @param username
     * @param password
     * @return  用户的uuid
     */
    int login(String username, String password);

    /**
     * 用户注册
     *
     * @param userRegistryModel
     * @return
     */
    boolean registry(UserRegistryModel userRegistryModel);

    /**
     * 校验用户名是否存在
     *
     * @param username
     * @return
     */
    boolean checkUserName(String username);

    /**
     * 根据uuid获取用户信息
     *
     * @param uuid
     * @return
     */
    UserInfoModel getUserInfo(int uuid);

    /**
     * 修改用户信息
     *
     * @param userInfoModel
     * @return
     */
    UserInfoModel updateUserInfo(UserInfoModel userInfoModel);
}
