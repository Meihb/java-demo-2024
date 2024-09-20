package com.example.demo.annotation.validations;

import com.example.demo.Validators.RuleExistsValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = RuleExistsValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RuleExists {
    String message() default "Value does not exist in the database";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    // 表名
    String table();

    // 表中的列名
    String column();
}
