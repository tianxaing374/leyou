package com.leyou.gateway.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Component
@Configuration
@Data
public class FilterProperty {

    @Value("${leyou.filter.allowPaths}")
    private String allowPathsString;

    private List<String> allowPaths;

    @PostConstruct
    public void init(){
        allowPaths = Arrays.asList(allowPathsString.split(";"));
    }

}
