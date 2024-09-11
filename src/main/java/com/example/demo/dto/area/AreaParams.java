package com.example.demo.dto.area;

import com.example.demo.dto.BaseParams;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class AreaParams<T> extends BaseParams {
    @Size(min = 1, max = 50)
    private Integer configKey;

    @NotNull(message = "configValue is required")
    private T configValue;
}
