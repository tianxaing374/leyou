package com.leyou.auth.utils;

import com.leyou.auth.entity.UserInfo;
import org.junit.Before;
import org.junit.Test;

import java.security.PrivateKey;
import java.security.PublicKey;

import static org.junit.Assert.*;

/**
 * Created by TianXiang on 2019/3/27.
 */
public class RsaUtilsTest {
    private static final String pubKeyPath = "C:\\Users\\TianXiang\\Desktop\\leyou\\rsa.pub";
    private static final String priKeyPath = "C:\\Users\\TianXiang\\Desktop\\leyou\\rsa.pri";
    private PublicKey publicKey;
    private PrivateKey privateKey;

    //测试生成公钥和私钥
    /*@Test
    public void testRsa() throws Exception {
        RsaUtils.generateKey(pubKeyPath,priKeyPath,"234");
    }

    @Before
    public void testGetRsa() throws Exception {
        this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
        this.privateKey = RsaUtils.getPrivateKey(priKeyPath);
    }

    @Test
    public void testGenerateToken() {
        String token = JwtUtils.generateToken(new UserInfo(20L, "jack"), privateKey, 5);
        System.out.println("token = " + token);
    }
    
    @Test
    public void testParseToken() {
        String token = "eyJhbGciOiJSUzI1NiJ9." +
                "eyJpZCI6MjAsInVzZXJuYW1lIjoiamFjayIsImV4cCI6MTU1MzY5MTk3MH0." +
                "Q6oAyJ-gJz9Cd0kGBATK5LtmEHpy2v5CGLAWOhjTJlkUsDPqrcgkVA6W33-pRVGCtpofq0NlVnMbsjg-V5YxrY1VnR_DOu3By5ctxgwn47gTFXnf-PJ_oHmc9_Pw8VSNVZn8gxqLQ0PuZFn3Vnc_zyTPlEr6DIcT4WY2oXWysrE";
        UserInfo userInfo = JwtUtils.getUserInfo(publicKey, token);
        System.out.println("userInfo = " + userInfo);
    }*/
    
}