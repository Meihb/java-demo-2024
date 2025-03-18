package com.example.demo.framework.api;

import lombok.Getter;
import lombok.Setter;

/**
 * 错误码及响应信息结构
 *
 *
 * @author hdf
 */
@Getter
@Setter
public class CodeMsg {

    private Integer code;
    private String msg;

    public CodeMsg(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
