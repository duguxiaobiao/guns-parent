package com.stylefeng.guns.alipay.service;

import com.alipay.api.AlipayResponse;
import com.alipay.api.domain.TradeFundBill;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.stylefeng.guns.alipay.trade.config.Configs;
import com.stylefeng.guns.alipay.trade.model.ExtendParams;
import com.stylefeng.guns.alipay.trade.model.GoodsDetail;
import com.stylefeng.guns.alipay.trade.model.builder.AlipayTradePrecreateRequestBuilder;
import com.stylefeng.guns.alipay.trade.model.builder.AlipayTradeQueryRequestBuilder;
import com.stylefeng.guns.alipay.trade.model.result.AlipayF2FPrecreateResult;
import com.stylefeng.guns.alipay.trade.model.result.AlipayF2FQueryResult;
import com.stylefeng.guns.alipay.trade.service.AlipayMonitorService;
import com.stylefeng.guns.alipay.trade.service.AlipayTradeService;
import com.stylefeng.guns.alipay.trade.service.impl.AlipayMonitorServiceImpl;
import com.stylefeng.guns.alipay.trade.service.impl.AlipayTradeServiceImpl;
import com.stylefeng.guns.alipay.trade.service.impl.AlipayTradeWithHBServiceImpl;
import com.stylefeng.guns.alipay.trade.utils.Utils;
import com.stylefeng.guns.alipay.trade.utils.ZxingUtils;
import com.stylefeng.guns.api.alipay.AlipayServiceAPI;
import com.stylefeng.guns.api.alipay.model.PayQrCodeVo;
import com.stylefeng.guns.api.alipay.model.PayResultVo;
import com.stylefeng.guns.api.order.OrderServiceAPI;
import com.stylefeng.guns.api.order.model.OrderVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ztkj-hzb
 * @Date 2019/10/13 14:12
 * @Description 订单支付接口实现
 */
@Service
@Slf4j
public class DefaultAlipayServiceImpl implements AlipayServiceAPI {

    /**
     * 支付宝当面付2.0服务
     */
    private static AlipayTradeService tradeService;

    /**
     * 支付宝当面付2.0服务（集成了交易保障接口逻辑）
     */
    private static AlipayTradeService tradeWithHBService;

    /**
     * 支付宝交易保障接口服务，供测试接口api使用，请先阅读readme.txt
     */
    private static AlipayMonitorService monitorService;

    @Reference
    private OrderServiceAPI orderServiceAPI;

    /**
     * ]
     * 获取支付二维码
     *
     * @param orderId
     * @return
     */
    @Override
    public PayQrCodeVo getPayInfo(String orderId) {

        //1. 根据订单id构建收费二维码
        String qrCodePath = this.tradePrecreate(orderId);
        if (StringUtils.isBlank(qrCodePath)) {
            //二维码构建失败
            log.error("二维码构建失败");
            return null;
        }

        PayQrCodeVo payQrCodeVo = new PayQrCodeVo();
        payQrCodeVo.setOrderId(orderId);
        payQrCodeVo.setQrCodeAddress(qrCodePath);
        return payQrCodeVo;
    }

    /**
     * 获取支付结果
     *
     * @param orderId
     * @return
     */
    @Override
    public PayResultVo getPayResult(String orderId) {

        //1.查询指定订单的状态
        boolean query = this.tradeQuery(orderId);

        //2.根据状态构建返回值
        PayResultVo payResultVo = new PayResultVo();
        payResultVo.setOrderId(orderId);
        payResultVo.setOrderStatus(query ? 1 : 2);
        payResultVo.setOrderMsg(query ? "支付成功" : "订单支付失败，请稍后重试");
        return payResultVo;
    }


    static {
        /** 一定要在创建AlipayTradeService之前调用Configs.init()设置默认参数
         *  Configs会读取classpath下的zfbinfo.properties文件配置信息，如果找不到该文件则确认该文件是否在classpath目录
         */
        Configs.init("zfbinfo.properties");

        /** 使用Configs提供的默认参数
         *  AlipayTradeService可以使用单例或者为静态成员对象，不需要反复new
         */
        tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();

        // 支付宝当面付2.0服务（集成了交易保障接口逻辑）
        tradeWithHBService = new AlipayTradeWithHBServiceImpl.ClientBuilder().build();

        /** 如果需要在程序中覆盖Configs提供的默认参数, 可以使用ClientBuilder类的setXXX方法修改默认参数 否则使用代码中的默认设置 */
        monitorService = new AlipayMonitorServiceImpl.ClientBuilder()
                .setGatewayUrl("http://mcloudmonitor.com/gateway.do").setCharset("GBK")
                .setFormat("json").build();
    }


