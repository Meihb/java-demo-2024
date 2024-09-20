package com.example.demo.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Enumeration;

@Aspect
@Component
public class ScheduledTaskLoggingAspect {

//    private static final Logger logger = LoggerFactory.getLogger(ScheduledTaskLoggingAspect.class);
    private static final Logger logger = LoggerFactory.getLogger("scheduled");
    @Around("@annotation(scheduled)")
    public Object logScheduledTask(ProceedingJoinPoint joinPoint, Scheduled scheduled) throws Throwable {
        // 获取任务调度的开始时间
        long startTime = System.currentTimeMillis();
        // 获取调度方 IP
//        String ipAddress = InetAddress.getLocalHost().getHostAddress();
        String ipAddress = this.getLocalIp();
        // 获取方法名和参数
        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();
        String className = joinPoint.getTarget().getClass().getName();
        String taskName = className + "." + methodName;

        logger.info("Scheduled Task started: Method={}, IP={}, Args={}", taskName, ipAddress, Arrays.toString(args));
        Object result = null;
        try {
            // 执行任务
            result = joinPoint.proceed();
            long duration = System.currentTimeMillis() - startTime;

            // 任务执行成功日志
            logger.info("Scheduled Task completed successfully: Method={}, IP={}, Duration={} ms, Result={}",
                    taskName, ipAddress, duration, result);
        } catch (Throwable ex) {
            long duration = System.currentTimeMillis() - startTime;
            // 任务执行失败日志
            logger.error("Scheduled Task failed: Method={}, IP={}, Duration={} ms, Error={}",
                    taskName, ipAddress, duration, ex.getMessage(), ex);
            throw ex; // 继续抛出异常
        }

        // 获取 `@Scheduled` 注解信息
//        String cron = scheduled.cron();
//        long fixedRate = scheduled.fixedRate();
//        long fixedDelay = scheduled.fixedDelay();
//        System.out.println("任务 [" + taskName + "] 配置: cron=" + cron + ", fixedRate=" + fixedRate + "ms, fixedDelay=" + fixedDelay + "ms");

        return result;
    }

    public String getLocalIp() throws SocketException {
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface networkInterface = interfaces.nextElement();
            Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
            while (addresses.hasMoreElements()) {
                InetAddress addr = addresses.nextElement();
                if (!addr.isLoopbackAddress() && addr.isSiteLocalAddress()) {
                    return addr.getHostAddress();  // 返回局域网 IP
                }
            }
        }
        return "127.0.0.1";  // 如果没有找到局域网 IP，则返回 localhost
    }
}
