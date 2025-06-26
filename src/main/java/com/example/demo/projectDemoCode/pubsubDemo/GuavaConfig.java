package com.example.demo.projectDemoCode.pubsubDemo;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.eventbus.EventBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
public class GuavaConfig {

    @Bean
    public EventBus eventBus() {
        return new EventBus("permission-event-bus");
    }

    @Bean
    public Cache<Long, List<String>> permissionCache() {
        return CacheBuilder.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .build();
    }

    @Bean
    public PermissionCacheService permissionCacheService(EventBus eventBus,
                                                         PermissionService permissionService,
                                                         Cache<Long, List<String>> permissionCache) {
        PermissionCacheService service = new PermissionCacheService(permissionService, permissionCache);
        eventBus.register(service);
        return service;
    }
}
