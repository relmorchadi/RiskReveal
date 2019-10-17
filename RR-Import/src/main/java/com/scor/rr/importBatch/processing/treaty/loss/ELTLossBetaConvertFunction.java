package com.scor.rr.importBatch.processing.treaty.loss;

import com.scor.rr.importBatch.processing.domain.loss.ConvertFunction;

/**
 * Created by u004602 on 24/10/2017.
 */
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

    public double getLoss() {
        return loss;
    }

    public void setLoss(double loss) {
        this.loss = loss;
    }

    public double getExposureValue() {
        return exposureValue;
    }

    public void setExposureValue(double exposureValue) {
        this.exposureValue = exposureValue;
    }

    public double getMinLayerAtt() {
        return minLayerAtt;
    }

    public void setMinLayerAtt(double minLayerAtt) {
        this.minLayerAtt = minLayerAtt;
    }

    public double getStdDevI() {
        return stdDevI;
    }

    public void setStdDevI(double stdDevI) {
        this.stdDevI = stdDevI;
    }

    public double getStdDevC() {
        return stdDevC;
    }

    public void setStdDevC(double stdDevC) {
        this.stdDevC = stdDevC;
    }

    public double getStdDevUSq() {
        return stdDevUSq;
    }

    public void setStdDevUSq(double stdDevUSq) {
        this.stdDevUSq = stdDevUSq;
    }

    public ConvertFunction getConvertFunction() {
        return convertFunction;
    }

    public void setConvertFunction(ConvertFunction convertFunction) {
        this.convertFunction = convertFunction;
    }

    public double getLossOverQuantile(Double quantile){
        return convertFunction.getLoss(quantile);
    }
}
