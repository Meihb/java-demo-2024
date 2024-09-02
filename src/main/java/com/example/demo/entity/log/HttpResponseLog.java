package com.example.demo.entity.log;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class HttpResponseLog {
    public Object responseBody;
    public Integer httpStatus;
    @JsonProperty("durationMs")
    public Long duration;
    public String contentType;
}
