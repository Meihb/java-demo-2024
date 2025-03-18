package com.example.demo.framework.api;

/**
 * 通用响应码及响应消息，子业务模块如果需要规范错误码可以继承该类
 * <p>
 * 不采用枚举的原因是因为，子业务模块都需要自己的定义响应码及响应消息，但枚举无法继承
 *
 * @author hdf
 */
public class DefaultCodeMsgBundle {

    public static final CodeMsg SUCCESS = new CodeMsg(0, "成功");
    public static final CodeMsg FAIL = new CodeMsg(-1, "%s");
    public static final CodeMsg NOT_FOUND_METHOD = new CodeMsg(404, "未找到处理器");
    public static final CodeMsg UNKNOWN = new CodeMsg(90000, "未知异常，%s");
    public static final CodeMsg PARAM_INVALID = new CodeMsg(90001, "%s");
    public static final CodeMsg PARAM_BIND_ERROR = new CodeMsg(90002, "%s");
    public static final CodeMsg UNAUTHORIZED = new CodeMsg(90003, "未认证");
    public static final CodeMsg UN_BIND_ENT_BASIC = new CodeMsg(90004, "请先填写基础信息");
    public static final CodeMsg UN_BIND_ENT = new CodeMsg(90005, "请先认证企业");

    public static final CodeMsg SAVE_ERROR = new CodeMsg(80001, "保存失败");
    public static final CodeMsg UPDATE_ERROR = new CodeMsg(80002, "更新失败");
    public static final CodeMsg DELETE_ERROR = new CodeMsg(80003, "删除失败");
    public static final CodeMsg COMMON_DATA_CHANGE_ERROR = new CodeMsg(80004, "处理失败");

}
