package com.example.demo.component.auth;

import jakarta.servlet.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class CustomFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    /**
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        // 模拟 preHandle - 在请求处理之前执行的逻辑
        System.out.println("before customFilter" + servletRequest.toString());
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Throwable throwable) {
            // 模拟 afterCompletion 异常
            log.error("error in customFilter", throwable);
            throw throwable;
        } finally {
            System.out.println("after loginPage" + servletResponse.toString());
            // 模拟 afterCompletion - 在整个请求处理完成后执行的逻辑

        }


    }

    @Override
    public void destroy() {
        Filter.super.destroy();
        System.out.println("here in destroy");
    }
}
