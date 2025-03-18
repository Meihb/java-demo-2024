package com.example.demo.framework.web.advice;


import com.example.demo.framework.api.CodeMsg;
import com.example.demo.framework.api.DefaultCodeMsgBundle;
import com.example.demo.framework.api.Result;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.rmi.ServerRuntimeException;
import java.util.List;
import java.util.Set;

/**
 * 异常advice
 *
 * @author hdf
 */
@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    /**
     * 使用{@link org.springframework.util.Assert} 来验证参数
     *
     * @param e
     * @return
     */
    @ExceptionHandler({IllegalArgumentException.class})
    public Result<String> illegalArgumentException(IllegalArgumentException e) {
        return Result.error(DefaultCodeMsgBundle.PARAM_INVALID, e.getMessage());
    }


    @ExceptionHandler(ServerRuntimeException.class)
    public Result<String> serverRuntimeException(ServerRuntimeException ex) {
        return handleException(ex, DefaultCodeMsgBundle.PARAM_INVALID, ex.getMessage());
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public Result<String> handleConstraintViolationException(ConstraintViolationException ex) {

        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

        int i = 0;
        String[] messages = new String[constraintViolations.size()];
        String field = "";
        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
            if (constraintViolation.getPropertyPath() != null && constraintViolation.getPropertyPath() instanceof PathImpl) {
                field = ((PathImpl) constraintViolation.getPropertyPath()).getLeafNode().getName();
            }
            messages[i] = StringUtils.join(field, " ", constraintViolation.getMessage());
            i++;
        }

        return handleException(ex, DefaultCodeMsgBundle.PARAM_INVALID, StringUtils.join(messages, ","));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        return getStringServiceStatus(bindingResult, ex);
    }

    @ExceptionHandler(BindException.class)
    public Result<String> handleMethodArgumentNotValidException(BindException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        return getStringServiceStatus(bindingResult, ex);
    }

    private Result<String> getStringServiceStatus(BindingResult bindingResult, Exception ex) {
        List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
        int i = 0;
        String[] messages = new String[fieldErrorList.size()];
        for (FieldError error : fieldErrorList) {
            messages[i] = StringUtils.join(error.getField() ,' ', error.getDefaultMessage());
            i++;
        }
        return handleException(ex, DefaultCodeMsgBundle.PARAM_BIND_ERROR, StringUtils.join(messages, ","));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public Result<String> noHandlerFoundException(NoHandlerFoundException ex) {
        return handleException(ex, DefaultCodeMsgBundle.NOT_FOUND_METHOD, ex.getMessage());
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public Result<String> maxUploadSizeExceededExceptionHandler(MaxUploadSizeExceededException ex) {
        String message;
        if (ex.getRootCause() != null) {
            message = ex.getRootCause().getMessage();
        } else {
            message = ex.getMessage();
        }
        return handleException(ex, DefaultCodeMsgBundle.FAIL, message);
    }

    @ExceptionHandler(RuntimeException.class)
    public Result<String> runtimeExceptionHandler(RuntimeException ex) {
        return handleException(ex, DefaultCodeMsgBundle.FAIL, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result<String> exceptionHandler(Exception ex) {
        return handleException(ex, DefaultCodeMsgBundle.FAIL, ex.getMessage());
    }

    private Result<String> handleException(Exception ex, CodeMsg codeMsg, String msg) {
        log.error(ex.getMessage(), ex);
        return Result.error(codeMsg, msg);
    }
}
