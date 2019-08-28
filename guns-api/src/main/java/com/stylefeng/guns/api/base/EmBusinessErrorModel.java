package com.stylefeng.guns.api.base;

/**
 * @author ztkj-hzb
 * @Date 2019/8/27 14:29
 * @Description
 */
public enum EmBusinessErrorModel implements CommonErrorModel {
    USER_NOT_EXISTS(10001,"用户不存在");


    private int errCode;
    private String errMsg;

    private EmBusinessErrorModel(int errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    /**
     * 获取错误编码
     *
     * @return
     */
    @Override
    public int getErrorCode() {
        return this.errCode;
    }

    /**
     * 获取异常消息
     *
     * @return
     */
    @Override
    public String getErrMsg() {
        return this.errMsg;
    }

    /**
     * 设置异常消息
     *
     * @param errMsg
     * @return
     */
    @Override
    public CommonErrorModel setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }
}