    /**
     * 当面付2.0生成支付二维码
     *
     * @param orderId
     * @return
     */
    private String tradePrecreate(String orderId) {

        String qrCodePath = StringUtils.EMPTY;

        //根据订单id获取对应的订单信息
        OrderVo orderInfoById = this.orderServiceAPI.getOrderInfoById(orderId);

        // (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
        // 需保证商户系统端不能重复，建议通过数据库sequence生成，
        String outTradeNo = orderId;

        // (必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店当面付扫码消费”
        String subject = "Lonely影城当面付扫码消费";

        // (必填) 订单总金额，单位为元，不能超过1亿元
        // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
        String totalAmount = orderInfoById.getOrderPrice();

        // (可选) 订单不可打折金额，可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段
        // 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】
        String undiscountableAmount = "0";

        // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
        // 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
        String sellerId = "";

        // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品2件共15.00元"
        String body = MessageFormat.format("本次购票共消费{0}元", orderInfoById.getOrderPrice());

        // 商户操作员编号，添加此参数可以为商户操作员做销售统计
        String operatorId = "lonely";

        // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
        String storeId = "Lonely影城";

        // 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
        ExtendParams extendParams = new ExtendParams();
        extendParams.setSysServiceProviderId("2088100200300400500");

        // 支付超时，定义为120分钟
        String timeoutExpress = "120m";

        // 商品明细列表，需填写购买商品详细信息，
        List<GoodsDetail> goodsDetailList = new ArrayList<>();

        // 创建扫码支付请求builder，设置请求参数
        AlipayTradePrecreateRequestBuilder builder = new AlipayTradePrecreateRequestBuilder()
                .setSubject(subject).setTotalAmount(totalAmount).setOutTradeNo(outTradeNo)
                .setUndiscountableAmount(undiscountableAmount).setSellerId(sellerId).setBody(body)
                .setOperatorId(operatorId).setStoreId(storeId).setExtendParams(extendParams)
                .setTimeoutExpress(timeoutExpress)
                //                .setNotifyUrl("http://www.test-notify-url.com")//支付宝服务器主动通知商户服务器里指定的页面http路径,根据需要设置
                .setGoodsDetailList(goodsDetailList);

        AlipayF2FPrecreateResult result = tradeService.tradePrecreate(builder);
        switch (result.getTradeStatus()) {
            case SUCCESS:
                log.info("支付宝预下单成功: )");

                AlipayTradePrecreateResponse response = result.getResponse();
                dumpResponse(response);

                //获取当前系统的resources/qrCodes目录下
                String pathPrefix = Thread.currentThread().getClass().getResource("/qrCodes").getPath();

                // 需要修改为运行机器上的路径
                qrCodePath = String.format("%s/qr-%s.png", pathPrefix, response.getOutTradeNo());
                log.info("filePath:" + qrCodePath);
                ZxingUtils.getQRCodeImge(response.getQrCode(), 256, qrCodePath);
                break;

            case FAILED:
                log.error("支付宝预下单失败!!!");
                break;

            case UNKNOWN:
                log.error("系统异常，预下单状态未知!!!");
                break;

            default:
                log.error("不支持的交易状态，交易返回异常!!!");
                break;
        }
        return qrCodePath;
    }


    /**
     * 当面付2.0查询订单
     *
     * @param orderId
     * @return
     */
    private boolean tradeQuery(String orderId) {

        boolean paySuccess = false;

        // (必填) 商户订单号，通过此商户订单号查询当面付的交易状态
        String outTradeNo = orderId;

        // 创建查询请求builder，设置请求参数
        AlipayTradeQueryRequestBuilder builder = new AlipayTradeQueryRequestBuilder()
                .setOutTradeNo(outTradeNo);

        AlipayF2FQueryResult result = tradeService.queryTradeResult(builder);
        switch (result.getTradeStatus()) {
            case SUCCESS:
                log.info("查询返回该订单支付成功: )");

                AlipayTradeQueryResponse response = result.getResponse();
                dumpResponse(response);

                log.info(response.getTradeStatus());
                if (Utils.isListNotEmpty(response.getFundBillList())) {
                    for (TradeFundBill bill : response.getFundBillList()) {
                        log.info(bill.getFundChannel() + ":" + bill.getAmount());
                    }
                }

                //支付成功，则修改订单状态为支付成功
                this.orderServiceAPI.paySuccess(orderId);
                paySuccess = true;
                break;

            case FAILED:
                log.error("查询返回该订单支付失败或被关闭!!!");
                this.orderServiceAPI.payFail(orderId);
                paySuccess = false;
                break;

            case UNKNOWN:
                log.error("系统异常，订单支付状态未知!!!");
                break;

            default:
                log.error("不支持的交易状态，交易返回异常!!!");
                break;
        }
        return paySuccess;
    }


    /**
     * 简单打印应答
     *
     * @param response
     */
    private void dumpResponse(AlipayResponse response) {
        if (response != null) {
            log.info(String.format("code:%s, msg:%s", response.getCode(), response.getMsg()));
            if (StringUtils.isNotEmpty(response.getSubCode())) {
                log.info(String.format("subCode:%s, subMsg:%s", response.getSubCode(),
                        response.getSubMsg()));
            }
            log.info("body:" + response.getBody());
        }
    }
}
