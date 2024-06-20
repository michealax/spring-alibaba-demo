package org.shane;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class NacosDemoApp {
    public static void main(String[] args) {
        SpringApplication.run(NacosDemoApp.class, args);
    }
}