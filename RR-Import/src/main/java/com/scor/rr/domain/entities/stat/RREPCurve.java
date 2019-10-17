package com.scor.rr.domain.entities.stat;

import lombok.Data;

import javax.persistence.*;

/**
 * The persistent class for the RREPCurve database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "RREPCurve")
@Data
public class RREPCurve {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RREPCurveId")
    private Long rrEPCurveId;
    @Column(name = "ExceedanceProbability")
    private Double exceedanceProbability;
    @Column(name = "LossAmount")
    private Double lossAmount;
    @Column(name = "ReturnPeriod")
    private Integer returnPeriod;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "StatisticDataId")
    private StatisticData statisticData;

    public RREPCurve() {
    }

    public RREPCurve(Double exceedanceProbability, Double lossAmount) {
        this(null, exceedanceProbability, lossAmount);
    }

    public RREPCurve(Integer returnPeriod, Double exceedanceProbability, Double lossAmount) {
        // @formatter:off
        this.returnPeriod = returnPeriod;
        this.exceedanceProbability = exceedanceProbability;
        this.lossAmount = lossAmount;
        // @formatter:on
    }

    public RREPCurve(RREPCurve eltepCurve) {
        this(eltepCurve.getReturnPeriod(), eltepCurve.getExceedanceProbability(), eltepCurve.getLossAmount());
    }
}
