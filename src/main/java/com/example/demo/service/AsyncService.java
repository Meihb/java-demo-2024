package com.example.demo.service;

import com.example.demo.ThreadExample;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

@Service
@Slf4j
public class AsyncService {
    @Autowired
    private Executor taskExecutor;

    @Async("taskExecutor")
    public void executeAsyncTask() {
        System.out.println("执行异步任务：" + Thread.currentThread().getName());
        // 模拟长时间任务
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.fillInStackTrace();
//        }
        ThreadExample thread1 = new ThreadExample("线程1");
        ThreadExample thread2 = new ThreadExample("线程2");

        thread1.start();
        thread2.start();
        System.out.println("任务执行完毕：" + Thread.currentThread().getName());
    }

    @Async("taskExecutor")
    public void executeAsyncTask2() {
        System.out.println("执行异步任务：" + Thread.currentThread().getName());
        // 模拟长时间任务
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.fillInStackTrace();
        }
        System.out.println("任务执行完毕：" + Thread.currentThread().getName());
    }

    @Async
    public Future<Boolean> processItemsAsync() {

        FutureTask<Boolean> futureTask = new FutureTask<>(() -> {
            log.info("开始执行任务");
            List<String> items = Arrays.asList("Item1", "Item2", "Item3", "Item4");
            try {
                for (String item : items) {
                    if (Thread.currentThread().isInterrupted()) {
                        log.info("Received shutdown signal, performing cleanup...");
                        // 进行必要的保护现场操作，如释放资源等
                        break;
                    }
                    // 处理每个 item 的逻辑
                    log.info("Processing item: {}", item);
                    // 模拟处理时间
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // 恢复中断状态
                log.warn("Processing interrupted, exiting...");
                return false;
            }
            return true;
        });
        futureTask.run();
        return futureTask;
    }

    @Async
    public CompletableFuture<Boolean> processItemsAsync2() {
        return CompletableFuture.supplyAsync(() -> {
            log.info("开始执行任务");
            List<String> items = Arrays.asList("Item1", "Item2", "Item3", "Item4");
            try {
                for (String item : items) {
                    if (Thread.currentThread().isInterrupted()) {
                        log.info("Received shutdown signal, performing cleanup...");
                        // 在此进行必要的保护现场操作
                        return false;
                    }
                    // 处理每个 item 的逻辑
                    log.info("Processing item: {}", item);
                    Thread.sleep(1000); // 模拟处理时间
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.warn("Processing interrupted, exiting...");
                return false;
            }
            return true;
        });


//        CompletableFuture.runAsync(() -> {
//            try {
//                for (String item : items) {
//                    if (Thread.currentThread().isInterrupted()) {
//                        log.info("Received shutdown signal, performing cleanup...");
//                        // 进行必要的保护现场操作，如释放资源等
//                        break;
//                    }
//                    // 处理每个 item 的逻辑
//                    log.info("Processing item: {}", item);
//                    // 模拟处理时间
//                    Thread.sleep(1000);
//                }
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt(); // 恢复中断状态
//                log.warn("Processing interrupted, exiting...");
//            }
//        });
    }
}
