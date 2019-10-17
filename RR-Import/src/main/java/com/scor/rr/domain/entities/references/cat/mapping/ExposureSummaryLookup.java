package com.scor.rr.domain.entities.references.cat.mapping;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

/**
 * The persistent class for the ExposureSummaryLookup database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "ExposureSummaryLookup")
@Data
public class ExposureSummaryLookup implements Comparable<ExposureSummaryLookup> {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private String id;
    @Column(name = "ExposureViewTitle")
    private String exposureViewTitle;
    @Column(name = "ExposureViewCode")
    private String exposureViewCode;
    @Column(name = "TargetValueCode")
    private String targetValueCode;
    @Column(name = "MetricOrder")
    private Integer metricOrder;

    public ExposureSummaryLookup() {
    }

    public ExposureSummaryLookup(String id, String exposureViewTitle, String exposureViewCode, String targetValueCode,
                                 Integer metricOrder) {
        this.id = id;
        this.exposureViewTitle = exposureViewTitle;
        this.exposureViewCode = exposureViewCode;
        this.targetValueCode = targetValueCode;
        this.metricOrder = metricOrder;
    }

    @Override
    public int hashCode() {
        if (id == null)
            return super.hashCode();
        else
            return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        final ExposureSummaryLookup other = (ExposureSummaryLookup) obj;

        return Objects.equals(this.id, other.id);
    }

    @Override
    public int compareTo(ExposureSummaryLookup o) {
        if (metricOrder == null) {
            if (o.metricOrder == null)
                return targetValueCode.compareTo(o.targetValueCode);
            else
                return -1;
        } else {
            if (o.metricOrder == null)
                return 1;
        }

        return metricOrder.compareTo(o.metricOrder);
    }
}
