package com.example.demo.practices;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class InfiniteSequence implements Iterator<Integer> {
    private int current = 0;

    @Override
    public boolean hasNext() {
        return true; // 无限序列
    }

    @Override
    public Integer next() {
        return ++current;
    }
}

@Slf4j
public class IteratorMemoryComparison {
    public static void main(String[] args) throws InterruptedException {
        // 测试 List 的内存使用
        Runtime runtime = Runtime.getRuntime();
        int listSize = 100000000;

        log.info("=== 测试 List<Integer> ===");
//        System.gc(); // 主动触发 GC，清理其他干扰
//        long beforeListMemory = runtime.totalMemory() - runtime.freeMemory();
//        log.info("beforeListMemory {}", beforeListMemory);
        printMemoryUsage("开始前内存使用量", runtime);
        List<Integer> list = createList(listSize); // 创建 0-9 的 List
//        long afterListMemory = runtime.totalMemory() - runtime.freeMemory();
//        log.info("afterListMemory {}", afterListMemory);
        printMemoryUsage("创建后内存使用量", runtime);

        log.info("=== 测试 InfiniteSequence ===");
//        System.gc(); // 主动触发 GC，清理其他干扰
//        Thread.sleep(1000); // 等待 GC

        printMemoryUsage("开始前内存使用量", runtime);
        InfiniteSequence sequence = new InfiniteSequence();
        for (int i = 0; i < listSize; i++) {
            sequence.next(); // 模拟调用 10 次
//            printMemoryUsage("遍历中内存使用量", runtime);

        }
        long afterSequenceMemory = runtime.totalMemory() - runtime.freeMemory();
//        log.info("InfiniteSequence 内存使用量: {} KB%n",afterSequenceMemory);
        printMemoryUsage("InfiniteSequence 内存使用量", runtime);

        // 加入延时，确保 JVisualVM 可以连接并进行监控
//        Thread.sleep(5000000); // 延时 5 秒

    }

    // 创建 List
    private static List<Integer> createList(int size) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(i);
        }
        return list;
    }

    // 辅助方法：打印内存使用情况
    private static void printMemoryUsage(String label, Runtime runtime) {
        long totalMemory = runtime.totalMemory();   // JVM 当前分配的内存
        long freeMemory = runtime.freeMemory();     // JVM 可用内存
        long usedMemory = totalMemory - freeMemory; // 已使用的内存
        log.info("{}: 已使用内存 = {} B, 可用内存 = {} B, 总分配内存 = {} B",
                label, usedMemory, freeMemory , totalMemory );
    }
}
