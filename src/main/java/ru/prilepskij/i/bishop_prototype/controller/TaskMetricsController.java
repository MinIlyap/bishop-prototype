package ru.prilepskij.i.bishop_prototype.controller;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/metrics/tasks")
public class TaskMetricsController {

    private final MeterRegistry registry;

    public TaskMetricsController(MeterRegistry registry) {
        this.registry = registry;
    }

    @GetMapping("/summary")
    public Map<String, Object> getMetricsSummary() {
        Map<String, Object> metrics = new LinkedHashMap<>();

        metrics.put("total_submitted", getCounterValue("tasks.total"));
        metrics.put("critical_executed", getCounterValue("tasks.critical"));
        metrics.put("rejected", getCounterValue("tasks.rejected"));
        metrics.put("queue_size", getGaugeValue("android.queue.size"));

        metrics.put("by_author", getMetricsByTag("tasks.by.author", "author"));

        return metrics;
    }

    private double getCounterValue(String metricName) {
        Counter counter = registry.find(metricName).counter();
        return counter != null ? counter.count() : 0.0;
    }

    private double getGaugeValue(String metricName) {
        Gauge gauge = registry.find(metricName).gauge();
        return gauge != null ? gauge.value() : 0.0;
    }

    private Map<String, Double> getMetricsByTag(String metricName, String tag) {
        Map<String, Double> result = new HashMap<>();

        registry.find(metricName).counters().forEach(counter -> {
            String tagValue = counter.getId().getTag(tag);
            if (tagValue != null) {
                result.put(tagValue, counter.count());
            }
        });

        return result;
    }
}