package com.example.demo.annotation.validations;

import com.example.demo.Validators.RuleInValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;


@Constraint(validatedBy = RuleInValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RuleIn {
    String message() default "Value is not in the list";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    // 指定允许的值列表
    String[] values();
}
