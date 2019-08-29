package com.stylefeng.guns.gateway.modular.user;

import com.stylefeng.guns.api.user.UserAPI;
import com.stylefeng.guns.api.user.model.UserInfoModel;
import com.stylefeng.guns.api.user.model.UserRegistryModel;
import com.stylefeng.guns.gateway.common.CurrentUser;
import com.stylefeng.guns.gateway.modular.auth.vo.CommonRespose;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

/**
 * @author ztkj-hzb
 * @Date 2019/8/27 10:04
 * @Description
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Reference
    private UserAPI userAPI;

    @GetMapping("/{username}/{password}")
    public String login(@PathVariable("username") String username, @PathVariable("password") String password) {
        //boolean login = this.userAPI.login(username, password);
        //return login ? "登陆成功" : "登陆失败";
        return "成功";
    }

    /**
     * 用户注册
     *
     * @param userRegistryModel
     * @return
     */
    @PostMapping("/registry")
    public CommonRespose registry(UserRegistryModel userRegistryModel) {

        //1. 校验参数是否准确
        if (StringUtils.isEmpty(userRegistryModel.getUserName())) {
            return CommonRespose.businessFail("用户名不能为空");
        }
        if (StringUtils.isEmpty(userRegistryModel.getPassword())) {
            return CommonRespose.businessFail("密码不能为空");
        }

        //2. 用户注册
        boolean registry = this.userAPI.registry(userRegistryModel);

        //3. 响应
        return registry ? CommonRespose.build(0, null, "注册成功") : CommonRespose.businessFail("用户已存在");

    }

    /**
     * 校验用户性是否存在
     *
     * @param username
     * @return
     */
    @PostMapping("/check")
    public CommonRespose check(String username) {
        if (StringUtils.isEmpty(username)) {
            return CommonRespose.businessFail("用户名不能为空");
        }

        //校验用户名
        boolean checkResult = this.userAPI.checkUserName(username);

        //响应
        return checkResult ? CommonRespose.success("该用户名可以使用") : CommonRespose.businessFail("用户已存在");
    }

    /**
     * 登出
     * 正常逻辑处理：
     * 1. 清除前端的jwt数据
     * 2. 清除后台的jwt活跃用户
     *
     * @return
     */
    @GetMapping("/logout")
    public CommonRespose logout() {


        return null;
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    @GetMapping("/getUserInfo")
    public CommonRespose getUserInfo() {

        String userId = CurrentUser.getUserId();
        if (StringUtils.isEmpty(userId)) {
            return CommonRespose.businessFail("查询失败，用户尚未登录");
        }

        //查询用户信息
        UserInfoModel userInfo = this.userAPI.getUserInfo(Integer.valueOf(userId));
        return CommonRespose.success(userInfo);
    }

    /**
     * 修改用户信息
     *
     * @param userInfoModel
     * @return
     */
    @PostMapping("/updateUserInfo")
    public CommonRespose updateUserInfo(UserInfoModel userInfoModel) {

        //校验用户信息
        String userId = CurrentUser.getUserId();
        if (StringUtils.isEmpty(userId)) {
            return CommonRespose.businessFail("修改失败，用户尚未登录");
        }
        //校验token中的用户信息 与 待修改的用户信息是否匹配
        if (!userId.equals(String.valueOf(userInfoModel.getUuid()))) {
            return CommonRespose.businessFail("修改失败，请选择自己信息修改");
        }

        //修改用户信息
        UserInfoModel updateUserInfo = this.userAPI.updateUserInfo(userInfoModel);
        return updateUserInfo == null ? CommonRespose.businessFail("用户信息修改失败") : CommonRespose.success(updateUserInfo);
    }

}
