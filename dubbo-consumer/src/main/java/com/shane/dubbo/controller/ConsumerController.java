package com.shane.dubbo.controller;

import com.shane.dubbo.service.DemoService;
import com.shane.dubbo.service.UserService;
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

    @DubboReference
    private UserService userService;

    @GetMapping("/{name}")
    public String sayHello(@PathVariable String name) {
        return demoService.sayHello(name);
    }

    @GetMapping("/bye/{name}")
    public String sayBye(@PathVariable String name) {
        return demoService.sayBye(name);
    }

    @GetMapping("/user/{id}")
    public String getUser(@PathVariable Integer id) {
        return userService.getUser(id);
    }
}
