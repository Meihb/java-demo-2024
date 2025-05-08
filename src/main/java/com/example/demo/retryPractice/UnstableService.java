package com.example.demo.retryPractice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UnstableService {

    private int counter = 0;

    @Autowired
    private RetryTemplate retryTemplate;

    public String callWithRetry() {
        return retryTemplate.execute(context -> {
            counter++;
            if (counter < 4) {
                log.info("Attempt {}: failing...", counter);
                throw new NeedRetryException("Temporary failure");
            }
            log.info("Attempt {}: success", counter);
            return "Success at attempt " + counter;
        }, context -> {
            log.info("recover...");
            return "Failed as last";
        });
    }

    public void resetCounter() {
        counter = 0;
    }
}
