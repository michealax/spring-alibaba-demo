package org.shane.event;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.shane.entity.OrderEntity;
import org.shane.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MyTransactionCheckListener implements TransactionListener {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public LocalTransactionState executeLocalTransaction(Message message, Object o) {
        try {
            String transactionId = message.getTransactionId();
            OrderEntity orderEntity = (OrderEntity) o;
            orderEntity.setTransactionId(transactionId);
            orderRepository.save(orderEntity);
            log.info("create order: orderId:{}, transactionId:{}", orderEntity.getOrderId(), transactionId);
            return LocalTransactionState.COMMIT_MESSAGE;
        } catch (Exception e) {
            log.info(e.getMessage());
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }
    }

    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
        String transactionId = messageExt.getTransactionId();
        Integer res = orderRepository.existByTransactionId(transactionId);
        if (res == 1) {
            return LocalTransactionState.COMMIT_MESSAGE;
        }
        return LocalTransactionState.ROLLBACK_MESSAGE;
    }
}
