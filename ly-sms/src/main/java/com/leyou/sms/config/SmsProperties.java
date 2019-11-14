package com.leyou.sms.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Data
@Configuration
@Component
public class SmsProperties {

    @Value("${leyou.sms.accessKeyId}")
    private String accessKeyId;

    @Value("${leyou.sms.accessKeySecret}")
    private String accessKeySecret;

    @Value("${leyou.sms.signName}")
    private String signName;

    @Value("${leyou.sms.verifyCodeTemplate}")
    private String verifyCodeTemplate;

}
