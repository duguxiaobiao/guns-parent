package com.stylefeng.guns.api.base;

/**
 * @author ztkj-hzb
 * @Date 2019/8/27 14:33
 * @Description 业务异常类
 */
public class BusinessException extends Exception implements CommonErrorModel {

    /**
     * 异常实体
     */
    private CommonErrorModel commonErrorModel;

    /**
     * 业务实体构建
     * @param commonErrorModel
     */
    public BusinessException(CommonErrorModel commonErrorModel) {
        super();

        this.commonErrorModel = commonErrorModel;
    }

    /**
     * 业务实体构建
     * @param commonErrorModel
     * @param errorMsg
     */
    public BusinessException(CommonErrorModel commonErrorModel, String errorMsg) {
        super();
        this.commonErrorModel = commonErrorModel;
        this.commonErrorModel.setErrMsg(errorMsg);
    }


    /**
     * 获取错误编码
     *
     * @return
     */
    @Override
    public int getErrorCode() {
        return this.commonErrorModel.getErrorCode();
    }

    /**
     * 获取异常消息
     *
     * @return
     */
    @Override
    public String getErrMsg() {
        return this.commonErrorModel.getErrMsg();
    }

    /**
     * 设置异常消息
     *
     * @param errMsg
     * @return
     */
    @Override
    public CommonErrorModel setErrMsg(String errMsg) {
        //this.commonErrorModel.setErrMsg(errMsg);
        //return this;

        return this.commonErrorModel.setErrMsg(errMsg);
    }
}
