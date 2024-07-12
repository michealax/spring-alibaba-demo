package org.shane.consumer;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(consumerGroup = "${rocketmq.consumer.group}", topic = "${rocketmq.consumer.topic}")
public class Consumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String s) {
        System.out.println("Received Msg: {}" + s);
    }
}
