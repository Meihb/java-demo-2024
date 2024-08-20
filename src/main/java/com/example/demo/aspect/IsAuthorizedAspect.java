package com.example.demo.aspect;

import com.example.demo.annotation.IsAuthorized;
import com.example.demo.util.HttpUtil;
import com.example.demo.util.MailUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 执行顺序
 *
 * @Around begin...
 * @Before begin...
 * @Before end...
 * @AfterReturning begin...
 * @AfterReturning end...
 * @After begin...
 * @After end...
 * @Around end...
 */
@Component
@Aspect
@Slf4j
public class IsAuthorizedAspect {

    @Autowired
    private MailUtil mailUtil;

//    @Pointcut("within(com.example.demo.controller..*)")
    private void pointCut() {
    }

    /**
     * 前置通知
     */
    @Before("pointCut()")
    public void doBefore(JoinPoint joinPoint) throws IOException {
//        log.info("@Before begin... ");
//        log.info("@Before end... ");
    }

    /**
     * 环绕通知
     */
    @Around("pointCut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        log.info("@Around begin... ");
        // 记录请求内容
        HttpServletRequest request = HttpUtil.getRequest();
        log.info("URL : " + request.getRequestURL().toString());
        log.info("HTTP_METHOD : " + request.getMethod());
        log.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        log.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
        // 请求IP
        log.info("IP : {}", request.getRemoteAddr());// 打印描述信息
        boolean needAdmin = true;

        //判断类上是否有@IsAuthorized注解
        IsAuthorized classAnnotation = joinPoint.getTarget().getClass().getAnnotation(IsAuthorized.class);
        if (classAnnotation == null) {
            //获取当前方法
            MethodSignature msig = (MethodSignature) joinPoint.getSignature();
            Method targetMethod = joinPoint.getTarget().getClass().getDeclaredMethod(msig.getName(), msig.getMethod().getParameterTypes());

            //获取方法上的@IsAuthorized注解
            IsAuthorized methodAnnotation = targetMethod.getAnnotation(IsAuthorized.class);
            // 如果类上面没有注解，则获取接口上此方法的注解
            if (methodAnnotation == null) {
                needAdmin = false;
            }
        }

        Object ret = null;
        if (needAdmin) {
            if ("admin".equals(HttpUtil.getRoleFromCookie())) {
                ret = joinPoint.proceed();//执行到这里开始走进来的方法体（必须声明）
            } else {
                ret = "NO PERMISSION ACCESS!";
                log.error((String) ret);
            }
        } else {
            ret = joinPoint.proceed();//执行到这里开始走进来的方法体（必须声明）
        }

        log.info("Output Parameter : {}", ret);
        // 执行时间
        log.info("Execution Time : {} ms", System.currentTimeMillis() - startTime);
        // 打印出参

        log.info("@Around end... ");

        return ret;
    }


    /**
     * 后置通知
     */
    @After("pointCut()")
    public void doAfter(JoinPoint joinPoint) {
//        log.info("@After begin... ");
//        log.info("@After end... ");
    }

    /**
     * 后置通知，返回
     */
    @AfterReturning(returning = "ret", pointcut = "pointCut()")
    public void doAfterReturning(Object ret) {
//        log.info("@AfterReturning begin... ");
//        log.info("@AfterReturning RET=" + ret);
//        log.info("@AfterReturning end... ");
    }

    /**
     * 后置通知，抛出异常时
     */
    @AfterThrowing(throwing = "e", pointcut = "pointCut()")
    public void doAfterThrowing(Throwable e) {
        log.info("@AfterThrowing begin... ");

        log.error(e.getMessage());
//        Mail mail = new Mail();
//        mail.setRecipient("herbert.mei@heavengifts.com");
//        mail.setSubject("异常报警");
//        mail.setContent(String.format("%s\n%s", e.getMessage(), "请注意处理"));
//        mailUtil.sendSimpleMail(mail);
        log.info("@AfterThrowing end... ");
        System.out.println("AfterThrowing");
    }
}