package com.example.demo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceExample implements Runnable {
    private String taskName;

    public ExecutorServiceExample(String name) {
        this.taskName = name;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " 执行任务: " + taskName);
        try {
            Thread.sleep(1000); // 模拟任务执行时间
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " 完成任务: " + taskName);
    }

    public static void main(String[] args) {
        // 创建固定大小为3的线程池
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        // 提交5个任务到线程池
        for (int i = 1; i <= 5; i++) {
            Runnable worker = new ExecutorServiceExample("任务" + i);
            executorService.execute(worker);
        }

        // 关闭线程池
        executorService.shutdown();

        while (!executorService.isTerminated()) {
            // 等待所有任务完成
        }

        System.out.println("所有任务已完成。");
    }
}

