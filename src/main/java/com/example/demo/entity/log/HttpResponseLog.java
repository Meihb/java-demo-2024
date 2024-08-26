package com.example.demo.entity.log;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class HttpResponseLog {
    public String responseBody;
    public Integer httpStatus;
    @JsonProperty("duration_ms")
    public Long duration;
}
