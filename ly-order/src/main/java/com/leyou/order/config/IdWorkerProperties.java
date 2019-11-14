package com.leyou.order.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
@Data
public class IdWorkerProperties {

    @Value("${ly.worker.workerId}")
    private long workedId;

    @Value("${ly.worker.dataCenterId}")
    private long dataCenterId;

}
