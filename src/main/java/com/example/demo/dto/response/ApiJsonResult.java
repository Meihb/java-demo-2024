package com.example.demo.dto.response;

import com.example.demo.interceptor.UserAuthInterceptor;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.slf4j.MDC;

/**
 * 响应结果生成工具
 */
@Data
@AllArgsConstructor
public class ApiJsonResult<T> {

    private static final boolean SUCCESS_CODE = true;
    private static final boolean ERROR_CODE = false;

    private boolean success;
    private String message;
    private T data;
    private String traceId;

    public static <T> ApiJsonResult<T> fail(String message) {
        return new ApiJsonResult<T>(ERROR_CODE, message, null, MDC.get(UserAuthInterceptor.TRACE_ID));
    }

    public static <T> ApiJsonResult<T> fail(String message, T data) {
        return new ApiJsonResult<T>(ERROR_CODE, message, data, MDC.get(UserAuthInterceptor.TRACE_ID));
    }

    public static <T> ApiJsonResult<T> success(String message) {
        return new ApiJsonResult<T>(SUCCESS_CODE, message, null, MDC.get(UserAuthInterceptor.TRACE_ID));
    }

    public static <T> ApiJsonResult<T> success(String message, T data) {
        return new ApiJsonResult<T>(SUCCESS_CODE, message, data, MDC.get(UserAuthInterceptor.TRACE_ID));
    }
}

