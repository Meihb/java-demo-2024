package com.example.demo.dto.area;

import com.example.demo.interfaces.area.UpdateGroup;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.ZonedDateTime;


@Data
public class AreaDto {
    @NotNull(groups = {UpdateGroup.class}, message = "areaId is required")
    @Null(message = "areaId must be null")
    private Integer areaId;

    @Size(min = 1, max = 200, message = "areaName must be between 1 and 200 characters")
    private String areaName;

    @Min(1)
    @Max(10)
    private Integer priority;
    private ZonedDateTime startTime;
    private ZonedDateTime endTime;
}


