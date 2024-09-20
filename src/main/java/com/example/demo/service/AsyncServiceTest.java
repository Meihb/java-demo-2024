package com.example.demo.service;

import com.example.demo.annotation.ConditionalOnCustomCondition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import static java.lang.Thread.sleep;

@Service
@Slf4j
//@ConditionalOnProperty(name = "scheduler.enabled", havingValue = "true", matchIfMissing = false)
@ConditionalOnCustomCondition
public class AsyncServiceTest {

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;// 一定要开启@EnableScheduling


//    @ConditionalOnCustomCondition
    @Scheduled(cron = "0/5 * * * * *")
    public Integer runScheduledTask() throws InterruptedException {
        log.info("in runScheduledTask");
        sleep(3000);
        log.info("end task");
        return 5;
    }

//    @PreDestroy
//    public void destroy()
//    {
//        log.info("in destroy");
//    }

    public void stopCronTask() {
        log.info("Application is shutting down start.");
        threadPoolTaskScheduler.stop();
    }
}
