package com.scor.rr.domain.dto;

import com.scor.rr.domain.ConvertFunction;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ELTLossBetaConvertFunction {

    private double loss;
    private double stdDevI;
    private double stdDevC;
    private double stdDevUSq;
    private double exposureValue;
    private double minLayerAtt;
    ConvertFunction convertFunction;

    public ELTLossBetaConvertFunction(double loss, double stdDevI, double stdDevC, double stdDevUSq, double exposureValue, double minLayerAtt) {
        this.loss = loss;
        this.stdDevI = stdDevI;
        this.stdDevC = stdDevC;
        this.stdDevUSq = stdDevUSq;
        this.exposureValue = exposureValue;
        this.minLayerAtt = minLayerAtt;
        convertFunction = null;
    }

    public double getLossOverQuantile(Double quantile){
        return convertFunction.getLoss(quantile);
    }
}
