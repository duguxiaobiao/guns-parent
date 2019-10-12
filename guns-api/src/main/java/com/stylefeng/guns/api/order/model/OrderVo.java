package com.stylefeng.guns.api.order.model;

import com.stylefeng.guns.api.base.BaseModel;
import lombok.Data;

/**
 * @author ztkj-hzb
 * @Date 2019/9/23 17:08
 * @Description 订单明细响应实体
 */
@Data
public class OrderVo extends BaseModel {

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 电影名称
     */
    private String filmName;

    /**
     * 上映时间
     */
    private String fieldTime;

    /**
     * 影院名称
     */
    private String cinemaName;

    /**
     * 购买座位信息
     */
    private String seatsName;

    /**
     * 订单总金额
     */
    private String orderPrice;

    /**
     * 下单时间
     */
    private String orderTimestamp;

}
