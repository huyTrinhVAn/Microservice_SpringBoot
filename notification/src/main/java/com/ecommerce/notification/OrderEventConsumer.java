package com.ecommerce.notification;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import java.util.function.Consumer;
import com.ecommerce.notification.payload.OrderCreatedEvent;
import com.ecommerce.notification.payload.OrderStatus;
import java.util.Map;

@Service
@Slf4j
public class OrderEventConsumer {
//    @RabbitListener(queues = "${rabbitmq.queue.name}")
//    public void handleOrderEvent(OrderCreatedEvent  orderEvent){
//        System.out.println("Received Order Event: " + orderEvent);
//
//
//        long orderId = orderEvent.getOrderId();
//        OrderStatus orderStatus = orderEvent.getStatus();
//
//        System.out.println("Order ID: " + orderId);
//        System.out.println("Order Status: " + orderStatus);
//
//        // Update Database
//        // Send Notification
//        // Send Emails
//        // Generate Invoice
//        // Send Seller Notification
//
//    }
@Bean
public Consumer<OrderCreatedEvent> orderCreated() {
    return event -> {
        log.info("Received order created event for order: {}", event.getOrderId());
        log.info("Received order created event for user id: {}", event.getUserId());
    };
}
}