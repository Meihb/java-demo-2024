package com.example.demo.component.exception;

import com.example.demo.dto.response.ApiJsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理异常，开启了 exceptionHandler之后，在 interceptor 中的 afterCompletion 将不会再能够获取到异常，因此异常日志的事情需要在这里处理
     * 此外，如果其他地方写了一种指定异常处理，将不会被此 handler 获取到，因此记录日志的功能将不会起效，需要手动记录
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ApiJsonResult<Object> handleGeneralException(Exception ex) {
        logEx(ex);
        return ApiJsonResult.fail(ex.getMessage(), ex);
    }

    public void logEx(Exception ex) {
//        Logger exceptionChanel = LoggerFactory.getLogger("exceptionChanel");
//        exceptionChanel.error(ex.getMessage(), ex);
        log.error(ex.getMessage(), ex);
    }


}
