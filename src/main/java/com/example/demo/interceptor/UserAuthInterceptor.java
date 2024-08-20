package com.example.demo.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Component
public class UserAuthInterceptor implements HandlerInterceptor {

    public static final String TRACE_ID = "traceId";

    @Override
    public boolean preHandle(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler
    ) throws Exception {
        MDC.put(TRACE_ID, UUID.randomUUID().toString());
//        MDC.put(TRACE_ID, UUID.randomUUID().toString());
        System.out.println(request);
        return true;
//        }
    }

    @Override
    public void postHandle(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler,
            ModelAndView modelAndView) {
        // 在处理请求之后执行的逻辑
    }

    @Override
    public void afterCompletion(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler,
            @Nullable Exception ex) throws Exception {
        // 在完成请求处理之后执行的逻辑
        System.out.println("after completion");

        MDC.remove(TRACE_ID);
        if (ex != null) {
            System.out.println("Request to " + request.getRequestURI() + " resulted in exception: " + ex.getMessage());
//            ex.printStackTrace();  // 打印异常堆栈信息
        }
    }
}
