package com.example.demo.listener;

// UserCreatedListener.java

import com.example.demo.event.UserCreatedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class UserCreatedListener {

    @EventListener
    public void handleUserCreatedEvent(UserCreatedEvent event) {
        System.out.println("User created with ID: " + event.getUserId());
        // Add logic here for user creation
    }
}



