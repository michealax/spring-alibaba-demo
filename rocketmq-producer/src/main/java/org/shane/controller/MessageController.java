package org.shane.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@Slf4j
public class MessageController {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @GetMapping("/msg/{name}")
    public String sendMsg(@PathVariable String name) {
//        rocketMQTemplate.syncSend("first-topic-str", "hello world test sync");
        SendResult res = rocketMQTemplate.syncSend("first-topic-str:tag1", "hello with " + name);
        log.info("send sync message: {}", res);
        return "OK";
    }

    @GetMapping("/msg/async/{name}")
    public String asyncSendMsg(@PathVariable String name) {
        rocketMQTemplate.asyncSend("first-topic-str", "async: " + name, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("send async message: {}", sendResult);
            }

            @Override
            public void onException(Throwable throwable) {
                log.error("send async error: {}", throwable.getMessage());
            }
        });

        return "OK";
    }

    @GetMapping("/msg/order/{name}")
    public String order(@PathVariable String name) {
        SendResult res = rocketMQTemplate.syncSendOrderly("first-topic-str", "order1-" + name, "0");
        log.info("send sync order message: {}", res);
        res = rocketMQTemplate.syncSendOrderly("first-topic-str", "order1-" + LocalDateTime.now(), "0");
        log.info("send sync order message: {}", res);
        res = rocketMQTemplate.syncSendOrderly("first-topic-str", "order1-finished", "0");
        log.info("send sync order message: {}", res);
        return "OK";
    }

    @GetMapping("/msg/delay/{name}")
    public String delay(@PathVariable String name) {
        SendResult res = rocketMQTemplate.syncSendDelayTimeSeconds("first-topic-str", "order1-" + name, 30);
        log.info("send sync delay message: {}", res);
         res = rocketMQTemplate.syncSendDelayTimeSeconds("demo-delay", "order1-" + name, 30);
        log.info("send sync delay message: {}", res);
        return "OK";
    }
}
