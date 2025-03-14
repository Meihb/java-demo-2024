package com.example.demo.entity;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Allocator {
    private final List<Account> als = new ArrayList<>();


    // 申请资源 破坏 占有且等待（hold and wait）
    synchronized boolean apply(Account fromAccount, Account toAccount) {
        while (als.contains(fromAccount) || als.contains(toAccount)) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        }
        als.add(fromAccount);
        als.add(toAccount);
        return true;
    }

    // 释放资源
    synchronized void free(Account fromAccount, Account toAccount) {
        als.remove(fromAccount);
        als.remove(toAccount);
        notifyAll();
    }
}
