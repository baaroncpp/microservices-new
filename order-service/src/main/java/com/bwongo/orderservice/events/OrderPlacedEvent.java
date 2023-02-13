package com.bwongo.orderservice.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author bkaaron
 * @Project microservices-new
 * @Date 2/13/23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderPlacedEvent {
    private String orderNumber;
}
