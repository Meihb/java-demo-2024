package com.example.demo.test_merge_interval;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class IntervalController {
    private static BigDecimal getAnnualProductionSystemsAvailability(List<ItAccidentRecord> depRecords, LocalDate metricDate) {
        List<Interval> intervalList = Optional.ofNullable(depRecords)
                .orElseGet(Collections::emptyList)
                .stream()
                .filter(record -> {
                    if (record.getOccurTime() == null || record.getRecoveryTime() == null) {
                        log.warn("发生时间或修复时间为空: {}", record);
                        return false;
                    }
                    if (record.getOccurTime().isAfter(record.getRecoveryTime())) {
                        log.warn("发生时间晚于修复时间: {}", record);
                        return false;
                    }
                    // 匹配指定metricDate的相关区间
                    LocalDate occurDate = record.getOccurTime().toLocalDate();
                    LocalDate recoveryDate = record.getRecoveryTime().toLocalDate();
                    return !metricDate.isBefore(occurDate) && !metricDate.isAfter(recoveryDate);
                })
                .map(record -> {
                    LocalDate occurDate = record.getOccurTime().toLocalDate();
                    LocalDate recoveryDate = record.getRecoveryTime().toLocalDate();

                    LocalDateTime start;
                    LocalDateTime end;

                    if (occurDate.equals(recoveryDate)) {
                        // 不跨天
                        start = record.getOccurTime();
                        end = record.getRecoveryTime();
                    } else if (metricDate.equals(occurDate)) {
                        // 发现日
                        start = record.getOccurTime();
                        end = occurDate.plusDays(1).atStartOfDay();
                    } else if (metricDate.equals(recoveryDate)) {
                        // 修复日
                        start = recoveryDate.atStartOfDay();
                        end = record.getRecoveryTime();
                    } else {
                        // 中间日
                        start = metricDate.atStartOfDay();
                        end = metricDate.plusDays(1).atStartOfDay();
                    }

                    return new Interval(start, end);
                })
                .collect(Collectors.toList());

        // 合并重叠时间段
        List<Interval> mergedIntervals = mergeIntervals(intervalList);

        // 累加总秒数
        long totalSeconds = mergedIntervals.stream()
                .mapToLong(interval -> Duration.between(interval.getStart(), interval.getEnd()).getSeconds())
                .sum();

        return BigDecimal.valueOf(totalSeconds);
    }

    public static List<Interval> mergeIntervals(List<Interval> intervals) {
        if (intervals == null || intervals.isEmpty()) {
            return Collections.emptyList();
        }

        // 按开始时间排序
        intervals.sort(Comparator.comparing(Interval::getStart));

        List<Interval> merged = new ArrayList<>();
        Interval prev = intervals.get(0);

        for (int i = 1; i < intervals.size(); i++) {
            Interval curr = intervals.get(i);

            if (!curr.getStart().isAfter(prev.getEnd())) {
                // 有重叠，合并
                prev = new Interval(prev.getStart(), curr.getEnd().isAfter(prev.getEnd()) ? curr.getEnd() : prev.getEnd());
            } else {
                merged.add(prev);
                prev = curr;
            }
        }
        merged.add(prev);

        return merged;
    }


    public static void main(String[] args) {
        List<ItAccidentRecord> records = new ArrayList<>();

        // 假设 metricDate 为 2025-07-08
        LocalDate metricDate = LocalDate.of(2025, 7,9);

        // 不跨天，落在 metricDate
        records.add(new ItAccidentRecord(
                LocalDateTime.of(2025, 7, 8, 1, 0, 0),
                LocalDateTime.of(2025, 7, 8, 3, 0, 0)
        ));

        // 跨天，发现日是 metricDate
        records.add(new ItAccidentRecord(
                LocalDateTime.of(2025, 7, 8, 22, 0, 0),
                LocalDateTime.of(2025, 7, 9, 2, 0, 0)
        ));

        // 跨天，metricDate 是中间日
        records.add(new ItAccidentRecord(
                LocalDateTime.of(2025, 7, 7, 23, 0, 0),
                LocalDateTime.of(2025, 7, 9, 1, 0, 0)
        ));

        // 跨天，修复日在 metricDate
        records.add(new ItAccidentRecord(
                LocalDateTime.of(2025, 7, 7, 20, 0, 0),
                LocalDateTime.of(2025, 7, 8, 2, 0, 0)
        ));

        // 不相关的，不会纳入计算
        records.add(new ItAccidentRecord(
                LocalDateTime.of(2025, 7, 5, 1, 0, 0),
                LocalDateTime.of(2025, 7, 5, 2, 0, 0)
        ));

        BigDecimal totalSeconds = getAnnualProductionSystemsAvailability(records, metricDate);
        System.out.println("总影响秒数: " + totalSeconds);
    }

}
