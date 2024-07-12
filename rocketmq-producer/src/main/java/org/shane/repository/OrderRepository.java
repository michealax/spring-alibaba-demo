package org.shane.repository;

import org.shane.entity.OrderEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository {

    int save(OrderEntity orderEntity);

    Long existByOrderId(Long orderId);

    Integer existByTransactionId(String transactionId);
}
