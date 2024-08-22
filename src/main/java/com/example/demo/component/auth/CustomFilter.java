package com.example.demo.component.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import java.io.IOException;
import java.util.Collections;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
public class CustomFilter implements Filter {


    private final static String TRACE_ID = "traceId";
    private final ObjectMapper objectMapper = new ObjectMapper();

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
        MDC.put(TRACE_ID, UUID.randomUUID().toString());

        long startTime = System.currentTimeMillis();
        // 模拟 preHandle - 在请求处理之前执行的逻辑
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

        CustomHttpServletResponseWrapper responseWrapper = new CustomHttpServletResponseWrapper((HttpServletResponse) servletResponse);

        log.info("method:{},uri:{},headers:{},parameters:{}",
                httpRequest.getMethod(),
                httpRequest.getRequestURI(),
                objectMapper.writeValueAsString(Collections.list(httpRequest.getHeaderNames())
                        .stream()
                        .collect(Collectors.toMap(
                                headerName -> headerName,
                                httpRequest::getHeader
                        ))),
                objectMapper.writeValueAsString(httpRequest.getParameterMap()));
        try {
            filterChain.doFilter(servletRequest, responseWrapper);
        } catch (Throwable throwable) {
            // 模拟 afterCompletion 异常
            log.error("error in customFilter", throwable);
            throw throwable;
        } finally {
            // 模拟 afterCompletion - 在整个请求处理完成后执行的逻辑
            // 获取响应内容
            String responseContent = responseWrapper.getContent();
            log.info("Response:{},duration:{}ms", responseWrapper.getContent(), System.currentTimeMillis() - startTime);
            // 将内容写回原始响应
            servletResponse.getWriter().write(responseContent);
            MDC.remove(TRACE_ID);
        }


    }
}
