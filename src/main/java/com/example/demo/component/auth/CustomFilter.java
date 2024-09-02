package com.example.demo.component.auth;

import com.example.demo.interceptor.UserAuthInterceptor;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Slf4j
public class CustomFilter implements Filter {

    public final static String SERVLET_REQUEST_URI = UserAuthInterceptor.SERVLET_REQUEST_URI;


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ContentCachingResponseWrapper responseWrapper =
                new ContentCachingResponseWrapper((HttpServletResponse) servletResponse);
        try {
            filterChain.doFilter(servletRequest, responseWrapper);
        } finally {
            responseWrapper.copyBodyToResponse();
        }
    }
}
