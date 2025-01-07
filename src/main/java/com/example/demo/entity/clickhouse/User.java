package com.example.demo.entity.clickhouse;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String createdAt; // ClickHouse 的日期时间字段
}
