package com.scor.rr.domain.entities.stat;

import lombok.Data;

import javax.persistence.*;

/**
 * The persistent class for the RRSummaryStatistic database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "RRSummaryStatistic")
@Data
public class RRSummaryStatistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RRSummaryStatisticId")
    private Long rrSummaryStatisticId;
    @Column(name = "PurePremium")
    private Double purePremium;
    @Column(name = "StandardDeviation")
    private Double standardDeviation;
    @Column(name = "Cov")
    private Double cov;

    public RRSummaryStatistic() {
    }

    public RRSummaryStatistic(RRSummaryStatistic summaryStatistic) {
        this(summaryStatistic.getPurePremium(), summaryStatistic.getStandardDeviation(), summaryStatistic.getCov());
    }

    public RRSummaryStatistic(Double purePremium, Double standardDeviation, Double cov) {
        // @formatter:off
        this.purePremium = purePremium;
        this.standardDeviation = standardDeviation;
        this.cov = cov;
        // @formatter:on
    }
}
