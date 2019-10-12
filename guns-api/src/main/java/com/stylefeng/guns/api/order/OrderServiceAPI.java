package com.stylefeng.guns.api.order;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.order.model.OrderVo;

import java.util.List;

/**
 * @author ztkj-hzb
 * @Date 2019/9/26 14:11
 * @Description 订单接口
 */
public interface OrderServiceAPI {


    /**
     * 验证传入的座位是否正确
     *
     * @param fieldId 放映场次id
     * @param seats   待验证的座位信息
     * @return
     */
    boolean isTrueSeats(String fieldId, String seats);

    /**
     * 验证传入的座位是否已卖出
     *
     * @param fieldId
     * @param seats
     * @return
     */
    boolean isNotSoldSeats(String fieldId, String seats);

    /**
     * 创建订单
     *
     * @param fieldId
     * @param soldSeats
     * @param seatsName
     * @param userId
     * @return
     */
    OrderVo saveOrderInfo(Integer fieldId, String soldSeats, String seatsName, Integer userId);

    /**
     * 获取指定用户的订单信息
     *
     * @param userId
     * @return
     */
    List<OrderVo> getOrdersByUserId(Integer userId, Page<OrderVo> page);

    /**
     * 根据放映场次id获取已售出的座位编号
     *
     * @param fieldId
     * @return
     */
    String getSoldSeatsByFieldId(Integer fieldId);

}
