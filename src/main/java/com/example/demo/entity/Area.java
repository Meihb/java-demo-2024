package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

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

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    private LocalDateTime lastEditTime;

    public Area(int areaId, String areaName, int priority) {
        this.areaId = areaId;
        this.areaName = areaName;
        this.priority = priority;
    }

    public Area() {

    }
}
