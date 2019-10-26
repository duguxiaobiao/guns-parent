package com.stylefeng.guns.api.alipay.model;

import com.stylefeng.guns.api.base.BaseModel;
import lombok.Data;

/**
 * @author ztkj-hzb
 * @Date 2019/10/13 14:10
 * @Description 支付结果响应实体
 */
@Data
public class PayResultVo extends BaseModel {

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 订单状态
     */
    private Integer orderStatus;

    /**
     * 订单结果
     */
    private String orderMsg;
}
