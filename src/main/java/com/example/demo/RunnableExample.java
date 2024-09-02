package com.example.demo;

public class RunnableExample implements Runnable {
    private final String threadName;

    public RunnableExample(String name) {
        this.threadName = name;
    }

    @Override
    public void run() {
        System.out.println(threadName + " 开始执行。");
        try {
            for (int i = 1; i <= 5; i++) {
                System.out.println(threadName + " 执行次数: " + i);
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            System.out.println(threadName + " 被中断。");
        }
        System.out.println(threadName + " 执行完毕。");
    }

    public static void main(String[] args) {
        Thread thread1 = new Thread(new RunnableExample("线程1"));
        Thread thread2 = new Thread(new RunnableExample("线程2"));

        thread1.start();
        thread2.start();
    }
}

