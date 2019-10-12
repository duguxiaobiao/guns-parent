package com.stylefeng.guns.order.common.persistence.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.order.model.OrderVo;
import com.stylefeng.guns.order.common.persistence.model.MoocOrderT;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 订单信息表 Mapper 接口
 * </p>
 *
 * @author lonely
 * @since 2019-09-23
 */
public interface MoocOrderTMapper extends BaseMapper<MoocOrderT> {


    /**
     * 根据放映场次id获取对应的场次影厅的座位分布地址
     *
     * @param fieldId
     * @return
     */
    String getSeatJsonAddress(@Param("fieldId") String fieldId);


    /**
     * 获取指定场次id的订单中的已售座位集合
     *
     * @param fieldId
     * @return
     */
    String getSeatsByFieldId(@Param("fieldId") String fieldId);

    /**
     * 查询指定订单id对应的订单信息
     *
     * @param orderId
     * @return
     */
    OrderVo getOrderVoByUuid(@Param("orderId") String orderId);

    /**
     * 查询指定用户对应的订单信息
     *
     * @param userId
     * @return
     */
    //List<OrderVo> getOrderVosByUsreId(@Param("userId") Integer userId,  @Param("page") Page<OrderVo> page);
    List<OrderVo> getOrderVosByUsreId(@Param("userId") Integer userId, @Param("limit") Integer limit, @Param("offset") Integer offset);

}
