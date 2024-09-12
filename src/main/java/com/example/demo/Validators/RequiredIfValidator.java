package com.example.demo.Validators;

import com.example.demo.annotation.validations.RequiredIf;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;

public class RequiredIfValidator implements ConstraintValidator<RequiredIf, Object> {

    private String field;
    private String requiredField;
    private String fieldValue;

    @Override
    public void initialize(RequiredIf constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.requiredField = constraintAnnotation.requiredField();
        this.fieldValue = constraintAnnotation.fieldValue();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        try {
            // 通过反射获取控制字段的值
            Field controlField = object.getClass().getDeclaredField(field);
            controlField.setAccessible(true);
            Object controlFieldValue = controlField.get(object);

            // 通过反射获取目标字段的值
            Field requiredFieldObj = object.getClass().getDeclaredField(requiredField);
            requiredFieldObj.setAccessible(true);
            Object requiredFieldValue = requiredFieldObj.get(object);

            // 如果控制字段等于指定的值，并且目标字段为空，验证失败
            if (fieldValue.equals(String.valueOf(controlFieldValue)) && (requiredFieldValue == null || requiredFieldValue.toString().isEmpty())) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                        .addPropertyNode(requiredField)
                        .addConstraintViolation();
                return false;
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
