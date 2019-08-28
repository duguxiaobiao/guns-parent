package com.stylefeng.guns.gateway.modular.user;

import com.stylefeng.guns.api.user.UserAPI;
import com.stylefeng.guns.api.user.model.UserRegistryModel;
import com.stylefeng.guns.core.util.ValidateUtil;
import com.stylefeng.guns.core.util.ValidationResult;
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
     * 用户注册s
     *
     * @param userRegistryModel
     * @return
     */
    @PostMapping("/registry")
    public String registry(@RequestBody UserRegistryModel userRegistryModel) {

        //1. 校验参数是否准确
        ValidationResult validationResult = ValidateUtil.validateEntity(userRegistryModel);
        if (validationResult.isHasErrors()) {
            return validationResult.getMessage();
        }

        //2. 用户注册
        boolean registry = this.userAPI.registry(userRegistryModel);

        //3. 响应
        return registry ? "注册成功" : "注册失败";

    }

}
