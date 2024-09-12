package com.example.demo.interceptor;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.demo.entity.log.HttpRequestLog;
import com.example.demo.entity.log.HttpResponseLog;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.net.InetAddress;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Component
public class UserAuthInterceptor implements HandlerInterceptor {

    public static final String TRACE_ID = "traceId";
    public static final String SERVLET_REQUEST_URI = "servletRequestURI";
    public static final String START_TIME = "startTime";
    public static final String VIEW_NAME = "view_name";
    public static final String MODEL_DATA = "model_data";
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 控制器之前
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler
    ) throws Exception {

        if (StringUtils.isEmpty(MDC.get(TRACE_ID))) {
            MDC.put(TRACE_ID, UUID.randomUUID().toString());
        }
        // 记录mvc开始时间
        request.setAttribute(START_TIME, System.currentTimeMillis());

        HttpRequestLog httpRequestLog = new HttpRequestLog();
        httpRequestLog.setHeaders(Collections.list(request.getHeaderNames())
                .stream()
                .collect(Collectors.toMap(
                        headerName -> headerName,
                        request::getHeader
                )));
        httpRequestLog.setParameters(request.getParameterMap());
        httpRequestLog.setUri(request.getRequestURI());
        httpRequestLog.setHost(request.getRemoteHost());
        httpRequestLog.setScheme(request.getScheme());
        httpRequestLog.setMethod(request.getMethod());
        httpRequestLog.setHeaders(httpRequestLog.getHeaders());
        httpRequestLog.setRemoteAddr(InetAddress.getByName(request.getRemoteAddr()));
        log.info(objectMapper.writeValueAsString(httpRequestLog));

        return true;
    }

    /**
     * 控制器之后，渲染视图之前
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     */
    @Override
    public void postHandle(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler,
            ModelAndView modelAndView) throws JsonProcessingException {


        if (modelAndView != null) {
            // 获取视图名称（可能是模板文件的路径或逻辑视图名）
            String viewName = modelAndView.getViewName();
            request.setAttribute(VIEW_NAME, viewName);

            // 获取绑定到视图的模型数据 todo 序列化报错无法解决
//            Map<String, Object> model = modelAndView.getModel();
//            request.setAttribute(MODEL_DATA, model);
            // 获取模型数据
            Map<String, Object> model = modelAndView.getModel();
            // 将模型数据转换为 JSON 字符串
//            String json = objectMapper.writeValueAsString(model);
//            model.forEach((key, value) -> System.out.println("Key: " + key + ", Value: " + value));
        }
    }

    /**
     * 渲染视图之后
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler,
            @Nullable Exception ex) throws Exception {

        if (response instanceof ContentCachingResponseWrapper responseWrapper) {
            // 获取响应内容
            HttpResponseLog httpResponseLog = new HttpResponseLog();

            if (request.getAttribute(VIEW_NAME) != null) {
                // 页面
                Map<String, Object> jsonMap = new HashMap<>();
                jsonMap.put(VIEW_NAME, request.getAttribute(VIEW_NAME));
//                jsonMap.put(MODEL_DATA, request.getAttribute(MODEL_DATA));
                httpResponseLog.setResponseBody(jsonMap);
            } else if (response.getContentType().contains("application/json")) {
                byte[] responseData = responseWrapper.getContentAsByteArray();
                String responseContent = new String(responseData, responseWrapper.getCharacterEncoding());
                httpResponseLog.setResponseBody(objectMapper.readTree(responseContent));
            }
            httpResponseLog.setDuration(System.currentTimeMillis() - (long) request.getAttribute(START_TIME));
            httpResponseLog.setHttpStatus(responseWrapper.getStatus());
            httpResponseLog.setContentType(response.getContentType());
            log.info(objectMapper.writeValueAsString(httpResponseLog));
        }

        if (ex != null) {
            log.error(ex.getMessage(), ex);
        }
        MDC.remove(TRACE_ID);
    }
}
