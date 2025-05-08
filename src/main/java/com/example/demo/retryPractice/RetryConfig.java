package com.example.demo.retryPractice;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.util.Collections;

@Configuration
@Slf4j
public class RetryConfig {
    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate template = new RetryTemplate();
        log.info("RetryTemplate init");
        // 重试策略：指定异常才重试
        SimpleRetryPolicy policy = new SimpleRetryPolicy(
                4, // 最大重试次数
                Collections.singletonMap(NeedRetryException.class, true) // 哪些异常触发重试
        );
        template.setRetryPolicy(policy);

        // 回退策略：初始 2s，倍数递增
        ExponentialBackOffPolicy backOff = new ExponentialBackOffPolicy();
        backOff.setInitialInterval(2000);
        backOff.setMultiplier(2.0);
        template.setBackOffPolicy(backOff);

        return template;
    }
}
