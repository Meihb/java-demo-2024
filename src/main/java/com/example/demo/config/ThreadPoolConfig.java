package com.example.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@Slf4j
@EnableScheduling // 一定要使用此注解
public class ThreadPoolConfig {

    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        // 核心线程数：线程池维护线程的最少数量
        executor.setCorePoolSize(5);

        // 最大线程数：线程池维护线程的最大数量
        executor.setMaxPoolSize(10);

        // 队列大小：用于存放任务的队列大小
        executor.setQueueCapacity(25);

        // 线程名的前缀
        executor.setThreadNamePrefix("MyExecutor-");

        // 线程空闲时间：当线程数量超过核心线程数时，多余的线程在等待任务时的最大空闲时间
        executor.setKeepAliveSeconds(60);

        // 线程池关闭时等待所有任务完成再继续销毁其他的Bean
        executor.setWaitForTasksToCompleteOnShutdown(true);

        // 设置线程池关闭前最大等待时间，确保最后的任务被执行
        executor.setAwaitTerminationSeconds(60);

        // 初始化线程池
        executor.initialize();
        return executor;
    }

    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {
        log.info("start init schedule thread pool");
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(10);
        threadPoolTaskScheduler.setThreadNamePrefix("Scheduled-Task-");
        threadPoolTaskScheduler.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());
        threadPoolTaskScheduler.initialize();
        return threadPoolTaskScheduler;
    }
}
