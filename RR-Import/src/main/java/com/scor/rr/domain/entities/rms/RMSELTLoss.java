package com.scor.rr.domain.entities.rms;

import lombok.Data;

import javax.persistence.*;

/**
 * The persistent class for the RMSELTLoss database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "RMSELTLoss")
@Data
public class RMSELTLoss {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RmsELTLossId")
    private Long rmsELTLossId;
    @Column(name = "EventId")
    private Long eventId;
    @Column(name = "Rate")
    private Double rate;
    @Column(name = "Loss")
    protected Double loss;
    @Column(name = "StdDevI")
    protected Double stdDevI;
    @Column(name = "StdDevC")
    protected Double stdDevC;
    @Column(name = "StdDevUSq")
    protected Double stdDevUSq;
    @Column(name = "ExposureValue")
    protected Double exposureValue;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RmsAnalysisELTId")
    private RmsAnalysisELT rmsAnalysisELT;

    public RMSELTLoss(Long eventId, Double rate, double loss, double stdDevI, double stdDevC, double stdDevUSq) {
        this.eventId = eventId;
        this.rate = rate;
        this.loss = loss;
        this.stdDevI = stdDevI;
        this.stdDevC = stdDevC;
        this.stdDevUSq = stdDevUSq;
    }

    public RMSELTLoss(RMSELTLoss rmsELTLoss) {
        this(rmsELTLoss.eventId, rmsELTLoss.rate, rmsELTLoss.loss, rmsELTLoss.stdDevI, rmsELTLoss.stdDevC, rmsELTLoss.exposureValue);
    }

    public RMSELTLoss() {

    }
}
