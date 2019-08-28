package com.stylefeng.guns.api.base;

/**
 * @author ztkj-hzb
 * @Date 2019/8/27 14:22
 * @Description 基础返回实体
 */
public class CommonResponseModel extends BaseModel {

    private String status;

    private Object data;

    public CommonResponseModel(String status, Object data) {
        this.status = status;
        this.data = data;
    }

    public CommonResponseModel create(Object data) {
        return create("success", data);
    }


    public CommonResponseModel create(String status, Object data) {
        return new CommonResponseModel(status, data);
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
