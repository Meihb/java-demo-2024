package com.example.demo.service;

import com.example.demo.annotation.ConditionalOnCustomCondition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;

import static java.lang.Thread.sleep;

@Service
@Slf4j
//@ConditionalOnProperty(name = "scheduler.enabled", havingValue = "true", matchIfMissing = false)
@ConditionalOnCustomCondition
public class AsyncServiceTest {

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;// 一定要开启@EnableScheduling


//    @ConditionalOnCustomCondition // Condition不能修饰方法
    @Scheduled(cron = "0/5 * * * * *")
    /**
     * Scheduled的任务执行中，不会被应用关闭的信号所结束，所以需要通过 GracefulShutdownTaskManager 来控制任务停止,原理是在jvm钩子函数中触发 Schedule.stop，然后才能通过 PreDestroy 来还原现场
     */
    public Integer runScheduledTask() throws InterruptedException {
        log.info("in runScheduledTask");
        sleep(30000);
        log.info("end task");
        return 5;
    }

//    @PreDestroy
//    public void destroy()
//    {
//        log.info("in destroy");
//    }
    @PreDestroy
    public void preDestroy(){
        log.info("in preDestroy");
    }

    public void stopCronTask() {
        log.info("Application is shutting down start.");
        threadPoolTaskScheduler.stop();
    }
}
