package com.example.demo;

public class ThreadExample extends Thread {
    private final String threadName;

    public ThreadExample(String name) {
        this.threadName = name;
    }

    @Override
    public void run() {
        System.out.println(threadName + " 开始执行。");
        try {
            for (int i = 1; i <= 5; i++) {
                System.out.println(threadName + " 执行次数: " + i);
                // 让线程休眠一段时间，模拟实际工作
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            System.out.println(threadName + " 被中断。");
        }
        System.out.println(threadName + " 执行完毕。");
    }

    public static void main(String[] args) {
        ThreadExample thread1 = new ThreadExample("线程1");
        ThreadExample thread2 = new ThreadExample("线程2");

        thread1.start();
        thread2.start();
    }
}

