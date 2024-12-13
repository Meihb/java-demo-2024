package com.example.demo.jobhandler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class IoInterruptDemo {
    
    private static final ExecutorService executor = Executors.newCachedThreadPool();

    public  void jobHandler() throws InterruptedException, ExecutionException {
        // 文件I/O任务
        Future<Void> fileTask = executor.submit(() -> {
            try {
                runFileTask();
            } catch (InterruptedException e) {
                System.out.println("File I/O Task was interrupted.");
            }
            return null;
        });

        // 网络请求任务
        Future<Void> networkTask = executor.submit(() -> {
            try {
                runNetworkTask();
            } catch (InterruptedException e) {
                System.out.println("Network Task was interrupted.");
            }
            return null;
        });

        // 模拟中断任务
        Thread.sleep(3000); // 模拟等待
        fileTask.cancel(true);  // 中断文件任务
        networkTask.cancel(true); // 中断网络任务

        // 等待任务完成
        fileTask.get();
        networkTask.get();

        // 关闭线程池
        executor.shutdown();
    }

    // 文件 I/O 操作
    public static void runFileTask() throws InterruptedException {
        try (BufferedReader reader = new BufferedReader(new FileReader("large_file.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (Thread.currentThread().isInterrupted()) {
                    throw new InterruptedException("File task was interrupted");
                }
                // 模拟处理行
                System.out.println("Reading line: " + line);
                Thread.sleep(100); // 模拟延迟
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 网络请求操作
    public static void runNetworkTask() throws InterruptedException {
        HttpURLConnection connection = null;
        try {
            URL url = new URL("http://example.com");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(2000); // 设置连接超时
            connection.setReadTimeout(2000); // 设置读取超时
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            // 模拟等待和数据处理
            Thread.sleep(1000); // 模拟处理过程
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
