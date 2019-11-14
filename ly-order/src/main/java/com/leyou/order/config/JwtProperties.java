package com.leyou.order.config;

import com.leyou.auth.utils.RsaUtils;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.PublicKey;

@Configuration
@Component
@Data
public class JwtProperties {

    @Value("${ly.jwt.pubKeyPath}")
    private String pubKeyPath;

    @Value("${ly.jwt.cookieName}")
    private String cookieName;

    private PublicKey publicKey;

    //对象一旦实例化后，就应该读取公钥私钥
    @PostConstruct //构造函数执行完毕之后
    public void init() throws Exception {
        //读取公钥私钥
        publicKey = RsaUtils.getPublicKey(pubKeyPath);
    }

}
