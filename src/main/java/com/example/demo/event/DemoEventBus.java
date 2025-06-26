package com.example.demo.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class DemoEventBus {
    private final ApplicationEventPublisher publisher;

    public DemoEventBus(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void publishEvent(Object event) {
        System.out.println("event "+ event.getClass());
        publisher.publishEvent(event);
    }
}

