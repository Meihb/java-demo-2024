package com.example.demo.aspect;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class SetterAspect {
    // 定义切点，匹配 models 包下所有类的 setXXX 方法
    @Pointcut("execution(* com.example.demo.entity.**.set*(..))")
    public void setterMethods() {}

    // 使用 @Around advice 在方法执行前后进行操作
    @Around("setterMethods()")
    public Object aroundSetterMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("Around advice: Before method: " + joinPoint.getSignature().getName());
        Object result = joinPoint.proceed(); // 执行目标方法
        System.out.println("Around advice: After method: " + joinPoint.getSignature().getName());
        return result;
    }
}
