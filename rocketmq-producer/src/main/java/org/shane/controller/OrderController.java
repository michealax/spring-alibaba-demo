package org.shane.controller;

import org.shane.entity.OrderEntity;
import org.shane.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderEntity> save() {
        LocalDateTime date = LocalDateTime.now();

        OrderEntity orderEntity = OrderEntity.builder()
                .orderId(UUID.randomUUID().toString())
                .createTime(date)
                .updateTime(date)
                .build();
        orderService.createOrder(orderEntity);
        return ResponseEntity.ok(orderEntity);
    }
}
