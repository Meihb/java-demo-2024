package com.example.demo.retryPractice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UnstableServiceTest {

    @Autowired
    private UnstableService unstableService;

    @Test
    void testRetrySuccess() {
        unstableService.resetCounter(); // 初始化状态
        String result = unstableService.callWithRetry();
        Assertions.assertEquals("Success at attempt 4", result);
    }

    @Test
    void testRetryFailsCompletely() {
        unstableService.resetCounter();

        Assertions.assertThrows(RuntimeException.class, () -> {
            // 自定义重试策略为最多1次（模拟彻底失败）
            RetryTemplate retryTemplate = new RetryTemplate();
            retryTemplate.setRetryPolicy(new SimpleRetryPolicy(1));

            retryTemplate.execute(context -> {
                throw new RuntimeException("Always fail");
            });
        });
    }
}