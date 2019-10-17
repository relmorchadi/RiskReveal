package com.scor.rr.domain.entities.meta;

import lombok.Data;

import javax.persistence.*;

/**
 * The persistent class for the SourceSummaryStatistic database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "SourceSummaryStatistic")
@Data
public class SourceSummaryStatistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SourceSummaryStatisticId")
    private Long sourceSummaryStatisticId;
    @Column(name = "PurePremium")
    private Double purePremium;
    @Column(name = "StdDev")
    private Double stdDev;
    @Column(name = "Cov")
    private Double cov;

    public SourceSummaryStatistic() {
    }

    public SourceSummaryStatistic(Double purePremium, Double stdDev, Double cov) {
        this.purePremium = purePremium;
        this.stdDev = stdDev;
        this.cov = cov;
    }
}
