package com.stylefeng.guns.user.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.api.user.UserAPI;
import com.stylefeng.guns.api.user.model.UserInfoModel;
import com.stylefeng.guns.api.user.model.UserRegistryModel;
import com.stylefeng.guns.core.util.MD5Util;
import com.stylefeng.guns.user.common.persistence.dao.MoocUserTMapper;
import com.stylefeng.guns.user.common.persistence.model.MoocUserT;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author ztkj-hzb
 * @Date 2019/8/26 18:19
 * @Description 用户接口实现类
 */
@Service
public class UserServiceImpl implements UserAPI {

    @Autowired
    private MoocUserTMapper moocUserTMapper;

    /**
     * 用户登陆 0：失败 !=0 :成功
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public int login(String username, String password) {
        MoocUserT moocUserT = new MoocUserT();
        moocUserT.setUserName(username);

        MoocUserT selectUser = this.moocUserTMapper.selectOne(moocUserT);
        if (selectUser != null) {
            //判断密码
            String pwdEncrypt = MD5Util.encrypt(password);
            if (pwdEncrypt.equals(selectUser.getUserPwd())) {
                return selectUser.getUuid();
            }
        }
        return 0;
    }

    /**
     * 用户注册
     *
     * @param userRegistryModel
     * @return
     */
    @Override
    public boolean registry(UserRegistryModel userRegistryModel) {

        MoocUserT moocUserT = new MoocUserT();
        BeanUtils.copyProperties(userRegistryModel, moocUserT);
        moocUserT.setUserPhone(userRegistryModel.getPhone());

        //设置密码加密
        MD5Util.encrypt(moocUserT.getUserPwd());

        //添加到数据库
        Integer insert = this.moocUserTMapper.insert(moocUserT);
        return insert > 0;
    }

    /**
     * 校验用户名是否有效
     *
     * @param username
     * @return
     */
    @Override
    public boolean checkUserName(String username) {

        EntityWrapper<MoocUserT> entityWrapper = new EntityWrapper<>();
        Wrapper<MoocUserT> userTWrapper = entityWrapper.eq("user_name", username);
        Integer count = this.moocUserTMapper.selectCount(userTWrapper);
        if (count != null && count > 0) {
            return false;
        }
        return true;
    }

    /**
     * 根据uuid获取用户信息
     *
     * @param uuid
     * @return
     */
    @Override
    public UserInfoModel getUserInfo(int uuid) {

        //1.根据uuid查询用户信息
        MoocUserT moocUserT = this.moocUserTMapper.selectById(uuid);

        //2.转换成UserInfoModel
        UserInfoModel userInfoModel = this.do2UserInfoModel(moocUserT);

        return userInfoModel;
    }

    /**
     * 修改用户信息
     *
     * @param userInfoModel
     * @return
     */
    @Override
    public UserInfoModel updateUserInfo(UserInfoModel userInfoModel) {

        //构建数据库实体bean
        MoocUserT moocUserT = new MoocUserT();
        moocUserT.setUuid(userInfoModel.getUuid());
        moocUserT.setUserName(userInfoModel.getUsername());
        moocUserT.setNickName(userInfoModel.getNickname());
        moocUserT.setUserSex(userInfoModel.getSex());
        moocUserT.setBirthday(userInfoModel.getBirthday());
        moocUserT.setEmail(userInfoModel.getEmail());
        moocUserT.setUserPhone(userInfoModel.getPhone());
        moocUserT.setAddress(userInfoModel.getAddress());
        moocUserT.setHeadUrl(userInfoModel.getHeadAddress());
        moocUserT.setBiography(userInfoModel.getBiography());
        moocUserT.setLifeState(Integer.valueOf(userInfoModel.getLifeState()));
        moocUserT.setBeginTime(time2Date(userInfoModel.getCreateTime()));
        moocUserT.setUpdateTime(time2Date(userInfoModel.getUpdateTime()));

        //2.更新实体
        Integer result = this.moocUserTMapper.updateById(moocUserT);
        if (result > 0) {
            return this.do2UserInfoModel(moocUserT);
        } else {
            return userInfoModel;
        }
    }

    /**
     * 根据长整型的时间构建Date类型
     *
     * @param time
     * @return
     */
    private Date time2Date(long time) {
        return new Date(time);
    }


    /**
     * 将数据库的实体bean 转换成 UserInfoModel
     *
     * @param moocUserT
     * @return
     */
    private UserInfoModel do2UserInfoModel(MoocUserT moocUserT) {
        UserInfoModel userInfoModel = new UserInfoModel();
        if (moocUserT != null) {
            userInfoModel.setUsername(moocUserT.getUserName());
            userInfoModel.setNickname(moocUserT.getNickName());
            userInfoModel.setEmail(moocUserT.getEmail());
            userInfoModel.setPhone(moocUserT.getUserPhone());
            userInfoModel.setSex(moocUserT.getUserSex());
            userInfoModel.setBirthday(moocUserT.getBirthday());
            userInfoModel.setLifeState("" + moocUserT.getLifeState());
            userInfoModel.setBiography(moocUserT.getBiography());
            userInfoModel.setAddress(moocUserT.getAddress());
            userInfoModel.setHeadAddress(moocUserT.getHeadUrl());
            userInfoModel.setCreateTime(moocUserT.getBeginTime().getTime());
            userInfoModel.setUpdateTime(moocUserT.getUpdateTime().getTime());
        }
        return userInfoModel;
    }
}
