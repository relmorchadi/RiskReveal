package com.scor.rr.importBatch.processing.domain.loss;

/**
 * Created by U002629 on 08/06/2015.
 */
public class EventLoss {
    final Integer eventId;
    Double meanLoss;
    Double exposureValue;
    Double stdDevC;
    Double stdDevU;
    Double stdDevUSq;
    Double freq;
    Double minQuantile;
    final Double minLayerAtt;
    ConvertFunction convertFunction;

    public EventLoss(Integer eventId, Double meanLoss, Double stdDevC, Double stdDevU, Double exposureValue, Double freq, Double minLayerAtt) {
        this.eventId = eventId;
        this.meanLoss = meanLoss;
        this.exposureValue = exposureValue;
        this.stdDevC = stdDevC;
        this.stdDevU = stdDevU;
        this.stdDevUSq = stdDevU*stdDevU;
        this.freq = freq;
        this.minLayerAtt = minLayerAtt;
    }

    public Integer getEventId() {
        return eventId;
    }

    public Double getMeanLoss() {
        return meanLoss;
    }

    public Double getExposureValue() {
        return exposureValue;
    }

    public Double getStdDevC() {
        return stdDevC;
    }

    public Double getStdDevU() {
        return stdDevU;
    }

    public Double getStdDevUSq() {
        return stdDevUSq;
    }

    public Double getFreq() {
        return freq;
    }

    public Double getMinQuantile() {
        return minQuantile;
    }

    public Double getMinLayerAtt() {
        return minLayerAtt;
    }

    public void setMeanLoss(Double meanLoss) {
        this.meanLoss = meanLoss;
    }

    public void buildFunction(ConvertFunctionFactory functionFactory){
        convertFunction = functionFactory.buildFunction(meanLoss, stdDevC, stdDevU, stdDevUSq, exposureValue, minLayerAtt);
    }

    public Double getLossOverMinQuantile(Double quantile){
        return convertFunction.getLossOverMinQuantile(quantile);
    }

    public Double getLoss(Double quantile){
        return convertFunction.getLoss(quantile);
    }

    public void addLoss(Double meanLoss, Double stdDevC, Double stdDevU, Double exposureValue, Double freq){
        this.meanLoss+=meanLoss;
        this.stdDevC+=stdDevC;
        this.stdDevU+=stdDevU;
        this.stdDevUSq+=(stdDevU*stdDevU);
        this.exposureValue+=exposureValue;
        if(freq>this.freq)
            this.freq=freq;
    }

    public void addLoss(EventLoss loss){
        this.meanLoss+=loss.meanLoss;
        this.stdDevC+=loss.stdDevC;
        this.stdDevU+=loss.stdDevU;
        this.stdDevUSq+=(stdDevU*stdDevU);
        this.exposureValue+=loss.exposureValue;
        if(loss.freq>this.freq)
            this.freq=loss.freq;
    }
}
