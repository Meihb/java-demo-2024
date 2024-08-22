package com.example.demo.component.auth;

import jakarta.servlet.*;

import java.io.IOException;

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

        System.out.println("before customFilter");
        filterChain.doFilter(servletRequest, servletResponse);
        System.out.println("after loginPage");
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
