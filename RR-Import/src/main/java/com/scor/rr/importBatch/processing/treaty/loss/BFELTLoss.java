package com.scor.rr.importBatch.processing.treaty.loss;

import com.scor.rr.domain.entities.rms.RMSELTLoss;
import com.scor.rr.importBatch.processing.domain.loss.ConvertFunction;
import com.scor.rr.importBatch.processing.domain.loss.ConvertFunctionFactory;

/**
 * Created by u004119 on 03/05/2016.
 */
public class BFELTLoss extends RMSELTLoss {

    Double minQuantile;
    Double minLayerAtt;
    ConvertFunction convertFunction;

    public BFELTLoss(RMSELTLoss rmsELTLoss) {
        super(rmsELTLoss);
    }

    public BFELTLoss(RMSELTLoss rmsELTLoss, Double minQuantile, Double minLayerAtt, ConvertFunction convertFunction) {
        super(rmsELTLoss);
        this.minQuantile = minQuantile;
        this.minLayerAtt = minLayerAtt;
        this.convertFunction = convertFunction;
    }

    public Double getMinQuantile() {
        return minQuantile;
    }

    public void setMinQuantile(Double minQuantile) {
        this.minQuantile = minQuantile;
    }

    public Double getMinLayerAtt() {
        return minLayerAtt;
    }

    public void setMinLayerAtt(Double minLayerAtt) {
        this.minLayerAtt = minLayerAtt;
    }

    public ConvertFunction getConvertFunction() {
        return convertFunction;
    }

    public void setConvertFunction(ConvertFunction convertFunction) {
        this.convertFunction = convertFunction;
    }

    public void buildFunction(ConvertFunctionFactory functionFactory){
        convertFunction = functionFactory.buildFunction(loss, stdDevC, stdDevI, stdDevUSq, exposureValue, minLayerAtt);
    }

    public Double getLossOverMinQuantile(Double quantile){
        return convertFunction.getLossOverMinQuantile(quantile);
    }

    public Double getLossOverQuantile(Double quantile){
        return convertFunction.getLoss(quantile);
    }

}
