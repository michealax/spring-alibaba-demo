package org.shane;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.shane.repository")
public class RocketMQProducerApp {
    public static void main(String[] args) {
        SpringApplication.run(RocketMQProducerApp.class, args);
    }
}