package com.stylefeng.guns.core.util;

import java.util.UUID;

/**
 * @author ztkj-hzb
 * @Date 2019/9/26 16:47
 * @Description 这里应该使用 分布式id生成测试略称，现在只是测试阶段，后期处理
 */
public class UuidUtil {

    /**
     * 生成随机uuid
     *
     * @return
     */
    public static String getUuid() {
        return UUID.randomUUID().toString();
    }


}
