package com.bwongo.orderservice.controller;

import com.bwongo.orderservice.model.Order;
import com.bwongo.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @Project order-service
 * @Date 1/6/23
 */
@RestController
@RequestMapping("/api/v1/order")
public class OrderApi {

    @Autowired
    private OrderService orderService;

    @PostMapping(produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Order makeOrder(@RequestBody Order order){
        return orderService.makeOrder(order);
    }
}
