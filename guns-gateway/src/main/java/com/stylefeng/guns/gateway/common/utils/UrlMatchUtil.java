package com.stylefeng.guns.gateway.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

/**
 * @author ztkj-hzb
 * @Date 2019/8/28 17:10
 * @Description url模糊匹配工具
 */
public class UrlMatchUtil {

    private UrlMatchUtil() {
    }

    /**
     * 匹配资料
     *
     * @param patternPath 模糊匹配表达式
     * @param requestPath 待匹配的url
     * @return
     */
    public static boolean match(String patternPath, String requestPath) {
        if (StringUtils.isEmpty(patternPath) || StringUtils.isEmpty(requestPath)) {
            return false;
        }
        PathMatcher matcher = new AntPathMatcher();
        return matcher.match(patternPath, requestPath);
    }

}
