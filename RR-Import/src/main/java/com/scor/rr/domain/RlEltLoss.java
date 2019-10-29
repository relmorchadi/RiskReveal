package com.scor.rr.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RlEltLoss {
    private int analysisId;
    private String finPerspCode;
    private Long eventId;
    private double rate;
    private double loss;
    private double stdDevI;
    private double stdDevC;
    private double stdDevUSq;
    private double exposureValue;

    public RlEltLoss(RlEltLoss loss){
        this.analysisId=loss.getAnalysisId();
        this.finPerspCode=loss.getFinPerspCode();
        this.eventId=loss.getEventId();
        this.rate=loss.getRate();
        this.loss=loss.getLoss();
        this.stdDevI=loss.getStdDevI();
        this.stdDevC=loss.getStdDevC();
        this.stdDevUSq=loss.getStdDevUSq();
        this.exposureValue=loss.getExposureValue();
    }

    public RlEltLoss(Long eventId,Double rate,Double loss,Double stdDevI,Double stdDevC,Double exposureValue)
    {
        super();
        this.eventId=eventId;
        this.rate=rate;
        this.loss=loss;
        this.stdDevC=stdDevC;
        this.stdDevI=stdDevI; // stdDevU
        this.stdDevUSq=stdDevC+stdDevI;
        this.exposureValue=exposureValue;
    }

}
