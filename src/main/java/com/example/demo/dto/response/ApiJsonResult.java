package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

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

    public static <T> ApiJsonResult<T> fail(String message) {
        return new ApiJsonResult<T>(ERROR_CODE, message, null);
    }

    public static <T> ApiJsonResult<T> fail(String message, T data) {
        return new ApiJsonResult<T>(ERROR_CODE, message, data);
    }

    public static <T> ApiJsonResult<T> success(String message) {
        return new ApiJsonResult<T>(SUCCESS_CODE, message, null);
    }

    public static <T> ApiJsonResult<T> success(String message, T data) {
        return new ApiJsonResult<T>(SUCCESS_CODE, message, data);
    }
}

