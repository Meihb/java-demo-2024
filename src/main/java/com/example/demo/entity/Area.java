package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author meihaibo
 * @since 2024-08-08
 */
@Getter
@Setter
@TableName("area")
public class Area implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "area_id", type = IdType.AUTO)
    private Integer areaId;

    private String areaName;

    private Integer priority;

    private LocalDateTime startTime;
    private ZonedDateTime endTime;

//    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    private LocalDateTime lastEditTime;

    public Area(int areaId, String areaName, int priority, LocalDateTime startTime) {
        this.areaId = areaId;
        this.areaName = areaName;
        this.priority = priority;
        this.startTime = startTime;
    }

    public Area() {

    }
}
