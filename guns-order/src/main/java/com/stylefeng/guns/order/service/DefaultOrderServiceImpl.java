package com.stylefeng.guns.order.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.cinema.CinemaServiceAPI;
import com.stylefeng.guns.api.cinema.model.vo.OrderQueryVo;
import com.stylefeng.guns.api.order.OrderServiceAPI;
import com.stylefeng.guns.api.order.model.OrderVo;
import com.stylefeng.guns.core.util.UuidUtil;
import com.stylefeng.guns.order.common.persistence.dao.MoocOrderTMapper;
import com.stylefeng.guns.order.common.persistence.model.MoocOrderT;
import com.stylefeng.guns.order.util.FtpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author ztkj-hzb
 * @Date 2019/9/26 14:18
 * @Description 订单服务默认实现
 */
@Slf4j
@Service
public class DefaultOrderServiceImpl implements OrderServiceAPI {

    @Autowired
    private MoocOrderTMapper moocOrderTMapper;

    @Reference
    private CinemaServiceAPI cinemaServiceAPI;

    @Autowired
    private FtpUtil ftpUtil;

    /**
     * 验证传入的座位是否正确
     *
     * @param fieldId 放映场次id
     * @param seats   待验证的座位信息
     * @return
     */
    @Override
    public boolean isTrueSeats(String fieldId, String seats) {

        //1.根据指定场次id获取对应的场次影厅的座位分布数据的ftp地址
        String seatJsonAddress = this.moocOrderTMapper.getSeatJsonAddress(fieldId);

        //2.从ftp中获取指定路径的文件数据
        //String seatContent = ftpUtil.getFileContentByAddress(seatJsonAddress);
        //todo 这里ftp连接变换ip，这里使用本次文件，先完成功能
        String path = Thread.currentThread().getClass().getResource("/").getPath() + File.separator + seatJsonAddress;
        String seatContent;
        try {
            seatContent = FileUtils.readFileToString(new File(path), "utf-8");
        } catch (IOException e) {
            log.error("读取资源文件异常，异常原因：{}}", ExceptionUtils.getStackTrace(e));
            throw new RuntimeException("文件读取异常");
        }

        //3.比较获取交集，判断是否都在文件中
        String[] arr1 = seats.split(",");
        //从内容中读取到ids的数据
        String seatIds = JSON.parseObject(seatContent).getString("ids");
        String[] arr2 = seatIds.split(",");

        //两个数组交集的数组的长度
        long jCount = Arrays.stream(arr1).distinct().filter(x -> Arrays.stream(arr2).distinct().anyMatch(y -> x.equals(y))).count();
        if (arr1.length == jCount) {
            //长度匹配
            return true;
        }
        return false;
    }

    /**
     * 验证传入的座位是否已卖出
     *
     * @param fieldId
     * @param seats
     * @return
     */
    @Override
    public boolean isNotSoldSeats(String fieldId, String seats) {

        //1.获取指定场次id已卖出的位置集合
        String seatsByFieldId = this.moocOrderTMapper.getSeatsByFieldId(fieldId);

        //2.验证和传入的位置信息是否有重合的
        String[] arr1 = seats.split(",");
        String[] arr2 = seatsByFieldId.split(",");
        long jCount = Arrays.stream(arr1).distinct().filter(x -> Arrays.stream(arr2).distinct().anyMatch(y -> x.equals(y))).count();

        //3.返回是否有重合的
        return jCount == 0;
    }

