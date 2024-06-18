package com.shane.alibaba.controller;

import com.shane.alibaba.lock.DistributedLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private DistributedLock distributedLock;

    @GetMapping("/{name}")
    public String hello(@PathVariable String name) {
        String mode = distributedLock.lock();
        try {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("hello " + name);
            return "ok";
        } finally {
            distributedLock.unlock(mode);
        }

    }
}
