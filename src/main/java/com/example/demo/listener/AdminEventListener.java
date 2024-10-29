package com.example.demo.listener;

import com.example.demo.event.AdminEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AdminEventListener {

    @EventListener
    public void onAdminEvent(AdminEvent event) {
        System.out.println("Received admin event - message: " + event.getMessage());
    }
}

