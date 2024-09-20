package com.example.demo.Validators;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.demo.annotation.validations.RuleExists;
import com.example.demo.entity.Area;
import com.example.demo.service.IAreaService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class RuleExistsValidator implements ConstraintValidator<RuleExists, String> {

    private String table;
    private String column;

    @Autowired
    private IAreaService areaService;

    @Override
    public void initialize(RuleExists constraintAnnotation) {
        this.table = constraintAnnotation.table();
        this.column = constraintAnnotation.column();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // 如果值为空，通常由其他约束处理
        }
        LambdaQueryWrapper<Area> queryWrapper = new LambdaQueryWrapper<Area>();
        queryWrapper.eq(Area::getAreaName, value);
        long count = areaService.count(queryWrapper);
        // 使用 JPA 查询数据库中是否存在该值
//        Long count = (Long) entityManager.createQuery(
//                        "SELECT COUNT(*) FROM " + table + " WHERE " + column + " = :value")
//                .setParameter("value", value)
//                .getSingleResult();

        return count > 0;
    }

}
