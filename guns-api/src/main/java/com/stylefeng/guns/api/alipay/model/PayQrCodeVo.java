package com.stylefeng.guns.api.alipay.model;

import com.stylefeng.guns.api.base.BaseModel;
import lombok.Data;

/**
 * @author ztkj-hzb
 * @Date 2019/10/13 14:08
 * @Description 订单支付二维码响应实体
 */
@Data
public class PayQrCodeVo extends BaseModel {

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 生成的二维码地址
     */
    private String qrCodeAddress;

}
