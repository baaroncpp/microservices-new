package com.bwongo.orderservice.controller;

import com.bwongo.orderservice.model.Order;
import com.bwongo.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

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
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "inventory")
    @Retry(name = "inventory")
    public CompletableFuture<Object> makeOrder(@RequestBody Order order){
        return CompletableFuture.supplyAsync(() -> orderService.makeOrder(order));
    }

    public CompletableFuture<Object> fallbackMethod(Order order, Exception e){
        return CompletableFuture.supplyAsync(() -> "Oops failed to reach inventory service try again later") ;
    }
}
