package com.stylefeng.guns.gateway.modular.auth.controller.dto;

import java.io.Serializable;

/**
 * 认证的请求dto
 *
 * @author fengshuonan
 * @Date 2017/8/24 14:00
 */
public class AuthRequest implements Serializable {

    private String userName;
    private String password;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getCredenceName() {
        return this.userName;
    }

    public String getCredenceCode() {
        return this.password;
    }
}
