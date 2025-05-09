package com.example.demo.retryPractice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.util.Collections;

@Configuration
@Slf4j
public class RetryConfig {
    public static final String RETRY_ATTRIBUTE_OPERATION_NAME = "operationName";
    private static final int MAX_RETRY_COUNT = 4;

    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate template = new RetryTemplate();
        log.info("RetryTemplate init");
        // 重试策略：指定异常才重试
        SimpleRetryPolicy policy = new SimpleRetryPolicy(
                MAX_RETRY_COUNT, // 最大重试次数
                Collections.singletonMap(NeedRetryException.class, true) // 哪些异常触发重试
        );
        template.setRetryPolicy(policy);

        // 回退策略：初始 2s，倍数递增
        ExponentialBackOffPolicy backOff = new ExponentialBackOffPolicy();
        backOff.setInitialInterval(5000);
        backOff.setMultiplier(2.0);
        template.setBackOffPolicy(backOff);

        // 添加监听器，记录每次尝试
        template.registerListener(new RetryListener() {
            @Override
            public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
                int currentCount = context.getRetryCount();

                boolean isFinalAttempt = currentCount == MAX_RETRY_COUNT;// 注意：retryCount 从 0 开始计
                if (isFinalAttempt) {
                    log.error("{} failed after {} attempts, last failure due to: {}",
                            context.getAttribute("operationName"),
                            context.getRetryCount(),
                            throwable.getMessage());
                } else {
                    log.info("Retry {} attempt #{} due to: {}",
                            context.getAttribute("operationName"),
                            context.getRetryCount(),
                            throwable.getMessage());
                }
            }
        });

        return template;
    }
}
