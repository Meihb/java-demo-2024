package com.example.demo.syncExample;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AccountConcurrencyTest {

    @Autowired
    private AccountTransferService transferService;

    @Test
    public void testConcurrentTransfers() throws InterruptedException {
        Account a1 = new Account("A001", new BigDecimal("10000"));
        Account a2 = new Account("A002", new BigDecimal("0"));

        int threadCount = 100;
        BigDecimal amountPerThread = new BigDecimal("50");

        ExecutorService executor = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try {
                    transferService.transfer(a1, a2, amountPerThread);
                } catch (Exception e) {
                    // 可以记录日志，不抛出
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        System.out.println("A1余额: " + a1.getBalance());
        System.out.println("A2余额: " + a2.getBalance());

        assertEquals(new BigDecimal("5000"), a1.getBalance());
        assertEquals(new BigDecimal("5000"), a2.getBalance());
    }
}
