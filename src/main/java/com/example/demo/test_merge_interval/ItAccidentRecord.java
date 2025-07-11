package com.example.demo.test_merge_interval;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 指标数据(Metric)实体类
 */

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ItAccidentRecord {
    private LocalDateTime occurTime;
    private LocalDateTime recoveryTime;

    public ItAccidentRecord(LocalDateTime occurTime, LocalDateTime recoveryTime){
        this.occurTime = occurTime;
        this.recoveryTime = recoveryTime;
    }
}

