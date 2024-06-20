package org.shane.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "provider")
public interface NacosProviderFeign {

    @GetMapping("/user/{name}")
    String getUser(@PathVariable("name") String name);
}
