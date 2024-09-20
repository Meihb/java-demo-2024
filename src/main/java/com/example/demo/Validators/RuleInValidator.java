package com.example.demo.Validators;

import com.example.demo.annotation.validations.RuleIn;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class RuleInValidator implements ConstraintValidator<RuleIn, String> {
    private Set<String> allowedValues;

    @Override
    public void initialize(RuleIn constraintAnnotation) {
        // 初始化允许的值列表
        allowedValues = new HashSet<>(Arrays.asList(constraintAnnotation.values()));
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // 验证值是否在允许的值列表中
        return value == null || allowedValues.contains(value);
    }
}