    /**
     * 创建订单
     *
     * @param fieldId
     * @param soldSeats
     * @param seatsName
     * @param userId
     * @return
     */
    @Override
    public OrderVo saveOrderInfo(Integer fieldId, String soldSeats, String seatsName, Integer userId) {

        //1.根据场次id获取对应的场次信息(其中包含了影院id，影片id，价格)
        OrderQueryVo orderQueryVo = this.cinemaServiceAPI.getFieldVoByFieldId(fieldId);

        //2.组装订单对象
        MoocOrderT moocOrderT = new MoocOrderT();
        moocOrderT.setUuid(UuidUtil.getUuid());
        moocOrderT.setCinemaId(Integer.valueOf(orderQueryVo.getCinemaId()));
        moocOrderT.setFieldId(fieldId);
        moocOrderT.setFilmId(Integer.valueOf(orderQueryVo.getFilmId()));
        moocOrderT.setSeatsIds(soldSeats);
        moocOrderT.setSeatsName(seatsName);
        double price = Double.valueOf(orderQueryVo.getPrice());
        int solds = soldSeats.split(",").length;
        moocOrderT.setFilmPrice(price);
        moocOrderT.setOrderPrice(getTotalPrice(solds, price));
        moocOrderT.setOrderUser(userId);

        Integer insert = this.moocOrderTMapper.insert(moocOrderT);
        if (insert > 0) {
            //查询订单，组装信息
            OrderVo orderVoByUuid = this.moocOrderTMapper.getOrderVoByUuid(moocOrderT.getUuid());
            if (orderVoByUuid == null) {
                log.error("根据uuid:{}查询订单失败", moocOrderT.getUuid());
                return null;
            }
            return orderVoByUuid;
        } else {
            //出现异常
            log.error("创建订单出现异常，插入失败");
        }

        return null;
    }

    /**
     * 获取当前订单总价格
     *
     * @param solds
     * @param filmPrice
     * @return
     */
    private double getTotalPrice(int solds, double filmPrice) {
        BigDecimal soldDecimal = new BigDecimal(solds);
        BigDecimal filmPriceDecimal = new BigDecimal(filmPrice);

        //相乘
        BigDecimal result = soldDecimal.multiply(filmPriceDecimal);

        //四舍五入
        BigDecimal bigDecimal = result.setScale(2, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }


    /**
     * 获取指定用户的订单信息
     *
     * @param userId
     * @return
     */
    @Override
    public List<OrderVo> getOrdersByUserId(Integer userId, Page<OrderVo> page) {
        //List<OrderVo> orderVosByUsreId = this.moocOrderTMapper.getOrderVosByUsreId(userId, page);
        List<OrderVo> orderVosByUsreId = this.moocOrderTMapper.getOrderVosByUsreId(userId, page.getLimit(), page.getOffset());
        if (CollectionUtils.isEmpty(orderVosByUsreId)) {
            return new ArrayList<>();
        }
        return orderVosByUsreId;
    }

    /**
     * 根据放映场次id获取已售出的座位编号
     *
     * @param fieldId
     * @return
     */
    @Override
    public String getSoldSeatsByFieldId(Integer fieldId) {
        if (fieldId == null) {
            log.error("根据放映场次id查询已售出的座位，放映场次id为空");
            return null;
        }
        return this.moocOrderTMapper.getSeatsByFieldId(String.valueOf(fieldId));
    }

    /**
     * 查询指定订单id对应的订单信息
     *
     * @param orderId
     * @return
     */
    @Override
    public OrderVo getOrderInfoById(String orderId) {
        return this.moocOrderTMapper.getOrderVoByUuid(orderId);
    }

    /**
     * 支付成功
     *
     * @param orderId
     * @return
     */
    @Override
    public boolean paySuccess(String orderId) {
        MoocOrderT moocOrderT = new MoocOrderT();
        moocOrderT.setUuid(orderId);
        moocOrderT.setOrderStatus(1);
        Integer result = this.moocOrderTMapper.updateById(moocOrderT);
        return result > 0;
    }

    /**
     * 支付失败
     *
     * @param orderId
     * @return
     */
    @Override
    public boolean payFail(String orderId) {
        MoocOrderT moocOrderT = new MoocOrderT();
        moocOrderT.setUuid(orderId);
        moocOrderT.setOrderStatus(2);
        Integer result = this.moocOrderTMapper.updateById(moocOrderT);
        return result > 0;
    }
}
