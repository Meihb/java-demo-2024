package com.example.demo.framework.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * web 层通用返回结果，按照约定，只出现在web层
 * <p>
 * 泛型是为了让swagger 能获取字段信息
 * <p>
 * 默认code = '0' 成功 ，code = '-1' 错误
 *
 * @author hdf
 */
@Schema
@Getter
@Setter
public class Result<T> {

    public final static Result<String> SUCCESS = new Result<String>(DefaultCodeMsgBundle.SUCCESS);

    @Schema(description = "响应码-0成功")
    private Integer code;

    @Schema(description = "响应消息")
    private String msg;

    @Schema(description = "业务字段")
    private T data;

    private Result(Integer code, String msg, Object... args) {
        this.code = code;
        this.msg = (null != args ? String.format(msg, args) : msg);
    }

    private Result(CodeMsg codeMsg, Object... args) {
        this(codeMsg.getCode(), codeMsg.getMsg(), args);
    }

    public static <T> Result<T> ok() {
        return ok(null);
    }

    public static <T> Result<T> ok(T data) {
        Result<T> result = new Result(DefaultCodeMsgBundle.SUCCESS);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> error(String msg) {
        Result<T> result = new Result<T>(DefaultCodeMsgBundle.FAIL.getCode(), msg);
        return result;
    }

    /**
     * 支持{@link String#format}格式化参数
     *
     * @param code
     * @param msg
     * @param agrs
     * @param <T>
     * @return
     */
    public static <T> Result<T> error(Integer code, String msg, Object... agrs) {
        Result<T> result = new Result<T>(code, msg, agrs);
        return result;
    }

    /**
     * 支持{@link String#format}格式化参数
     *
     * @param codeMsg
     * @param agrs    参数
     * @param <T>
     * @return
     */
    public static <T> Result<T> error(CodeMsg codeMsg, Object... agrs) {
        Result<T> result = new Result<T>(codeMsg, agrs);
        return result;
    }
}
