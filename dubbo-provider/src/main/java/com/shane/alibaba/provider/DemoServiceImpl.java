package com.shane.alibaba.provider;

import com.shane.dubbo.service.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Value;

@DubboService
@Slf4j
public class DemoServiceImpl implements DemoService {

    @Value("${server.port}")
    private int port;

    @Override
    public String sayHello(String name) {
        log.info("provider port: {}", port);
        return "Hello " + name;
    }
}
