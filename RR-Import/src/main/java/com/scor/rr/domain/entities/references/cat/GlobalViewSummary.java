package com.scor.rr.domain.entities.references.cat;

import com.scor.rr.domain.entities.references.cat.mapping.ExposureSummaryLookup;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Data
public class GlobalViewSummary implements Comparable {

    private String summaryTitle;
    private List<String> summaryMetrics;//rows
    private List<ExposureSummaryLookup> summaryLookups;//rows
    private Map<String, ExposureViewSummaryData> summaryMetricValues;//columns
    private Integer orderNb;

    public GlobalViewSummary(){}

    public GlobalViewSummary(String summaryTitle, List<String> summaryMetrics, List<ExposureSummaryLookup> summaryLookups, Map<String, ExposureViewSummaryData> summaryMetricValues, Integer orderNb) {
        this.summaryTitle = summaryTitle;
        this.summaryMetrics = summaryMetrics;
        this.summaryLookups = summaryLookups;
        this.summaryMetricValues = summaryMetricValues;
        this.orderNb = orderNb;
    }


    @Override
    public int hashCode() {
        return Objects.hash(summaryTitle);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final GlobalViewSummary other = (GlobalViewSummary) obj;
        return Objects.equals(this.summaryTitle, other.summaryTitle);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GlobalViewSummary{");
        sb.append("summaryTitle='").append(summaryTitle).append('\'');
        sb.append(", summaryMetrics=").append(summaryMetrics);
        sb.append(", summaryMetricValues=").append(summaryMetricValues);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int compareTo(Object o) {
        if (o != null && o instanceof GlobalViewSummary) {
            GlobalViewSummary g = (GlobalViewSummary) o;
            return Integer.compare(orderNb, g.orderNb);
        }
        return 1;
    }
}