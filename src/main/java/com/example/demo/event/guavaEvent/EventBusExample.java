package com.example.demo.event.guavaEvent;

import com.google.common.eventbus.EventBus;

public class EventBusExample {
    public static void main(String[] args) {
        // 如果把 eventBus 创建后 作为 bean,那不是跟 laravel serviceprovider 一样吗
        EventBus eventBus = new EventBus(); // 或 new AsyncEventBus(executor) # 异步

        EventListener listener = new EventListener();
        eventBus.register(listener);  // 注册监听器

        eventBus.post(new MessageEvent("Hello Guava EventBus!"));  // 发布事件
    }
}
