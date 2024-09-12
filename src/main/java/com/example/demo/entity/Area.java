package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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
@Slf4j
@Data
@TableName("area")
@Component
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

    public void setAreaName(String areaName) {
        log.info("setAreaName: " + areaName);
        this.areaName = areaName;
    }
}
