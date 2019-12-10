package com.scor.rr.domain.dto;

import com.scor.rr.domain.enums.StatisticMetric;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by u004602 on 09/12/2019.
 */
public class EPMetric {
    private StatisticMetric metric;
    List<EPMetricPoint> epMetricPoints;

    public EPMetric() {
    }

    public EPMetric(StatisticMetric metric, List<EPMetricPoint> epMetricPoints) {
        this.metric = metric;
        this.epMetricPoints = epMetricPoints;
    }

    public StatisticMetric getMetric() {
        return metric;
    }

    public void setMetric(StatisticMetric metric) {
        this.metric = metric;
    }

    public List<EPMetricPoint> getEpMetricPoints() {
        if (epMetricPoints == null) {
            epMetricPoints  = new ArrayList<>();
        }
        return epMetricPoints;
    }

    public void setEpMetricPoints(List<EPMetricPoint> epMetricPoints) {
        this.epMetricPoints = epMetricPoints;
    }
}
