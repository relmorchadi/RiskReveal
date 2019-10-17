package com.scor.rr.domain.entities.meta;

import lombok.Data;

import javax.persistence.*;

/**
 * The persistent class for the SourceEpCurve database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "SourceEpCurve")
@Data
public class SourceEpCurve {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SourceEpCurveId")
    private Long sourceEpCurveId;
    @Column(name = "Loss")
    private Double loss;
    @Column(name = "ExceedanceProbability")
    private Double exceedanceProbability;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SourceEpHeaderId")
    private SourceEpHeader sourceEpHeader;

    public SourceEpCurve() {
    }

    public SourceEpCurve(Double loss, Double exceedanceProbability) {
        this.loss = loss;
        this.exceedanceProbability = exceedanceProbability;
    }
}
