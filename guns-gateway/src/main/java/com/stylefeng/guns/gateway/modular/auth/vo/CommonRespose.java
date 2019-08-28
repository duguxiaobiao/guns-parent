package com.stylefeng.guns.gateway.modular.auth.vo;

/**
 * @author ztkj-hzb
 * @Date 2019/8/27 15:17
 * @Description
 */
public class CommonRespose<T> {


    /**
     * 状态编码 0：成功 1：业务异常  999：系统异常
     */
    private int status;
    /**
     * 异常消息
     */
    private String msg;
    /**
     * 响应实体
     */
    private T data;

    private CommonRespose() {
    }

    /**
     * 成功响应
     *
     * @param data
     * @return
     */
    public static <T> CommonRespose<T> success(T data) {
        CommonRespose<T> commonRespose = new CommonRespose<>();
        commonRespose.setStatus(0);
        commonRespose.setData(data);
        return commonRespose;
    }

    /**
     * 业务异常
     *
     * @param errMsg
     * @return
     */
    public static <T> CommonRespose<T> businessFail(String errMsg) {
        CommonRespose<T> commonRespose = new CommonRespose<>();
        commonRespose.setStatus(1);
        commonRespose.setMsg(errMsg);
        return commonRespose;
    }

    /**
     * 系统异常
     *
     * @param errMsg
     * @return
     */
    public static <T> CommonRespose<T> systemFail(String errMsg) {
        CommonRespose<T> commonRespose = new CommonRespose<>();
        commonRespose.setStatus(999);
        commonRespose.setMsg(errMsg);
        return commonRespose;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
