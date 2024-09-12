package com.example.demo.dto.area;

import com.example.demo.interfaces.area.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.time.ZonedDateTime;


@Data
public class AreaDto {
    @NotNull(groups = {UpdateGroup.class}, message = "areaId is required")
    @Null(groups = {CreateGroup.class}, message = "areaId must be null")
    private Integer areaId;

    @Size(min = 1, max = 200, message = "areaName must be between 1 and 200 characters")
    private String areaName;

    @Min(1)
    @Max(100)
    @Range(min = 1, max = 100)
    private Integer priority;
    private ZonedDateTime startTime;
    private ZonedDateTime endTime;
}


