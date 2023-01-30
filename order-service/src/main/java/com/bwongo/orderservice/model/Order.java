package com.bwongo.orderservice.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * @Project order-service
 * @Date 1/6/23
 */
@Entity
@Table(name = "t_order")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderNumber;
    @OneToMany
    private List<OrderLineItem> orderLineItemsList;
}
