package com.leyou.order.util;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.order.config.PayConfig;
import com.leyou.order.enums.OrderStatusEnum;
import com.leyou.order.enums.PayStateEnum;
import com.leyou.order.mapper.OrderMapper;
import com.leyou.order.mapper.OrderStatusMapper;
import com.leyou.order.pojo.Order;
import com.leyou.order.pojo.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.github.wxpay.sdk.WXPayConstants.*;

@Slf4j
@Component
public class PayHelper {

    @Autowired
    private WXPay wxPay;

    @Autowired
    private PayConfig config;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderStatusMapper orderStatusMapper;

    public String createOrder(Long orderId,Long totalPay,String description){
        try {
            HashMap<String, String> data = new HashMap<>();
            //描述
            data.put("body", description);
            //订单号
            data.put("out_trade_no", orderId.toString());
            //货币（默认就是人民币）
            //data.put("fee_type", "CNY");
            //总金额
            data.put("total_fee", totalPay.toString());
            //调用微信支付的终端ip
            data.put("spbill_create_ip", "127.0.0.1");
            //回调地址
            data.put("notify_url", config.getNotifyUrl());
            //交易类型为扫码支付
            data.put("trade_type", "NATIVE");

            Map<String, String> result = wxPay.unifiedOrder(data);
            for (Map.Entry<String, String> entry : result.entrySet()) {
                log.info("key={};value={}",entry.getKey(),entry.getValue());
            }
            isSuccess(result);
            //下单成功
            return result.get("code_url");
        } catch (Exception e) {
            log.error("【微信下单】创建预交易订单异常", e);
            return null;
        }
    }

    public void isSuccess(Map<String, String> result) {
        //判断通信表示
        String returnCode = result.get("return_code");
        if(FAIL.equals(returnCode)){
            //通信失败
            log.error("[微信下单]通信失败，失败原因:{}",result.get("return_msg"));
            throw new LyException(ExceptionEnum.WX_PAY_CONNECT_FAIL);
        }

        String resultCode = result.get("result_code");
        if(FAIL.equals(resultCode)){
            //业务失败
            log.error("【微信下单】创建预交易订单失败，错误码：{}，错误信息：{}",
                    result.get("err_code"), result.get("err_code_des"));
            throw new LyException(ExceptionEnum.WX_PAY_CONNECT_FAIL);
        }
    }

    public void isValidSign(Map<String, String> data) {
        try {
            //重新生成签名，然后比较
            String sign1 = WXPayUtil.generateSignature(data, config.getKey(), SignType.HMACSHA256);
            String sign2 = WXPayUtil.generateSignature(data, config.getKey(), SignType.MD5);
            String sign = data.get("sign");
            if (!StringUtils.equals(sign,sign1) && !StringUtils.equals(sign,sign2)) {
                //签名有误
                throw new LyException(ExceptionEnum.INVALID_SIGN_ERROR);
            }
        } catch (Exception e) {
            throw new LyException(ExceptionEnum.INVALID_SIGN_ERROR);
        }
    }

    public PayStateEnum queryPayState(Long orderId) {
        try {
            Map<String, String> data = new HashMap<>();
            data.put("out_trade_no",orderId.toString());
            Map<String, String> result = wxPay.orderQuery(data);
            //检验通信状态
            isSuccess(result);
            //校验签名
            isValidSign(result);
            //校验金额
            String totalFeeStr = result.get("total_fee");
            String tradeNo = result.get("out_trade_no");
            if(StringUtils.isBlank(totalFeeStr) || StringUtils.isBlank(tradeNo)){
                throw new LyException(ExceptionEnum.INVALID_ORDER_PARAM);
            }
            Long totalFee = Long.valueOf(totalFeeStr);
            Order order = orderMapper.selectByPrimaryKey(orderId);
            if(totalFee != order.getActualPay()){
                throw new LyException(ExceptionEnum.INVALID_ORDER_PARAM);
            }
            //
            String state = result.get("trade_state");
            if(SUCCESS.equals(state)){
                //修改订单状态
                OrderStatus orderStatus = new OrderStatus();
                orderStatus.setStatus(OrderStatusEnum.PAYED.value());
                orderStatus.setOrderId(orderId);
                orderStatus.setPaymentTime(new Date());
                int count = orderStatusMapper.updateByPrimaryKeySelective(orderStatus);
                if(count!=1){
                    throw new LyException(ExceptionEnum.UPDATE_ORDER_STATUS_ERROR);
                }
                return PayStateEnum.SUCCESS;
            }

            if("NOTPAY".equals(state) || "USERPAYING".equals(state)){
                return PayStateEnum.NOT_PAY;
            }

            return PayStateEnum.FAIL;

        } catch (Exception e) {
            return PayStateEnum.NOT_PAY;
        }
    }
}
