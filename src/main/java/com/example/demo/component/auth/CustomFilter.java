package com.example.demo.component.auth;

import com.example.demo.entity.log.HttpRequestLog;
import com.example.demo.entity.log.HttpResponseLog;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.net.InetAddress;
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

        ContentCachingResponseWrapper responseWrapper =
                new ContentCachingResponseWrapper((HttpServletResponse) servletResponse);

        HttpRequestLog httpRequestLog = new HttpRequestLog();
        httpRequestLog.setHeaders(Collections.list(httpRequest.getHeaderNames())
                .stream()
                .collect(Collectors.toMap(
                        headerName -> headerName,
                        httpRequest::getHeader
                )));
        httpRequestLog.setParameters(httpRequest.getParameterMap());
        httpRequestLog.setUri(httpRequest.getRequestURI());
        httpRequestLog.setHost(httpRequest.getRemoteHost());
        httpRequestLog.setScheme(httpRequest.getScheme());
        httpRequestLog.setMethod(httpRequest.getMethod());
        httpRequestLog.setHeaders(httpRequestLog.getHeaders());
        httpRequestLog.setRemoteAddr(InetAddress.getByName(httpRequest.getRemoteAddr()));

        log.info(objectMapper.writeValueAsString(httpRequestLog));
        httpRequestLog = null;

        HttpResponseLog httpResponseLog = new HttpResponseLog();

        try {
            filterChain.doFilter(servletRequest, responseWrapper);

        } catch (Throwable throwable) {
            // todo 邮件发送
            log.error("error in customFilter", throwable);
            throw throwable;
        } finally {
            // 模拟 afterCompletion - 在整个请求处理完成后执行的逻辑

            // 获取响应内容
            String responseContent = new String(responseWrapper.getContentAsByteArray());
            httpResponseLog.setResponseBody(responseContent);
            httpResponseLog.setDuration(System.currentTimeMillis() - startTime);
            httpResponseLog.setHttpStatus(responseWrapper.getStatus());
            log.info(objectMapper.writeValueAsString(httpResponseLog));
            // 将内容写回原始响应
            responseWrapper.copyBodyToResponse();
            // 清除追踪id
            MDC.remove(TRACE_ID);
        }
    }

    //    @Override
    public void doFilter3(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("here in filter");
        // 包装HttpServletRequest，把输入流缓存下来
        ContentCachingRequestWrapper wrappedRequest =
                new ContentCachingRequestWrapper((HttpServletRequest) servletRequest);
        ContentCachingResponseWrapper wrappedResponse =
                new ContentCachingResponseWrapper((HttpServletResponse) servletResponse);
        // 包装HttpServletResponse，把输出流缓存下来
        filterChain.doFilter(wrappedRequest, wrappedResponse);

        // 读取请求内容
        byte[] requestBody = wrappedRequest.getContentAsByteArray();
        String requestContent = new String(requestBody, wrappedRequest.getCharacterEncoding());

        // 读取响应内容
        byte[] responseBody = wrappedResponse.getContentAsByteArray();
        String responseContent = new String(responseBody, wrappedResponse.getCharacterEncoding());

        log.info("http request:{}", new String(wrappedRequest.getContentAsByteArray()));
        log.info("http response:{}", new String(wrappedResponse.getContentAsByteArray()));

        // 记录日志
        System.out.println("Request Content: " + requestContent);
        System.out.println("Response Content: " + responseContent);
        // 注意这一行代码一定要调用，不然无法返回响应体
        wrappedResponse.copyBodyToResponse();

    }
}
