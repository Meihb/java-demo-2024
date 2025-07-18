package com.example.demo.customvalidator;

import lombok.Data;

@Data
public class OptionJsonDTO {
    private String type;   // 类型，例如 score / text / other
    private String value;  // 选项值
}
