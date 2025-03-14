package com.example.demo.entity;

import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

public class Account {

    @Autowired
    private static Allocator actr; // 资源分配器
    private BigDecimal balance;

    public Account(BigDecimal balance) {
        this.balance = balance;
    }

    // 转账方法
    void transfer(Account target, BigDecimal amt) {
        // 可以 sort 优化,作为防止循环等待
        actr.apply(this, target);
        try {
            synchronized (this) {
                synchronized (target) {
                    if (this.balance.compareTo(amt) < 0) {
                        throw new RuntimeException("余额不足");
                    }
                    this.balance = this.balance.subtract(amt);
                    target.balance = target.balance.add(amt);
                }
            }
        } finally {
            actr.free(this, target);
        }
    }
}
