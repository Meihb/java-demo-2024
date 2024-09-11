package com.example.demo.enumeration.product;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.example.demo.enumeration.BaseEnum;
import lombok.Getter;

@Getter
public enum Status implements BaseEnum {

    UNSHELVE(0, "common_unshelve"),

    SHELVE(1, "common_shelve"),
    ;

    @JsonValue
    @EnumValue
    private final Integer value;

    private final String text;

    Status(Integer value, String text) {
        this.value = value;
        this.text = text;
    }
}
