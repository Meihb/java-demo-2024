package com.example.demo.framework.web.advice;

import com.example.demo.framework.api.Result;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nullable;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


/**
 * @author hdf
 */
@ControllerAdvice
@Slf4j
public class JsonResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {

        return  (AnnotatedElementUtils.hasAnnotation(methodParameter.getContainingClass(), ResponseBody.class) ||
                methodParameter.hasMethodAnnotation(ResponseBody.class));
    }

    @SneakyThrows
    @Override
    public Object beforeBodyWrite(@Nullable Object body, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        log.info("1111 {}",serverHttpRequest.getURI());
        if (isSwaggerResponse(serverHttpRequest)) { //Ignore the Swagger Response
            return body;
        }

        Object result;
        if (body instanceof Result) {
            result = body;
        } else if (body instanceof String) {
            try {
                result = objectMapper.writeValueAsString(Result.ok(body));
            } catch (JsonProcessingException e) {
                return StringUtils.EMPTY;
            }
        } else {
            result = Result.ok(body);
        }

        if (log.isDebugEnabled()) {
            log.debug("Response Body=>{}", result);
        }
        return result;
    }

    private static boolean isSwaggerResponse(ServerHttpRequest serverHttpRequest) {
        String uri = serverHttpRequest.getURI().toString();

        return (uri.endsWith("/v3/api-docs") || uri.endsWith("/swagger-resources"));
    }
}
