package com.example.demo;

/**
 * 同步方法：
 * deposit() 和 withdraw() 方法使用 synchronized 关键字修饰，确保同一时间只有一个线程可以执行这些方法，防止并发访问导致的数据不一致。
 * 线程操作：
 * 创建了两个线程，一个执行存款操作，另一个执行取款操作。
 * 线程同时启动，synchronized 确保操作的原子性。
 */
public class BankAccount {
    private int balance = 1000; // 初始余额

    // 存款操作
    public synchronized void deposit(int amount) {
        balance += amount;
        System.out.println(Thread.currentThread().getName() + " 存款 " + amount + "，当前余额：" + balance);
    }

    // 取款操作
    public synchronized void withdraw(int amount) {
        if (balance >= amount) {
            balance -= amount;
            System.out.println(Thread.currentThread().getName() + " 取款 " + amount + "，当前余额：" + balance);
        } else {
            System.out.println(Thread.currentThread().getName() + " 取款失败，余额不足！");
        }
    }

    public static void main(String[] args) {
        BankAccount account = new BankAccount();

        // 创建存款线程
        Thread depositThread = new Thread(() -> {
            account.deposit(500);
        }, "存款线程");

        // 创建取款线程
        Thread withdrawThread = new Thread(() -> {
            account.withdraw(700);
        }, "取款线程");

        // 同时启动线程
        depositThread.start();
        withdrawThread.start();
    }
}

