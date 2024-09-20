package com.example.demo.enumeration.area;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.example.demo.enumeration.BaseEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum AreaName implements BaseEnum {
    Shanghai(1, "上海"),
    Beijing(2, "北京"),
    Guangzhou(3, "广州"),
    Shenzhen(4, "深圳"),
    Hangzhou(5, "杭州"),
    ;


    @JsonValue
    @EnumValue
    private final Integer value;

    private final String text;

    AreaName(Integer value, String text) {
        this.value = value;
        this.text = text;
    }
}
