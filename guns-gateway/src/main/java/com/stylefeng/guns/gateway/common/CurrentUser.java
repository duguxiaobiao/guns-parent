package com.stylefeng.guns.gateway.common;

/**
 * @author ztkj-hzb
 * @Date 2019/8/27 16:13
 * @Description
 */
public class CurrentUser {


    private static final ThreadLocal<String> userIdThreadLocal = new ThreadLocal<>();

    /**
     * 保存userid
     *
     * @param userId
     */
    public static void saveUserId(String userId) {
        userIdThreadLocal.set(userId);
    }

    /**
     * 获取userid
     *
     * @return
     */
    public static String getUserId() {
        return userIdThreadLocal.get();
    }

}
