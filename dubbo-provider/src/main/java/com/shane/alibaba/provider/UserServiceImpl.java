package com.shane.alibaba.provider;

import com.shane.dubbo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
@Slf4j
public class UserServiceImpl implements UserService {
    @Override
    public String getUser(Integer id) {
        log.info("user id : {}", id);
        return "shane:" + id;
    }
}
