package com.leyou.order.config;

import com.github.wxpay.sdk.WXPayConfig;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;

@Data
//@Configuration
public class PayConfig implements WXPayConfig {
    //@Value("${ly.pay.appId}")
    private String appID;  //公众账号ID
    
    //@Value("${ly.pay.mchID}")
    private String mchID;  //商户号
    
    //@Value("${ly.pay.key}")
    private String key;  //生成签名的密钥
    
    //@Value("${ly.pay.httpConnectTimeoutMs}")
    private int httpConnectTimeoutMs;   //连接超时时间
    
    //@Value("${ly.pay.httpReadTimeoutMs}")
    private int httpReadTimeoutMs;  //读取超时时间
    
    //@Value("${ly.pay.notifyUrl}")
    private String notifyUrl;// 下单通知回调地址
    
    @Override
    public InputStream getCertStream() {
        return null;
    }
}
