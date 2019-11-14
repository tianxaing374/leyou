package com.leyou.upload.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "ly.upload") //需要加入getter和setter方法，配置文件注入属性需要依赖getter和setter方法
public class UploadProperties {
    private String baseUrl;
    private List<String> allowTypes;
}
