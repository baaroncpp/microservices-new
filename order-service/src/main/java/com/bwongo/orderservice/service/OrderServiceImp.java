package com.bwongo.orderservice.service;

import com.bwongo.orderservice.model.Inventory;
import com.bwongo.orderservice.model.Order;
import com.bwongo.orderservice.model.OrderLineItem;
import com.bwongo.orderservice.repository.OrderLineItemRepository;
import com.bwongo.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Project order-service
 * @Date 1/6/23
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImp implements OrderService{

    private OrderRepository orderRepository;
    private WebClient.Builder webClient;
    private OrderLineItemRepository orderLineItemRepository;

    @Autowired
    public OrderServiceImp(OrderRepository orderRepository,
                           //WebClient webClient,
                           WebClient.Builder webClient,
                           OrderLineItemRepository orderLineItemRepository) {
        this.orderRepository = orderRepository;
        this.webClient = webClient;
        this.orderLineItemRepository = orderLineItemRepository;
    }

    @Override
    @Transactional
    public Order makeOrder(Order order) {

        List<String> skuCodes = order.getOrderLineItemsList().stream()
                .map(orderLineItem -> orderLineItem.getSkuCode())
                .collect(Collectors.toList());

        //make call to inventory service
        Inventory[] result = webClient.build().get()
                .uri("http://inventory-service/api/v1/inventory",
                        uriBuilder -> uriBuilder
                                .queryParam("skuCodes", skuCodes)
                                .build())
                .retrieve()
                .bodyToMono(Inventory[].class)
                .block();

        Boolean allInStock = Arrays.stream(result)
                .allMatch(Inventory::getIsInStock);

        if(!allInStock){
            throw new IllegalArgumentException("Some of the order Items are in stock");
        }
        List<OrderLineItem> items = orderLineItemRepository.saveAll(order.getOrderLineItemsList());
        order.setOrderLineItemsList(items);

        return orderRepository.save(order);
    }
}
