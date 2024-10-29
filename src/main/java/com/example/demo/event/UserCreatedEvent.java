package com.example.demo.event;

// UserCreatedEvent.java
public class UserCreatedEvent {
    private final String userId;

    public UserCreatedEvent(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}