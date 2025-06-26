package com.example.demo.event.guavaEvent;

/**
 * 事件类 这个跟 laravel events 一模一样
 */
public class MessageEvent {
    private final String message;
    public MessageEvent(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}