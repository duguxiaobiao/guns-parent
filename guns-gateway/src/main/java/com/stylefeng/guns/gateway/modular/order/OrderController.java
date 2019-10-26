package com.stylefeng.guns.gateway.modular.order;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.alipay.AlipayServiceAPI;
import com.stylefeng.guns.api.alipay.model.PayQrCodeVo;
import com.stylefeng.guns.api.alipay.model.PayResultVo;
import com.stylefeng.guns.api.order.OrderServiceAPI;
import com.stylefeng.guns.api.order.model.OrderVo;
import com.stylefeng.guns.gateway.common.CurrentUser;
import com.stylefeng.guns.gateway.modular.auth.util.JwtTokenUtil;
import com.stylefeng.guns.gateway.modular.auth.vo.CommonRespose;
import com.stylefeng.guns.gateway.modular.movie.response.MovieIndexResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ztkj-hzb
 * @Date 2019/9/23 17:04
 * @Description 订单服务管理
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Reference
    private OrderServiceAPI orderServiceAPI;

    @Reference
    private AlipayServiceAPI alipayServiceAPI;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    /**
     * 用户下单购票接口，需要携带jwt信息
     *
     * @param fieldId   场次编号
     * @param soldSeats 购买座位编号
     * @param seatsName 购买座位的名称
     * @return
     */
    @PostMapping("/buyTickets")
    public CommonRespose buyTickets(@RequestParam("fieldId") Integer fieldId,
                                    @RequestParam("soldSeats") String soldSeats,
                                    @RequestParam("seatsName") String seatsName) {
        try {
            //1.验证传入的座位是否正确
            boolean trueSeats = this.orderServiceAPI.isTrueSeats(String.valueOf(fieldId), soldSeats);
            if (!trueSeats) {
                return CommonRespose.businessFail("传入的座位信息异常");
            }

            //2.验证传入的座位是否已卖出
            boolean notSoldSeats = this.orderServiceAPI.isNotSoldSeats(String.valueOf(fieldId), soldSeats);
            if (!notSoldSeats) {
                return CommonRespose.businessFail("传入的座位有的已存在已处理的订单中");
            }

            //3.创建订单
            String userId = CurrentUser.getUserId();
            OrderVo orderVo = this.orderServiceAPI.saveOrderInfo(fieldId, soldSeats, seatsName, Integer.valueOf(userId));
            return CommonRespose.success(orderVo);

        } catch (NumberFormatException e) {
            log.error("用户下单异常，异常原因：{}", ExceptionUtils.getStackTrace(e));
            return CommonRespose.systemFail("系统出现异常，请联系管理员");
        }
    }


    /**
     * 获取用户订单信息接口，需要携带jwt信息
     *
     * @param nowPage
     * @param pageSize
     * @return
     */
    @PostMapping("/getOrderInfo")
    public CommonRespose getOrderInfo(@RequestParam(name = "nowPage", required = false, defaultValue = "1") Integer nowPage,
                                      @RequestParam(name = "pageSize", required = false, defaultValue = "5") Integer pageSize) {

        //1. 获取用户信息
        String userId = CurrentUser.getUserId();
        try {
            //2. 获取指定用户的订单信息
            Page<OrderVo> page = new Page<>(nowPage, pageSize);
            List<OrderVo> ordersByUserId = this.orderServiceAPI.getOrdersByUserId(Integer.valueOf(userId), page);

            return CommonRespose.success(ordersByUserId);
        } catch (NumberFormatException e) {
            log.error("查询用户：{}订单列表异常，异常原因：{}", ExceptionUtils.getStackTrace(e), userId);
            return CommonRespose.systemFail("系统出现异常， 请联系管理员");
        }
    }


    /**
     * 获取支付二维码
     *
     * @param orderId
     * @return
     */
    public CommonRespose getPayInfo(@RequestParam String orderId) {

        try {
            PayQrCodeVo payInfo = this.alipayServiceAPI.getPayInfo(orderId);
            return MovieIndexResponse.success("http://img.meetingshop.cn/", payInfo);
        } catch (Exception e) {
            log.error("获取支付二维码异常， 异常原因：{}", ExceptionUtils.getStackTrace(e));
            return CommonRespose.businessFail("订单支付失败，请稍后重试");
        }
    }

    /**
     * 获取支付结果
     *
     * @param orderId
     * @param tryNums
     * @return
     */
    public CommonRespose getPayResult(@RequestParam String orderId,
                                      @RequestParam(name = "tryNums", required = false, defaultValue = "1") int tryNums) {

        PayResultVo payResult = this.alipayServiceAPI.getPayResult(orderId);

        if (tryNums >= 4) {
            return CommonRespose.businessFail("订单支付失败，请稍后重试");
        }

        return CommonRespose.success(payResult);
    }
}
