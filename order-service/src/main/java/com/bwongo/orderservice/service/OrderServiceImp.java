package com.bwongo.orderservice.service;

import com.bwongo.orderservice.events.OrderPlacedEvent;
import com.bwongo.orderservice.model.Inventory;
import com.bwongo.orderservice.model.Order;
import com.bwongo.orderservice.model.OrderLineItem;
import com.bwongo.orderservice.repository.OrderLineItemRepository;
import com.bwongo.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
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
    private Tracer tracer;
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    public OrderServiceImp(OrderRepository orderRepository,
                           //WebClient webClient,
                           WebClient.Builder webClient,
                           OrderLineItemRepository orderLineItemRepository, Tracer tracer, KafkaTemplate<String, Object> kafkaTemplate) {
        this.orderRepository = orderRepository;
        this.webClient = webClient;
        this.orderLineItemRepository = orderLineItemRepository;
        this.tracer = tracer;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    @Transactional
    public Order makeOrder(Order order) {
        //make call to inventory service

        List<String> skuCodes = order.getOrderLineItemsList().stream()
                .map(orderLineItem -> orderLineItem.getSkuCode())
                .collect(Collectors.toList());

        Span inventoryServiceLookup = tracer.nextSpan().name("InventoryServiceLookup");

        try(Tracer.SpanInScope spanInScope = tracer.withSpan(inventoryServiceLookup.start())){
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
            order.setOrderNumber(UUID.randomUUID().toString());

            kafkaTemplate.send("notificationTopic", new OrderPlacedEvent(order.getOrderNumber()));
            return orderRepository.save(order);
        }finally {
            inventoryServiceLookup.end();
        }

    }
}
