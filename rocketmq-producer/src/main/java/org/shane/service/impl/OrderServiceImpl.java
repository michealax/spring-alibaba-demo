package org.shane.service.impl;


import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.shane.entity.OrderEntity;
import org.shane.repository.OrderRepository;
import org.shane.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;


    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TransactionListener transactionListener;

    @Override
    @Transactional
    public boolean createOrder(OrderEntity orderEntity) {
        ((TransactionMQProducer) rocketMQTemplate.getProducer()).setTransactionListener(transactionListener);
        Message<OrderEntity> msg = MessageBuilder.withPayload(orderEntity)
                .setHeader(RocketMQHeaders.TRANSACTION_ID, "transaction-test")
                .build();
        rocketMQTemplate.sendMessageInTransaction("TEST_TRANSACTION", msg, orderEntity);
        return true;
    }
}
