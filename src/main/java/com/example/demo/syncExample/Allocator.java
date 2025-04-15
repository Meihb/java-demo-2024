package com.example.demo.syncExample;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class Allocator {

    private final Set<String> lockedIds = new HashSet<>();

    // 申请资源（使用账户ID来标识资源）
    public synchronized void apply(String fromId, String toId) {
        while (lockedIds.contains(fromId) || lockedIds.contains(toId)) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("线程中断", e);
            }
        }
        lockedIds.add(fromId);
        lockedIds.add(toId);
    }

    // 释放资源
    public synchronized void free(String fromId, String toId) {
        lockedIds.remove(fromId);
        lockedIds.remove(toId);
        notifyAll();
    }
}
