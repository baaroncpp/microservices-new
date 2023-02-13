package com.bwongo.notificationservice;

import com.bwongo.notificationservice.events.OrderPlacedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

/**
 * @Author bkaaron
 * @Project microservices-new
 * @Date 2/13/23
 */
@SpringBootApplication
@Slf4j
public class NotificationServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }

    @KafkaListener(topics = "notificationTopic")
    public void handleNotification(OrderPlacedEvent orderPlacedEvent){
        log.info("Received Notification for Order - {}", orderPlacedEvent.getOrderNumber());
    }

}
