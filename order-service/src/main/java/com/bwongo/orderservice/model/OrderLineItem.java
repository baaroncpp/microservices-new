package com.bwongo.orderservice.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @Project order-service
 * @Date 1/6/23
 */
@Entity
@Table(name = "t_order_line_item")
@Data
public class OrderLineItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String skuCode;
    private BigDecimal price;
    private int quantity;
}
