package com.shane.dubbo.controller;

import com.shane.dubbo.service.DemoService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consumer")
public class ConsumerController {
    @DubboReference
    private DemoService demoService;

    @GetMapping("/{name}")
    public String sayHello(@PathVariable String name) {
        return demoService.sayHello(name);
    }
}
