package com.example.demo.dto.area;

import com.example.demo.annotation.validations.RuleIn;
import com.example.demo.interfaces.area.CreateGroup;
import com.example.demo.interfaces.area.UpdateGroup;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.time.ZonedDateTime;


@Data
//@RequiredIf(field = "priority", requiredField = "areaName", fieldValue = "2", message = "priority is required when areaName is 'Hangzhou'")
//@ScriptAssert(lang = "groovy", script = "_this.startTime < _this.endTime", message = "startTime must be less than or equal to endTime")
public class AreaDto {
    @NotNull(groups = {UpdateGroup.class}, message = "areaId is required")
    @Null(groups = {CreateGroup.class}, message = "areaId must be null")
    private Integer areaId;

    @Size(min = 1, max = 200, message = "areaName must be between 1 and 200 characters")
    @RuleIn(values = {"1","2","3"}, message = "areaName must be in the list")
    private String areaName;

    @Min(1)
    @Max(100)
    @Range(min = 1, max = 100)
    private Integer priority;

    private ZonedDateTime startTime;
    private ZonedDateTime endTime;

}


