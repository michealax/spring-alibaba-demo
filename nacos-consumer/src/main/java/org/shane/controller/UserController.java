package org.shane.controller;

import org.shane.feign.NacosProviderFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private NacosProviderFeign feign;


    @GetMapping("/{name}")
    public String getUser(@PathVariable String name) {
        return feign.getUser(name);
    }
}
