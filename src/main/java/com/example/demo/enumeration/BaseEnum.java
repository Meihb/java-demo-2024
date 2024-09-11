package com.example.demo.enumeration;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public interface BaseEnum {

    /**
     * 获取枚举选项
     */
    static <T extends Enum<T>> List<EnumOption> getOptions(Class<T> clazz) {
        List<EnumOption> list = new ArrayList<>();
        try {
            Method method = clazz.getMethod("values");
            @SuppressWarnings("unchecked")
            T[] values = (T[]) method.invoke(null, new Object[0]);
            for (T item : values) {
                Method getValue = clazz.getMethod("getValue");
                Method getText = clazz.getMethod("getText");
                Object value = getValue.invoke(item);
                String text = (String) getText.invoke(item, new Object[0]);
                EnumOption data = new EnumOption(value, text);
                //翻译处理
                list.add(data);
            }
        } catch (Exception e) {
            throw new RuntimeException("enum get options error for " + clazz.getName(), e);
        }
        return list;
    }

    @AllArgsConstructor
    @Data
    class EnumOption {
        private Object value;
        private String name;
    }

}