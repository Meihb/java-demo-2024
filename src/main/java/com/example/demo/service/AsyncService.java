package com.example.demo.service;

import com.example.demo.ThreadExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executor;

@Service
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
}
