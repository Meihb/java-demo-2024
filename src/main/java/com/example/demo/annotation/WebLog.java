package com.example.demo.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Web 日志注解
 */
//运行时使用
@Retention(RetentionPolicy.RUNTIME)
//注解用于方法
@Target({ElementType.METHOD, ElementType.TYPE})
//注解包含在JavaDoc中
@Documented
public @interface WebLog {

    String value() default "";
}

