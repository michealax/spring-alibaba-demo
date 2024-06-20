package org.shane.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/config")
@RefreshScope
public class ConfigController {

    @Value(value = "${useLocalCache:false}")
    private boolean useLocalCache;


    @GetMapping("/cacheStatus")
    public boolean get() {
        return useLocalCache;
    }
}
