package com.bwongo.orderservice.repository;

import com.bwongo.orderservice.model.OrderLineItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Project order-service
 * @Date 1/6/23
 */
@Repository
public interface OrderLineItemRepository extends JpaRepository<OrderLineItem, Long> {
}
