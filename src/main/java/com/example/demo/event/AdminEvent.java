package com.example.demo.event;

import org.springframework.context.ApplicationEvent;

public class AdminEvent extends ApplicationEvent {
    private final String message;

    public AdminEvent(Object source, String message) {
        super(source);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
