package com.stylefeng.guns.api.base;

/**
 * @author ztkj-hzb
 * @Date 2019/8/27 14:27
 * @Description 基础的异常类
 */
public interface CommonErrorModel {

    /**
     * 获取错误编码
     * @return
     */
    int getErrorCode();

    /**
     * 获取异常消息
     * @return
     */
    String getErrMsg();

    /**
     * 设置异常消息
     * @param errMsg
     * @return
     */
    CommonErrorModel setErrMsg(String errMsg);

}
