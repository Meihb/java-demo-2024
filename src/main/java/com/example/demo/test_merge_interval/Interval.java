package com.example.demo.test_merge_interval;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Interval {
    private final LocalDateTime start;
    private final LocalDateTime end;

    public Interval(LocalDateTime start, LocalDateTime end) {
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start must be before end");
        }
        this.start = start;
        this.end = end;
    }

}
