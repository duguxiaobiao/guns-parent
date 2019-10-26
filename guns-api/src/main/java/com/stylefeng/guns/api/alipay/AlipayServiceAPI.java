package com.stylefeng.guns.api.alipay;

import com.stylefeng.guns.api.alipay.model.PayQrCodeVo;
import com.stylefeng.guns.api.alipay.model.PayResultVo;

/**
 * @author ztkj-hzb
 * @Date 2019/10/13 14:03
 * @Description 支付服务接口api
 */
public interface AlipayServiceAPI {

    /**
     * 获取支付二维码
     *
     * @param orderId
     * @return
     */
    PayQrCodeVo getPayInfo(String orderId);

    /**
     * 获取支付结果
     *
     * @param orderId
     * @return
     */
    PayResultVo getPayResult(String orderId);
}
