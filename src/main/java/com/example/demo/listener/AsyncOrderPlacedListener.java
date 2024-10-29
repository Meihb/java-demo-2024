package com.example.demo.listener;

// AsyncOrderPlacedListener.java

import com.example.demo.event.OrderPlacedEvent;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AsyncOrderPlacedListener {

    // 异步事件处理
    @Async
    @EventListener
    @SneakyThrows
    public void handleOrderPlacedEvent(OrderPlacedEvent event) {
        Thread.sleep(3000);
        System.out.println("Order placed with ID: " + event.getOrderId());
        log.info("Order placed with ID: " + event.getOrderId());
        // Add logic here for order processing
    }
}
