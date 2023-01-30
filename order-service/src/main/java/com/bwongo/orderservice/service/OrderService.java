package com.bwongo.orderservice.service;

import com.bwongo.orderservice.model.Order;

/**
 * @Project order-service
 * @Date 1/6/23
 */
public interface OrderService {
    Order makeOrder(Order order);
}
