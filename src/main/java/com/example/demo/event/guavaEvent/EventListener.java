package com.example.demo.event.guavaEvent;

import com.google.common.eventbus.Subscribe;

/**
 * 订阅者 这个跟 laravel listener 一样
 */
public class EventListener {

    @Subscribe
    public void handleMessage(MessageEvent event) {
        System.out.println("收到消息: " + event.getMessage());
    }
}
