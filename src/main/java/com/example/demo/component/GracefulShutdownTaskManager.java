package com.example.demo.component;

import com.example.demo.service.AsyncServiceTest;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class GracefulShutdownTaskManager {

    @Autowired(required = false)
    private AsyncServiceTest asyncService;

    @PostConstruct
    public void init() {
        // 添加JVM钩子，在应用关闭时中断任务
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("Shutdown hook triggered, interrupting task...");
            if (asyncService != null) {
                asyncService.stopCronTask();
            }
        }));
    }
}