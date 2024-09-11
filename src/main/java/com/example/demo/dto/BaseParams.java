package com.example.demo.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class BaseParams {

    protected String languageCode;

    @Min(value = 1, message = "page must be greater than 1")
    protected Integer page = 1;

    @Min(value = 1, message = "pageSize must be greater than 1")
    @Max(value = 100, message = "pageSize must be less than 100")
    protected Integer pageSize = 10;


}
