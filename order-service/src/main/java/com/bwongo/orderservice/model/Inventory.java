package com.bwongo.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @Author bkaaron
 * @Project fully_covered
 * @Date 1/16/23
 */
@Data
public class Inventory {
    private Long id;
    private String skuCode;
    private int quantity;
    private Boolean isInStock;
}
