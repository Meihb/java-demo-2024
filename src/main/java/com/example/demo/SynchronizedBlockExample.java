package com.example.demo;


/**
 * 使用对象 lock 作为同步锁，确保 count 的增减操作是线程安全的。
 * 多个线程同时调用 increment() 方法，通过同步块防止数据竞争。
 */
public class SynchronizedBlockExample {
    private int count = 0;
    private final Object lock = new Object();

    public void increment() {
        synchronized (lock) {
            count++;
            System.out.println(Thread.currentThread().getName() + " 增加计数，当前值：" + count);
        }
    }

    public static void main(String[] args) {
        SynchronizedBlockExample example = new SynchronizedBlockExample();

        Runnable task = example::increment;

        // 创建并启动多个线程
        for (int i = 1; i <= 5; i++) {
            new Thread(task, "线程" + i).start();
        }
    }
}

