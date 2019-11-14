package com.leyou.auth.config;

import com.leyou.auth.utils.RsaUtils;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;

@Configuration
@Component
@Data
public class JwtProperties {

    @Value("${leyou.jwt.secret}")
    private String secret;

    @Value("${leyou.jwt.pubKeyPath}")
    private String pubKeyPath;

    @Value("${leyou.jwt.priKeyPath}")
    private String priKeyPath;

    @Value("${leyou.jwt.expire}")
    private int expire;

    @Value("${leyou.jwt.cookieName}")
    private String cookieName;

    private PublicKey publicKey;
    private PrivateKey privateKey;

    //对象一旦实例化后，就应该读取公钥私钥
    @PostConstruct //构造函数执行完毕之后
    public void init() throws Exception {
        //公钥私钥不存在
        File pubPath = new File(pubKeyPath);
        File priPath = new File(priKeyPath);
        if(!pubPath.exists() || !priPath.exists()){
            RsaUtils.generateKey(pubKeyPath,priKeyPath,secret);
        }
        //读取公钥私钥
        publicKey = RsaUtils.getPublicKey(pubKeyPath);
        privateKey = RsaUtils.getPrivateKey(priKeyPath);
    }

}
