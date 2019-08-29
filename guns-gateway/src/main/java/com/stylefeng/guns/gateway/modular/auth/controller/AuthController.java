package com.stylefeng.guns.gateway.modular.auth.controller;

import com.stylefeng.guns.api.user.UserAPI;
import com.stylefeng.guns.gateway.common.exception.BizExceptionEnum;
import com.stylefeng.guns.gateway.modular.auth.controller.dto.AuthRequest;
import com.stylefeng.guns.gateway.modular.auth.controller.dto.AuthResponse;
import com.stylefeng.guns.gateway.modular.auth.util.JwtTokenUtil;
import com.stylefeng.guns.gateway.modular.auth.vo.CommonRespose;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 请求验证的
 *
 * @author fengshuonan
 * @Date 2017/8/24 14:22
 */
@RestController
public class AuthController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Reference
    private UserAPI userAPI;

    @RequestMapping(value = "${jwt.auth-path}")
    public CommonRespose<?> createAuthenticationToken(AuthRequest authRequest) {

        //用户校验 , 获取对应的用户信息
        int uuid = this.userAPI.login(authRequest.getUserName(), authRequest.getPassword());
        if (uuid > 0) {
            final String randomKey = jwtTokenUtil.getRandomKey();
            final String token = jwtTokenUtil.generateToken("" + uuid, randomKey);

            return CommonRespose.success(new AuthResponse(token, randomKey));
            //return ResponseEntity.ok(new AuthResponse(token, randomKey));
        } else {
            //throw new GunsException(BizExceptionEnum.AUTH_REQUEST_ERROR);
            return CommonRespose.businessFail(BizExceptionEnum.AUTH_REQUEST_ERROR.getMessage());
        }
    }
}
